/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.*/
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3339.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Drivetrain
	public static final int FRONT_RIGHT = 5; // TalonSRX 5
	public static final int FRONT_RIGHT_PDP = 2; // PDP Port
	public static final int MIDDLE_RIGHT = 4; // TalonSRX 4
	public static final int MIDDLE_RIGHT_PDP = 1; // PDP Port
	public static final int REAR_RIGHT = 3; // TalonSRX 3
	public static final int REAR_RIGHT_PDP = 0; // PDP Port
	public static final int FRONT_LEFT = 2; // TalonSRX 2
	public static final int FRONT_LEFT_PDP = 13; // PDP Port
	public static final int MIDDLE_LEFT = 1; // TalonSRX 1
	public static final int MIDDLE_LEFT_PDP = 14; // PDP Port
	public static final int REAR_LEFT = 0; // TalonSRX 0
	public static final int REAR_LEFT_PDP = 15; // PDP Port

	// Cube Intake
	public static final int INTAKE_LEFT = 9; // VictorSPX
	public static final int INTAKE_LEFT_PDP = 9; // PDP Port
	public static final int INTAKE_RIGHT = 10; // VictorSPX
	public static final int INTAKE_RIGHT_PDP = 6; // PDP Port
	public static final int RIGHT_INTAKE_IR = 1; // Analog
	public static final int LEFT_INTAKE_IR = 2; // Analog

	// Cube Arm
	public static final int ARM_MOTOR = 8; // TalonSRX
	public static final int ARM_MOTOR_PDP = 5; // PDP Port
	public static final int ARM_POTENTIOMETER = 0;

	// Cube Lift
	public static final int LIFT_MOTOR = 6; // TalonSRX 6
	public static final int LIFT_MOTOR_PDP = 12; // PDP Port
	public static final int LIFT_MOTOR_FOLLOWER = 7; // TalonSRX 7
	public static final int LIFT_MOTOR_FOLLOWER_PDP = 3; // PDP Port

	// Climb
	public static final int LEFT_CLIMB_ROLLER_MOTOR = 0; // VictorSP
	public static final int LEFT_CLIMB_ROLLER_MOTOR_PDP = 8;
	public static final int RIGHT_CLIMB_ROLLER_MOTOR = 1; // VictorSP
	public static final int RIGHT_CLIMB_ROLLER_MOTOR_PDP = 7;
	public static final int CLIMB_ARM_MOTOR = 2; // VictorSP
	public static final int CLIMB_ARM_MOTOR_PDP = 10; // PDP Port
	public static final int CLIMB_ARM_SERVO = 3; // PWM

	// Controller Ports
	public static final int DRIVER_PORT = 1;
	public static final int OPERATOR_PORT = 0;
}
