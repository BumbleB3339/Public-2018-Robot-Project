package org.usfirst.frc.team3339.robot.profiles;

import util.PIDPreset;

/**
 * An abstract class contains all the relevant robot parameters in 3 groupps:
 * autonamous, teleop, and general parameters.
 * 
 * @author BumbleB 3339
 *
 */
public abstract class RobotProfile {

	public GeneralParams generalParams = new GeneralParams();
	public AutonomousParams autonomousParams = new AutonomousParams();
	public TeleopParams teleopParams = new TeleopParams();
	public CubeArmParams cubeArmParams = new CubeArmParams();
	public CubeIntakeParams cubeIntakeParams = new CubeIntakeParams();
	public CubeLiftParams cubeLiftParams = new CubeLiftParams();
	public DrivetrainParams drivetrainParams = new DrivetrainParams();
	public ClimbArmParams climbArmParams = new ClimbArmParams();
	public ClimbRollerParams climbRollerParams = new ClimbRollerParams();

	public static class GeneralParams {
		public double wheel_diameter, wheel_base_width, dt, robot_length, real_width, cube_cut, cor_shift;
		public int ticks_per_rev;

	}

	public static class AutonomousParams {
		public double max_acceleration, max_jerk, kP, kD, kV, kA, gP, gD;
		public PIDPreset slow_path_pid_preset, fast_path_pid_preset;
	}

	public static class TeleopParams {
		// TODO: Add relevant parameters
	}

	public static class CubeArmParams {
		public double up_manual_power, down_manual_power;
		public double base_power_up, base_power_down, tolerance, max_up, max_down;
		public double voltage0, voltage90;
		public boolean is_inverted;
		public double kP, kI, kD;
	}

	public static class CubeIntakeParams {
		public boolean is_inverted_right, is_inverted_left;
	}

	public static class CubeLiftParams {
		public int max_allowable_lift_height_ticks;
		public double scale_high_height, base_power, ramp_rate;
		public boolean sensor_phase, is_main_inverted, is_follower_inverted;
		public PIDPreset up_movement_pid_preset, down_movement_pid_preset;
	}

	public static class DrivetrainParams {
		public double rbd_kP, rbd_kI, rbd_kD, pixy_kP, pixy_kI, pixy_kD, rbd_minimum_output, rbd_maximum_output;
		public boolean is_inverted_right, is_inverted_left, right_sensor_phase, left_sensor_phase;
	}

	public static class ClimbArmParams {
		public double climb_arm_power_open, climb_arm_power_close;
		public boolean is_inverted;
	}

	public static class ClimbRollerParams {
		public double power_up, power_down;
		public boolean is_right_inverted, is_left_inverted;
	}
}
