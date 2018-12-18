package org.usfirst.frc.team3339.robot.autonomous.auto_commands;

import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeInsert;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousGoToCollectMode extends CommandGroup {

	public AutonomousGoToCollectMode() {
		addParallel(new SetCubeArmAngle(CubeArmState.DOWN));
		addSequential(new SetCubeLiftPosition(CubeLiftState.COLLECT));
		addSequential(new CubeIntakeInsert());
	}
}
