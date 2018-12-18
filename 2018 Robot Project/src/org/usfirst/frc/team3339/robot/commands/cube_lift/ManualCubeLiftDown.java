package org.usfirst.frc.team3339.robot.commands.cube_lift;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualCubeLiftDown extends Command {

	public ManualCubeLiftDown() {
		requires(Robot.m_cubeLift);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.m_cubeLift.startManualMovingMode();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.m_cubeLift.manualCubeLiftDown();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_cubeLift.stopManualMovingMode();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}