package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
	public Drive() {
		requires(Robot.m_drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.m_drivetrain.initDriveMode();
	}

	@Override
	protected void execute() {
		switch (Robot.selectedDriveMode) {
		case "GTA":
			Robot.m_drivetrain.bumbleDrive.GTADrive(OI.driverController.getX(Hand.kLeft),
					OI.driverController.getTriggerAxis(Hand.kRight), OI.driverController.getTriggerAxis(Hand.kLeft),
					OI.driverController.getAButton());
			break;
		case "TRX":
			Robot.m_drivetrain.bumbleDrive.TRXDrive(OI.driverController.getTriggerAxis(Hand.kLeft),
					OI.driverController.getTriggerAxis(Hand.kRight), OI.driverController.getY(Hand.kLeft),
					OI.driverController.getAButton());
			break;
		case "Tank": // Tank is default
		default:
			Robot.m_drivetrain.bumbleDrive.tankDrive(OI.driverController.getY(Hand.kLeft),
					OI.driverController.getY(Hand.kRight));
			break;
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.stopMotors();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
