package org.usfirst.frc.team3339.robot.autonomous.auto_commands;

import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WaitAndPrepareToCollect extends CommandGroup {

	public WaitAndPrepareToCollect(double waitTime) {
		addSequential(new Wait(waitTime));
		addSequential(new SetCubeLiftPosition(CubeLiftState.COLLECT));
		addSequential(new SetCubeArmAngle(CubeArmState.DOWN));
	}
}
