package org.usfirst.frc.team3339.robot.autonomous.sequences.side;

import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.Wait;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndPrepareToCollect;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndThen;
import org.usfirst.frc.team3339.robot.autonomous.sub_sequences.BackupFromCloseScale;
import org.usfirst.frc.team3339.robot.autonomous.sub_sequences.ReleaseToScaleFromCollectPosition;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.RotateByDegrees;
import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeInsert;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeStop;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode in which the ROBOT starts on the side with one POWER CUBE
 * preloaded, places it in the close SWITCH, collects a POWER CUBE and places it
 * in the close SCALE.
 * 
 * @see <a href="https://www.thebluealliance.com/match/2018isde4_qm8">Quals 8 2018 ISR District Event #4</a>
 */
public class SideCloseSwitchCloseScale extends CommandGroup {

	public SideCloseSwitchCloseScale() {
		addSequential(new SetStartedFacingWall(false));

		addParallel(new WaitAndThen(1.0, new SetCubeLiftPosition(CubeLiftState.SWITCH)));
		addSequential(new DrivePath(AutoPaths.sideStartingPosToCubeCollect));

		addParallel(new SetCubeArmAngle(CubeArmState.DOWN));
		addSequential(new RotateByDegrees(AutoParams.angleToScaleCollectCube, true));

		addSequential(new DrivePath(AutoPaths.toSwitchReleasePosition));
		addParallel(new CubeIntakeRelease(true, true));

		addParallel(new WaitAndPrepareToCollect(0.3));
		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionBackward));

		// collect 2nd cube
		addParallel(new CubeIntakeInsert());
		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionForward));
		addSequential(new Wait(0.2));
		addParallel(new WaitAndThen(0.5, new SetCubeArmAngle(CubeArmState.UP)));
		addSequential(new DrivePath(AutoPaths.cubeCollectSecondSwitchCubeReleaseBackward));
		addSequential(new CubeIntakeStop());
		addParallel(new SetCubeArmAngle(CubeArmState.MIDDLE));

		addSequential(new ReleaseToScaleFromCollectPosition(true));

		addSequential(new BackupFromCloseScale());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
