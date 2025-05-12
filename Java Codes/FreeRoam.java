package application;

import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.JointImpedanceControlMode;
import com.kuka.task.ITaskLogger;


public class FreeRoam extends RoboticsAPIApplication {

    private static final double STIFFNESS_TRANSLATION = 0; // Stiffness for translational movement
    private static final double STIFFNESS_ROTATION = 300; // Stiffness for rotational movement
    private static final double DAMPING = 1; // Damping factor
    
    @Inject
    private LBR robot;
    
    @Inject
    private ITaskLogger logger;
    
    @Override
    public void initialize() {
        logger.info("Initializing force-based movement application with impedance control...");
    }
    
    @Override
    public void run() {
        logger.info("Starting force monitoring...");
        while (true) {
        	applyImpedanceControl();
        }
    }
    
    public void applyImpedanceControl() {
    	double[] stiffness = {3000, 3000, 3000, 3000, 0, 3000, 3000};
        JointImpedanceControlMode impedanceControlMode = new JointImpedanceControlMode(stiffness);
        impedanceControlMode.setDampingForAllJoints(0);
        //impedanceControlMode.parametrize(CartDOF.ROT).setStiffness(STIFFNESS_ROTATION);
        //impedanceControlMode.parametrize(CartDOF.ALL).setDamping(DAMPING);
        
        // Apply impedance control without actively moving
        robot.moveAsync(
        	    com.kuka.roboticsAPI.motionModel.BasicMotions.positionHold(impedanceControlMode, -1, java.util.concurrent.TimeUnit.SECONDS)
        	);

        logger.info("Impedance control activated. Robot will now follow manual guidance.");
    }



}