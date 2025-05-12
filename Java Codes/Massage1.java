package application;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.LBRE1Redundancy;
import com.kuka.roboticsAPI.geometricModel.Frame;
import javax.inject.Inject;
import javax.inject.Named;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.motionModel.CartesianPTP;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Massage1 extends RoboticsAPIApplication {
	
    @Inject
    private LBR lbr;
    
    @Inject
    @Named("Tool2")
    private Tool tool;
    
    private volatile double velocity = 0.1;
    private volatile double controlForce = 0.0;
    private volatile int stopValue = 0;
    private volatile IMotionContainer current_move = null;
    private final JSONParser parser = new JSONParser();
    private volatile boolean isRunning = true;
    
    @Override
    public void initialize() {
        getLogger().info("Initializing the application ...");
        tool.attachTo(lbr.getFlange());
        CommandQueue.clear();
    }

    
    @Override
    public void run() {
    	
        double blendingRadius = 0;

        final double offset = 20;
    	
        // Define frames for each move.
        Frame posHOME = new Frame(-0.01, 734.19, 151.21, Math.toRadians(0.001), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame posvia = new Frame(592.84, -8.54, 280.02, Math.toRadians(-90.0), Math.toRadians(0.0), Math.toRadians(179.989));
        Frame posvia1 = new Frame(686.06, -8.54, 41.77, Math.toRadians(-90.0), Math.toRadians(0.0), Math.toRadians(166.989));
        Frame posvia2 = new Frame(689.43, 161.46, 56.39, Math.toRadians(-90.0), Math.toRadians(0.0), Math.toRadians(166.989));
        Frame posvia3 = new Frame(621.23, 161.46, 72.15, Math.toRadians(-90.0), Math.toRadians(0.0), Math.toRadians(170.989));
        Frame posvia4 = new Frame(640.98, 306.46, 69.02, Math.toRadians(-89.057), Math.toRadians(-5.925), Math.toRadians(170.94));
        Frame posvia5 = new Frame(640.69, -57.83, 67.16, Math.toRadians(-88.579), Math.toRadians(-8.888), Math.toRadians(170.879));
        final Frame posvia6 = new Frame(667.32, -57.83, 280.0, Math.toRadians(-90.0), Math.toRadians(0.0), Math.toRadians(179.989));
        Frame posvia7 = new Frame(495.8, -14.21, 58.07, Math.toRadians(23.682), Math.toRadians(0.348), Math.toRadians(178.036));
        Frame posvia8 = new Frame(478.65, 152.33, 51.36, Math.toRadians(23.183), Math.toRadians(1.293), Math.toRadians(-175.271));
        Frame posvia9 = new Frame(529.31, 154.14, 63.83, Math.toRadians(33.149), Math.toRadians(0.453), Math.toRadians(-175.119));
        Frame posvia10 = new Frame(522.66, 306.64, 50.52, Math.toRadians(43.495), Math.toRadians(-2.413), Math.toRadians(-172.325));
        Frame posvia11 = new Frame(524.08, -54.61, 71.04, Math.toRadians(53.426), Math.toRadians(-3.706), Math.toRadians(-172.856));
        Frame posvia12 = new Frame(574.1, 0.52, 298.15, Math.toRadians(-0.002), Math.toRadians(0.0), Math.toRadians(-179.999));
        Frame posHOMEFINAL = new Frame(-0.01, 734.19, 151.21, Math.toRadians(0.001), Math.toRadians(0.0), Math.toRadians(180.0));
    	
        Thread commandThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning && !Thread.currentThread().isInterrupted()) {
                    try {
                        String cmd = application.CommandQueue.getCommand();
                        JSONObject jsonCmd = (JSONObject) parser.parse(cmd.trim());
                        String type = (String) jsonCmd.get("type");

                        if ("UPDATE_PARAMS".equals(type)) {
                            if (jsonCmd.get("velocity") != null) {
                                velocity = ((Number) jsonCmd.get("velocity")).doubleValue();
                                getLogger().info("Updated velocity: " + velocity);
                            }
                            if (jsonCmd.get("force") != null) {
                                controlForce = ((Number) jsonCmd.get("force")).doubleValue();
                                getLogger().info("Updated force: " + controlForce);
                            }
                            if (jsonCmd.get("stop") != null) {
                                stopValue = ((Number) jsonCmd.get("stop")).intValue();
                                if (stopValue == 1) {
                                    getLogger().warn("Emergency stop called from voice control!");
                                    tool.move(ptp(posvia6).setJointVelocityRel(0.25));
                                    stopValue = 0;
                                    getApplicationControl().pause();
                                } else {
                                    getLogger().info("Emergency stop disabled by voice control, resuming.");
                                }
                            }
                        }
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        getLogger().error("Error processing parameter update: " + e.getMessage());
                    }
                }
                getLogger().info("Command thread exiting");
            }
        });
        commandThread.start();

        // Create redundancy objects
        LBRE1Redundancy posHOMERed = new LBRE1Redundancy();
        posHOMERed.setStatus(2);
        posHOMERed.setTurn(88);
        posHOMERed.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posviaRed = new LBRE1Redundancy();
        posviaRed.setStatus(2);
        posviaRed.setTurn(88);
        posviaRed.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia1Red = new LBRE1Redundancy();
        posvia1Red.setStatus(2);
        posvia1Red.setTurn(89);
        posvia1Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia2Red = new LBRE1Redundancy();
        posvia2Red.setStatus(2);
        posvia2Red.setTurn(8);
        posvia2Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia3Red = new LBRE1Redundancy();
        posvia3Red.setStatus(2);
        posvia3Red.setTurn(8);
        posvia3Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia4Red = new LBRE1Redundancy();
        posvia4Red.setStatus(2);
        posvia4Red.setTurn(24);
        posvia4Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia5Red = new LBRE1Redundancy();
        posvia5Red.setStatus(2);
        posvia5Red.setTurn(24);
        posvia5Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia6Red = new LBRE1Redundancy();
        posvia6Red.setStatus(2);
        posvia6Red.setTurn(89);
        posvia6Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia7Red = new LBRE1Redundancy();
        posvia7Red.setStatus(2);
        posvia7Red.setTurn(89);
        posvia7Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia8Red = new LBRE1Redundancy();
        posvia8Red.setStatus(2);
        posvia8Red.setTurn(89);
        posvia8Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia9Red = new LBRE1Redundancy();
        posvia9Red.setStatus(2);
        posvia9Red.setTurn(72);
        posvia9Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia10Red = new LBRE1Redundancy();
        posvia10Red.setStatus(2);
        posvia10Red.setTurn(89);
        posvia10Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia11Red = new LBRE1Redundancy();
        posvia11Red.setStatus(2);
        posvia11Red.setTurn(89);
        posvia11Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posvia12Red = new LBRE1Redundancy();
        posvia12Red.setStatus(2);
        posvia12Red.setTurn(89);
        posvia12Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posHOMEFINALRed = new LBRE1Redundancy();
        posHOMEFINALRed.setStatus(2);
        posHOMEFINALRed.setTurn(88);
        posHOMEFINALRed.setE1(Math.toRadians(70.0));

        getLogger().info("Show modal dialog and wait for user to confirm");
        int isCancel = getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, "Impedance mode active", "OK", "Cancel");
        if (isCancel == 1)
        {
            return;
        }
        CartesianImpedanceControlMode impedanceControlMode = new CartesianImpedanceControlMode();
        impedanceControlMode.parametrize(CartDOF.X).setStiffness(1500);
        impedanceControlMode.parametrize(CartDOF.Y).setStiffness(1500);
        impedanceControlMode.parametrize(CartDOF.Z).setStiffness(200);
        impedanceControlMode.parametrize(CartDOF.ROT).setStiffness(300);
        //impedanceControlMode.parametrize(CartDOF.Z).setAdditionalControlForce(20);
        impedanceControlMode.parametrize(CartDOF.Z).setDamping(1);

        posHOME.setX(posHOME.getX() - offset);
        posHOME.setRedundancyInformation(lbr, posHOMEFINALRed);
        CartesianPTP ptpMove_posHOME = ptp(posHOME);
        ptpMove_posHOME.setJointVelocityRel(velocity*5).setBlendingCart(blendingRadius);
        tool.move(ptpMove_posHOME);
        

        
        isCancel = getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, "Begin massage", "OK", "Cancel");
        if (isCancel == 1)
        {
            return;
        }

        posvia.setX(posvia.getX() - offset);
        posvia.setRedundancyInformation(lbr, posHOMEFINALRed);
        CartesianPTP ptpMove_posvia = ptp(posvia);
        ptpMove_posvia.setJointVelocityRel(velocity*5).setBlendingCart(blendingRadius);
        ptpMove_posvia.setMode(impedanceControlMode);
        tool.move(ptpMove_posvia);
        

        posvia1.setX(posvia1.getX() - offset);
        posvia1.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia1).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia2.setX(posvia2.getX() - offset);
        posvia2.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia2).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia3.setX(posvia3.getX() - offset);
        posvia3.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia3).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia4.setX(posvia4.getX() - offset);
        posvia4.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia4).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia5.setX(posvia5.getX() - offset);
        posvia5.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia5).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia6.setX(posvia6.getX() - offset);
        posvia6.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia6).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia7.setX(posvia7.getX() - offset);
        posvia7.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia7).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia8.setX(posvia8.getX() - offset);
        posvia8.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia8).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia9.setX(posvia9.getX() - offset);
        posvia9.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia9).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia10.setX(posvia10.getX() - offset);
        posvia10.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia10).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia11.setX(posvia11.getX() - offset);
        posvia11.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia11).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posvia12.setX(posvia12.getX() - offset);
        posvia12.setRedundancyInformation(lbr, posHOMEFINALRed);
        tool.move(lin(posvia12).setJointVelocityRel(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posHOMEFINAL.setX(posHOMEFINAL.getX() - offset);
        posHOMEFINAL.setRedundancyInformation(lbr, posHOMEFINALRed);
        CartesianPTP ptpMove_posHOMEFINAL = ptp(posHOMEFINAL);
        ptpMove_posHOMEFINAL.setJointVelocityRel(velocity).setBlendingCart(blendingRadius);
        ptpMove_posHOMEFINAL.setMode(impedanceControlMode);
        tool.move(ptpMove_posHOMEFINAL);
        

        isRunning = false;
        commandThread.interrupt();
        try {
            commandThread.join(1000);
        } catch (InterruptedException e) {
            getLogger().warn("Interrupted while waiting for command thread to finish");
            Thread.currentThread().interrupt();
        }
        
    }
    
    // Helper method to apply the transformation.
    public Frame TfromPS(Frame frame) {
        frame.transform(Transformation.ofDeg(0, 0, 0, 0, 180, 0));
        frame.setX(-frame.getX());
        frame.setZ(-frame.getZ());
        Frame massageTeached = new Frame(getApplicationData().getFrame("/MassageTeached"));
        frame.setParent(massageTeached, false);
        frame.setParent(lbr.getRootFrame(), true);
        getLogger().info(frame.toString());
        return frame;
    }
}
