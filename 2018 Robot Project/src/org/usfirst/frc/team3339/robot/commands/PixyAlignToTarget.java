package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import util.pixy.PixyVision.PowerCubeType;

/**
 *
 */
public class PixyAlignToTarget extends Command {

	private PowerCubeType PIDCubeType;
	private int noTargetCounter = 0;

	public PixyAlignToTarget(PowerCubeType PIDCubeType) {
		requires(Robot.m_drivetrain);
		this.PIDCubeType = PIDCubeType;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.m_drivetrain.initPixyAlignToCube(PIDCubeType);
		Robot.m_pixyVision.setPixyDataToNeutral();
		Robot.m_pixyVision.updatePixyData();
		noTargetCounter = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.m_pixyVision.getCenterX(Robot.powerCubeType) == -99) {
			noTargetCounter++;
		} else {
			noTargetCounter = 0;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.m_drivetrain.isPixyAlignToCubeOnTarget() || noTargetCounter > 5;
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
