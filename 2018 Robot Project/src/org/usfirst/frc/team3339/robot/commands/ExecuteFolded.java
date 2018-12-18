package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ExecuteFolded extends CommandGroup {

	public ExecuteFolded() {
		addParallel(new LowerCollectHeightMode());
		addParallel(new LowerCollectHeightMode());
		addParallel(new SetCubeArmAngle(CubeArmState.UP));
		addSequential(new SetCubeLiftPosition(CubeLiftState.GROUND));
	}

	@Override
	protected void interrupted() {
		Robot.m_cubeLift.setPosition(CubeLiftState.GROUND);
	}
}
