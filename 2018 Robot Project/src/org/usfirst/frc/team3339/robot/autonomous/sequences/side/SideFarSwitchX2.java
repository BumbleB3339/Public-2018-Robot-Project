package org.usfirst.frc.team3339.robot.autonomous.sequences.side;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.Wait;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndPrepareToCollect;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndThen;
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
 * preloaded, places it in the far SWITCH, collects a POWER CUBE and places it in
 * the far SWITCH.
 * 
 * @see <a href="https://www.thebluealliance.com/match/2018hop_sf2m2">Semis 2 Match 2 2018 Hopper Division</a>
 */
public class SideFarSwitchX2 extends CommandGroup {

	public SideFarSwitchX2() {
		addSequential(new SetStartedFacingWall(false));

		addParallel(new WaitAndThen(3.0, new SetCubeLiftPosition(CubeLiftState.SWITCH)));
		addParallel(new WaitAndThen(3.0, new SetCubeArmAngle(CubeArmState.DOWN)));
		addSequential(new DrivePath(AutoPaths.sideToFarSwitchPreRotation));
		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToFarSwitchPreReleasePosition, true));
		addSequential(new PixyAlignToTarget(Robot.powerCubeType));

		addSequential(new DrivePath(AutoPaths.sideFarSwitchToFirstCubeReleasePosition));
		addParallel(new RotateByDegrees(AutoParams.absoluteAngleToFarSwitchCorrectToCenterOfSwitch, true));
		addSequential(new Wait(0.2));
		addParallel(new CubeIntakeRelease(true, true));

		addParallel(new WaitAndPrepareToCollect(0.3));
		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionBackward));

		// collect 2nd cube
		addParallel(new CubeIntakeInsert());

		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionForward));

		addParallel(new WaitAndThen(0.3, new SetCubeLiftPosition(CubeLiftState.SWITCH)));
		addSequential(new DrivePath(AutoPaths.cubeCollectSecondSwitchCubeReleaseBackward));
		addSequential(new CubeIntakeStop());

		addSequential(new DrivePath(AutoPaths.sideFarSwitchCubeCollectSecondSwitchCubeReleaseForward));

		addParallel(new CubeIntakeRelease(true, true));

		addParallel(new WaitAndThen(0.2, new ExecuteFolded()));
		addSequential(new DrivePath(AutoPaths.cubePreApproachToCubeCollectPositionBackward));

		addParallel(new ExecuteFolded());
		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToFarSwitchThirdCubeCollect, true));
	}
}
