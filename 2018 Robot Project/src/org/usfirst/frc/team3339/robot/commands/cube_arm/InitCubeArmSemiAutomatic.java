package org.usfirst.frc.team3339.robot.commands.cube_arm;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class InitCubeArmSemiAutomatic extends Command {

	public InitCubeArmSemiAutomatic() {

	}

	@Override
	protected void initialize() {
		Robot.m_cubeArm.setPosition(CubeArmState.SEMI_AUTOMATIC);
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
