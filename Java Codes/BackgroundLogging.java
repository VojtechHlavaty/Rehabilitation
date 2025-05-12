package application;

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
import com.kuka.roboticsAPI.geometricModel.Frame; 
import com.kuka.roboticsAPI.sensorModel.ForceSensorData;

public class BackgroundLogging extends RoboticsAPICyclicBackgroundTask {

    private static final int COMMAND_PORT = 30000;  // Port for commands
    private static final int MONITOR_PORT = 30001;  // Separate port for monitoring
    
    private ServerSocket commandSocket;
    private ServerSocket monitorSocket;
    private Socket commandClient;
    private Socket monitorClient;
    private PrintWriter commandOut;
    private BufferedReader commandIn;
    private PrintWriter monitorOut;
    private boolean running = true;

    @Inject
    private LBR robot;

    @Override
    public void initialize() {
        // Initialize the background task
        initializeCyclic(0, 20, TimeUnit.MILLISECONDS, CycleBehavior.BestEffort);
        getLogger().info("BackgroundLogging task initialized.");

        setupCommandSocket();
        setupMonitorSocket();
    }

    private void setupCommandSocket() {
        try {
            commandSocket = new ServerSocket(COMMAND_PORT);
            getLogger().info("Command server initialized on port " + COMMAND_PORT);

            Thread commandThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (running) {
                        try {
                            getLogger().info("Waiting for command client...");
                            commandClient = commandSocket.accept();
                            commandOut = new PrintWriter(new OutputStreamWriter(commandClient.getOutputStream()), true);
                            commandIn = new BufferedReader(new InputStreamReader(commandClient.getInputStream()));
                            getLogger().info("Command client connected: " + commandClient.getInetAddress().toString());

                            handleCommands();
                        } catch (Exception e) {
                            if (running) {
                                getLogger().error("Error with command connection: " + e.getMessage());
                            }
                        }
                    }
                }
            });
            commandThread.setDaemon(true);
            commandThread.start();
        } catch (Exception e) {
            getLogger().error("Error initializing command server: " + e.getMessage());
        }
    }

    private void setupMonitorSocket() {
        try {
            monitorSocket = new ServerSocket(MONITOR_PORT);
            getLogger().info("Monitor server initialized on port " + MONITOR_PORT);

            Thread monitorThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (running) {
                        try {
                            getLogger().info("Waiting for monitor client...");
                            monitorClient = monitorSocket.accept();
                            monitorOut = new PrintWriter(new OutputStreamWriter(monitorClient.getOutputStream()), true);
                            getLogger().info("Monitor client connected: " + monitorClient.getInetAddress().toString());
                        } catch (Exception e) {
                            if (running) {
                                getLogger().error("Error with monitor connection: " + e.getMessage());
                            }
                            cleanupMonitorClient();
                        }
                    }
                }
            });
            monitorThread.setDaemon(true);
            monitorThread.start();
        } catch (Exception e) {
            getLogger().error("Error initializing monitor server: " + e.getMessage());
        }
    }

    @Override
    public void runCyclic() {
        if (monitorOut != null && monitorClient != null && !monitorClient.isClosed()) {
            try {
                // Get Joint Positions
                JointPosition q_pos = robot.getCurrentJointPosition();
                double[] jointAngles = q_pos.getInternalArray();  // Get joint angles

                // Get Cartesian Position
                Frame cartesianPosition = robot.getCurrentCartesianPosition(robot.getFlange());
                double x = cartesianPosition.getX();
                double y = cartesianPosition.getY();
                double z = cartesianPosition.getZ();

                // Get Orientation
                double a = cartesianPosition.getAlphaRad();
                double b = cartesianPosition.getBetaRad();
                double c = cartesianPosition.getGammaRad();

                ForceSensorData force = robot.getExternalForceTorque(robot.getFlange());
                double fz = force.getForce().getZ();  // Z-direction force in Newtons

                // Format as JSON including Z-force
                String jsonPosition = String.format(
                    "{\"timestamp\": %.3f, \"joint_positions\": [%s], \"coordinates\": [%.2f, %.2f, %.2f], \"orientation\": [%.2f, %.2f, %.2f], \"force_z\": %.2f}",
                    System.currentTimeMillis() / 1000.0,
                    arrayToJson(jointAngles),
                    x, y, z,
                    Math.toDegrees(a), Math.toDegrees(b), Math.toDegrees(c),
                    fz
                );

                // Send to monitor
                monitorOut.println(jsonPosition);
                monitorOut.flush();

            } catch (Exception e) {
                getLogger().error("Error sending monitor data: " + e.getMessage());
                cleanupMonitorClient();
            }
        }
    }

    // Helper method to convert array to JSON-friendly string
    private String arrayToJson(double[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(String.format("%.2f", Math.toDegrees(array[i]))); // Convert to degrees
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private void handleCommands() {
        try {
            String command;
            while (running && (command = commandIn.readLine()) != null) {
                if (command.trim().isEmpty()) {
                    cleanupCommandClient();
                    break;
                }
                CommandQueue.addCommand(command);
            }
        } catch (Exception e) {
            cleanupCommandClient();
        }
    }

    private void cleanupCommandClient() {
        try {
            if (commandOut != null) {
                commandOut.close();
                commandOut = null;
            }
            if (commandIn != null) {
                commandIn.close();
                commandIn = null;
            }
            if (commandClient != null && !commandClient.isClosed()) {
                commandClient.close();
                commandClient = null;
            }
            getLogger().info("Command client connection cleaned up.");
        } catch (Exception e) {
            getLogger().error("Error closing command client: " + e.getMessage());
        }
    }

    private void cleanupMonitorClient() {
        try {
            if (monitorOut != null) {
                monitorOut.close();
                monitorOut = null;
            }
            if (monitorClient != null && !monitorClient.isClosed()) {
                monitorClient.close();
                monitorClient = null;
            }
            getLogger().info("Monitor client connection cleaned up.");
        } catch (Exception e) {
            getLogger().error("Error closing monitor client: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {
        running = false;
        cleanupCommandClient();
        cleanupMonitorClient();
        try {
            if (commandSocket != null && !commandSocket.isClosed()) {
                commandSocket.close();
            }
            if (monitorSocket != null && !monitorSocket.isClosed()) {
                monitorSocket.close();
            }
        } catch (Exception e) {
            getLogger().error("Error closing server sockets: " + e.getMessage());
        }
        getLogger().info("BackgroundLogging task disposed.");
    }
}