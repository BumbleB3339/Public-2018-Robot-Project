package org.usfirst.frc.team3339.robot.autonomous.auto_commands;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveByTime extends TimedCommand {

	public DriveByTime(double timeout) {
		super(timeout);
		requires(Robot.m_drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.m_drivetrain.initDriveMode();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.m_drivetrain.setLeftRightMotorOutputs(0.7, 0.7);
	}

	// Called once after timeout
	@Override
	protected void end() {
		Robot.m_drivetrain.stopMotors();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
