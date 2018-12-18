package org.usfirst.frc.team3339.robot.autonomous.sub_sequences;

import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndPrepareToCollect;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.RotateByDegrees;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PostFirstSideCloseScaleCubeBackUpAndLowerLift extends CommandGroup {

	public PostFirstSideCloseScaleCubeBackUpAndLowerLift() {
		addParallel(new WaitAndPrepareToCollect(0.3));
		addSequential(new DrivePath(AutoPaths.sideCloseScalePostFirstCubeToCubeInsert));
		addSequential(new RotateByDegrees(AutoParams.angleToScaleCollectCube, true));
	}
}
