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


public class Massage3 extends RoboticsAPIApplication {
    @Inject
    private LBR lbr;
    
    @Inject
    @Named("Tool2")
    private Tool tool;
    
    @Override
    public void initialize() {
    	getLogger().info(lbr.getTemperatures().toString());
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
        Frame posHOME = new Frame(0.0, 750.0, 139.52, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        final Frame posbefore = new Frame(530.39, 281.35, 288.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos1 = new Frame(530.39, 281.35, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos2 = new Frame(524.33, 262.69, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos3 = new Frame(508.46, 251.16, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos4 = new Frame(488.84, 251.16, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos5 = new Frame(472.96, 262.69, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos6 = new Frame(466.9, 281.35, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos7 = new Frame(472.96, 300.01, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos8 = new Frame(488.84, 311.54, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos9 = new Frame(508.46, 311.54, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos10 = new Frame(524.33, 300.01, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos11 = new Frame(498.65, 249.6, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos12 = new Frame(498.65, 245.79, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos13 = new Frame(530.39, 214.05, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos14 = new Frame(524.33, 195.39, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos15 = new Frame(508.46, 183.86, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos16 = new Frame(488.84, 183.86, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos17 = new Frame(472.96, 195.39, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos18 = new Frame(466.9, 214.05, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos19 = new Frame(472.96, 232.71, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos20 = new Frame(488.84, 244.24, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos21 = new Frame(508.46, 244.24, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos22 = new Frame(524.33, 232.71, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos23 = new Frame(498.65, 182.3, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos24 = new Frame(498.65, 178.49, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos25 = new Frame(530.39, 146.75, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos26 = new Frame(524.33, 128.09, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos27 = new Frame(508.46, 116.55, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos28 = new Frame(488.84, 116.55, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos29 = new Frame(472.96, 128.09, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos30 = new Frame(466.9, 146.75, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos31 = new Frame(472.96, 165.41, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos32 = new Frame(488.84, 176.94, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos33 = new Frame(508.46, 176.94, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos34 = new Frame(524.33, 165.41, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos35 = new Frame(498.65, 115.0, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos36 = new Frame(498.65, 111.19, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos37 = new Frame(530.39, 79.44, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos38 = new Frame(524.33, 60.78, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos39 = new Frame(508.46, 49.25, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos40 = new Frame(488.84, 49.25, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos41 = new Frame(472.96, 60.78, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos42 = new Frame(466.9, 79.44, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos43 = new Frame(472.96, 98.1, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos44 = new Frame(488.84, 109.64, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos45 = new Frame(508.46, 109.64, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos46 = new Frame(524.33, 98.1, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos47 = new Frame(498.65, 47.7, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos48 = new Frame(498.65, 43.89, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos49 = new Frame(530.39, 12.14, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos50 = new Frame(524.33, -6.52, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos51 = new Frame(508.46, -18.05, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos52 = new Frame(488.84, -18.05, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos53 = new Frame(472.96, -6.52, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos54 = new Frame(466.9, 12.14, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos55 = new Frame(472.96, 30.8, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos56 = new Frame(488.84, 42.34, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos57 = new Frame(508.46, 42.34, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos58 = new Frame(524.33, 30.8, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos59 = new Frame(498.65, -19.6, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos60 = new Frame(498.65, -23.41, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos61 = new Frame(530.39, -55.16, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos62 = new Frame(524.33, -73.82, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos63 = new Frame(508.46, -85.35, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos64 = new Frame(488.84, -85.35, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos65 = new Frame(472.96, -73.82, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos66 = new Frame(466.9, -55.16, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos67 = new Frame(472.96, -36.5, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos68 = new Frame(488.84, -24.97, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos69 = new Frame(508.46, -24.97, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos70 = new Frame(524.33, -36.5, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos71 = new Frame(730.39, 281.35, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos72 = new Frame(724.33, 262.69, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos73 = new Frame(708.46, 251.16, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos74 = new Frame(688.84, 251.16, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos75 = new Frame(672.96, 262.69, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos76 = new Frame(666.9, 281.35, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos77 = new Frame(672.96, 300.01, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos78 = new Frame(688.84, 311.54, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos79 = new Frame(708.46, 311.54, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos80 = new Frame(724.33, 300.01, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos81 = new Frame(698.65, 249.6, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos82 = new Frame(698.65, 245.79, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos83 = new Frame(730.39, 214.05, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos84 = new Frame(724.33, 195.39, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos85 = new Frame(708.46, 183.86, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos86 = new Frame(688.84, 183.86, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos87 = new Frame(672.96, 195.39, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos88 = new Frame(666.9, 214.05, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos89 = new Frame(672.96, 232.71, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos90 = new Frame(688.84, 244.24, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos91 = new Frame(708.46, 244.24, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos92 = new Frame(724.33, 232.71, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos93 = new Frame(698.65, 182.3, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos94 = new Frame(698.65, 178.49, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos95 = new Frame(730.39, 146.75, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos96 = new Frame(724.33, 128.09, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos97 = new Frame(708.46, 116.55, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos98 = new Frame(688.84, 116.55, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos99 = new Frame(672.96, 128.09, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos100 = new Frame(666.9, 146.75, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos101 = new Frame(672.96, 165.41, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos102 = new Frame(688.84, 176.94, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos103 = new Frame(708.46, 176.94, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos104 = new Frame(724.33, 165.41, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos105 = new Frame(698.65, 115.0, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos106 = new Frame(698.65, 111.19, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos107 = new Frame(730.39, 79.44, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos108 = new Frame(724.33, 60.78, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos109 = new Frame(708.46, 49.25, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos110 = new Frame(688.84, 49.25, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos111 = new Frame(672.96, 60.78, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos112 = new Frame(666.9, 79.44, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos113 = new Frame(672.96, 98.1, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos114 = new Frame(688.84, 109.64, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos115 = new Frame(708.46, 109.64, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos116 = new Frame(724.33, 98.1, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos117 = new Frame(698.65, 47.7, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos118 = new Frame(698.65, 43.89, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos119 = new Frame(730.39, 12.14, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos120 = new Frame(724.33, -6.52, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos121 = new Frame(708.46, -18.05, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos122 = new Frame(688.84, -18.05, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos123 = new Frame(672.96, -6.52, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos124 = new Frame(666.9, 12.14, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos125 = new Frame(672.96, 30.8, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos126 = new Frame(688.84, 42.34, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos127 = new Frame(708.46, 42.34, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos128 = new Frame(724.33, 30.8, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos129 = new Frame(698.65, -19.6, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos130 = new Frame(698.65, -23.41, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos131 = new Frame(730.39, -55.16, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos132 = new Frame(724.33, -73.82, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos133 = new Frame(708.46, -85.35, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos134 = new Frame(688.84, -85.35, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos135 = new Frame(672.96, -73.82, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos136 = new Frame(666.9, -55.16, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos137 = new Frame(672.96, -36.5, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos138 = new Frame(688.84, -24.97, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos139 = new Frame(708.46, -24.97, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame pos140 = new Frame(724.33, -36.5, 75.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame posafter = new Frame(629.33, -36.5, 248.4, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));
        Frame posHOMEafter = new Frame(0.0, 750.0, 150.0, Math.toRadians(0.0), Math.toRadians(0.0), Math.toRadians(180.0));

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
                                	current_move.cancel();
                                    current_move = tool.moveAsync(ptp(posbefore).setJointVelocityRel(0.25));
                                    current_move.await();
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

        LBRE1Redundancy pos101Red = new LBRE1Redundancy();
        pos101Red.setStatus(2);
        pos101Red.setTurn(89);
        pos101Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos102Red = new LBRE1Redundancy();
        pos102Red.setStatus(2);
        pos102Red.setTurn(89);
        pos102Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos103Red = new LBRE1Redundancy();
        pos103Red.setStatus(2);
        pos103Red.setTurn(89);
        pos103Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos104Red = new LBRE1Redundancy();
        pos104Red.setStatus(2);
        pos104Red.setTurn(89);
        pos104Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos105Red = new LBRE1Redundancy();
        pos105Red.setStatus(2);
        pos105Red.setTurn(89);
        pos105Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos106Red = new LBRE1Redundancy();
        pos106Red.setStatus(2);
        pos106Red.setTurn(89);
        pos106Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos107Red = new LBRE1Redundancy();
        pos107Red.setStatus(2);
        pos107Red.setTurn(89);
        pos107Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos108Red = new LBRE1Redundancy();
        pos108Red.setStatus(2);
        pos108Red.setTurn(89);
        pos108Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos109Red = new LBRE1Redundancy();
        pos109Red.setStatus(2);
        pos109Red.setTurn(89);
        pos109Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos110Red = new LBRE1Redundancy();
        pos110Red.setStatus(2);
        pos110Red.setTurn(89);
        pos110Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos111Red = new LBRE1Redundancy();
        pos111Red.setStatus(2);
        pos111Red.setTurn(89);
        pos111Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos112Red = new LBRE1Redundancy();
        pos112Red.setStatus(2);
        pos112Red.setTurn(89);
        pos112Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos113Red = new LBRE1Redundancy();
        pos113Red.setStatus(2);
        pos113Red.setTurn(89);
        pos113Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos114Red = new LBRE1Redundancy();
        pos114Red.setStatus(2);
        pos114Red.setTurn(89);
        pos114Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos115Red = new LBRE1Redundancy();
        pos115Red.setStatus(2);
        pos115Red.setTurn(89);
        pos115Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos116Red = new LBRE1Redundancy();
        pos116Red.setStatus(2);
        pos116Red.setTurn(89);
        pos116Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos117Red = new LBRE1Redundancy();
        pos117Red.setStatus(2);
        pos117Red.setTurn(89);
        pos117Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos118Red = new LBRE1Redundancy();
        pos118Red.setStatus(2);
        pos118Red.setTurn(89);
        pos118Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos119Red = new LBRE1Redundancy();
        pos119Red.setStatus(2);
        pos119Red.setTurn(89);
        pos119Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos120Red = new LBRE1Redundancy();
        pos120Red.setStatus(2);
        pos120Red.setTurn(89);
        pos120Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos121Red = new LBRE1Redundancy();
        pos121Red.setStatus(2);
        pos121Red.setTurn(89);
        pos121Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos122Red = new LBRE1Redundancy();
        pos122Red.setStatus(2);
        pos122Red.setTurn(89);
        pos122Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos123Red = new LBRE1Redundancy();
        pos123Red.setStatus(2);
        pos123Red.setTurn(89);
        pos123Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos124Red = new LBRE1Redundancy();
        pos124Red.setStatus(2);
        pos124Red.setTurn(89);
        pos124Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos125Red = new LBRE1Redundancy();
        pos125Red.setStatus(2);
        pos125Red.setTurn(89);
        pos125Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos126Red = new LBRE1Redundancy();
        pos126Red.setStatus(2);
        pos126Red.setTurn(89);
        pos126Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos127Red = new LBRE1Redundancy();
        pos127Red.setStatus(2);
        pos127Red.setTurn(89);
        pos127Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos128Red = new LBRE1Redundancy();
        pos128Red.setStatus(2);
        pos128Red.setTurn(89);
        pos128Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos129Red = new LBRE1Redundancy();
        pos129Red.setStatus(2);
        pos129Red.setTurn(89);
        pos129Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos130Red = new LBRE1Redundancy();
        pos130Red.setStatus(2);
        pos130Red.setTurn(89);
        pos130Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos131Red = new LBRE1Redundancy();
        pos131Red.setStatus(2);
        pos131Red.setTurn(89);
        pos131Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos132Red = new LBRE1Redundancy();
        pos132Red.setStatus(2);
        pos132Red.setTurn(89);
        pos132Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos133Red = new LBRE1Redundancy();
        pos133Red.setStatus(2);
        pos133Red.setTurn(89);
        pos133Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos134Red = new LBRE1Redundancy();
        pos134Red.setStatus(2);
        pos134Red.setTurn(89);
        pos134Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos135Red = new LBRE1Redundancy();
        pos135Red.setStatus(2);
        pos135Red.setTurn(89);
        pos135Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos136Red = new LBRE1Redundancy();
        pos136Red.setStatus(2);
        pos136Red.setTurn(89);
        pos136Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos137Red = new LBRE1Redundancy();
        pos137Red.setStatus(2);
        pos137Red.setTurn(89);
        pos137Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos138Red = new LBRE1Redundancy();
        pos138Red.setStatus(2);
        pos138Red.setTurn(89);
        pos138Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos139Red = new LBRE1Redundancy();
        pos139Red.setStatus(2);
        pos139Red.setTurn(89);
        pos139Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy pos140Red = new LBRE1Redundancy();
        pos140Red.setStatus(2);
        pos140Red.setTurn(89);
        pos140Red.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posafterRed = new LBRE1Redundancy();
        posafterRed.setStatus(2);
        posafterRed.setTurn(89);
        posafterRed.setE1(Math.toRadians(70.0));

        LBRE1Redundancy posHOMEafterRed = new LBRE1Redundancy();
        posHOMEafterRed.setStatus(2);
        posHOMEafterRed.setTurn(88);
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
        current_move = tool.moveAsync(ptpMove_posHOME);

        
        isCancel = getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, "Begin massage", "OK", "Cancel");
        if (isCancel == 1)
        {
            return;
        }

        posbefore.setX(posbefore.getX() - offset);
        posbefore.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(posbefore).setCartVelocity(5 * velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos1.setX(pos1.getX() - offset);
        pos1.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos1).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos2.setX(pos2.getX() - offset);
        pos2.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos2).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos3.setX(pos3.getX() - offset);
        pos3.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos3).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos4.setX(pos4.getX() - offset);
        pos4.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos4).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos5.setX(pos5.getX() - offset);
        pos5.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos5).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos6.setX(pos6.getX() - offset);
        pos6.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos6).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos7.setX(pos7.getX() - offset);
        pos7.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos7).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos8.setX(pos8.getX() - offset);
        pos8.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos8).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos9.setX(pos9.getX() - offset);
        pos9.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos9).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos10.setX(pos10.getX() - offset);
        pos10.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos10).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos11.setX(pos11.getX() - offset);
        pos11.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos11).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));

        pos12.setX(pos12.getX() - offset);
        pos12.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos12).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos13.setX(pos13.getX() - offset);
        pos13.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos13).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos14.setX(pos14.getX() - offset);
        pos14.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos14).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos15.setX(pos15.getX() - offset);
        pos15.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos15).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos16.setX(pos16.getX() - offset);
        pos16.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos16).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos17.setX(pos17.getX() - offset);
        pos17.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos17).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos18.setX(pos18.getX() - offset);
        pos18.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos18).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos19.setX(pos19.getX() - offset);
        pos19.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos19).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos20.setX(pos20.getX() - offset);
        pos20.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos20).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos21.setX(pos21.getX() - offset);
        pos21.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos21).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos22.setX(pos22.getX() - offset);
        pos22.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos22).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos23.setX(pos23.getX() - offset);
        pos23.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos23).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos24.setX(pos24.getX() - offset);
        pos24.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos24).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos25.setX(pos25.getX() - offset);
        pos25.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos25).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos26.setX(pos26.getX() - offset);
        pos26.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos26).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos27.setX(pos27.getX() - offset);
        pos27.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos27).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos28.setX(pos28.getX() - offset);
        pos28.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos28).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos29.setX(pos29.getX() - offset);
        pos29.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos29).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos30.setX(pos30.getX() - offset);
        pos30.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos30).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos31.setX(pos31.getX() - offset);
        pos31.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos31).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos32.setX(pos32.getX() - offset);
        pos32.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos32).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos33.setX(pos33.getX() - offset);
        pos33.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos33).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos34.setX(pos34.getX() - offset);
        pos34.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos34).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos35.setX(pos35.getX() - offset);
        pos35.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos35).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos36.setX(pos36.getX() - offset);
        pos36.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos36).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos37.setX(pos37.getX() - offset);
        pos37.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos37).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos38.setX(pos38.getX() - offset);
        pos38.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos38).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos39.setX(pos39.getX() - offset);
        pos39.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos39).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos40.setX(pos40.getX() - offset);
        pos40.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos40).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos41.setX(pos41.getX() - offset);
        pos41.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos41).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos42.setX(pos42.getX() - offset);
        pos42.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos42).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos43.setX(pos43.getX() - offset);
        pos43.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos43).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos44.setX(pos44.getX() - offset);
        pos44.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos44).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos45.setX(pos45.getX() - offset);
        pos45.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos45).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos46.setX(pos46.getX() - offset);
        pos46.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos46).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos47.setX(pos47.getX() - offset);
        pos47.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos47).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos48.setX(pos48.getX() - offset);
        pos48.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos48).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos49.setX(pos49.getX() - offset);
        pos49.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos49).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos50.setX(pos50.getX() - offset);
        pos50.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos50).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos51.setX(pos51.getX() - offset);
        pos51.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos51).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos52.setX(pos52.getX() - offset);
        pos52.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos52).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos53.setX(pos53.getX() - offset);
        pos53.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos53).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos54.setX(pos54.getX() - offset);
        pos54.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos54).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos55.setX(pos55.getX() - offset);
        pos55.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos55).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos56.setX(pos56.getX() - offset);
        pos56.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos56).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos57.setX(pos57.getX() - offset);
        pos57.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos57).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos58.setX(pos58.getX() - offset);
        pos58.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos58).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos59.setX(pos59.getX() - offset);
        pos59.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos59).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos60.setX(pos60.getX() - offset);
        pos60.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos60).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos61.setX(pos61.getX() - offset);
        pos61.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos61).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos62.setX(pos62.getX() - offset);
        pos62.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos62).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos63.setX(pos63.getX() - offset);
        pos63.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos63).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos64.setX(pos64.getX() - offset);
        pos64.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos64).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos65.setX(pos65.getX() - offset);
        pos65.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos65).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos66.setX(pos66.getX() - offset);
        pos66.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos66).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos67.setX(pos67.getX() - offset);
        pos67.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos67).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos68.setX(pos68.getX() - offset);
        pos68.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos68).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos69.setX(pos69.getX() - offset);
        pos69.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos69).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos70.setX(pos70.getX() - offset);
        pos70.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos70).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos71.setX(pos71.getX() - offset);
        pos71.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos71).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos72.setX(pos72.getX() - offset);
        pos72.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos72).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos73.setX(pos73.getX() - offset);
        pos73.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos73).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos74.setX(pos74.getX() - offset);
        pos74.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos74).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos75.setX(pos75.getX() - offset);
        pos75.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos75).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos76.setX(pos76.getX() - offset);
        pos76.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos76).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos77.setX(pos77.getX() - offset);
        pos77.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos77).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos78.setX(pos78.getX() - offset);
        pos78.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos78).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos79.setX(pos79.getX() - offset);
        pos79.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos79).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos80.setX(pos80.getX() - offset);
        pos80.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos80).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos81.setX(pos81.getX() - offset);
        pos81.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos81).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos82.setX(pos82.getX() - offset);
        pos82.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos82).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos83.setX(pos83.getX() - offset);
        pos83.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos83).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos84.setX(pos84.getX() - offset);
        pos84.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos84).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos85.setX(pos85.getX() - offset);
        pos85.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos85).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos86.setX(pos86.getX() - offset);
        pos86.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos86).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos87.setX(pos87.getX() - offset);
        pos87.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos87).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos88.setX(pos88.getX() - offset);
        pos88.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos88).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos89.setX(pos89.getX() - offset);
        pos89.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos89).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos90.setX(pos90.getX() - offset);
        pos90.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos90).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos91.setX(pos91.getX() - offset);
        pos91.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos91).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos92.setX(pos92.getX() - offset);
        pos92.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos92).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos93.setX(pos93.getX() - offset);
        pos93.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos93).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos94.setX(pos94.getX() - offset);
        pos94.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos94).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos95.setX(pos95.getX() - offset);
        pos95.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos95).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos96.setX(pos96.getX() - offset);
        pos96.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos96).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos97.setX(pos97.getX() - offset);
        pos97.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos97).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos98.setX(pos98.getX() - offset);
        pos98.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos98).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos99.setX(pos99.getX() - offset);
        pos99.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos99).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos100.setX(pos100.getX() - offset);
        pos100.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos100).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos101.setX(pos101.getX() - offset);
        pos101.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos101).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos102.setX(pos102.getX() - offset);
        pos102.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos102).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos103.setX(pos103.getX() - offset);
        pos103.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos103).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos104.setX(pos104.getX() - offset);
        pos104.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos104).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos105.setX(pos105.getX() - offset);
        pos105.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos105).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos106.setX(pos106.getX() - offset);
        pos106.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos106).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos107.setX(pos107.getX() - offset);
        pos107.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos107).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos108.setX(pos108.getX() - offset);
        pos108.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos108).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos109.setX(pos109.getX() - offset);
        pos109.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos109).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos110.setX(pos110.getX() - offset);
        pos110.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos110).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos111.setX(pos111.getX() - offset);
        pos111.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos111).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos112.setX(pos112.getX() - offset);
        pos112.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos112).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos113.setX(pos113.getX() - offset);
        pos113.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos113).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos114.setX(pos114.getX() - offset);
        pos114.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos114).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos115.setX(pos115.getX() - offset);
        pos115.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos115).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos116.setX(pos116.getX() - offset);
        pos116.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos116).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos117.setX(pos117.getX() - offset);
        pos117.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos117).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos118.setX(pos118.getX() - offset);
        pos118.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos118).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos119.setX(pos119.getX() - offset);
        pos119.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos119).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos120.setX(pos120.getX() - offset);
        pos120.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos120).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos121.setX(pos121.getX() - offset);
        pos121.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos121).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos122.setX(pos122.getX() - offset);
        pos122.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos122).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos123.setX(pos123.getX() - offset);
        pos123.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos123).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos124.setX(pos124.getX() - offset);
        pos124.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos124).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos125.setX(pos125.getX() - offset);
        pos125.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos125).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos126.setX(pos126.getX() - offset);
        pos126.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos126).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos127.setX(pos127.getX() - offset);
        pos127.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos127).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos128.setX(pos128.getX() - offset);
        pos128.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos128).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos129.setX(pos129.getX() - offset);
        pos129.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos129).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos130.setX(pos130.getX() - offset);
        pos130.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos130).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos131.setX(pos131.getX() - offset);
        pos131.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos131).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos132.setX(pos132.getX() - offset);
        pos132.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos132).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos133.setX(pos133.getX() - offset);
        pos133.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos133).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos134.setX(pos134.getX() - offset);
        pos134.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos134).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos135.setX(pos135.getX() - offset);
        pos135.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos135).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos136.setX(pos136.getX() - offset);
        pos136.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos136).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos137.setX(pos137.getX() - offset);
        pos137.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos137).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos138.setX(pos138.getX() - offset);
        pos138.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos138).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos139.setX(pos139.getX() - offset);
        pos139.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos139).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        pos140.setX(pos140.getX() - offset);
        pos140.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(pos140).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posafter.setX(posafter.getX() - offset);
        posafter.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(posafter).setCartVelocity(velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        

        posHOMEafter.setX(posHOMEafter.getX() - offset);
        posHOMEafter.setRedundancyInformation(lbr, posHOMEafterRed);
        current_move = tool.moveAsync(lin(posHOMEafter).setCartVelocity(5 * velocity).setMode(impedanceControlMode).setBlendingCart(blendingRadius));
        current_move.await();



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
