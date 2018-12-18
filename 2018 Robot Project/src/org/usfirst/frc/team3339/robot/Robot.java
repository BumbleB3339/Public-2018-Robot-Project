/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3339.robot;

import org.usfirst.frc.team3339.robot.autonomous.AutoChooser;
import org.usfirst.frc.team3339.robot.commands.climb.initClimbServo;
import org.usfirst.frc.team3339.robot.subsystems.ClimbArm;
import org.usfirst.frc.team3339.robot.subsystems.ClimbRoller;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm;
import org.usfirst.frc.team3339.robot.subsystems.CubeIntake;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;
import org.usfirst.frc.team3339.robot.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.PathRegistry;
import util.pixy.PixyVision;
import util.pixy.PixyVision.PowerCubeType;

@SuppressWarnings("unused")
public class Robot extends TimedRobot {

	public static boolean IS_CALIBRATION_MODE = false;

	public static boolean IS_AUTO_TEST_MODE_ACTIVE = false;

	/**
	 * Robot state machine
	 */
	public enum RobotMode {
		FOLDED, COLLECT, EXCHANGE, SWITCH, SCALE
	}

	public enum AllianceColor {
		RED, BLUE
	}

	public static PowerCubeType powerCubeType = PowerCubeType.REGULAR_POWER_CUBE; // TODO: Change to regular in
																					// competition

	public static RobotMode robotMode = RobotMode.FOLDED;

	// Subsystems
	public static CubeIntake m_cubeIntake = new CubeIntake();
	public static CubeArm m_cubeArm = new CubeArm();
	public static CubeLift m_cubeLift = new CubeLift();
	public static PixyVision m_pixyVision = new PixyVision();
	public static Drivetrain m_drivetrain = new Drivetrain();
	public static ClimbRoller m_climbRoller = new ClimbRoller();
	public static ClimbArm m_climbArm = new ClimbArm();

	public static PowerDistributionPanel PDP = new PowerDistributionPanel();

	public static OI m_oi;

	// Drive Type Selector
	public static String selectedDriveMode = "Default";
	SendableChooser<String> driveModeChooser = new SendableChooser<>();

	CommandGroup m_autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		new initClimbServo().start();

		if (IS_CALIBRATION_MODE) {
			Calibration.initDashboardParameters();
		} else {
			m_oi = new OI();
		}

		AutoChooser.initDashboardChoosers();

		// Drive Type Selector
		driveModeChooser.addDefault("TRX", "TRX");
		driveModeChooser.addObject("Tank", "Tank");
		driveModeChooser.addObject("GTA", "GTA");
		SmartDashboard.putData("Drive Mode", driveModeChooser);

		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(320, 240);
			camera.setFPS(24);
			camera.setExposureManual(40);

		}).start();

		m_drivetrain.resetNavx();

		// This init of the system is required for autonomous to work!
		Robot.m_cubeLift.setPosition(CubeLiftState.GROUND);

		// Make sure all paths are generated
		PathRegistry.getInstance().generate();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		// To make it easier to move the robot on the field when testing autonomous not
		// at a competition
		if (!DriverStation.getInstance().isFMSAttached()) {
			Robot.m_drivetrain.setDrivetrainNeutralMode(NeutralMode.Coast);
		}
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		AutoChooser.updateDashboardChoosersConfirmation();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		new initClimbServo().start();
		m_drivetrain.resetNavx();
		m_drivetrain.setVoltageCompensation(true);
		m_drivetrain.enableRampRate(false);
		Robot.m_drivetrain.setDrivetrainNeutralMode(NeutralMode.Brake);

		m_autonomousCommand = AutoChooser.getAutoCommand();

		// temp
		// AutoSide side = AutoSide.RIGHT;
		// AutoPoints.createWaypoints(AllianceColor.RED, side);
		// AutoParams.adjustAnglesToLeft(side);
		// m_autonomousCommand = new SideFarScaleX2Improved();
		// -----

		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().removeAll();
		new initClimbServo().start();
		Robot.m_drivetrain.setDrivetrainNeutralMode(NeutralMode.Brake);

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}

		if (IS_CALIBRATION_MODE) {
			m_drivetrain.setVoltageCompensation(true);
			m_drivetrain.enableRampRate(false);

			m_drivetrain.resetNavx();
			Calibration.startCalibration();
		} else {
			m_drivetrain.setVoltageCompensation(false);
			m_drivetrain.enableRampRate(true);
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		if (!IS_CALIBRATION_MODE) {
			OI.periodic();
		} else {
			Calibration.calibrationPeriodic();
		}
	}

	/**
	 * Initialization code for test mode should go here.
	 */
	@Override
	public void testInit() {
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	/**
	 * This function is called periodically during all robot modes.
	 */
	@Override
	public void robotPeriodic() {

		// Drive Type Selector
		selectedDriveMode = driveModeChooser.getSelected();
		SmartDashboard.putString("Active Drive Mode", selectedDriveMode);
		SmartDashboard.putData("Drive Mode", driveModeChooser);
		// ---

		BumbleDashboard.updateGameTab();
		BumbleDashboard.updateHardwareTab();
		BumbleDashboard.uptadeLogicTab();
	}

	public static void setRobotMode(RobotMode robotMode) {
		Robot.robotMode = robotMode;
	}

	public static boolean isInRobotMode(RobotMode robotMode) {
		// check that not in autonomous to prevent unwanted operation logic happening in
		// that period
		return Robot.robotMode == robotMode && !DriverStation.getInstance().isAutonomous();
	}
}
