package org.usfirst.frc.team3339.robot.autonomous.sequences.side;

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
 * the far SWITCH.
 * 
 * <br><br> Never used in a competition.
 */
public class SideFarScaleFarSwitch extends CommandGroup {

	public SideFarScaleFarSwitch() {
		addSequential(new SetStartedFacingWall(false));
		addParallel(new SetCubeLiftPosition(CubeLiftState.SWITCH));
		addParallel(new Wait(1));

		addParallel(new WaitAndThen(3.5, new SetCubeLiftPosition(CubeLiftState.SCALE)));

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
		addParallel(new SetCubeLiftPosition(CubeLiftState.SWITCH));
		addSequential(new DrivePath(AutoPaths.cubeCollectSecondSwitchCubeReleaseBackward));
		addSequential(new CubeIntakeStop());
		addSequential(new Wait(0.2));

		addSequential(new DrivePath(AutoPaths.cubeCollectSecondSwitchCubeReleaseForward));
		addParallel(new CubeIntakeRelease(true, true));

		addParallel(new WaitAndThen(0.5, new ExecuteFolded()));
		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionBackward));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
