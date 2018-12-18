package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetRobotMode extends Command {

	Robot.RobotMode desiredRobotMode;

	public SetRobotMode(Robot.RobotMode robotMode) {
		desiredRobotMode = robotMode;
	}

	@Override
	protected void initialize() {
		Robot.setRobotMode(desiredRobotMode);
		if (desiredRobotMode == RobotMode.COLLECT) {
			Robot.m_cubeIntake.startNewInsert();
		}
		Robot.m_cubeLift.unInterruptLift();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
