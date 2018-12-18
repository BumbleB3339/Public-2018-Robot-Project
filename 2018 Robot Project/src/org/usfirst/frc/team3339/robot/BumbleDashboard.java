package org.usfirst.frc.team3339.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BumbleDashboard {
	public static void updateGameTab() {

		SmartDashboard.putString("Game/Robot Mode", Robot.robotMode.toString());

		Robot.m_drivetrain.sendSmartdashboardGameTab();
		Robot.m_cubeIntake.sendSmartdashboardGameTab();
		Robot.m_cubeLift.sendSmartdashboardGameTab();
		Robot.m_cubeArm.sendSmartdashboardGameTab();
		Robot.m_pixyVision.sendSmartdashboardGameTab();
	}

	public static void updateHardwareTab() {

		Robot.m_drivetrain.sendSmartdashboardDebuggingHardware();
		Robot.m_cubeIntake.sendSmartdashboardDebuggingHardware();
		Robot.m_cubeLift.sendSmartdashboardDebuggingHardware();
		Robot.m_cubeArm.sendSmartdashboardDebuggingHardware();
		Robot.m_pixyVision.sendSmartdashboardDebuggingHardware();

		// PDP Disabled because of error to DS
		// SmartDashboard.putData("Hardware/PDP", Robot.PDP); // TODO: Disable in real
		// match

		// try {
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Front Right Drive",
		// Robot.PDP.getCurrent(RobotMap.FRONT_RIGHT_PDP));
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Rear Right Drive",
		// Robot.PDP.getCurrent(RobotMap.REAR_RIGHT_PDP));
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Middle Right Drive",
		// Robot.PDP.getCurrent(RobotMap.MIDDLE_RIGHT_PDP));
		//
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Front Left Drive",
		// Robot.PDP.getCurrent(RobotMap.FRONT_LEFT_PDP));
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Middle Left Drive",
		// Robot.PDP.getCurrent(RobotMap.MIDDLE_LEFT_PDP));
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Rear Left Drive",
		// Robot.PDP.getCurrent(RobotMap.REAR_LEFT_PDP));
		//
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Master Lift",
		// Robot.PDP.getCurrent(RobotMap.LIFT_MOTOR_PDP));
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Follower Lift",
		// Robot.PDP.getCurrent(RobotMap.LIFT_MOTOR_FOLLOWER_PDP));
		//
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Cube Arm",
		// Robot.PDP.getCurrent(RobotMap.ARM_MOTOR_PDP));
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Intake Left",
		// Robot.PDP.getCurrent(RobotMap.INTAKE_LEFT_PDP));
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/Intake Right",
		// Robot.PDP.getCurrent(RobotMap.INTAKE_RIGHT_PDP));
		//
		// SmartDashboard.putNumber("Hardware/PDP/PDP ports/LED",
		// Robot.PDP.getCurrent(RobotMap.LED_PDP));
		// } catch (Exception e) {
		// }

	}

	public static void uptadeLogicTab() {
		SmartDashboard.putString("Logic/Robot Mode", Robot.robotMode.toString());
		Robot.m_drivetrain.sendSmartdashboardDebuggingLogic();
		Robot.m_cubeIntake.sendSmartdashboardDebuggingLogic();
		Robot.m_cubeLift.sendSmartdashboardDebuggingLogic();
		Robot.m_cubeArm.sendSmartdashboardDebuggingLogic();
		Robot.m_pixyVision.sendSmartdashboardDebuggingLogic();
	}
}
