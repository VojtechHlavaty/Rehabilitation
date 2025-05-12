package application;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.LBRE1Redundancy;
import com.kuka.roboticsAPI.geometricModel.Frame;
import javax.inject.Inject;
import javax.inject.Named;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.motionModel.CartesianPTP;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;


public class Massage2 extends RoboticsAPIApplication {
    @Inject
    private LBR lbr;
    
    @Inject
    @Named("Tool2")
    private Tool tool;
    
    @Override
    public void initialize() {
        getLogger().info("Initializing the application ...");
        tool.attachTo(lbr.getFlange());
        CommandQueue.clear();
    }
    
    private volatile double velocity = 35;
    private volatile double controlForce = 0.0;
    private volatile int stopValue = 0;
    private volatile IMotionContainer current_move = null;
    private final JSONParser parser = new JSONParser();
    private volatile boolean isRunning = true;

    @Override
    public void run() {
        // Define frames for each move.
        Frame posHOME = new Frame(4.31, 730.0, 150.0, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        final Frame posbefore = new Frame(564.31, 159.97, 309.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos1 = new Frame(564.31, 319.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos2 = new Frame(504.31, 303.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos3 = new Frame(444.31, 286.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos4 = new Frame(504.31, 269.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos5 = new Frame(564.31, 253.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos6 = new Frame(504.31, 236.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos7 = new Frame(444.31, 219.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos8 = new Frame(504.31, 203.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos9 = new Frame(564.31, 186.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos10 = new Frame(504.31, 169.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos11 = new Frame(444.31, 153.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos12 = new Frame(504.31, 136.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos13 = new Frame(564.31, 119.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos14 = new Frame(504.31, 103.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos15 = new Frame(444.31, 86.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos16 = new Frame(504.31, 69.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos17 = new Frame(564.31, 53.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos18 = new Frame(504.31, 36.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos19 = new Frame(444.31, 19.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos20 = new Frame(504.31, 3.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos21 = new Frame(564.31, -13.36, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos22 = new Frame(504.31, -30.03, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos23 = new Frame(444.31, -46.69, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos24 = new Frame(504.31, -63.36, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos25 = new Frame(564.31, -80.03, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos26 = new Frame(564.31, -80.03, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos27 = new Frame(504.31, -63.36, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos28 = new Frame(444.31, -46.69, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos29 = new Frame(504.31, -30.03, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos30 = new Frame(564.31, -13.36, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos31 = new Frame(504.31, 3.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos32 = new Frame(444.31, 19.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos33 = new Frame(504.31, 36.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos34 = new Frame(564.31, 53.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos35 = new Frame(504.31, 69.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos36 = new Frame(444.31, 86.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos37 = new Frame(504.31, 103.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos38 = new Frame(564.31, 119.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos39 = new Frame(504.31, 136.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos40 = new Frame(444.31, 153.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos41 = new Frame(504.31, 169.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos42 = new Frame(564.31, 186.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos43 = new Frame(504.31, 203.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos44 = new Frame(444.31, 219.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos45 = new Frame(504.31, 236.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos46 = new Frame(564.31, 253.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos47 = new Frame(504.31, 269.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos48 = new Frame(444.31, 286.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos49 = new Frame(504.31, 303.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos50 = new Frame(564.31, 319.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos51 = new Frame(644.31, 319.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos52 = new Frame(704.31, 303.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos53 = new Frame(764.31, 286.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos54 = new Frame(704.31, 269.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos55 = new Frame(644.31, 253.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos56 = new Frame(704.31, 236.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos57 = new Frame(764.31, 219.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos58 = new Frame(704.31, 203.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos59 = new Frame(644.31, 186.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos60 = new Frame(704.31, 169.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos61 = new Frame(764.31, 153.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos62 = new Frame(704.31, 136.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos63 = new Frame(644.31, 119.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos64 = new Frame(704.31, 103.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos65 = new Frame(764.31, 86.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos66 = new Frame(704.31, 69.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos67 = new Frame(644.31, 53.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos68 = new Frame(704.31, 36.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos69 = new Frame(764.31, 19.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos70 = new Frame(704.31, 3.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos71 = new Frame(644.31, -13.36, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos72 = new Frame(704.31, -30.03, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos73 = new Frame(764.31, -46.69, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos74 = new Frame(704.31, -63.36, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos75 = new Frame(644.31, -80.03, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos76 = new Frame(644.31, -80.03, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos77 = new Frame(704.31, -63.36, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos78 = new Frame(764.31, -46.69, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos79 = new Frame(704.31, -30.03, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos80 = new Frame(644.31, -13.36, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos81 = new Frame(704.31, 3.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos82 = new Frame(764.31, 19.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos83 = new Frame(704.31, 36.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos84 = new Frame(644.31, 53.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos85 = new Frame(704.31, 69.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos86 = new Frame(764.31, 86.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos87 = new Frame(704.31, 103.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos88 = new Frame(644.31, 119.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos89 = new Frame(704.31, 136.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos90 = new Frame(764.31, 153.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos91 = new Frame(704.31, 169.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos92 = new Frame(644.31, 186.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos93 = new Frame(704.31, 203.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos94 = new Frame(764.31, 219.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos95 = new Frame(704.31, 236.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos96 = new Frame(644.31, 253.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos97 = new Frame(704.31, 269.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos98 = new Frame(764.31, 286.64, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos99 = new Frame(704.31, 303.31, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos100 = new Frame(644.31, 319.97, 84.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame posafter = new Frame(644.31, 319.97, 249.35, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame posHOMEafter = new Frame(0.0, 730.0, 150.0, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));

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
                                    tool.move(ptp(posbefore).setJointVelocityRel(0.25));
                                    
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

        LBRE1Redundancy posbeforeRed = new LBRE1Redundancy();
        posbeforeRed.setStatus(2);
        posbeforeRed.setTurn(88);
        posbeforeRed.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos1Red = new LBRE1Redundancy();
        pos1Red.setStatus(2);
        pos1Red.setTurn(89);
        pos1Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos2Red = new LBRE1Redundancy();
        pos2Red.setStatus(2);
        pos2Red.setTurn(89);
        pos2Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos3Red = new LBRE1Redundancy();
        pos3Red.setStatus(2);
        pos3Red.setTurn(89);
        pos3Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos4Red = new LBRE1Redundancy();
        pos4Red.setStatus(2);
        pos4Red.setTurn(89);
        pos4Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos5Red = new LBRE1Redundancy();
        pos5Red.setStatus(2);
        pos5Red.setTurn(89);
        pos5Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos6Red = new LBRE1Redundancy();
        pos6Red.setStatus(2);
        pos6Red.setTurn(89);
        pos6Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos7Red = new LBRE1Redundancy();
        pos7Red.setStatus(2);
        pos7Red.setTurn(89);
        pos7Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos8Red = new LBRE1Redundancy();
        pos8Red.setStatus(2);
        pos8Red.setTurn(89);
        pos8Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos9Red = new LBRE1Redundancy();
        pos9Red.setStatus(2);
        pos9Red.setTurn(89);
        pos9Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos10Red = new LBRE1Redundancy();
        pos10Red.setStatus(2);
        pos10Red.setTurn(89);
        pos10Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos11Red = new LBRE1Redundancy();
        pos11Red.setStatus(2);
        pos11Red.setTurn(89);
        pos11Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos12Red = new LBRE1Redundancy();
        pos12Red.setStatus(2);
        pos12Red.setTurn(89);
        pos12Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos13Red = new LBRE1Redundancy();
        pos13Red.setStatus(2);
        pos13Red.setTurn(89);
        pos13Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos14Red = new LBRE1Redundancy();
        pos14Red.setStatus(2);
        pos14Red.setTurn(89);
        pos14Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos15Red = new LBRE1Redundancy();
        pos15Red.setStatus(2);
        pos15Red.setTurn(89);
        pos15Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos16Red = new LBRE1Redundancy();
        pos16Red.setStatus(2);
        pos16Red.setTurn(89);
        pos16Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos17Red = new LBRE1Redundancy();
        pos17Red.setStatus(2);
        pos17Red.setTurn(89);
        pos17Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos18Red = new LBRE1Redundancy();
        pos18Red.setStatus(2);
        pos18Red.setTurn(89);
        pos18Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos19Red = new LBRE1Redundancy();
        pos19Red.setStatus(2);
        pos19Red.setTurn(89);
        pos19Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos20Red = new LBRE1Redundancy();
        pos20Red.setStatus(2);
        pos20Red.setTurn(89);
        pos20Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos21Red = new LBRE1Redundancy();
        pos21Red.setStatus(2);
        pos21Red.setTurn(89);
        pos21Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos22Red = new LBRE1Redundancy();
        pos22Red.setStatus(2);
        pos22Red.setTurn(89);
        pos22Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos23Red = new LBRE1Redundancy();
        pos23Red.setStatus(2);
        pos23Red.setTurn(89);
        pos23Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos24Red = new LBRE1Redundancy();
        pos24Red.setStatus(2);
        pos24Red.setTurn(89);
        pos24Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos25Red = new LBRE1Redundancy();
        pos25Red.setStatus(2);
        pos25Red.setTurn(89);
        pos25Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos26Red = new LBRE1Redundancy();
        pos26Red.setStatus(2);
        pos26Red.setTurn(89);
        pos26Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos27Red = new LBRE1Redundancy();
        pos27Red.setStatus(2);
        pos27Red.setTurn(89);
        pos27Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos28Red = new LBRE1Redundancy();
        pos28Red.setStatus(2);
        pos28Red.setTurn(89);
        pos28Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos29Red = new LBRE1Redundancy();
        pos29Red.setStatus(2);
        pos29Red.setTurn(89);
        pos29Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos30Red = new LBRE1Redundancy();
        pos30Red.setStatus(2);
        pos30Red.setTurn(89);
        pos30Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos31Red = new LBRE1Redundancy();
        pos31Red.setStatus(2);
        pos31Red.setTurn(89);
        pos31Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos32Red = new LBRE1Redundancy();
        pos32Red.setStatus(2);
        pos32Red.setTurn(89);
        pos32Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos33Red = new LBRE1Redundancy();
        pos33Red.setStatus(2);
        pos33Red.setTurn(89);
        pos33Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos34Red = new LBRE1Redundancy();
        pos34Red.setStatus(2);
        pos34Red.setTurn(89);
        pos34Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos35Red = new LBRE1Redundancy();
        pos35Red.setStatus(2);
        pos35Red.setTurn(89);
        pos35Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos36Red = new LBRE1Redundancy();
        pos36Red.setStatus(2);
        pos36Red.setTurn(89);
        pos36Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos37Red = new LBRE1Redundancy();
        pos37Red.setStatus(2);
        pos37Red.setTurn(89);
        pos37Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos38Red = new LBRE1Redundancy();
        pos38Red.setStatus(2);
        pos38Red.setTurn(89);
        pos38Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos39Red = new LBRE1Redundancy();
        pos39Red.setStatus(2);
        pos39Red.setTurn(89);
        pos39Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos40Red = new LBRE1Redundancy();
        pos40Red.setStatus(2);
        pos40Red.setTurn(89);
        pos40Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos41Red = new LBRE1Redundancy();
        pos41Red.setStatus(2);
        pos41Red.setTurn(89);
        pos41Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos42Red = new LBRE1Redundancy();
        pos42Red.setStatus(2);
        pos42Red.setTurn(89);
        pos42Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos43Red = new LBRE1Redundancy();
        pos43Red.setStatus(2);
        pos43Red.setTurn(89);
        pos43Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos44Red = new LBRE1Redundancy();
        pos44Red.setStatus(2);
        pos44Red.setTurn(89);
        pos44Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos45Red = new LBRE1Redundancy();
        pos45Red.setStatus(2);
        pos45Red.setTurn(89);
        pos45Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos46Red = new LBRE1Redundancy();
        pos46Red.setStatus(2);
        pos46Red.setTurn(89);
        pos46Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos47Red = new LBRE1Redundancy();
        pos47Red.setStatus(2);
        pos47Red.setTurn(89);
        pos47Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos48Red = new LBRE1Redundancy();
        pos48Red.setStatus(2);
        pos48Red.setTurn(89);
        pos48Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos49Red = new LBRE1Redundancy();
        pos49Red.setStatus(2);
        pos49Red.setTurn(89);
        pos49Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos50Red = new LBRE1Redundancy();
        pos50Red.setStatus(2);
        pos50Red.setTurn(89);
        pos50Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos51Red = new LBRE1Redundancy();
        pos51Red.setStatus(2);
        pos51Red.setTurn(89);
        pos51Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos52Red = new LBRE1Redundancy();
        pos52Red.setStatus(2);
        pos52Red.setTurn(89);
        pos52Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos53Red = new LBRE1Redundancy();
        pos53Red.setStatus(2);
        pos53Red.setTurn(89);
        pos53Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos54Red = new LBRE1Redundancy();
        pos54Red.setStatus(2);
        pos54Red.setTurn(89);
        pos54Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos55Red = new LBRE1Redundancy();
        pos55Red.setStatus(2);
        pos55Red.setTurn(89);
        pos55Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos56Red = new LBRE1Redundancy();
        pos56Red.setStatus(2);
        pos56Red.setTurn(89);
        pos56Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos57Red = new LBRE1Redundancy();
        pos57Red.setStatus(2);
        pos57Red.setTurn(89);
        pos57Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos58Red = new LBRE1Redundancy();
        pos58Red.setStatus(2);
        pos58Red.setTurn(89);
        pos58Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos59Red = new LBRE1Redundancy();
        pos59Red.setStatus(2);
        pos59Red.setTurn(89);
        pos59Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos60Red = new LBRE1Redundancy();
        pos60Red.setStatus(2);
        pos60Red.setTurn(89);
        pos60Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos61Red = new LBRE1Redundancy();
        pos61Red.setStatus(2);
        pos61Red.setTurn(89);
        pos61Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos62Red = new LBRE1Redundancy();
        pos62Red.setStatus(2);
        pos62Red.setTurn(89);
        pos62Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos63Red = new LBRE1Redundancy();
        pos63Red.setStatus(2);
        pos63Red.setTurn(89);
        pos63Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos64Red = new LBRE1Redundancy();
        pos64Red.setStatus(2);
        pos64Red.setTurn(89);
        pos64Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos65Red = new LBRE1Redundancy();
        pos65Red.setStatus(2);
        pos65Red.setTurn(89);
        pos65Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos66Red = new LBRE1Redundancy();
        pos66Red.setStatus(2);
        pos66Red.setTurn(89);
        pos66Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos67Red = new LBRE1Redundancy();
        pos67Red.setStatus(2);
        pos67Red.setTurn(89);
        pos67Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos68Red = new LBRE1Redundancy();
        pos68Red.setStatus(2);
        pos68Red.setTurn(89);
        pos68Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos69Red = new LBRE1Redundancy();
        pos69Red.setStatus(2);
        pos69Red.setTurn(89);
        pos69Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos70Red = new LBRE1Redundancy();
        pos70Red.setStatus(2);
        pos70Red.setTurn(89);
        pos70Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos71Red = new LBRE1Redundancy();
        pos71Red.setStatus(2);
        pos71Red.setTurn(89);
        pos71Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos72Red = new LBRE1Redundancy();
        pos72Red.setStatus(2);
        pos72Red.setTurn(89);
        pos72Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos73Red = new LBRE1Redundancy();
        pos73Red.setStatus(2);
        pos73Red.setTurn(89);
        pos73Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos74Red = new LBRE1Redundancy();
        pos74Red.setStatus(2);
        pos74Red.setTurn(89);
        pos74Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos75Red = new LBRE1Redundancy();
        pos75Red.setStatus(2);
        pos75Red.setTurn(89);
        pos75Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos76Red = new LBRE1Redundancy();
        pos76Red.setStatus(2);
        pos76Red.setTurn(89);
        pos76Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos77Red = new LBRE1Redundancy();
        pos77Red.setStatus(2);
        pos77Red.setTurn(89);
        pos77Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos78Red = new LBRE1Redundancy();
        pos78Red.setStatus(2);
        pos78Red.setTurn(89);
        pos78Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos79Red = new LBRE1Redundancy();
        pos79Red.setStatus(2);
        pos79Red.setTurn(89);
        pos79Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos80Red = new LBRE1Redundancy();
        pos80Red.setStatus(2);
        pos80Red.setTurn(89);
        pos80Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos81Red = new LBRE1Redundancy();
        pos81Red.setStatus(2);
        pos81Red.setTurn(89);
        pos81Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos82Red = new LBRE1Redundancy();
        pos82Red.setStatus(2);
        pos82Red.setTurn(89);
        pos82Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos83Red = new LBRE1Redundancy();
        pos83Red.setStatus(2);
        pos83Red.setTurn(89);
        pos83Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos84Red = new LBRE1Redundancy();
        pos84Red.setStatus(2);
        pos84Red.setTurn(89);
        pos84Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos85Red = new LBRE1Redundancy();
        pos85Red.setStatus(2);
        pos85Red.setTurn(89);
        pos85Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos86Red = new LBRE1Redundancy();
        pos86Red.setStatus(2);
        pos86Red.setTurn(89);
        pos86Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos87Red = new LBRE1Redundancy();
        pos87Red.setStatus(2);
        pos87Red.setTurn(89);
        pos87Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos88Red = new LBRE1Redundancy();
        pos88Red.setStatus(2);
        pos88Red.setTurn(89);
        pos88Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos89Red = new LBRE1Redundancy();
        pos89Red.setStatus(2);
        pos89Red.setTurn(89);
        pos89Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos90Red = new LBRE1Redundancy();
        pos90Red.setStatus(2);
        pos90Red.setTurn(89);
        pos90Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos91Red = new LBRE1Redundancy();
        pos91Red.setStatus(2);
        pos91Red.setTurn(89);
        pos91Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos92Red = new LBRE1Redundancy();
        pos92Red.setStatus(2);
        pos92Red.setTurn(89);
        pos92Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos93Red = new LBRE1Redundancy();
        pos93Red.setStatus(2);
        pos93Red.setTurn(89);
        pos93Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos94Red = new LBRE1Redundancy();
        pos94Red.setStatus(2);
        pos94Red.setTurn(89);
        pos94Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos95Red = new LBRE1Redundancy();
        pos95Red.setStatus(2);
        pos95Red.setTurn(89);
        pos95Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos96Red = new LBRE1Redundancy();
        pos96Red.setStatus(2);
        pos96Red.setTurn(89);
        pos96Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos97Red = new LBRE1Redundancy();
        pos97Red.setStatus(2);
        pos97Red.setTurn(89);
        pos97Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos98Red = new LBRE1Redundancy();
        pos98Red.setStatus(2);
        pos98Red.setTurn(89);
        pos98Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos99Red = new LBRE1Redundancy();
        pos99Red.setStatus(2);
        pos99Red.setTurn(89);
        pos99Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos100Red = new LBRE1Redundancy();
        pos100Red.setStatus(2);
        pos100Red.setTurn(89);
        pos100Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posafterRed = new LBRE1Redundancy();
        posafterRed.setStatus(2);
        posafterRed.setTurn(88);
        posafterRed.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posHOMEafterRed = new LBRE1Redundancy();
        posHOMEafterRed.setStatus(2);
        posHOMEafterRed.setTurn(26);
        posHOMEafterRed.setE1(Math.toRadians(70.0));

        double blendingRadius = 50;

        double offset = 10;

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
        posHOME.setRedundancyInformation(lbr, posHOMEafterRed);
        CartesianPTP ptpMove_posHOME = ptp(posHOME);
        ptpMove_posHOME.setJointVelocityRel(0.3).setBlendingCart(blendingRadius);
        tool.move(ptpMove_posHOME);
        

        
        isCancel = getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, "Begin massage", "OK", "Cancel");
        if (isCancel == 1)
        {
            return;
        }

        posbefore.setX(posbefore.getX() - offset);
        posbefore.setRedundancyInformation(lbr, posHOMEafterRed);
        CartesianPTP ptpMove_posbefore = ptp(posbefore);
        ptpMove_posbefore.setJointVelocityRel(0.3).setBlendingCart(blendingRadius);
        ptpMove_posbefore.setMode(impedanceControlMode);
        tool.move(ptpMove_posbefore);
        

        pos1.setX(pos1.getX() - offset);
        pos1.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos1).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos2.setX(pos2.getX() - offset);
        pos2.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos2).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos3.setX(pos3.getX() - offset);
        pos3.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos3).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos4.setX(pos4.getX() - offset);
        pos4.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos4).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos5.setX(pos5.getX() - offset);
        pos5.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos5).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos6.setX(pos6.getX() - offset);
        pos6.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos6).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos7.setX(pos7.getX() - offset);
        pos7.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos7).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos8.setX(pos8.getX() - offset);
        pos8.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos8).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos9.setX(pos9.getX() - offset);
        pos9.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos9).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos10.setX(pos10.getX() - offset);
        pos10.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos10).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos11.setX(pos11.getX() - offset);
        pos11.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos11).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos12.setX(pos12.getX() - offset);
        pos12.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos12).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos13.setX(pos13.getX() - offset);
        pos13.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos13).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos14.setX(pos14.getX() - offset);
        pos14.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos14).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos15.setX(pos15.getX() - offset);
        pos15.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos15).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos16.setX(pos16.getX() - offset);
        pos16.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos16).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos17.setX(pos17.getX() - offset);
        pos17.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos17).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos18.setX(pos18.getX() - offset);
        pos18.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos18).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos19.setX(pos19.getX() - offset);
        pos19.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos19).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos20.setX(pos20.getX() - offset);
        pos20.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos20).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos21.setX(pos21.getX() - offset);
        pos21.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos21).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos22.setX(pos22.getX() - offset);
        pos22.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos22).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos23.setX(pos23.getX() - offset);
        pos23.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos23).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos24.setX(pos24.getX() - offset);
        pos24.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos24).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos25.setX(pos25.getX() - offset);
        pos25.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos25).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos26.setX(pos26.getX() - offset);
        pos26.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos26).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos27.setX(pos27.getX() - offset);
        pos27.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos27).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos28.setX(pos28.getX() - offset);
        pos28.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos28).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos29.setX(pos29.getX() - offset);
        pos29.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos29).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos30.setX(pos30.getX() - offset);
        pos30.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos30).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos31.setX(pos31.getX() - offset);
        pos31.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos31).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos32.setX(pos32.getX() - offset);
        pos32.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos32).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos33.setX(pos33.getX() - offset);
        pos33.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos33).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos34.setX(pos34.getX() - offset);
        pos34.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos34).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos35.setX(pos35.getX() - offset);
        pos35.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos35).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos36.setX(pos36.getX() - offset);
        pos36.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos36).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos37.setX(pos37.getX() - offset);
        pos37.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos37).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos38.setX(pos38.getX() - offset);
        pos38.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos38).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos39.setX(pos39.getX() - offset);
        pos39.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos39).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos40.setX(pos40.getX() - offset);
        pos40.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos40).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos41.setX(pos41.getX() - offset);
        pos41.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos41).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos42.setX(pos42.getX() - offset);
        pos42.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos42).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos43.setX(pos43.getX() - offset);
        pos43.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos43).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos44.setX(pos44.getX() - offset);
        pos44.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos44).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos45.setX(pos45.getX() - offset);
        pos45.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos45).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos46.setX(pos46.getX() - offset);
        pos46.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos46).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos47.setX(pos47.getX() - offset);
        pos47.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos47).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos48.setX(pos48.getX() - offset);
        pos48.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos48).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos49.setX(pos49.getX() - offset);
        pos49.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos49).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos50.setX(pos50.getX() - offset);
        pos50.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos50).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos51.setX(pos51.getX() - offset);
        pos51.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos51).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos52.setX(pos52.getX() - offset);
        pos52.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos52).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos53.setX(pos53.getX() - offset);
        pos53.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos53).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos54.setX(pos54.getX() - offset);
        pos54.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos54).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos55.setX(pos55.getX() - offset);
        pos55.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos55).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos56.setX(pos56.getX() - offset);
        pos56.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos56).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos57.setX(pos57.getX() - offset);
        pos57.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos57).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos58.setX(pos58.getX() - offset);
        pos58.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos58).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos59.setX(pos59.getX() - offset);
        pos59.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos59).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos60.setX(pos60.getX() - offset);
        pos60.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos60).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos61.setX(pos61.getX() - offset);
        pos61.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos61).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos62.setX(pos62.getX() - offset);
        pos62.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos62).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos63.setX(pos63.getX() - offset);
        pos63.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos63).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos64.setX(pos64.getX() - offset);
        pos64.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos64).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos65.setX(pos65.getX() - offset);
        pos65.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos65).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos66.setX(pos66.getX() - offset);
        pos66.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos66).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos67.setX(pos67.getX() - offset);
        pos67.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos67).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos68.setX(pos68.getX() - offset);
        pos68.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos68).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos69.setX(pos69.getX() - offset);
        pos69.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos69).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos70.setX(pos70.getX() - offset);
        pos70.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos70).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos71.setX(pos71.getX() - offset);
        pos71.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos71).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos72.setX(pos72.getX() - offset);
        pos72.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos72).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos73.setX(pos73.getX() - offset);
        pos73.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos73).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos74.setX(pos74.getX() - offset);
        pos74.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos74).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos75.setX(pos75.getX() - offset);
        pos75.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos75).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos76.setX(pos76.getX() - offset);
        pos76.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos76).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos77.setX(pos77.getX() - offset);
        pos77.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos77).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos78.setX(pos78.getX() - offset);
        pos78.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos78).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos79.setX(pos79.getX() - offset);
        pos79.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos79).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos80.setX(pos80.getX() - offset);
        pos80.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos80).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos81.setX(pos81.getX() - offset);
        pos81.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos81).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos82.setX(pos82.getX() - offset);
        pos82.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos82).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos83.setX(pos83.getX() - offset);
        pos83.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos83).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos84.setX(pos84.getX() - offset);
        pos84.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos84).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos85.setX(pos85.getX() - offset);
        pos85.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos85).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos86.setX(pos86.getX() - offset);
        pos86.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos86).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos87.setX(pos87.getX() - offset);
        pos87.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos87).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos88.setX(pos88.getX() - offset);
        pos88.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos88).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos89.setX(pos89.getX() - offset);
        pos89.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos89).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos90.setX(pos90.getX() - offset);
        pos90.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos90).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos91.setX(pos91.getX() - offset);
        pos91.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos91).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos92.setX(pos92.getX() - offset);
        pos92.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos92).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos93.setX(pos93.getX() - offset);
        pos93.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos93).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos94.setX(pos94.getX() - offset);
        pos94.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos94).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos95.setX(pos95.getX() - offset);
        pos95.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos95).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos96.setX(pos96.getX() - offset);
        pos96.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos96).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos97.setX(pos97.getX() - offset);
        pos97.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos97).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos98.setX(pos98.getX() - offset);
        pos98.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos98).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos99.setX(pos99.getX() - offset);
        pos99.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos99).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos100.setX(pos100.getX() - offset);
        pos100.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(pos100).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posafter.setX(posafter.getX() - offset);
        posafter.setRedundancyInformation(lbr, posHOMEafterRed);
        tool.move(lin(posafter).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posHOMEafter.setX(posHOMEafter.getX() - offset);
        posHOMEafter.setRedundancyInformation(lbr, posHOMEafterRed);
        CartesianPTP ptpMove_posHOMEafter = ptp(posHOMEafter);
        ptpMove_posHOMEafter.setJointVelocityRel(0.3).setBlendingCart(blendingRadius);
        ptpMove_posHOMEafter.setMode(impedanceControlMode);
        tool.move(ptpMove_posHOMEafter);
        



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
