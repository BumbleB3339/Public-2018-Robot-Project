package org.usfirst.frc.team3339.robot.commands.cube_arm;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to set CubeArm position
 */
public class SetCubeArmAngle extends Command {
	private CubeArm.CubeArmState position;

	public SetCubeArmAngle(CubeArmState position) {
		requires(Robot.m_cubeArm);
		this.position = position;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.m_cubeArm.setPosition(position);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.m_cubeArm.isInPosition(position);
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
