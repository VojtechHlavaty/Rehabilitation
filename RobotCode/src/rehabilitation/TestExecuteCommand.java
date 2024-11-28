package rehabilitation;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.motionModel.CartesianPTP;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TestExecuteCommand extends RoboticsAPIApplication {

    @Inject
    private LBR robot;

    private volatile boolean running = true;

    @Override
    public void initialize() {
        getLogger().info("CommandExecutorApp initialized.");
    }

    @Override
    public void run() {
        JSONParser parser = new JSONParser();

        while (running) {
            try {
                // Get the next command from the CommandQueue
                String command = CommandQueue.getCommand();

                // Parse JSON command
                JSONObject jsonCommand = (JSONObject) parser.parse(command.trim());
                String type = (String) jsonCommand.get("type");

                if ("MOVE_XYZ".equals(type)) {
                    JSONArray coordinates = (JSONArray) jsonCommand.get("coordinates");
                    moveXYZ(coordinates);
                } else if ("MOVE_JOINTS".equals(type)) {
                    JSONArray angles = (JSONArray) jsonCommand.get("angles");
                    moveJoints(angles);
                } else {
                    getLogger().warn("Unknown command received: " + type);
                }

            } catch (Exception e) {
                getLogger().error("Error executing command: " + e.getMessage());
            }
        }
    }

    private void moveXYZ(JSONArray coordinates) {
        try {
            if (coordinates.size() != 3) {
                getLogger().warn("Invalid MOVE_XYZ command format.");
                return;
            }

            double x = ((Number) coordinates.get(0)).doubleValue();
            double y = ((Number) coordinates.get(1)).doubleValue();
            double z = ((Number) coordinates.get(2)).doubleValue();

            Frame currentFrame = robot.getCurrentCartesianPosition(robot.getFlange());
            Frame targetFrame = currentFrame.copyWithRedundancy();

            targetFrame.setX(x);
            targetFrame.setY(y);
            targetFrame.setZ(z);

            robot.move(new CartesianPTP(targetFrame));
            getLogger().info("Moved robot to XYZ coordinates: (" + x + ", " + y + ", " + z + ")");

        } catch (Exception e) {
            getLogger().error("Error processing MOVE_XYZ command: " + e.getMessage());
        }
    }

    private void moveJoints(JSONArray angles) {
        try {
            if (angles.size() != 7) {
                getLogger().warn("Invalid MOVE_JOINTS command format.");
                return;
            }

            double[] jointPositions = new double[7];
            for (int i = 0; i < 7; i++) {
                jointPositions[i] = Math.toRadians(((Number) angles.get(i)).doubleValue());  // Convert degrees to radians
            }

            JointPosition targetPosition = new JointPosition(jointPositions);
            robot.move(ptp(targetPosition));
            getLogger().info("Moved robot to joint angles: " + targetPosition.toString());

        } catch (Exception e) {
            getLogger().error("Error processing MOVE_JOINTS command: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {
        running = false;
        getLogger().info("CommandExecutorApp disposed.");
    }
}
