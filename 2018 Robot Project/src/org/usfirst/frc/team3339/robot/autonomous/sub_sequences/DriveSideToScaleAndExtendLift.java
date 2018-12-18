package org.usfirst.frc.team3339.robot.autonomous.sub_sequences;

import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.Wait;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetScaleHeightMode;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.ScaleHeightMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveSideToScaleAndExtendLift extends CommandGroup {

	public DriveSideToScaleAndExtendLift() {
		addParallel(new DrivePath(AutoPaths.sideToCloseScale));
		addSequential(new Wait(1.0));
		addSequential(new SetScaleHeightMode(ScaleHeightMode.SCALE_HIGH));
		addSequential(new SetCubeLiftPosition(CubeLiftState.SCALE));
	}
}
