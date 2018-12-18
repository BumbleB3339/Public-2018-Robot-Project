package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ExecuteCollection extends CommandGroup {

	public ExecuteCollection() {
		addParallel(new SetCubeArmAngle(CubeArmState.DOWN));
		addSequential(new SetCubeLiftPosition(CubeLiftState.COLLECT));
	}
}
