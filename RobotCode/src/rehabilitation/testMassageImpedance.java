package rehabilitation;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.LBRE1Redundancy;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.motionModel.CartesianPTP;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.PTP;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;

        
public class testMassageImpedance extends RoboticsAPIApplication {
    private LBR lbr;
	private static final int stiffnessZ = 200;
	private static final int stiffnessY = 500;
	private static final int stiffnessX = 3500;
	private static final int stiffnessROT = 300;
	private final static String informationText=
			"This application is intended for not floor mounted robots!"+ "\n" +
			"\n" +
			"The robot moves follow a trajectory in impedance control mode" +
			"by confirming the modal dialog on the smartPAD." + "\n" + 
			"The stiffness is set to " +
			" X="+stiffnessX+" Y="+stiffnessY+" Z="+stiffnessZ+" ROT="+stiffnessROT+" in N/m.";
    
    @Override
    public void initialize() {
        lbr = getContext().getDeviceFromType(LBR.class);
        getLogger().info("Initializing the application ...");
    }

    @Override
    public void run() {
        Frame via1 = new Frame(540.24, -50.5, 200.4, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via2 = new Frame(537.28, -5.04, 212.2, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via3 = new Frame(535.19, 27.27, 219.35, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via4 = new Frame(533.2, 58.51, 224.72, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via5 = new Frame(530.33, 103.38, 232.19, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via6 = new Frame(527.52, 148.72, 233.48, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via7 = new Frame(525.67, 179.12, 231.71, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via8 = new Frame(524.27, 202.45, 229.52, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via9 = new Frame(522.61, 230.95, 221.99, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via10 = new Frame(520.68, 264.2, 213.08, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via11 = new Frame(518.91, 294.9, 204.22, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));
        Frame via12 = new Frame(516.96, 328.9, 193.2, Math.toRadians(-176.475), Math.toRadians(0.769), Math.toRadians(-172.473));

        LBRE1Redundancy redundancy_via1 = new LBRE1Redundancy();
        redundancy_via1.setStatus(2);
        redundancy_via1.setTurn(25);
        redundancy_via1.setE1(Math.toRadians(0.0));
        via1.setRedundancyInformation(lbr, redundancy_via1);

        LBRE1Redundancy redundancy_via2 = new LBRE1Redundancy();
        redundancy_via2.setStatus(2);
        redundancy_via2.setTurn(24);
        redundancy_via2.setE1(Math.toRadians(0.0));
        via2.setRedundancyInformation(lbr, redundancy_via2);

        LBRE1Redundancy redundancy_via3 = new LBRE1Redundancy();
        redundancy_via3.setStatus(2);
        redundancy_via3.setTurn(24);
        redundancy_via3.setE1(Math.toRadians(0.0));
        via3.setRedundancyInformation(lbr, redundancy_via3);

        LBRE1Redundancy redundancy_via4 = new LBRE1Redundancy();
        redundancy_via4.setStatus(2);
        redundancy_via4.setTurn(24);
        redundancy_via4.setE1(Math.toRadians(0.0));
        via4.setRedundancyInformation(lbr, redundancy_via4);

        LBRE1Redundancy redundancy_via5 = new LBRE1Redundancy();
        redundancy_via5.setStatus(2);
        redundancy_via5.setTurn(24);
        redundancy_via5.setE1(Math.toRadians(0.0));
        via5.setRedundancyInformation(lbr, redundancy_via5);

        LBRE1Redundancy redundancy_via6 = new LBRE1Redundancy();
        redundancy_via6.setStatus(2);
        redundancy_via6.setTurn(24);
        redundancy_via6.setE1(Math.toRadians(0.0));
        via6.setRedundancyInformation(lbr, redundancy_via6);

        LBRE1Redundancy redundancy_via7 = new LBRE1Redundancy();
        redundancy_via7.setStatus(2);
        redundancy_via7.setTurn(24);
        redundancy_via7.setE1(Math.toRadians(0.0));
        via7.setRedundancyInformation(lbr, redundancy_via7);

        LBRE1Redundancy redundancy_via8 = new LBRE1Redundancy();
        redundancy_via8.setStatus(2);
        redundancy_via8.setTurn(24);
        redundancy_via8.setE1(Math.toRadians(0.0));
        via8.setRedundancyInformation(lbr, redundancy_via8);

        LBRE1Redundancy redundancy_via9 = new LBRE1Redundancy();
        redundancy_via9.setStatus(2);
        redundancy_via9.setTurn(24);
        redundancy_via9.setE1(Math.toRadians(0.0));
        via9.setRedundancyInformation(lbr, redundancy_via9);

        LBRE1Redundancy redundancy_via10 = new LBRE1Redundancy();
        redundancy_via10.setStatus(2);
        redundancy_via10.setTurn(24);
        redundancy_via10.setE1(Math.toRadians(0.0));
        via10.setRedundancyInformation(lbr, redundancy_via10);

        LBRE1Redundancy redundancy_via11 = new LBRE1Redundancy();
        redundancy_via11.setStatus(2);
        redundancy_via11.setTurn(24);
        redundancy_via11.setE1(Math.toRadians(0.0));
        via11.setRedundancyInformation(lbr, redundancy_via11);

        LBRE1Redundancy redundancy_via12 = new LBRE1Redundancy();
        redundancy_via12.setStatus(2);
        redundancy_via12.setTurn(24);
        redundancy_via12.setE1(Math.toRadians(0.0));
        via12.setRedundancyInformation(lbr, redundancy_via12);
        
        getLogger().info("Show modal dialog and wait for user to confirm");
        int isCancel = getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, informationText, "OK", "Cancel");
        if (isCancel == 1)
        {
            return;
        }
        
        CartesianImpedanceControlMode impedanceControlMode = new CartesianImpedanceControlMode();
		impedanceControlMode.parametrize(CartDOF.X).setStiffness(stiffnessX);
		impedanceControlMode.parametrize(CartDOF.Y).setStiffness(stiffnessY);
		impedanceControlMode.parametrize(CartDOF.Z).setStiffness(stiffnessZ);
		impedanceControlMode.parametrize(CartDOF.ROT).setStiffness(stiffnessROT);
		
		CartesianPTP ptpvia1 = ptp(via1);
		CartesianPTP ptpvia2 = ptp(via2);
		CartesianPTP ptpvia3 = ptp(via3);
		CartesianPTP ptpvia4 = ptp(via4);
		CartesianPTP ptpvia5 = ptp(via5);
		CartesianPTP ptpvia6 = ptp(via6);
		CartesianPTP ptpvia7 = ptp(via7);
		CartesianPTP ptpvia8 = ptp(via8);
		CartesianPTP ptpvia9 = ptp(via9);
		CartesianPTP ptpvia10 = ptp(via10);
		CartesianPTP ptpvia11 = ptp(via11);
		CartesianPTP ptpvia12 = ptp(via12);
		
		double blendingRadius = 50; // Ajustez ce rayon selon vos besoins
		
		ptpvia1.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia2.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia3.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia4.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia5.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia6.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia7.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia8.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia9.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia10.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia11.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		ptpvia12.setJointVelocityRel(0.1).setMode(impedanceControlMode).setBlendingCart(blendingRadius);
		
		ptpvia1.setBlendingCart(blendingRadius);
		
		IMotionContainer trajvia = lbr.moveAsync(ptpvia1);
		trajvia.append(ptpvia2);
		trajvia.append(ptpvia3);
		trajvia.append(ptpvia4);
		trajvia.append(ptpvia5);
		trajvia.append(ptpvia6);
		trajvia.append(ptpvia7);
		trajvia.append(ptpvia8);
		trajvia.append(ptpvia9);
		trajvia.append(ptpvia10);
		trajvia.append(ptpvia11);
		trajvia.append(ptpvia12);
		getLogger().info("Show modal dialog while executing position hold");
		getApplicationUI().displayModalDialog(ApplicationDialogType.INFORMATION, "Press ok to finish the application.", "OK");
		trajvia.cancel();

    }
 }
 