package rehabilitation;

import javax.inject.Inject;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.applicationModel.IApplicationData;
import com.kuka.task.ITaskLogger;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.PositionControlMode;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;
import com.kuka.roboticsAPI.sensorModel.ForceSensorData;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.linRel;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

public class testingCode extends RoboticsAPIApplication {

	private final double ptp_vel = 0.25;
	private final double lin_vel = 0.25;

    private final int stiffnessX = 300;
    private final int stiffnessY = 300;
    private final int stiffnessZ = 150;
    private final int stiffnessROT = 200;
    
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
        	while (!terminated)
        	{
                if (running) {
                    ForceSensorData forceData = robot.getExternalForceTorque(robot.getFlange());
                    double forceX = forceData.getForce().getX();
                    double forceY = forceData.getForce().getY();
                    double forceZ = forceData.getForce().getZ();
                    logger.info(String.format("External Forces [N] - X: %.2f, Y: %.2f, Z: %.2f", forceX, forceY, forceZ));

                    ThreadUtil.milliSleep(50);
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
    }
    
	@Inject
    private LBR robot;
	
	@Inject
    private ITaskLogger logger;
	
	@Inject
    private IApplicationData data;
    
    @Override
    public void initialize() {
    	logger.warn("Robot will move to starting position!");
    	robot.move(ptp(data.getFrame("/P45/P5")).setJointVelocityRel(ptp_vel)).await();
    }

    public int getPermission()
    {
    	return getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, "Start movement?", "Start", "Cancel");
    }
    
	public void doMoveCycle(CartesianImpedanceControlMode impedanceControl, PositionControlMode positionControl, ForceMonitoringThread forceMonitor)
    {
    	robot.move(ptp(data.getFrame("/P45/P6")).setJointVelocityRel(ptp_vel)).await();
    	logger.warn("Impedance mode enabled!");
    	
    	forceMonitor.startMonitoring();
    	
    	float stepDistance = 5;
    	while (Math.abs(robot.getExternalForceTorque(robot.getFlange()).getForce().getZ()) > 1)
    		robot.move(linRel(0, 0, stepDistance).setJointVelocityRel(0.1).setMode(impedanceControl));
    		ThreadUtil.milliSleep(250);
    		
    	impedanceControl.parametrize(CartDOF.Z).setAdditionalControlForce(20.0);
    	
    	robot.move(lin(data.getFrame("/P45/P7")).setJointVelocityRel(lin_vel).setMode(impedanceControl)).await();
    	forceMonitor.stopMonitoring();
    	logger.warn("Impedance mode disabled!");
    	
    	robot.move(ptp(data.getFrame("/P45/P8")).setJointVelocityRel(ptp_vel).setMode(positionControl)).await();
    	logger.info("Finished move cycle");
    }
    
    @Override
    public void run() {
    	ForceMonitoringThread forceMonitor = new ForceMonitoringThread(robot, logger);
    	try
    	{
	    	if (getPermission() == 1)
	    	{
	    		return;
	    	}
	
	        CartesianImpedanceControlMode impedanceControlMode = new CartesianImpedanceControlMode();
	    	PositionControlMode positionControlMode = new PositionControlMode();
	        forceMonitor.start();
	    
	        impedanceControlMode.parametrize(CartDOF.X).setStiffness(stiffnessX);
	        impedanceControlMode.parametrize(CartDOF.Y).setStiffness(stiffnessY);
	        impedanceControlMode.parametrize(CartDOF.Z).setStiffness(stiffnessZ);
	        impedanceControlMode.parametrize(CartDOF.ROT).setStiffness(stiffnessROT);
	
	    	doMoveCycle(impedanceControlMode, positionControlMode, forceMonitor);
	    	forceMonitor.terminate();
    	}
    	catch (Exception e)
    	{
    		forceMonitor.stopMonitoring();
    		forceMonitor.terminate();
    		logger.error(e.toString());
    	}
    }
}
