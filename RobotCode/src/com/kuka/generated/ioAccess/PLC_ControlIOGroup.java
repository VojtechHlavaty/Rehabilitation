package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;
import com.kuka.roboticsAPI.ioModel.OutputReservedException;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>PLC_Control</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * ./.
 */
@Singleton
public class PLC_ControlIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'PLC_Control'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'PLC_Control'
	 */
	@Inject
	public PLC_ControlIOGroup(Controller controller)
	{
		super(controller, "PLC_Control");

		addInput("In_GetCup", IOTypes.BOOLEAN, 1);
		addInput("In_TapSoda", IOTypes.BOOLEAN, 1);
		addInput("In_TapBeer", IOTypes.BOOLEAN, 1);
		addInput("In_NoCupDetected", IOTypes.BOOLEAN, 1);
		addInput("In_Pos1Continue", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_ResetReq", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_TapSodaDone", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_TapBeerDone", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_ServeDone", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_IsAtHome", IOTypes.BOOLEAN, 1);
		addMockedInput("State", IOTypes.UNSIGNED_INTEGER, 8, 0);
		addInput("In_Pos2Continue", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_InitDone", IOTypes.BOOLEAN, 1);
		addDigitalOutput("TestInt", IOTypes.INTEGER, 16);
		addInput("App_Start", IOTypes.BOOLEAN, 1);
		addInput("App_Enable", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("AutExt_Active", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("AutExt_AppReadyToStart", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("DefaultApp_Error", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("Station_Error", IOTypes.BOOLEAN, 1);
		addInput("In_ResetReq", IOTypes.BOOLEAN, 1);
		addInput("In_CupPresenceChecked", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Motor_1_Angle_Off", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_1_Angle_On", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_1_Angle_Foam", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_2_Angle_Foam", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_2_Angle_On", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_2_Angle_Off", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_1_Mode", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Motor_2_Mode", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Motor_2_Open_timer", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_2_Foam_timer", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_1_Open_timer", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Motor_1_Foam_timer", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Out_DrinkReady", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_ErrorInDrinkPrep", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Servo1Start", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Servo2Start", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_DrinkPrepBegan", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_IsGripping", IOTypes.BOOLEAN, 1);
		addInput("In_Pos3Continue", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("AppStart_SP", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("AppEnable_SP", IOTypes.BOOLEAN, 1);
		addInput("In_Force", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Out_Force", IOTypes.UNSIGNED_INTEGER, 8);
		addDigitalOutput("Out_ManualPullOut", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_PullOutServo", IOTypes.BOOLEAN, 1);
		addInput("In_ManualPullOut", IOTypes.BOOLEAN, 1);
		addInput("In_OPCUA_beer_start_left", IOTypes.BOOLEAN, 1);
		addInput("In_OPCUA_beer_start_right", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_OpenCupDispencer", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_CloseCupDispencer", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_CupReceivedByHooman", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_RobotReadyForOrder", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_SecondBeerPosition", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_ThirdBeerPosition", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Out_BeerIsServed", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_GetCup</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_GetCup'
	 */
	public boolean getIn_GetCup()
	{
		return getBooleanIOValue("In_GetCup", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_TapSoda</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_TapSoda'
	 */
	public boolean getIn_TapSoda()
	{
		return getBooleanIOValue("In_TapSoda", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_TapBeer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_TapBeer'
	 */
	public boolean getIn_TapBeer()
	{
		return getBooleanIOValue("In_TapBeer", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_NoCupDetected</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_NoCupDetected'
	 */
	public boolean getIn_NoCupDetected()
	{
		return getBooleanIOValue("In_NoCupDetected", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_Pos1Continue</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_Pos1Continue'
	 */
	public boolean getIn_Pos1Continue()
	{
		return getBooleanIOValue("In_Pos1Continue", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_ResetReq</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_ResetReq'
	 */
	public boolean getOut_ResetReq()
	{
		return getBooleanIOValue("Out_ResetReq", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_ResetReq</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_ResetReq'
	 */
	public void setOut_ResetReq(java.lang.Boolean value)
	{
		setDigitalOutput("Out_ResetReq", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_TapSodaDone</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_TapSodaDone'
	 */
	public boolean getOut_TapSodaDone()
	{
		return getBooleanIOValue("Out_TapSodaDone", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_TapSodaDone</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_TapSodaDone'
	 */
	public void setOut_TapSodaDone(java.lang.Boolean value)
	{
		setDigitalOutput("Out_TapSodaDone", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_TapBeerDone</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_TapBeerDone'
	 */
	public boolean getOut_TapBeerDone()
	{
		return getBooleanIOValue("Out_TapBeerDone", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_TapBeerDone</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_TapBeerDone'
	 */
	public void setOut_TapBeerDone(java.lang.Boolean value)
	{
		setDigitalOutput("Out_TapBeerDone", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_ServeDone</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_ServeDone'
	 */
	public boolean getOut_ServeDone()
	{
		return getBooleanIOValue("Out_ServeDone", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_ServeDone</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_ServeDone'
	 */
	public void setOut_ServeDone(java.lang.Boolean value)
	{
		setDigitalOutput("Out_ServeDone", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_IsAtHome</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_IsAtHome'
	 */
	public boolean getOut_IsAtHome()
	{
		return getBooleanIOValue("Out_IsAtHome", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_IsAtHome</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_IsAtHome'
	 */
	public void setOut_IsAtHome(java.lang.Boolean value)
	{
		setDigitalOutput("Out_IsAtHome", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>State</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital input 'State'
	* 
	 * @deprecated The output 'State' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public java.lang.Integer getState()
	{
		return getNumberIOValue("State", false).intValue();
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>State</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'State'
	* 
	 * @deprecated The output 'State' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedStateValue(java.lang.Integer value)
	{
		setMockedInput("State", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_Pos2Continue</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_Pos2Continue'
	 */
	public boolean getIn_Pos2Continue()
	{
		return getBooleanIOValue("In_Pos2Continue", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_InitDone</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_InitDone'
	 */
	public boolean getOut_InitDone()
	{
		return getBooleanIOValue("Out_InitDone", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_InitDone</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_InitDone'
	 */
	public void setOut_InitDone(java.lang.Boolean value)
	{
		setDigitalOutput("Out_InitDone", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>TestInt</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-32768; 32767]
	 *
	 * @return current value of the digital output 'TestInt'
	 */
	public java.lang.Integer getTestInt()
	{
		return getNumberIOValue("TestInt", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>TestInt</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-32768; 32767]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'TestInt'
	 */
	public void setTestInt(java.lang.Integer value)
	{
		setDigitalOutput("TestInt", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>App_Start</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'App_Start'
	 */
	public boolean getApp_Start()
	{
		return getBooleanIOValue("App_Start", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>App_Enable</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'App_Enable'
	 */
	public boolean getApp_Enable()
	{
		return getBooleanIOValue("App_Enable", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AutExt_Active</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AutExt_Active'
	* 
	 * @deprecated The output 'AutExt_Active' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getAutExt_Active()
	{
		return getBooleanIOValue("AutExt_Active", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>AutExt_Active</i>'</b> is currently used as station state output in the Sunrise project properties.
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AutExt_Active'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'AutExt_Active' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setAutExt_Active(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'AutExt_Active' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>AutExt_AppReadyToStart</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AutExt_AppReadyToStart'
	* 
	 * @deprecated The output 'AutExt_AppReadyToStart' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getAutExt_AppReadyToStart()
	{
		return getBooleanIOValue("AutExt_AppReadyToStart", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>AutExt_AppReadyToStart</i>'</b> is currently used as station state output in the Sunrise project properties.
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AutExt_AppReadyToStart'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'AutExt_AppReadyToStart' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setAutExt_AppReadyToStart(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'AutExt_AppReadyToStart' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>DefaultApp_Error</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DefaultApp_Error'
	* 
	 * @deprecated The output 'DefaultApp_Error' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getDefaultApp_Error()
	{
		return getBooleanIOValue("DefaultApp_Error", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>DefaultApp_Error</i>'</b> is currently used as station state output in the Sunrise project properties.
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DefaultApp_Error'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'DefaultApp_Error' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setDefaultApp_Error(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'DefaultApp_Error' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>Station_Error</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Station_Error'
	* 
	 * @deprecated The output 'Station_Error' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getStation_Error()
	{
		return getBooleanIOValue("Station_Error", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>Station_Error</i>'</b> is currently used as station state output in the Sunrise project properties.
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Station_Error'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'Station_Error' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setStation_Error(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'Station_Error' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_ResetReq</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_ResetReq'
	 */
	public boolean getIn_ResetReq()
	{
		return getBooleanIOValue("In_ResetReq", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_CupPresenceChecked</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_CupPresenceChecked'
	 */
	public boolean getIn_CupPresenceChecked()
	{
		return getBooleanIOValue("In_CupPresenceChecked", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_1_Angle_Off</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_1_Angle_Off'
	 */
	public java.lang.Integer getMotor_1_Angle_Off()
	{
		return getNumberIOValue("Motor_1_Angle_Off", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_1_Angle_Off</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_1_Angle_Off'
	 */
	public void setMotor_1_Angle_Off(java.lang.Integer value)
	{
		setDigitalOutput("Motor_1_Angle_Off", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_1_Angle_On</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_1_Angle_On'
	 */
	public java.lang.Integer getMotor_1_Angle_On()
	{
		return getNumberIOValue("Motor_1_Angle_On", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_1_Angle_On</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_1_Angle_On'
	 */
	public void setMotor_1_Angle_On(java.lang.Integer value)
	{
		setDigitalOutput("Motor_1_Angle_On", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_1_Angle_Foam</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_1_Angle_Foam'
	 */
	public java.lang.Integer getMotor_1_Angle_Foam()
	{
		return getNumberIOValue("Motor_1_Angle_Foam", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_1_Angle_Foam</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_1_Angle_Foam'
	 */
	public void setMotor_1_Angle_Foam(java.lang.Integer value)
	{
		setDigitalOutput("Motor_1_Angle_Foam", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_2_Angle_Foam</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_2_Angle_Foam'
	 */
	public java.lang.Integer getMotor_2_Angle_Foam()
	{
		return getNumberIOValue("Motor_2_Angle_Foam", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_2_Angle_Foam</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_2_Angle_Foam'
	 */
	public void setMotor_2_Angle_Foam(java.lang.Integer value)
	{
		setDigitalOutput("Motor_2_Angle_Foam", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_2_Angle_On</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_2_Angle_On'
	 */
	public java.lang.Integer getMotor_2_Angle_On()
	{
		return getNumberIOValue("Motor_2_Angle_On", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_2_Angle_On</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_2_Angle_On'
	 */
	public void setMotor_2_Angle_On(java.lang.Integer value)
	{
		setDigitalOutput("Motor_2_Angle_On", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_2_Angle_Off</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_2_Angle_Off'
	 */
	public java.lang.Integer getMotor_2_Angle_Off()
	{
		return getNumberIOValue("Motor_2_Angle_Off", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_2_Angle_Off</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_2_Angle_Off'
	 */
	public void setMotor_2_Angle_Off(java.lang.Integer value)
	{
		setDigitalOutput("Motor_2_Angle_Off", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_1_Mode</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Motor_1_Mode'
	 */
	public boolean getMotor_1_Mode()
	{
		return getBooleanIOValue("Motor_1_Mode", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_1_Mode</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_1_Mode'
	 */
	public void setMotor_1_Mode(java.lang.Boolean value)
	{
		setDigitalOutput("Motor_1_Mode", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_2_Mode</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Motor_2_Mode'
	 */
	public boolean getMotor_2_Mode()
	{
		return getBooleanIOValue("Motor_2_Mode", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_2_Mode</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_2_Mode'
	 */
	public void setMotor_2_Mode(java.lang.Boolean value)
	{
		setDigitalOutput("Motor_2_Mode", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_2_Open_timer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_2_Open_timer'
	 */
	public java.lang.Integer getMotor_2_Open_timer()
	{
		return getNumberIOValue("Motor_2_Open_timer", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_2_Open_timer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_2_Open_timer'
	 */
	public void setMotor_2_Open_timer(java.lang.Integer value)
	{
		setDigitalOutput("Motor_2_Open_timer", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_2_Foam_timer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_2_Foam_timer'
	 */
	public java.lang.Integer getMotor_2_Foam_timer()
	{
		return getNumberIOValue("Motor_2_Foam_timer", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_2_Foam_timer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_2_Foam_timer'
	 */
	public void setMotor_2_Foam_timer(java.lang.Integer value)
	{
		setDigitalOutput("Motor_2_Foam_timer", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_1_Open_timer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_1_Open_timer'
	 */
	public java.lang.Integer getMotor_1_Open_timer()
	{
		return getNumberIOValue("Motor_1_Open_timer", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_1_Open_timer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_1_Open_timer'
	 */
	public void setMotor_1_Open_timer(java.lang.Integer value)
	{
		setDigitalOutput("Motor_1_Open_timer", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Motor_1_Foam_timer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Motor_1_Foam_timer'
	 */
	public java.lang.Integer getMotor_1_Foam_timer()
	{
		return getNumberIOValue("Motor_1_Foam_timer", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Motor_1_Foam_timer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Motor_1_Foam_timer'
	 */
	public void setMotor_1_Foam_timer(java.lang.Integer value)
	{
		setDigitalOutput("Motor_1_Foam_timer", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_DrinkReady</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_DrinkReady'
	 */
	public boolean getOut_DrinkReady()
	{
		return getBooleanIOValue("Out_DrinkReady", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_DrinkReady</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_DrinkReady'
	 */
	public void setOut_DrinkReady(java.lang.Boolean value)
	{
		setDigitalOutput("Out_DrinkReady", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_ErrorInDrinkPrep</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_ErrorInDrinkPrep'
	 */
	public boolean getOut_ErrorInDrinkPrep()
	{
		return getBooleanIOValue("Out_ErrorInDrinkPrep", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_ErrorInDrinkPrep</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_ErrorInDrinkPrep'
	 */
	public void setOut_ErrorInDrinkPrep(java.lang.Boolean value)
	{
		setDigitalOutput("Out_ErrorInDrinkPrep", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Servo1Start</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Servo1Start'
	 */
	public boolean getServo1Start()
	{
		return getBooleanIOValue("Servo1Start", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Servo1Start</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Servo1Start'
	 */
	public void setServo1Start(java.lang.Boolean value)
	{
		setDigitalOutput("Servo1Start", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Servo2Start</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Servo2Start'
	 */
	public boolean getServo2Start()
	{
		return getBooleanIOValue("Servo2Start", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Servo2Start</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Servo2Start'
	 */
	public void setServo2Start(java.lang.Boolean value)
	{
		setDigitalOutput("Servo2Start", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_DrinkPrepBegan</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_DrinkPrepBegan'
	 */
	public boolean getOut_DrinkPrepBegan()
	{
		return getBooleanIOValue("Out_DrinkPrepBegan", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_DrinkPrepBegan</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_DrinkPrepBegan'
	 */
	public void setOut_DrinkPrepBegan(java.lang.Boolean value)
	{
		setDigitalOutput("Out_DrinkPrepBegan", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_IsGripping</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_IsGripping'
	 */
	public boolean getOut_IsGripping()
	{
		return getBooleanIOValue("Out_IsGripping", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_IsGripping</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_IsGripping'
	 */
	public void setOut_IsGripping(java.lang.Boolean value)
	{
		setDigitalOutput("Out_IsGripping", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_Pos3Continue</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_Pos3Continue'
	 */
	public boolean getIn_Pos3Continue()
	{
		return getBooleanIOValue("In_Pos3Continue", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AppStart_SP</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AppStart_SP'
	* 
	 * @deprecated The output 'AppStart_SP' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAppStart_SP()
	{
		return getBooleanIOValue("AppStart_SP", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>AppStart_SP</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AppStart_SP'
	* 
	 * @deprecated The output 'AppStart_SP' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setAppStart_SP(java.lang.Boolean value)
	{
		setDigitalOutput("AppStart_SP", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AppEnable_SP</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AppEnable_SP'
	* 
	 * @deprecated The output 'AppEnable_SP' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAppEnable_SP()
	{
		return getBooleanIOValue("AppEnable_SP", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>AppEnable_SP</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AppEnable_SP'
	* 
	 * @deprecated The output 'AppEnable_SP' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setAppEnable_SP(java.lang.Boolean value)
	{
		setDigitalOutput("AppEnable_SP", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_Force</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital input 'In_Force'
	 */
	public java.lang.Integer getIn_Force()
	{
		return getNumberIOValue("In_Force", false).intValue();
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_Force</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'Out_Force'
	 */
	public java.lang.Integer getOut_Force()
	{
		return getNumberIOValue("Out_Force", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_Force</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_Force'
	 */
	public void setOut_Force(java.lang.Integer value)
	{
		setDigitalOutput("Out_Force", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_ManualPullOut</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_ManualPullOut'
	 */
	public boolean getOut_ManualPullOut()
	{
		return getBooleanIOValue("Out_ManualPullOut", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_ManualPullOut</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_ManualPullOut'
	 */
	public void setOut_ManualPullOut(java.lang.Boolean value)
	{
		setDigitalOutput("Out_ManualPullOut", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_PullOutServo</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_PullOutServo'
	 */
	public boolean getOut_PullOutServo()
	{
		return getBooleanIOValue("Out_PullOutServo", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_PullOutServo</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_PullOutServo'
	 */
	public void setOut_PullOutServo(java.lang.Boolean value)
	{
		setDigitalOutput("Out_PullOutServo", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_ManualPullOut</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_ManualPullOut'
	 */
	public boolean getIn_ManualPullOut()
	{
		return getBooleanIOValue("In_ManualPullOut", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_OPCUA_beer_start_left</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_OPCUA_beer_start_left'
	 */
	public boolean getIn_OPCUA_beer_start_left()
	{
		return getBooleanIOValue("In_OPCUA_beer_start_left", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>In_OPCUA_beer_start_right</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'In_OPCUA_beer_start_right'
	 */
	public boolean getIn_OPCUA_beer_start_right()
	{
		return getBooleanIOValue("In_OPCUA_beer_start_right", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_OpenCupDispencer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_OpenCupDispencer'
	 */
	public boolean getOut_OpenCupDispencer()
	{
		return getBooleanIOValue("Out_OpenCupDispencer", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_OpenCupDispencer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_OpenCupDispencer'
	 */
	public void setOut_OpenCupDispencer(java.lang.Boolean value)
	{
		setDigitalOutput("Out_OpenCupDispencer", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_CloseCupDispencer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_CloseCupDispencer'
	 */
	public boolean getOut_CloseCupDispencer()
	{
		return getBooleanIOValue("Out_CloseCupDispencer", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_CloseCupDispencer</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_CloseCupDispencer'
	 */
	public void setOut_CloseCupDispencer(java.lang.Boolean value)
	{
		setDigitalOutput("Out_CloseCupDispencer", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_CupReceivedByHooman</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_CupReceivedByHooman'
	 */
	public boolean getOut_CupReceivedByHooman()
	{
		return getBooleanIOValue("Out_CupReceivedByHooman", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_CupReceivedByHooman</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_CupReceivedByHooman'
	 */
	public void setOut_CupReceivedByHooman(java.lang.Boolean value)
	{
		setDigitalOutput("Out_CupReceivedByHooman", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_RobotReadyForOrder</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_RobotReadyForOrder'
	 */
	public boolean getOut_RobotReadyForOrder()
	{
		return getBooleanIOValue("Out_RobotReadyForOrder", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_RobotReadyForOrder</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_RobotReadyForOrder'
	 */
	public void setOut_RobotReadyForOrder(java.lang.Boolean value)
	{
		setDigitalOutput("Out_RobotReadyForOrder", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_SecondBeerPosition</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_SecondBeerPosition'
	 */
	public boolean getOut_SecondBeerPosition()
	{
		return getBooleanIOValue("Out_SecondBeerPosition", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_SecondBeerPosition</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_SecondBeerPosition'
	 */
	public void setOut_SecondBeerPosition(java.lang.Boolean value)
	{
		setDigitalOutput("Out_SecondBeerPosition", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_ThirdBeerPosition</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_ThirdBeerPosition'
	 */
	public boolean getOut_ThirdBeerPosition()
	{
		return getBooleanIOValue("Out_ThirdBeerPosition", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_ThirdBeerPosition</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_ThirdBeerPosition'
	 */
	public void setOut_ThirdBeerPosition(java.lang.Boolean value)
	{
		setDigitalOutput("Out_ThirdBeerPosition", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Out_BeerIsServed</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Out_BeerIsServed'
	 */
	public boolean getOut_BeerIsServed()
	{
		return getBooleanIOValue("Out_BeerIsServed", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Out_BeerIsServed</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Out_BeerIsServed'
	 */
	public void setOut_BeerIsServed(java.lang.Boolean value)
	{
		setDigitalOutput("Out_BeerIsServed", value);
	}

}
