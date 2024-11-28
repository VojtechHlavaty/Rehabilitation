package rehabilitation;

import javax.inject.Inject;
import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.PositionControlMode;
import com.kuka.roboticsAPI.sensorModel.ForceSensorData;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;
import com.kuka.task.ITaskLogger;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

public class TestCounterForce extends RoboticsAPIApplication {

    private class ForceMonitoringThread extends Thread {
        
        private volatile boolean running = false;
        private volatile boolean terminated = false;
        private LBR robot;
        private ITaskLogger logger;

        public ForceMonitoringThread(LBR robot, ITaskLogger logger) {
            this.robot = robot;
            this.logger = logger;
        }

        @Override
        public void run() {
            while (!terminated) {
                if (running) {
                    ForceSensorData forceData = robot.getExternalForceTorque(robot.getFlange());
                    double forceX = forceData.getForce().getX();
                    double forceY = forceData.getForce().getY();
                    double forceZ = forceData.getForce().getZ();

                    // Get the current joint positions
                    JointPosition jointPosition = robot.getCurrentJointPosition();
                    double[] jointAngles = jointPosition.getInternalArray();

                    // Calculate the Jacobian matrix
                    double[][] jacobian = computeJacobian(jointAngles);

                    // Calculate sqrt(det(J * J^T))
                    double manipulabilityMeasure = calculateManipulability(jacobian);

                    // Log forces and manipulability measure
                    logger.info(String.format("External Forces [N] - X: %.2f, Y: %.2f, Z: %.2f", forceX, forceY, forceZ));
                    logger.info(String.format("Manipulability Measure (sqrt(det(J * J^T))): %.4f", manipulabilityMeasure));

                    ThreadUtil.milliSleep(250);
                }   
            }
        }

        public void startMonitoring() {
            logger.info("Force monitoring started!");
            running = true;
        }
        
        public void stopMonitoring() {
            logger.info("Force monitoring stopped!");
            running = false;
        }
        
        public void terminate() {
            terminated = true;
        }

        // Calculate the Jacobian matrix based on current joint angles
        private double[][] computeJacobian(double[] theta) {
            // DH Parameters for KUKA iiwa
            double[] alpha = {-Math.PI / 2, Math.PI / 2, Math.PI / 2, -Math.PI / 2, -Math.PI / 2, Math.PI / 2, 0};
            double[] d = {0.340, 0, 0.400, 0, 0.400, 0, 0.126};
            double[] a = {0, 0, 0, 0, 0, 0, 0}; // all zero for KUKA iiwa
            
            double[][] jacobian = new double[6][7];
            double[][][] transformations = new double[7][][];
            double[][] positions = new double[8][3];

            for (int i = 0; i < 7; i++) {
                transformations[i] = computeTransformation(alpha[i], d[i], a[i], theta[i]);
                if (i == 0) {
                    positions[i + 1] = getPosition(transformations[i]);
                } else {
                    transformations[i] = multiplyMatrices(transformations[i - 1], transformations[i]);
                    positions[i + 1] = getPosition(transformations[i]);
                }
            }

            for (int i = 0; i < 7; i++) {
                double[] zAxis;
                double[] pi;
                if (i == 0) {
                    // Base frame
                    zAxis = new double[]{0, 0, 1};
                    pi = new double[]{0, 0, 0};
                } else {
                    zAxis = getZAxis(transformations[i - 1]);
                    pi = positions[i];
                }
                double[] pEndEffector = positions[7];
                // Linear velocity component
                double[] linearPart = crossProduct(zAxis, subtractVectors(pEndEffector, pi));
                jacobian[0][i] = linearPart[0];
                jacobian[1][i] = linearPart[1];
                jacobian[2][i] = linearPart[2];

                // Angular velocity component (for revolute joints)
                jacobian[3][i] = zAxis[0];
                jacobian[4][i] = zAxis[1];
                jacobian[5][i] = zAxis[2];
            }
            return jacobian;
        }

