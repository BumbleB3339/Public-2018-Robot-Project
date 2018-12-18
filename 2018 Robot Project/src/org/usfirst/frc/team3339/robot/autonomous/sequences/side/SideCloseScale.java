package org.usfirst.frc.team3339.robot.autonomous.sequences.side;

import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.autonomous.sub_sequences.DriveSideToScaleAndExtendLift;
import org.usfirst.frc.team3339.robot.autonomous.sub_sequences.PostFirstSideCloseScaleCubeBackUpAndLowerLift;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode in which the ROBOT starts on the side with one POWER CUBE
 * preloaded and places it in the close SCALE.
 * 
 * <br><br> Never used in a competition because {@link SideCloseScaleX2} was ready before the first one.
 */
public class SideCloseScale extends CommandGroup {

	public SideCloseScale() {
		addSequential(new SetStartedFacingWall(false));

		addSequential(new DriveSideToScaleAndExtendLift());

		addSequential(new CubeIntakeRelease(false, true)); // powerRelease - false, useTimeout - true

		addSequential(new PostFirstSideCloseScaleCubeBackUpAndLowerLift());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
