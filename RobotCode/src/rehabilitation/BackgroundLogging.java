package rehabilitation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.sensorModel.ForceSensorData;

public class BackgroundLogging extends RoboticsAPICyclicBackgroundTask {

    private static final int PORT = 30000;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running = true;

    @Inject
    private LBR robot;

    @Override
    public void initialize() {
        // Initialize the background task
        initializeCyclic(0, 50, TimeUnit.MILLISECONDS, CycleBehavior.BestEffort);
        getLogger().info("BackgroundLogging task initialized.");

        // Initialize the server socket
        try {
            serverSocket = new ServerSocket(PORT);
            getLogger().info("TCP Server initialized on port " + PORT);

            // Start a thread to handle new client connections
            Thread connectionHandlerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (running) {
                        try {
                            getLogger().info("Waiting for client connection...");
                            clientSocket = serverSocket.accept();
                            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            getLogger().info("Client connected: " + clientSocket.getInetAddress().toString());

                            // Handle commands for the connected client
                            Thread commandHandlerThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    handleCommands();
                                }
                            });
                            commandHandlerThread.start();
                        } catch (Exception e) {
                            if (running) { // Prevent logging errors after dispose
                                getLogger().error("Error accepting client connection: " + e.getMessage());
                            }
                        }
                    }
                }
            });

            connectionHandlerThread.setDaemon(true); // Ensure thread stops with the task
            connectionHandlerThread.start();

        } catch (Exception e) {
            getLogger().error("Error initializing server socket: " + e.getMessage());
        }
    }

    @Override
    protected void runCyclic() {
        if (out != null && !clientSocket.isClosed()) {
            try {
                JointPosition jointPosition = robot.getCurrentJointPosition();
                String jointAngles = "Joint Angles: " + jointPosition.toString();

                ForceSensorData forceData = robot.getExternalForceTorque(robot.getFlange());
                String forces = "External Forces: F=" + forceData.getForce().toString();

                out.println(jointAngles);
                out.println(forces);
                out.flush();
            } catch (Exception e) {
                getLogger().error("Error sending data to client: " + e.getMessage());
                cleanupClient();
            }
        }
    }
    private void handleCommands() {
        try {
            String command;
            while (running) {
                command = in.readLine();  // Read command

                // Ensure valid input
                if (command == null || command.trim().isEmpty()) {
                    cleanupClient();
                    break;
                }

                // Instead of executing the command, add it to the CommandQueue
                CommandQueue.addCommand(command);
            }
        } catch (Exception e) {
            cleanupClient();
        }
    }

    private void cleanupClient() {
        try {
            if (out != null) {
                out.close();
                out = null;
            }
            if (in != null) {
                in.close();
                in = null;
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                clientSocket = null;
            }
            getLogger().info("Client connection cleaned up.");
        } catch (Exception e) {
            getLogger().error("Error closing client socket: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {
        running = false;
        cleanupClient();
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (Exception e) {
            getLogger().error("Error closing server socket: " + e.getMessage());
        }
        getLogger().info("BackgroundLogging task disposed.");
    }
}
