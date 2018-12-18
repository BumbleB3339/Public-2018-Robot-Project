package org.usfirst.frc.team3339.robot.commands.climb;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseClimbArm extends Command {

	public CloseClimbArm() {
		requires(Robot.m_climbArm);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.m_climbArm.closeArm();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_climbArm.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
