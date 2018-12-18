package org.usfirst.frc.team3339.robot.autonomous.sequences.side;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.Wait;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndThen;
import org.usfirst.frc.team3339.robot.autonomous.sub_sequences.SideCloseScaleFirstReleaseAndPrepareToCollect;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.ExecuteFolded;
import org.usfirst.frc.team3339.robot.commands.PixyAlignToTarget;
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
 * preloaded, places it in the close SCALE, collects a POWER CUBE and places it
 * in the close SCALE.
 * 
 * @see <a href="https://www.thebluealliance.com/match/2018hop_f1m1">Finals 1 2018 Hopper Division</a>
 */
public class SideCloseScaleX2 extends CommandGroup {

	public SideCloseScaleX2() {
		addSequential(new SetStartedFacingWall(false));

		addSequential(new SideCloseScaleFirstReleaseAndPrepareToCollect());

		addParallel(new CubeIntakeInsert());
		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionForward));
		addSequential(new Wait(0.2));
		addParallel(new WaitAndThen(0.5, new SetCubeArmAngle(CubeArmState.MIDDLE)));
		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionBackward));
		addSequential(new CubeIntakeStop());

		addParallel(new SetCubeLiftPosition(CubeLiftState.SCALE));

		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToSideScaleSecondCubeReleasePosition, true));
		addSequential(new DrivePath(AutoPaths.sideCloseScaleToSecondReleasePosition));

		addParallel(new CubeIntakeRelease(false, true));

		addParallel(new WaitAndThen(0.5, new ExecuteFolded()));
		addSequential(new DrivePath(AutoPaths.sideCloseScaleToSecondBackupPosition));

		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToCloseScaleThirdCubeCollect, true));
		addSequential(new PixyAlignToTarget(Robot.powerCubeType));

		// TODO: un-comment after angle is verified to be correct
		// addParallel(new AutonomousGoToCollectMode());
		// addSequential(new
		// DrivePath(AutoPaths.sideCloseScaleToThirdCubeCollectPosition));
		// -----
	}

	@Override
	protected void initialize() {
		Robot.m_cubeIntake.isRegularRelease = true;
	}

	@Override
	protected void end() {
		Robot.m_cubeIntake.isRegularRelease = false;
		new ExecuteFolded().start();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
