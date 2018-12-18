package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ReleaseSwitchCube extends CommandGroup {

	public ReleaseSwitchCube() {
		addParallel(new SetCubeArmAngle(CubeArmState.SWITCH));
		addSequential(new WaitForSufficientSwitchAngle());
		addSequential(new CubeIntakeRelease(false));
	}
}
