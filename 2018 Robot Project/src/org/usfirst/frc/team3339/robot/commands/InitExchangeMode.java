package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class InitExchangeMode extends CommandGroup {

	public InitExchangeMode() {
		// addSequential(new SetCubeArmPosition(CubeArmState.UP));
		addParallel(new SetCubeArmAngle(CubeArmState.DOWN));
		addSequential(new SetCubeLiftPosition(CubeLiftState.EXCHANGE));
	}
}
