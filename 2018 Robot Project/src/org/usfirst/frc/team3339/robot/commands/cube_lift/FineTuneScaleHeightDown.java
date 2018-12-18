package org.usfirst.frc.team3339.robot.commands.cube_lift;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FineTuneScaleHeightDown extends Command {

	public FineTuneScaleHeightDown() {
		requires(Robot.m_cubeLift);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.m_cubeLift.fineTuneScaleHeightDown();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
