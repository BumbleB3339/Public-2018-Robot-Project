package org.usfirst.frc.team3339.robot.autonomous.sequences.middle;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.AutonomousGoToSwitchMode;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndPrepareToCollect;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndThen;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.ExecuteFolded;
import org.usfirst.frc.team3339.robot.commands.PixyAlignToTarget;
import org.usfirst.frc.team3339.robot.commands.RaiseCollectHeightMode;
import org.usfirst.frc.team3339.robot.commands.RotateByDegrees;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeInsert;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode in which the ROBOT starts in the middle with one POWER CUBE
 * preloaded, places one POWER CUBE in the SWITCH, collects a second POWER CUBE
 * from the ALLIANCE POWER CUBE PILE, places it in the SWITCH, and then collects
 * a third POWER CUBE from the ALLIANCE POWER CUBE PILE.
 * 
 * @see <a href="https://www.thebluealliance.com/match/2018hop_qm109">Quals 109 2018 Hopper Division</a>
 */
public class MiddleSwitchX2_5 extends CommandGroup {

	public MiddleSwitchX2_5() {
		addSequential(new SetStartedFacingWall(false));

		addParallel(new AutonomousGoToSwitchMode());
		addSequential(new DrivePath(AutoPaths.middleToSwitchFront));

		addParallel(new CubeIntakeRelease(true, true));

		// second cube:

		addParallel(new WaitAndPrepareToCollect(0.3));
		addSequential(new DrivePath(AutoPaths.switchFrontToPreCollectPosition));

		addSequential(new RotateByDegrees(AutoParams.angleToFrontSwitchCollectCube));

		addParallel(new CubeIntakeInsert(3.0));
		addSequential(new DrivePath(AutoPaths.frontSwitchPreCollectToCollectPosition));

		addSequential(new DrivePath(AutoPaths.frontSwitchCollectToPostCollectPosition));

		addParallel(new AutonomousGoToSwitchMode());
		addSequential(new RotateByDegrees(-AutoParams.angleToFrontSwitchCollectCube));

		addSequential(new DrivePath(AutoPaths.middlePostSwitchCollectToSwitchFront));

		addParallel(new CubeIntakeRelease(true, true));

		addSequential(new RaiseCollectHeightMode());
		addParallel(new WaitAndThen(0.5, new SetCubeLiftPosition(CubeLiftState.COLLECT)));
		addSequential(new DrivePath(AutoPaths.middleSwitchFrontToPreThirdCubeCollect));

		addSequential(new PixyAlignToTarget(Robot.powerCubeType));

		addParallel(new CubeIntakeInsert());
		addSequential(new DrivePath(AutoPaths.middleSwitchThirdCubeCollect));

		addParallel(new CubeIntakeInsert(0.3));
		addParallel(new WaitAndThen(0.5, new ExecuteFolded()));
		addSequential(new DrivePath(AutoPaths.middleSwitchThirdCubePostCollect));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		new ExecuteFolded().start();
	}

	@Override
	protected void interrupted() {
		end();
	}
}