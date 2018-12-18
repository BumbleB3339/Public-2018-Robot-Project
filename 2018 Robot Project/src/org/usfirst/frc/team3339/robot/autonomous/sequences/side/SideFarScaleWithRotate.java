package org.usfirst.frc.team3339.robot.autonomous.sequences.side;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.autonomous.AutoParams;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.RotateByDegrees;
import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;
import org.usfirst.frc.team3339.robot.commands.cube_lift.SetCubeLiftPosition;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode in which the ROBOT starts on the side with one POWER CUBE
 * preloaded and places it in the far SCALE, performing a sharp turn in place to
 * be compatible with other autonomous modes of the alliance.
 * 
 * @see <a href="https://www.thebluealliance.com/match/2018hop_f1m2">Finals 2 2018 Hopper Division</a>
 */
public class SideFarScaleWithRotate extends CommandGroup {

	public SideFarScaleWithRotate() {
		addSequential(new SetStartedFacingWall(false));

		addParallel(new SetCubeArmAngle(CubeArmState.UP));

		addSequential(new DrivePath(AutoPaths.sideToFarScaleWithRotate));

		addParallel(new SetCubeLiftPosition(CubeLiftState.SCALE));
		// addSequential(new SetCubeLiftPosition(CubeLiftState.SCALE)); // to raise the
		// lift only when rotation in place ends

		addSequential(new RotateByDegrees(AutoParams.absoluteAngleToSideFarScaleFirstCubeReleasePosition, true));

		addParallel(new SetCubeArmAngle(CubeArmState.MIDDLE));
		addSequential(new DrivePath(AutoPaths.sideFarScaleToFirstReleasePosition));

		addSequential(new CubeIntakeRelease(false, true));

		// backup from scale, commented to be compatible with Ratchet Rockers #1706
		// close SCALE autonomous mode.
		// addParallel(new WaitAndThen(0.5, new ExecuteFolded()));
		// addSequential(new DrivePath(AutoPaths.sideFarScaleToFirstBackUp));
		// ----------
	}

	@Override
	protected void initialize() {
		Robot.m_cubeIntake.isRegularRelease = true;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_cubeIntake.isRegularRelease = false;
	}
}
