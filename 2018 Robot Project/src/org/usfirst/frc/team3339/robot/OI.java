/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3339.robot;

import org.usfirst.frc.team3339.robot.Robot.RobotMode;
import org.usfirst.frc.team3339.robot.commands.ExecuteCollection;
import org.usfirst.frc.team3339.robot.commands.ExecuteFolded;
import org.usfirst.frc.team3339.robot.commands.InitExchangeMode;
import org.usfirst.frc.team3339.robot.commands.InitScaleMode;
import org.usfirst.frc.team3339.robot.commands.LowerCollectHeightMode;
import org.usfirst.frc.team3339.robot.commands.PixyAlignToTargetTeleop;
import org.usfirst.frc.team3339.robot.commands.RaiseCollectHeightMode;
import org.usfirst.frc.team3339.robot.commands.ReleaseSwitchCube;
import org.usfirst.frc.team3339.robot.commands.SetRobotMode;
import org.usfirst.frc.team3339.robot.commands.SignalCubeIn;
import org.usfirst.frc.team3339.robot.commands.climb.Climb_Up;
import org.usfirst.frc.team3339.robot.commands.climb.CloseClimbArm;
import org.usfirst.frc.team3339.robot.commands.climb.OpenClimbArm;
import org.usfirst.frc.team3339.robot.commands.cube_arm.CubeArmSemiAutomatic;
import org.usfirst.frc.team3339.robot.commands.cube_arm.InitCubeArmSemiAutomatic;
import org.usfirst.frc.team3339.robot.commands.cube_arm.ManualCubeArmDown;
import org.usfirst.frc.team3339.robot.commands.cube_arm.ManualCubeArmUp;
import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_arm.ToggleCubeArmManualMode;
import org.usfirst.frc.team3339.robot.commands.cube_arm.ToggleCubeArmPosition;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeInsert;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;
import org.usfirst.frc.team3339.robot.commands.cube_intake.ToggleCubeIntakeManualOverride;
import org.usfirst.frc.team3339.robot.commands.cube_lift.FineTuneScaleHeightDown;
import org.usfirst.frc.team3339.robot.commands.cube_lift.FineTuneScaleHeightUp;
import org.usfirst.frc.team3339.robot.commands.cube_lift.FineTuneSwitchHeightDown;
import org.usfirst.frc.team3339.robot.commands.cube_lift.FineTuneSwitchHeightUp;
import org.usfirst.frc.team3339.robot.commands.cube_lift.InterruptLift;
import org.usfirst.frc.team3339.robot.commands.cube_lift.ManualCubeLiftDown;
import org.usfirst.frc.team3339.robot.commands.cube_lift.ManualCubeLiftUp;
import org.usfirst.frc.team3339.robot.commands.cube_lift.ResetCubeLiftEncoder;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetScaleHeightMode;
import org.usfirst.frc.team3339.robot.commands.cube_lift.toggleCubeLiftManualOverride;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.ScaleHeightMode;
import org.usfirst.frc.team3339.robot.triggers.TriggerAlignToTarget;
import org.usfirst.frc.team3339.robot.triggers.TriggerChangeCollectHeightModeDown;
import org.usfirst.frc.team3339.robot.triggers.TriggerChangeCollectHeightModeUp;
import org.usfirst.frc.team3339.robot.triggers.TriggerClimbArmClose;
import org.usfirst.frc.team3339.robot.triggers.TriggerClimbArmForceOpen;
import org.usfirst.frc.team3339.robot.triggers.TriggerClimbArmOpen;
import org.usfirst.frc.team3339.robot.triggers.TriggerClimbUp;
import org.usfirst.frc.team3339.robot.triggers.TriggerCubeArmGoToSemiAutomatic;
import org.usfirst.frc.team3339.robot.triggers.TriggerCubeArmSemiAutomatic;
import org.usfirst.frc.team3339.robot.triggers.TriggerCubeIntakeManualMode;
import org.usfirst.frc.team3339.robot.triggers.TriggerCubeLiftManualMode;
import org.usfirst.frc.team3339.robot.triggers.TriggerDriverToSwitchMode;
import org.usfirst.frc.team3339.robot.triggers.TriggerExecuteCollection;
import org.usfirst.frc.team3339.robot.triggers.TriggerExecuteFolded;
import org.usfirst.frc.team3339.robot.triggers.TriggerFineTuneScaleHeightDown;
import org.usfirst.frc.team3339.robot.triggers.TriggerFineTuneScaleHeightUp;
import org.usfirst.frc.team3339.robot.triggers.TriggerFineTuneSwitchHeightDown;
import org.usfirst.frc.team3339.robot.triggers.TriggerFineTuneSwitchHeightUp;
import org.usfirst.frc.team3339.robot.triggers.TriggerInitExchangeMode;
import org.usfirst.frc.team3339.robot.triggers.TriggerInitScaleMode;
import org.usfirst.frc.team3339.robot.triggers.TriggerInitSwitchMode;
import org.usfirst.frc.team3339.robot.triggers.TriggerInterruptLift;
import org.usfirst.frc.team3339.robot.triggers.TriggerManualCubeArmDown;
import org.usfirst.frc.team3339.robot.triggers.TriggerManualCubeArmUp;
import org.usfirst.frc.team3339.robot.triggers.TriggerManualCubeLiftDown;
import org.usfirst.frc.team3339.robot.triggers.TriggerManualCubeLiftUp;
import org.usfirst.frc.team3339.robot.triggers.TriggerReleaseCubeExchange;
import org.usfirst.frc.team3339.robot.triggers.TriggerResetCubeLiftEncoderPosition;
import org.usfirst.frc.team3339.robot.triggers.TriggerScaleInsertCube;
import org.usfirst.frc.team3339.robot.triggers.TriggerScaleReleaseCube;
import org.usfirst.frc.team3339.robot.triggers.TriggerSetScaleHeightModeHigh;
import org.usfirst.frc.team3339.robot.triggers.TriggerSetScaleHeightModeLow;
import org.usfirst.frc.team3339.robot.triggers.TriggerSignalCubeIn;
import org.usfirst.frc.team3339.robot.triggers.TriggerSwitchCubeArmPosition;
import org.usfirst.frc.team3339.robot.triggers.TriggerSwitchReleaseCube;
import org.usfirst.frc.team3339.robot.triggers.TriggerToggleCubeArmManualMode;
import org.usfirst.frc.team3339.robot.triggers.robotModesTriggers.TriggerSetCollectRobotMode;
import org.usfirst.frc.team3339.robot.triggers.robotModesTriggers.TriggerSetExchangeRobotMode;
import org.usfirst.frc.team3339.robot.triggers.robotModesTriggers.TriggerSetFoldedRobotMode;
import org.usfirst.frc.team3339.robot.triggers.robotModesTriggers.TriggerSetScaleRobotMode;
import org.usfirst.frc.team3339.robot.triggers.robotModesTriggers.TriggerSetSwitchRobotMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import util.BumbleController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static BumbleController driverController = new BumbleController(RobotMap.DRIVER_PORT);
	public static BumbleController operatorController = new BumbleController(RobotMap.OPERATOR_PORT);

	private TriggerManualCubeArmUp manualCubeArmUpTrigger = new TriggerManualCubeArmUp();
	private TriggerManualCubeArmDown manualCubeArmDownTrigger = new TriggerManualCubeArmDown();
	private TriggerManualCubeLiftUp manualCubeLiftUpTrigger = new TriggerManualCubeLiftUp();
	private TriggerManualCubeLiftDown manualCubeLiftDownTrigger = new TriggerManualCubeLiftDown();
	private TriggerSetCollectRobotMode setCollectRobotMode = new TriggerSetCollectRobotMode();
	private TriggerSetExchangeRobotMode setExchangeRobotMode = new TriggerSetExchangeRobotMode();
	private TriggerSetScaleRobotMode setScaleRobotMode = new TriggerSetScaleRobotMode();
	private TriggerSetSwitchRobotMode setSwitchRobotMode = new TriggerSetSwitchRobotMode();
	private TriggerSetFoldedRobotMode setFoldedRobotMode = new TriggerSetFoldedRobotMode();
	private TriggerExecuteCollection executeCollection = new TriggerExecuteCollection();
	private TriggerExecuteFolded executeFolded = new TriggerExecuteFolded();
	private TriggerChangeCollectHeightModeDown changeCollectHeightModeDown = new TriggerChangeCollectHeightModeDown();
	private TriggerChangeCollectHeightModeUp changeCollectHeightModeUp = new TriggerChangeCollectHeightModeUp();
	private TriggerReleaseCubeExchange releaseCubeExchange = new TriggerReleaseCubeExchange();
	private TriggerInitExchangeMode initExchangeMode = new TriggerInitExchangeMode();
	private TriggerInitScaleMode initScaleMode = new TriggerInitScaleMode();
	private TriggerScaleReleaseCube scaleReleaseCube = new TriggerScaleReleaseCube();
	private TriggerInitSwitchMode initSwitchMode = new TriggerInitSwitchMode();
	private TriggerSetScaleHeightModeHigh setScaleHeightModeHigh = new TriggerSetScaleHeightModeHigh();
	private TriggerSetScaleHeightModeLow setScaleHeightModeLow = new TriggerSetScaleHeightModeLow();
	private TriggerFineTuneScaleHeightDown fineTuneScaleHeightDown = new TriggerFineTuneScaleHeightDown();
	private TriggerFineTuneScaleHeightUp fineTuneScaleHeightUp = new TriggerFineTuneScaleHeightUp();
	private TriggerInterruptLift interruptLift = new TriggerInterruptLift();
	private TriggerCubeIntakeManualMode cubeIntakeManualMode = new TriggerCubeIntakeManualMode();
	private TriggerCubeLiftManualMode cubeLiftManualMode = new TriggerCubeLiftManualMode();
	private TriggerResetCubeLiftEncoderPosition resetCubeLiftEncoderPosition = new TriggerResetCubeLiftEncoderPosition();
	private TriggerScaleInsertCube scaleInsertCube = new TriggerScaleInsertCube();
	private TriggerClimbUp climbUp = new TriggerClimbUp();
	// private TriggerClimbDown climbDown = new TriggerClimbDown();
	private TriggerClimbArmOpen climbArmOpen = new TriggerClimbArmOpen();
	private TriggerClimbArmClose climbArmClose = new TriggerClimbArmClose();
	private TriggerSwitchCubeArmPosition switchCubeArmPosition = new TriggerSwitchCubeArmPosition();
	private TriggerCubeArmSemiAutomatic cubeArmSemiAutomatic = new TriggerCubeArmSemiAutomatic();
	private TriggerCubeArmGoToSemiAutomatic cubeArmGoToSemiAutomatic = new TriggerCubeArmGoToSemiAutomatic();
	private TriggerToggleCubeArmManualMode toggleCubeArmManualMode = new TriggerToggleCubeArmManualMode();
	private TriggerClimbArmForceOpen climbArmForceOpen = new TriggerClimbArmForceOpen();
	private TriggerDriverToSwitchMode driverToSwitchMode = new TriggerDriverToSwitchMode();
	private TriggerSwitchReleaseCube switchReleaseCube = new TriggerSwitchReleaseCube();
	private TriggerAlignToTarget alignToTarget = new TriggerAlignToTarget();
	private TriggerFineTuneSwitchHeightUp fineTuneSwitchHeightUp = new TriggerFineTuneSwitchHeightUp();
	private TriggerFineTuneSwitchHeightDown fineTuneSwitchHeightDown = new TriggerFineTuneSwitchHeightDown();
	private TriggerSignalCubeIn signalCubeIn = new TriggerSignalCubeIn();

	public static boolean readyForScale = false;

	public OI() {

		// Manual Cube Arm (always active)
		manualCubeArmUpTrigger.whileActive(new ManualCubeArmUp());
		manualCubeArmDownTrigger.whileActive(new ManualCubeArmDown());

		// manual mode
		manualCubeLiftUpTrigger.whileActive(new ManualCubeLiftUp());
		manualCubeLiftDownTrigger.whileActive(new ManualCubeLiftDown());

		// Set Logical Robot Modes
		setCollectRobotMode.whenActive(new SetRobotMode(Robot.RobotMode.COLLECT));
		setExchangeRobotMode.whenActive(new SetRobotMode(Robot.RobotMode.EXCHANGE));
		setScaleRobotMode.whenActive(new SetRobotMode(Robot.RobotMode.SCALE));
		setSwitchRobotMode.whenActive(new SetRobotMode(Robot.RobotMode.SWITCH));
		setFoldedRobotMode.whenActive(new SetRobotMode(Robot.RobotMode.FOLDED));

		// Collect Mode
		executeCollection.whenActive(new ExecuteCollection());
		executeCollection.whileActive(new CubeIntakeInsert());
		// ----------

		// Folded Mode
		executeFolded.whenActive(new ExecuteFolded());
		// ----------

		// Exchange Mode
		initExchangeMode.whenActive(new InitExchangeMode());
		releaseCubeExchange.whenActive(new CubeIntakeRelease());
		// ----------

		// Scale Mode
		initScaleMode.whenActive(new InitScaleMode());
		fineTuneScaleHeightDown.whileActive(new FineTuneScaleHeightDown());
		fineTuneScaleHeightUp.whileActive(new FineTuneScaleHeightUp());
		// ----------

		// Switch Mode
		initSwitchMode.whenActive(new SetCubeLiftPosition(CubeLiftState.SWITCH));
		driverToSwitchMode.whenActive(new SetCubeArmAngle(CubeArmState.SWITCH));
		// ----------

		// Climb up and down
		climbUp.whileActive(new Climb_Up());
		// climbDown.whileActive(new Climb_Down());

		// Climb arm open and close
		climbArmOpen.whileActive(new OpenClimbArm());
		climbArmForceOpen.whileActive(new OpenClimbArm(true));
		climbArmClose.whileActive(new CloseClimbArm());

		// Release Cube When In Scale or Switch Mode
		// scaleReleaseCube.whenInactive(new CubeIntakeRelease()); // Release cube for
		// minimal time in scale mode
		scaleReleaseCube.whileActive(new CubeIntakeRelease());
		switchReleaseCube.whenActive(new ReleaseSwitchCube());
		switchReleaseCube.whenInactive(new CubeIntakeRelease());
		// ----------

		// Collect Height Mode Changing
		changeCollectHeightModeDown.whenActive(new LowerCollectHeightMode());
		changeCollectHeightModeUp.whenActive(new RaiseCollectHeightMode());
		// ----------

		// Scale Height Mode Changing
		setScaleHeightModeHigh.whenActive(new SetScaleHeightMode(ScaleHeightMode.SCALE_HIGH));
		setScaleHeightModeLow.whenActive(new SetScaleHeightMode(ScaleHeightMode.SCALE_LOW));
		// ----------

		// Cube Arm Mode Switch
		switchCubeArmPosition.whenActive(new ToggleCubeArmPosition());
		// ----------

		// InterruptLift
		interruptLift.whenActive(new InterruptLift());
		// ----------

		// Manual Setting
		cubeIntakeManualMode.whenActive(new ToggleCubeIntakeManualOverride());
		cubeLiftManualMode.whenActive(new toggleCubeLiftManualOverride());
		toggleCubeArmManualMode.whenActive(new ToggleCubeArmManualMode());
		// ----------

		// Reset Encoder
		resetCubeLiftEncoderPosition.whenActive(new ResetCubeLiftEncoder());
		// ----------

		// Insert Cube in Switch and Scale
		scaleInsertCube.whileActive(new CubeIntakeInsert());
		// ----------

		// move to Semi Automatic
		cubeArmGoToSemiAutomatic.whenActive(new InitCubeArmSemiAutomatic());
		cubeArmSemiAutomatic.whileActive(new CubeArmSemiAutomatic());
		// ----------

		// align to cube
		alignToTarget.whileActive(new PixyAlignToTargetTeleop(Robot.powerCubeType));
		// ----------

		// fine tune switch height
		fineTuneSwitchHeightUp.whileActive(new FineTuneSwitchHeightUp());
		fineTuneSwitchHeightDown.whileActive(new FineTuneSwitchHeightDown());
		// ----------

		// signal cube in to driver
		signalCubeIn.whenActive(new SignalCubeIn());
		// ----------
	}

	private static boolean lastDriverBButton = false;
	private static boolean lastCollectMode = false;

	// Called from Robot.robotPeriodic()
	public static void periodic() {

		// Reset exchange parameters
		if (!Robot.isInRobotMode(RobotMode.EXCHANGE)) {
			TriggerReleaseCubeExchange.isB_ButtonRePressed = false;
		}

		if (Robot.isInRobotMode(RobotMode.COLLECT)) {
			if (!Robot.m_cubeIntake.isCubeIn()) {
				driverController.setRumble(RumbleType.kLeftRumble, 0.5);
				driverController.setRumble(RumbleType.kRightRumble, 0.5);
			}
		} else if (lastCollectMode) {
			driverController.setRumble(RumbleType.kLeftRumble, 0);
			driverController.setRumble(RumbleType.kRightRumble, 0);
		}

		lastCollectMode = Robot.isInRobotMode(RobotMode.COLLECT);

		// Reset scale parameters
		if (!Robot.isInRobotMode(RobotMode.SCALE))
			readyForScale = false;

		// Release logic
		if (driverController.getBButton() && !lastDriverBButton) {
			Robot.m_cubeIntake.isPowerRelease = false;
			Robot.m_cubeIntake.isRegularRelease = false;
		}

		Robot.m_cubeIntake.isPowerRelease = driverController.getBButton()
				? driverController.GetPOV_Up() || Robot.isInRobotMode(RobotMode.EXCHANGE)
				: Robot.m_cubeIntake.isPowerRelease;

		Robot.m_cubeIntake.isRegularRelease = driverController.getBButton()
				? driverController.getBumper(Hand.kRight) || Robot.isInRobotMode(RobotMode.SWITCH)
				: Robot.m_cubeIntake.isRegularRelease;

		lastDriverBButton = driverController.getBButton();
		// ----------
	}
}
