package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import util.pixy.PixyVision.PowerCubeType;

/**
 *
 */
public class PixyAlignToTargetTeleop extends Command {

	private PowerCubeType PIDCubeType;
	private boolean isEnabled = true;

	public PixyAlignToTargetTeleop(PowerCubeType PIDCubeType) {
		requires(Robot.m_drivetrain);
		this.PIDCubeType = PIDCubeType;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("init");
		Robot.m_drivetrain.initPixyAlignToCube(PIDCubeType);
		Robot.m_pixyVision.setPixyDataToNeutral();
		Robot.m_pixyVision.updatePixyData();
		isEnabled = true;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (isEnabled && Robot.m_pixyVision.getCenterX(Robot.powerCubeType) == -99) {
			end();
			isEnabled = false;
		} else if (!isEnabled && Robot.m_pixyVision.getCenterX(Robot.powerCubeType) != -99) {
			Robot.m_drivetrain.initPixyAlignToCube(PIDCubeType);
			isEnabled = true;
		}
		if (Robot.m_pixyVision.getCenterX(Robot.powerCubeType) == -99) {
			OI.driverController.setRumble(RumbleType.kLeftRumble, 1);
			OI.driverController.setRumble(RumbleType.kRightRumble, 1);
			Robot.m_drivetrain.setLeftRightMotorOutputs(0, 0);
		}
		Robot.m_drivetrain.bumbleDrive.setAligningXFactor(-OI.driverController.getY(Hand.kLeft));
		if (!isEnabled) {
			Robot.m_drivetrain.bumbleDrive.pidWrite(0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;// Robot.m_drivetrain.isPixyAlignToCubeOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_drivetrain.endDrivetrainPID();
		OI.driverController.setRumble(RumbleType.kLeftRumble, 0);
		OI.driverController.setRumble(RumbleType.kRightRumble, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
