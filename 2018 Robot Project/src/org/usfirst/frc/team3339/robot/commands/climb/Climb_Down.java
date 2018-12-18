package org.usfirst.frc.team3339.robot.commands.climb;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climb_Down extends Command {

	public Climb_Down() {
		requires(Robot.m_climbRoller);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.m_climbRoller.climbDown();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_climbRoller.stopClimb();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
