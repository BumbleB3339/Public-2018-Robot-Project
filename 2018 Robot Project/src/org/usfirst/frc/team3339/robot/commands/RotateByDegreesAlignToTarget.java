package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import util.pixy.PixyVision.PowerCubeType;

/**
 *
 */
public class RotateByDegreesAlignToTarget extends Command {
	private PowerCubeType powerCubeType;
	private double angleToTarget;

	public RotateByDegreesAlignToTarget(PowerCubeType powerCubeType) {
		requires(Robot.m_drivetrain);
		this.powerCubeType = powerCubeType;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		angleToTarget = Robot.m_pixyVision.getAngle(powerCubeType);
		Robot.m_drivetrain.initRotateByDegrees(angleToTarget, false);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.m_drivetrain.isRotateByDegreesOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_drivetrain.endDrivetrainPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
