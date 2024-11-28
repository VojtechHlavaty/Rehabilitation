package rehabilitation;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.motionModel.PTP;

public class RobotMove extends RoboticsAPIApplication {
    private LBR robot;

    @Override
    public void initialize() {
        robot = getContext().getDeviceFromType(LBR.class);
    }

    @Override
    public void run() {
        Frame Xinstallation = new Frame(-246.94, 143.53, 689.31, Math.toRadians(5.0), Math.toRadians(-2.817), Math.toRadians(-92.45));
        Frame Xstart = new Frame(-271.03, 534.41, 484.63, Math.toRadians(88.296), Math.toRadians(2.11), Math.toRadians(176.919));
        Frame Xp3C = new Frame(-162.23, 436.88, 210.43, Math.toRadians(96.48), Math.toRadians(0.043), Math.toRadians(-172.994));

    }
}
