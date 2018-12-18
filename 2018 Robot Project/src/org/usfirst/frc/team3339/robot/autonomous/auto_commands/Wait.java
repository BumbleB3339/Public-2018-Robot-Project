package org.usfirst.frc.team3339.robot.autonomous.auto_commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Wait extends Command {

	private double time;

	public Wait(double time) {
		this.time = time;
	}

	@Override
	protected void initialize() {
		super.setTimeout(time);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