        // Calculate sqrt(det(J * J^T))
        private double calculateManipulability(double[][] jacobian) {
            double[][] jjT = new double[6][6];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    jjT[i][j] = 0;
                    for (int k = 0; k < 7; k++) {
                        jjT[i][j] += jacobian[i][k] * jacobian[j][k];
                    }
                }
            }
            double det = determinant(jjT, 6);
            return Math.sqrt(det);
        }

        // Recursive function to find determinant
        public double determinant(double[][] matrix, int n) {
            double det = 0;
            if (n == 1) {
                return matrix[0][0];
            }
            double[][] temp = new double[n][n];
            int sign = 1;
            for (int f = 0; f < n; f++) {
                getCofactor(matrix, temp, 0, f, n);
                det += sign * matrix[0][f] * determinant(temp, n - 1);
                sign = -sign;
            }
            return det;
        }

        // Helper method to get cofactor
        public void getCofactor(double[][] matrix, double[][] temp, int p, int q, int n) {
            int i = 0, j = 0;
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (row != p && col != q) {
                        temp[i][j++] = matrix[row][col];
                        if (j == n - 1) {
                            j = 0;
                            i++;
                        }
                    }
                }
            }
        }

        // Transformation matrix computation based on DH parameters
        private double[][] computeTransformation(double alpha, double d, double a, double theta) {
            return new double[][]{
                {Math.cos(theta), -Math.sin(theta) * Math.cos(alpha), Math.sin(theta) * Math.sin(alpha), a * Math.cos(theta)},
                {Math.sin(theta), Math.cos(theta) * Math.cos(alpha), -Math.cos(theta) * Math.sin(alpha), a * Math.sin(theta)},
                {0, Math.sin(alpha), Math.cos(alpha), d},
                {0, 0, 0, 1}
            };
        }

        private double[] getPosition(double[][] transformation) {
            return new double[]{transformation[0][3], transformation[1][3], transformation[2][3]};
        }

        private double[] getZAxis(double[][] transformation) {
            return new double[]{transformation[0][2], transformation[1][2], transformation[2][2]};
        }

        private double[][] multiplyMatrices(double[][] m1, double[][] m2) {
            double[][] result = new double[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    result[i][j] = 0;
                    for (int k = 0; k < 4; k++) {
                        result[i][j] += m1[i][k] * m2[k][j];
                    }
                }
            }
            return result;
        }

        private double[] subtractVectors(double[] v1, double[] v2) {
            return new double[]{v1[0] - v2[0], v1[1] - v2[1], v1[2] - v2[2]};
        }

        private double[] crossProduct(double[] v1, double[] v2) {
            return new double[]{
                v1[1] * v2[2] - v1[2] * v2[1],
                v1[2] * v2[0] - v1[0] * v2[2],
                v1[0] * v2[1] - v1[1] * v2[0]
            };
        }
    }
    
    private final int stiffnessX = 100;
    private final int stiffnessY = 100;
    private final int stiffnessZ = 100;
    private final int stiffnessROT = 200;
    private final double impedanceForce = 0.0;

    public final JointPosition homePos = new JointPosition(
        0, Math.toRadians(20), 0, Math.toRadians(-100), 0, Math.toRadians(-30), 0);

    @Inject
    private LBR robot;   

    @Inject
    private ITaskLogger logger;

    @Override
    public void initialize() {
        logger.warn("Robot will move to the home position!");
        robot.move(ptp(homePos).setJointVelocityRel(0.5));
    }
    
    public int getPermission() {
        return getApplicationUI().displayModalDialog(
            ApplicationDialogType.QUESTION, "Start movement?", "Start", "Cancel");
    }
    
    public CartesianImpedanceControlMode getImpedanceMode() {
        CartesianImpedanceControlMode impedanceControlMode = new CartesianImpedanceControlMode();
        impedanceControlMode.parametrize(CartDOF.X).setStiffness(stiffnessX);
        impedanceControlMode.parametrize(CartDOF.Y).setStiffness(stiffnessY);
        impedanceControlMode.parametrize(CartDOF.Z).setStiffness(stiffnessZ);
        impedanceControlMode.parametrize(CartDOF.ROT).setStiffness(stiffnessROT);
        impedanceControlMode.parametrize(CartDOF.Z).setAdditionalControlForce(impedanceForce);
        
        return impedanceControlMode;
    }
    
    @Override
    public void run() {
        try {
            if (getPermission() == 1) return;
            ForceMonitoringThread forceMonitor = new ForceMonitoringThread(robot, logger);
            PositionControlMode positionControlMode = new PositionControlMode();
            CartesianImpedanceControlMode impedanceControlMode = getImpedanceMode();
            
            forceMonitor.start();
            forceMonitor.startMonitoring();
            
            Frame currentFrame = robot.getCurrentCartesianPosition(robot.getFlange());
            Frame targetFrame = currentFrame.copyWithRedundancy();
            targetFrame.setZ(currentFrame.getZ() - 100);
            
            robot.move(ptp(targetFrame).setJointVelocityRel(0.01).setMode(impedanceControlMode));
            
            forceMonitor.stopMonitoring();
            forceMonitor.terminate();
            forceMonitor.join();
        } catch (Exception e) {
            logger.error("An error occurred: " + e.toString());
        }
    }
}
