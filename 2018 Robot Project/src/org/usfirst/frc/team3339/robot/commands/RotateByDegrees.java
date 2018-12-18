package org.usfirst.frc.team3339.robot.commands;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateByDegrees extends Command {

	private double degrees;
	private int stopCount = 0;
	private boolean isAbsolute = false;
	private boolean customPID = false;
	private double kP = 0.0, kI = 0.0, kD = 0.0;

	public RotateByDegrees(double degrees) {
		requires(Robot.m_drivetrain);
		this.degrees = degrees;
		this.isAbsolute = false;
	}

	public RotateByDegrees(double degrees, double kP, double kI, double kD) {
		requires(Robot.m_drivetrain);
		this.degrees = degrees;
		this.isAbsolute = false;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		customPID = true;
	}

	public RotateByDegrees(double degrees, boolean isAbsolute) {
		requires(Robot.m_drivetrain);
		this.degrees = degrees;
		this.isAbsolute = isAbsolute;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("start");
		stopCount = 0;

		if (customPID)
			Robot.m_drivetrain.setRotateByDegreesPID(kP, kI, kD);

		Robot.m_drivetrain.initRotateByDegrees(degrees, isAbsolute);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (Robot.m_drivetrain.isRotateByDegreesOnTarget()) {
			if (!Robot.m_drivetrain.getNavxIsRotating()) {
				stopCount++;
			} else {
				stopCount = 0;
			}
		}
		return stopCount >= 8; // 8 * 20 = 160 ms
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_drivetrain.endDrivetrainPID();
		if (customPID)
			Robot.m_drivetrain.setRotateByDegreesPID(ROBOT_PROFILE.drivetrainParams.rbd_kP,
					ROBOT_PROFILE.drivetrainParams.rbd_kI, ROBOT_PROFILE.drivetrainParams.rbd_kD);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
