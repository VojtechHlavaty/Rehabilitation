package rehabilitation;


import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


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
public class RobotApplication extends RoboticsAPIApplication {
	
	private LBR lbr;
	
	@Override
    public void initialize() {
        lbr = getContext().getDeviceFromType(LBR.class);
    }

	public void run() {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse("C:\\Users\\Testbed-PC\\SunriseWorkspace\\MiniRobobar\\src\\RoboticsAPI.data.xml");
			document.getDocumentElement().normalize();
			Element rootElement = document.getDocumentElement();
			Element newFrame = document.createElement("frame");
			newFrame.setAttribute("id", "_new-frame-id");
			newFrame.setAttribute("name", "NewFrame");
			
			Element transformation  = document.createElement("transformation");
			transformation.setAttribute("x", "100.0");
			transformation.setAttribute("y", "200.0");
			transformation.setAttribute("z", "300.0");
			transformation.setAttribute("a", "100.0");
			transformation.setAttribute("b", "100.0");
			transformation.setAttribute("c", "100.0");
			rootElement.appendChild(newFrame);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult("C:\\Users\\Testbed-PC\\SunriseWorkspace\\MiniRobobar\\src\\RoboticsAPI.data.xml");
			transformer.transform(source, result);
			System.out.println("Le fichier a ete modifie avec succes");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}