package org.usfirst.frc.team3339.robot.commands.cube_arm;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to set CubeArm position
 */
public class ToggleCubeArmPosition extends Command {
	CubeArmState targetPosition;

	public ToggleCubeArmPosition() {
		requires(Robot.m_cubeArm);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (Robot.m_cubeArm.getCubeArmState() == CubeArmState.DOWN
				|| (Robot.m_cubeArm.getCubeArmState() == CubeArmState.SEMI_AUTOMATIC
						&& !Robot.isInRobotMode(RobotMode.SCALE))) {
			targetPosition = CubeArmState.UP;
		} else {
			targetPosition = CubeArmState.DOWN;
		}
		Robot.m_cubeArm.setPosition(targetPosition);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true; // Robot.m_cubeArm.isInPosition(targetPosition);
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
