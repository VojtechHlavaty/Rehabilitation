package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>GripperEGI</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * ./.
 */
@Singleton
public class GripperEGIIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'GripperEGI'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'GripperEGI'
	 */
	@Inject
	public GripperEGIIOGroup(Controller controller)
	{
		super(controller, "GripperEGI");

		addInput("Input1StatusWord", IOTypes.UNSIGNED_INTEGER, 32);
		addInput("Input2ActualPosition", IOTypes.INTEGER, 32);
		addInput("Input3Reserve", IOTypes.INTEGER, 32);
		addInput("Input4Diagnostics", IOTypes.INTEGER, 32);
		addDigitalOutput("Output1ControlWord", IOTypes.UNSIGNED_INTEGER, 32);
		addDigitalOutput("Output2Position", IOTypes.INTEGER, 32);
		addDigitalOutput("Output3Speed", IOTypes.INTEGER, 32);
		addDigitalOutput("Output4GrippingForce", IOTypes.INTEGER, 32);
	}

	/**
	 * Gets the value of the <b>digital input '<i>Input1StatusWord</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 4294967295]
	 *
	 * @return current value of the digital input 'Input1StatusWord'
	 */
	public java.lang.Long getInput1StatusWord()
	{
		return getNumberIOValue("Input1StatusWord", false).longValue();
	}

	/**
	 * Gets the value of the <b>digital input '<i>Input2ActualPosition</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @return current value of the digital input 'Input2ActualPosition'
	 */
	public java.lang.Long getInput2ActualPosition()
	{
		return getNumberIOValue("Input2ActualPosition", false).longValue();
	}

	/**
	 * Gets the value of the <b>digital input '<i>Input3Reserve</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @return current value of the digital input 'Input3Reserve'
	 */
	public java.lang.Long getInput3Reserve()
	{
		return getNumberIOValue("Input3Reserve", false).longValue();
	}

	/**
	 * Gets the value of the <b>digital input '<i>Input4Diagnostics</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @return current value of the digital input 'Input4Diagnostics'
	 */
	public java.lang.Long getInput4Diagnostics()
	{
		return getNumberIOValue("Input4Diagnostics", false).longValue();
	}

	/**
	 * Gets the value of the <b>digital output '<i>Output1ControlWord</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 4294967295]
	 *
	 * @return current value of the digital output 'Output1ControlWord'
	 */
	public java.lang.Long getOutput1ControlWord()
	{
		return getNumberIOValue("Output1ControlWord", true).longValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Output1ControlWord</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 4294967295]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Output1ControlWord'
	 */
	public void setOutput1ControlWord(java.lang.Long value)
	{
		setDigitalOutput("Output1ControlWord", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Output2Position</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @return current value of the digital output 'Output2Position'
	 */
	public java.lang.Long getOutput2Position()
	{
		return getNumberIOValue("Output2Position", true).longValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Output2Position</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Output2Position'
	 */
	public void setOutput2Position(java.lang.Long value)
	{
		setDigitalOutput("Output2Position", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Output3Speed</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @return current value of the digital output 'Output3Speed'
	 */
	public java.lang.Long getOutput3Speed()
	{
		return getNumberIOValue("Output3Speed", true).longValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Output3Speed</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Output3Speed'
	 */
	public void setOutput3Speed(java.lang.Long value)
	{
		setDigitalOutput("Output3Speed", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Output4GrippingForce</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @return current value of the digital output 'Output4GrippingForce'
	 */
	public java.lang.Long getOutput4GrippingForce()
	{
		return getNumberIOValue("Output4GrippingForce", true).longValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Output4GrippingForce</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-2147483648; 2147483647]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Output4GrippingForce'
	 */
	public void setOutput4GrippingForce(java.lang.Long value)
	{
		setDigitalOutput("Output4GrippingForce", value);
	}

}
