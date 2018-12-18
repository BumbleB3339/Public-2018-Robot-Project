package org.usfirst.frc.team3339.robot.autonomous.sequences.side;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.Wait;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndThen;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.ExecuteFolded;
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
 * preloaded, places it in the far SCALE, collects a POWER CUBE and places it in
 * the far SCALE.
 * 
 * @see <a href="https://www.thebluealliance.com/match/2018hop_qm54">Quals 54 2018 Hopper Division</a>
 */
public class SideFarScaleX2 extends CommandGroup {

	public SideFarScaleX2() {
		addSequential(new SetStartedFacingWall(false));
		addParallel(new SetCubeLiftPosition(CubeLiftState.SWITCH));
		addParallel(new Wait(1));

		addParallel(new WaitAndThen(4.5, new SetCubeLiftPosition(CubeLiftState.SCALE)));

		addSequential(new DrivePath(AutoPaths.sideToFarScale));
		addParallel(new WaitAndThen(0.5, new SetCubeArmAngle(CubeArmState.MIDDLE)));
		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToFarScaleReleasePosition, true));
		addParallel(new WaitAndThen(0.0, new CubeIntakeRelease(false, true)));
		addSequential(new Wait(0.2));
		addParallel(new WaitAndThen(0.3, new ExecuteFolded()));
		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToFarScaleCubeCollectPosition, true));
		// addSequential(new PixyAlignToTarget(Robot.powerCubeType));
		addParallel(new SetCubeLiftPosition(CubeLiftState.COLLECT));
		addParallel(new SetCubeArmAngle(CubeArmState.DOWN));
		addParallel(new CubeIntakeInsert());
		addSequential(new DrivePath(AutoPaths.farScaleToCubeCollect));
		addSequential(new Wait(0.4));
		addParallel(new WaitAndThen(0.2, new CubeIntakeStop()));
		addParallel(new WaitAndThen(0.2, new SetCubeArmAngle(CubeArmState.UP)));
		addParallel(new WaitAndThen(0.2, new SetCubeLiftPosition(CubeLiftState.SCALE)));
		addSequential(new DrivePath(AutoPaths.farScalePostCollectBackUpToReleasePosition));
		addParallel(new WaitAndThen(0.5, new SetCubeArmAngle(CubeArmState.MIDDLE)));
		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToFarScaleSecondReleasePosition, true));
		addParallel(new WaitAndThen(0, new CubeIntakeRelease(false, true)));
		addSequential(new Wait(0.3));
		addParallel(new CubeIntakeStop());
		addParallel(new WaitAndThen(0.3, new ExecuteFolded()));
		addParallel(new DrivePath(AutoPaths.sideFarScaleBackoffFromSecondCubeRelease));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void initialize() {
		Robot.m_cubeIntake.isRegularRelease = true;
	}

	@Override
	protected void end() {
		Robot.m_cubeIntake.isRegularRelease = false;
	}

	@Override
	protected void interrupted() {
		end();
		new SetCubeArmAngle(CubeArmState.UP).start();
	}
}
