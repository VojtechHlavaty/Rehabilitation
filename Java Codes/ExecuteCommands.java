package application;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

import javax.inject.Inject;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Frame;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ExecuteCommands extends RoboticsAPIApplication {

    @Inject
    private LBR robot;
    private final JSONParser parser = new JSONParser();

    @Override
    public void initialize() {
        CommandQueue.clear();
        // Minimal initialization; no extra control mode or logging
    }

    @Override
    public void run() {
        // Run endlessly processing commands from the queue
        while (true) {
            try {
                String command = CommandQueue.getCommand();
                JSONObject jsonCommand = (JSONObject) parser.parse(command.trim());
                String type = (String) jsonCommand.get("type");
                if ("MOVE_XYZ".equals(type)) {
                    handleMoveXYZ(jsonCommand);
                } else if ("MOVE_Q".equals(type)) {
                    handleMoveJoints(jsonCommand);
                }
            } catch (Exception e) {
                // In a minimal example, we simply ignore errors.
            }
        }
    }

    // Process a Cartesian move; expecting 6 values: x, y, z, a, b, c
    private void handleMoveXYZ(JSONObject command) throws Exception {
        JSONArray values = (JSONArray) command.get("value");
        double x = ((Number) values.get(0)).doubleValue();
        double y = ((Number) values.get(1)).doubleValue();
        double z = ((Number) values.get(2)).doubleValue();
        Frame currentFrame = robot.getCurrentCartesianPosition(robot.getFlange());
        Frame targetFrame = new Frame(currentFrame.getX() + x, currentFrame.getY() + y, currentFrame.getZ() + z, currentFrame.getAlphaRad(), currentFrame.getBetaRad(), currentFrame.getGammaRad());
        getLogger().info("Jedeme " + targetFrame.toString());
        robot.move(ptp(targetFrame));
    }

    // Process a joint move; expecting 7 joint angle values in degrees
    private void handleMoveJoints(JSONObject command) throws Exception {
        JSONArray angles = (JSONArray) command.get("value");
        double[] jointPositions = new double[7];
        for (int i = 0; i < 7; i++) {
            // Convert degrees to radians
            jointPositions[i] = Math.toRadians(((Number) angles.get(i)).doubleValue());
        }
        JointPosition targetPosition = new JointPosition(jointPositions);
        getLogger().info("Jedeme " + targetPosition.toString());
        robot.move(ptp(targetPosition));
    }
    
    @Override
    public void dispose() {
        // Minimal cleanup; no extra threads or sockets to close
    }
}
