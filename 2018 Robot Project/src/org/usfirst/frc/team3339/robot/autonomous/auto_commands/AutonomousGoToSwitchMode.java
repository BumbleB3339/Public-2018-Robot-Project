package org.usfirst.frc.team3339.robot.autonomous.auto_commands;

import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousGoToSwitchMode extends CommandGroup {

	public AutonomousGoToSwitchMode() {
		addSequential(new SetCubeLiftPosition(CubeLiftState.SWITCH));
		addSequential(new SetCubeArmAngle(CubeArmState.DOWN));
	}
}
