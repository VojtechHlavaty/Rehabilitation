package application;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.LIN;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.List;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;

public class ForceTest extends RoboticsAPIApplication {

    @Inject
    private LBR lbr;

    @Inject
    @Named("Tool5")
    private Tool tool;

    private double forceBias = 0;
    
    @Override
    public void initialize() {
        getLogger().info("Initializing the application ...");
        tool.attachTo(lbr.getFlange());
        forceBias = 25 * getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, "Impedance mode active", "0 N", "25 N", "50 N");
        CommandQueue.clear();
    }

    @Override
    public void run() {
        JointPosition home = new JointPosition(0, 0, 0, Math.toRadians(-90), 0, Math.toRadians(90), 0);

        Frame pos1 = new Frame(725, -210, -127, Math.toRadians(-180.0), 0.0, Math.toRadians(180.0));
        Frame pos2 = new Frame(450, 230, -127, Math.toRadians(-180.0), 0.0, Math.toRadians(180.0));

        // Define Cartesian impedance mode
        CartesianSineImpedanceControlMode impedanceControlMode = new CartesianSineImpedanceControlMode();
        impedanceControlMode.parametrize(CartDOF.X).setStiffness(1500);
        impedanceControlMode.parametrize(CartDOF.Y).setStiffness(1500);
        impedanceControlMode.parametrize(CartDOF.Z).setStiffness(200).setDamping(1);
        impedanceControlMode.parametrize(CartDOF.ROT).setStiffness(300);
        impedanceControlMode.parametrize(CartDOF.Z).setBias(forceBias);
        impedanceControlMode.setRiseTime(1);

        // Move to home and then to start
        tool.move(ptp(home).setJointVelocityRel(0.3));
        tool.move(lin(pos1).setCartVelocity(300)); // fast move to start

        // Create zigzag frames
        int zigzagSteps = 10;
        double yStep = (pos2.getY() - pos1.getY()) / (double) zigzagSteps;

        List<LIN> zigzagMoves = new ArrayList<LIN>();
 
        for (int i = 0; i <= zigzagSteps; i++) {
            double y = pos1.getY() + i * yStep;
            double x = (i % 2 == 0) ? pos1.getX() : pos2.getX();

            Frame zigzagFrame = new Frame(
                x, y, -127,
                pos1.getAlphaRad(), pos1.getBetaRad(), pos1.getGammaRad()
            );

            LIN move = lin(zigzagFrame)
                .setMode(impedanceControlMode)
                .setCartVelocity(50);

            zigzagMoves.add(move);
        }

        // Execute all moves sequentially with blending
        IMotionContainer motion = tool.move(zigzagMoves.get(0)); // first move (blocking)
        for (int i = 1; i < zigzagMoves.size(); i++) {
            motion = tool.moveAsync(zigzagMoves.get(i)); // async with blending
            if (i == 1) {impedanceControlMode.setRiseTime(0);}
        }

        motion.await(); // wait for final move to complete

        getLogger().info("Zigzag motion complete.");
        tool.move(ptp(home).setJointVelocityRel(0.3));
    }
}
