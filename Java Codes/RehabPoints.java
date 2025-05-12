package application;

import javax.inject.Inject;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.applicationModel.IApplicationData;
import com.kuka.roboticsAPI.motionModel.Spline;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.PositionControlMode;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;
import com.kuka.task.ITaskLogger;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.circ;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

public class RehabPoints extends RoboticsAPIApplication {

    private final int stiffnessX = 400;
    private final int stiffnessY = 400;
    private final int stiffnessZ = 400;
    private final int stiffnessROT = 200;
    private final double impedanceForce = 100.0;

    public final JointPosition homePos = new JointPosition(
        0, Math.toRadians(20), 0, Math.toRadians(-100), 0, Math.toRadians(-30), 0);

    @Inject
    private LBR robot;

    @Inject
    private ITaskLogger logger;

    @Inject
    private IApplicationData data;

    @Override
    public void initialize() {
        logger.warn("Robot will move to the home position!");
        robot.move(ptp(homePos).setJointVelocityRel(0.5));
    } 

    public int getPermission() {
        return getApplicationUI().displayModalDialog(
            ApplicationDialogType.QUESTION, "Start movement?", "Start", "Cancel");
    }

    public int movementType() {
        return getApplicationUI().displayModalDialog(
            ApplicationDialogType.QUESTION, "Select path", "Circle", "Up/Down line", "Left/Right line", "Diagonal");
    }
    
    public Spline getCircleSpline(Object startFrame, float radius)
    {
    	
    	Frame start;
    	
        if (startFrame instanceof ObjectFrame) {
            start = new Frame((ObjectFrame) startFrame);
        } else if (startFrame instanceof Frame) {
            start = new Frame((Frame) startFrame);
        } else {
            throw new IllegalArgumentException("startFrame must be of type Frame or ObjectFrame.");
        }
    	
    	Frame P1 = new Frame(start);
    	P1.setX(P1.getX() - radius);
    	P1.setY(P1.getY() - radius);
    	Frame P2 = new Frame(start);
    	P2.setX(P2.getX() - 2 * radius);
    	Frame P3 = new Frame(P1);
    	P3.setY(P3.getY() + 2 * radius);
    	
    	Spline circle = new Spline(
    			circ(P1, P2),
    			circ(P3, start)
    			);
    	
    	return circle;
    }
    
    public CartesianImpedanceControlMode getImpedanceMode()
    {
    	CartesianImpedanceControlMode impedanceControlMode = new CartesianImpedanceControlMode();
        impedanceControlMode.parametrize(CartDOF.X).setStiffness(stiffnessX);
        impedanceControlMode.parametrize(CartDOF.Y).setStiffness(stiffnessY);
        impedanceControlMode.parametrize(CartDOF.Z).setStiffness(stiffnessZ);
        impedanceControlMode.parametrize(CartDOF.ROT).setStiffness(stiffnessROT);
        impedanceControlMode.parametrize(CartDOF.Z).setAdditionalControlForce(impedanceForce);
        
        return impedanceControlMode;
    }
    
    public void doMovement(int type, PositionControlMode pCM, CartesianImpedanceControlMode iCM)
    {
        switch(type)
        {
        case 0:
        	robot.move(ptp(data.getFrame("/RehabPoints/CircleStart")).setMode(pCM).setJointVelocityRel(0.5));
        	robot.move(getCircleSpline(data.getFrame("/RehabPoints/CircleStart"), 200).setMode(iCM).setJointVelocityRel(0.25));
        	break;
        case 1:
        	robot.move(ptp(data.getFrame("/RehabPoints/Down")).setMode(pCM).setJointVelocityRel(0.5));
        	robot.move(ptp(data.getFrame("/RehabPoints/Up")).setMode(iCM).setJointVelocityRel(0.25));
        	robot.move(ptp(data.getFrame("/RehabPoints/Down")).setMode(iCM).setJointVelocityRel(0.25));
        	break;
        case 2:
        	robot.move(ptp(data.getFrame("/RehabPoints/Left")).setMode(pCM).setJointVelocityRel(0.5));
        	robot.move(ptp(data.getFrame("/RehabPoints/Right")).setMode(iCM).setJointVelocityRel(0.25));
        	robot.move(ptp(data.getFrame("/RehabPoints/Left")).setMode(iCM).setJointVelocityRel(0.25));
        	break;
        case 3:
        	robot.move(ptp(data.getFrame("/RehabPoints/BotLeft")).setMode(pCM).setJointVelocityRel(0.5));
        	robot.move(ptp(data.getFrame("/RehabPoints/TopRight")).setMode(iCM).setJointVelocityRel(0.25));
        	break;
        default:
        	break;
        }
    }
    
    @Override
    public void run() {
        try {
            if (getPermission() == 1) return;
            
            PositionControlMode positionControlMode = new PositionControlMode();
            CartesianImpedanceControlMode impedanceControlMode = getImpedanceMode();
            
            doMovement(movementType(), positionControlMode, impedanceControlMode);

            robot.move(ptp(homePos).setMode(positionControlMode).setJointVelocityRel(0.25));
            
            
        } catch (Exception e) {
            logger.error("An error occurred: " + e.toString());
        }
    }
}
