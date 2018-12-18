package org.usfirst.frc.team3339.robot.autonomous.sub_sequences;

import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.RotateByDegrees;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ReleaseToScaleFromCollectPosition extends CommandGroup {

	public ReleaseToScaleFromCollectPosition() {
		addParallel(new SetCubeLiftPosition(CubeLiftState.SCALE));

		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToSideScaleSecondCubeReleasePosition, true));
		addSequential(new DrivePath(AutoPaths.sideCloseScaleToSecondReleasePosition));

		addSequential(new CubeIntakeRelease(false, true));
	}

	public ReleaseToScaleFromCollectPosition(boolean isPowerRelease) {
		addParallel(new SetCubeLiftPosition(CubeLiftState.SCALE));

		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToSideScaleSecondCubeReleasePosition, true));
		addSequential(new DrivePath(AutoPaths.sideCloseScaleToSecondReleasePosition));

		addSequential(new CubeIntakeRelease(isPowerRelease, true));
	}
}
