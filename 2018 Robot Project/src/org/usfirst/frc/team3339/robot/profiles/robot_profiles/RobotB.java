package org.usfirst.frc.team3339.robot.profiles.robot_profiles;

import org.usfirst.frc.team3339.robot.profiles.RobotProfile;

import util.PIDPreset;

/**
 * Robot profile of Robot B with all the relevant parameters.
 * 
 * @author BumbleB 3339
 *
 */
public class RobotB extends RobotProfile {

	public RobotB() {
		// General Parmeters Initialization
		generalParams.wheel_diameter = 0.1016;
		generalParams.wheel_base_width = 0.59;
		generalParams.robot_length = 0.98; // TODO Measure actual value
		generalParams.ticks_per_rev = 4096;
		generalParams.dt = 0.02;
		generalParams.real_width = 0.86;
		generalParams.cube_cut = 0.2;
		generalParams.cor_shift = 0.55;

		// Autonomous & Motion Profiling Parameters Initialization
		autonomousParams.max_acceleration = 3.5; // 19.842;
		autonomousParams.max_jerk = 60.0;
		autonomousParams.kP = 0.23; // for a3.5 v1.5
		autonomousParams.kD = 0.3;
		autonomousParams.kV = 0.1812;
		autonomousParams.kA = 0;
		autonomousParams.gP = 0.08;
		autonomousParams.gD = 0.0;
		autonomousParams.slow_path_pid_preset = new PIDPreset(0.38, 0.0, 0.26); // No kI Needed
		autonomousParams.fast_path_pid_preset = new PIDPreset(0.35, 0.0, 0.25); // No kI Needed

		// autonomousParams.kP = 0.1674; //for a3.5 v1.5
		// autonomousParams.kD = 0.3339;

		// autonomousParams.kP = 0.075; //for a3.5 v3.0
		// autonomousParams.kD = 0.25;

		// Teleop Parmeters Initialization

		// CubeLift Parameters
		cubeLiftParams.up_movement_pid_preset = new PIDPreset(0.087, 0.0, 1.2);
		cubeLiftParams.down_movement_pid_preset = new PIDPreset(0.068, 0.0, 0.09);
		cubeLiftParams.scale_high_height = 165;
		cubeLiftParams.is_follower_inverted = false;
		cubeLiftParams.is_main_inverted = true;
		cubeLiftParams.sensor_phase = true;
		cubeLiftParams.max_allowable_lift_height_ticks = 45500;
		cubeLiftParams.base_power = 0.1;
		cubeLiftParams.ramp_rate = 0.605;

		// CubeArm Parameters
		cubeArmParams.up_manual_power = 0.4;
		cubeArmParams.down_manual_power = 0.2;
		cubeArmParams.kP = 0.016;
		cubeArmParams.base_power_down = 0.05;
		cubeArmParams.base_power_up = 0.15;
		cubeArmParams.tolerance = 4;
		cubeArmParams.voltage0 = 2.575;
		cubeArmParams.voltage90 = 2.11;
		cubeArmParams.max_up = 1;
		cubeArmParams.max_down = 0.5;
		cubeArmParams.is_inverted = true;

		// CubeIntake Parameters
		cubeIntakeParams.is_inverted_right = true;
		cubeIntakeParams.is_inverted_left = false;

		// Drivetrain Parameters
		drivetrainParams.pixy_kP = 0.007; // pixy
		drivetrainParams.pixy_kI = 0.0; // pixy
		drivetrainParams.pixy_kD = 0.022; // pixy
		drivetrainParams.rbd_kP = 0.07; // Rotate By Degrees
		drivetrainParams.rbd_kI = 0.0; // Rotate By Degrees
		drivetrainParams.rbd_kD = 0.34; // Rotate By Degrees
		drivetrainParams.right_sensor_phase = false; // Sensor Phase
		drivetrainParams.left_sensor_phase = false; // Sensor Phase
		drivetrainParams.is_inverted_right = false; // Inverted
		drivetrainParams.is_inverted_left = true; // Inverted
		drivetrainParams.rbd_minimum_output = -0.7; // Rotate By Degrees
		drivetrainParams.rbd_maximum_output = 0.7; // Rotate By Degrees

		// ClimbArm Parameters
		climbArmParams.is_inverted = true;
		climbArmParams.climb_arm_power_close = -0.9;
		climbArmParams.climb_arm_power_open = 0.9;

		// ClimbRoller Parameters
		climbRollerParams.is_left_inverted = true;
		climbRollerParams.is_right_inverted = false;
		climbRollerParams.power_down = -1.0;
		climbRollerParams.power_up = 1.0;
	}
}
