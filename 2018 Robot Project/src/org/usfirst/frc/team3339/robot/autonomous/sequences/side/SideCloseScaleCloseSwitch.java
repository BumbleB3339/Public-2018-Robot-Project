package org.usfirst.frc.team3339.robot.autonomous.sequences.side;

import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.AutonomousGoToCollectMode;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.Wait;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndThen;
import org.usfirst.frc.team3339.robot.autonomous.sub_sequences.SideCloseScaleFirstReleaseAndPrepareToCollect;
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
 * preloaded, places it in the close SCALE, collects a POWER CUBE and places it
 * in the close SWITCH.
 * 
 * <br><br> Never used in a competition.
 */
public class SideCloseScaleCloseSwitch extends CommandGroup {

	public SideCloseScaleCloseSwitch() {
		addSequential(new SetStartedFacingWall(false));

		addSequential(new SideCloseScaleFirstReleaseAndPrepareToCollect());

		addParallel(new CubeIntakeInsert());

		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionForward));
		addSequential(new Wait(0.2));

		addParallel(new WaitAndThen(0.3, new SetCubeLiftPosition(CubeLiftState.SWITCH)));
		addSequential(new DrivePath(AutoPaths.cubeCollectSecondSwitchCubeReleaseBackward));
		addSequential(new CubeIntakeStop());

		addSequential(new DrivePath(AutoPaths.cubeCollectSecondSwitchCubeReleaseForward));

		// New changes and additions for Houston start here
		addParallel(new CubeIntakeRelease(true, true));

		addSequential(new DrivePath(AutoPaths.sideCloseScaleCloseSwitchPostSecondCubeReleaseBackoff));

		addParallel(new ExecuteFolded());
		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToSideSwitchThirdCubeCollect, true));
		addParallel(new AutonomousGoToCollectMode());

		addSequential(new DrivePath(AutoPaths.sideCloseSwitchToThirdCubeCollect));
		addSequential(new Wait(0.4));
		addParallel(new WaitAndThen(0.2, new CubeIntakeStop()));
		addParallel(new WaitAndThen(0.2, new SetCubeArmAngle(CubeArmState.UP)));
		addParallel(new SetCubeLiftPosition(CubeLiftState.GROUND));

		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionBackward));
		addSequential(new ExecuteFolded());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
