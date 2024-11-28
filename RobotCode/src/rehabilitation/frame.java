package rehabilitation;


import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.JointEnum;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.TeachInformation;
import com.kuka.roboticsAPI.deviceModel.LBRE1Redundancy;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.AdditionalData;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.SpatialObject;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.Workpiece;
import com.kuka.roboticsAPI.geometricModel.World;
import com.kuka.roboticsAPI.motionModel.LIN;
import com.kuka.roboticsAPI.motionModel.PTP;
import com.kuka.roboticsAPI.motionModel.Spline;
import com.kuka.roboticsAPI.persistenceModel.IPersistenceEngine;
import com.kuka.roboticsAPI.persistenceModel.templateModel.FrameTemplate.Transformation;
import com.kuka.roboticsAPI.applicationModel.IApplicationData;



/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a 
 * {@link RoboticsAPITask#run()} method, which will be called successively in 
 * the application lifecycle. The application will terminate automatically after 
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the 
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an 
 * exception is thrown during initialization or run. 
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the 
 * {@link RoboticsAPITask#dispose()} method.</b> 
 * 
 * @see UseRoboticsAPIContext
 * @see #initialize()
 * @see #run()
 * @see #dispose()
 */
public class frame extends RoboticsAPIApplication {
    private LBR lbr;
    private Workpiece testWorkpiece;
    
    @Inject
    @Named("Test")
    private Tool Test;

    @Override
    public void initialize() {
        lbr = getContext().getDeviceFromType(LBR.class);
        Test.attachTo(lbr.getFlange());
        Test.getFrame("/frame_TCP");
    }

    @Override
    public void run() {
    	Test.move(ptpHome());
    	getLogger().info("Info " + lbr.getAllFrames());
        getLogger().info("Home");
        
        Frame Xinstallation = new Frame(-250, 143.53, 721.31, Math.toRadians(5), Math.toRadians(3), Math.toRadians(-92));
        Frame Xstart = new Frame(-271.03, 534.41, 484.63, Math.toRadians(88.296), Math.toRadians(2.11), Math.toRadians(176.919));
        Frame Xp3C = new Frame(-162.23, 436.88, 210.43, Math.toRadians(96.48), Math.toRadians(0.043), Math.toRadians(-172.994));
        //Frame Xp3Cbis = new Frame(-162.23, 436.88, 1216.86, Math.toRadians(-172.99), Math.toRadians(0.04), Math.toRadians(96.48));
        Frame XA3 = new Frame(146.95, 247.07, 584.12, Math.toRadians(-34.182), Math.toRadians(-18.291), Math.toRadians(170.088));
        
        JointPosition pos = new JointPosition(Math.toRadians(-4.33),Math.toRadians(21.83),Math.toRadians(0.0),Math.toRadians(119.76),
        		Math.toRadians(91.17),Math.toRadians(80.50),Math.toRadians(4.82));
        
        //Test.move(ptp(pos).setJointVelocityRel(0.6));
        
        //JointPosition pos1 = new JointPosition(Math.toRadians(-62.76),Math.toRadians(-31.75),Math.toRadians(0.0),Math.toRadians(73.73),
        		//Math.toRadians(-1.71),Math.toRadians(-77.86),Math.toRadians(29.30));
        
        //Test.move(ptp(pos1).setJointVelocityRel(0.6));
      
        LBRE1Redundancy redundancy = new LBRE1Redundancy();
        redundancy.setStatus(1);
        redundancy.setTurn(1);
        redundancy.setE1(Math.toRadians(0.0));
        Xinstallation.setRedundancyInformation(lbr, redundancy);   
        
        LBRE1Redundancy redundancy1 = new LBRE1Redundancy();
        redundancy1.setStatus(5);
        redundancy1.setTurn(51);
        redundancy.setE1(Math.toRadians(0.0));
        Xstart.setRedundancyInformation(lbr, redundancy1);
        
        LBRE1Redundancy redundancy2 = new LBRE1Redundancy();
        redundancy2.setStatus(5);
        redundancy2.setTurn(99);
        //Xp3C.setRedundancyInformation(lbr, redundancy2);    
        
        LBRE1Redundancy redundancy3 = new LBRE1Redundancy();
        redundancy3.setStatus(1);
        redundancy3.setTurn(0);
        redundancy3.setE1(Math.toRadians(-110));
        XA3.setRedundancyInformation(lbr, redundancy3);
        
        //JointPosition joint = lbr.getInverseKinematicFromFrameAndRedundancy(Xinstallation);
        //double[] target = {joint.get(JointEnum.J1),
        		//joint.get(JointEnum.J2),
        		//joint.get(JointEnum.J3),
        		//joint.get(JointEnum.J4),
        		//joint.get(JointEnum.J5),
        		//joint.get(JointEnum.J6),
        		//joint.get(JointEnum.J7)};        
        //String message = "Target Joint";
        //for (int index=0;index<target.length;index++){
        	//message += " " + Math.toDegrees(target[index]);
        //}       
        //getLogger().info("joint " + message);            
        //Test.move(ptp(joint).setJointVelocityRel(0.6));
        //getLogger().info("info " + Xinstallation.getRedundancyInformationForDevice(lbr));
        //getLogger().info("OK");
        
        Test.move(ptp(Xinstallation).setJointVelocityRel(0.6));
        getLogger().info("X " + lbr.getAllFrames());
        getLogger().info("info " + Xinstallation.getRedundancyInformationForDevice(lbr));
        getLogger().info("OK");
        
        //Test.move(ptp(Xstart).setJointVelocityRel(0.6));
        //getLogger().info("X " + lbr.getAllFrames());
        //getLogger().info("info " + Xinstallation.getRedundancyInformationForDevice(lbr));
        //getLogger().info("OK");
        
        //Test.move(lin(Xstart).setJointVelocityRel(0.6));
        //lbr.move(ptp(Xp3C).setJointVelocityRel(0.6));
       
        //lbr.move(ptp(getApplicationData().getFrame("/P44")).setJointVelocityRel(0.6));
        //getLogger().info("P44 " + lbr.getAllFrames());
        //getLogger().info("OK");
        
        //Test.move(ptp(Xstart).setJointVelocityRel(0.6));
        //getLogger().info("Xstart " + lbr.getAllFrames());
        //getLogger().info("info " + Xstart.getRedundancyInformationForDevice(lbr));
        //getLogger().info("OK");
        
        
    }   
}