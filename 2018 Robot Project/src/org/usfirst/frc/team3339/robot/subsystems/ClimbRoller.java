package org.usfirst.frc.team3339.robot.subsystems;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbRoller extends Subsystem {

	private VictorSP leftClimb = new VictorSP(RobotMap.LEFT_CLIMB_ROLLER_MOTOR);
	private VictorSP rightClimb = new VictorSP(RobotMap.RIGHT_CLIMB_ROLLER_MOTOR);
	private static double POWER_UP = ROBOT_PROFILE.climbRollerParams.power_up;
	private static double POWER_DOWN = ROBOT_PROFILE.climbRollerParams.power_down;

	public ClimbRoller() {
		leftClimb.setInverted(ROBOT_PROFILE.climbRollerParams.is_left_inverted);
		rightClimb.setInverted(ROBOT_PROFILE.climbRollerParams.is_right_inverted);
	}

	public void climbUp() {
		leftClimb.set(POWER_UP);
		rightClimb.set(POWER_UP);
	}

	public void climbDown() {
		leftClimb.set(POWER_DOWN);
		rightClimb.set(POWER_DOWN);
	}

	public void stopClimb() {
		leftClimb.set(0);
		rightClimb.set(0);
	}

	@Override
	public void initDefaultCommand() {

	}

	@Override
	public void periodic() {
	}
}
