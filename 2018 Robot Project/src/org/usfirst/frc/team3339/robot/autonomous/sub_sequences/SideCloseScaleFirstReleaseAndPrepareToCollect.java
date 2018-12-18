package org.usfirst.frc.team3339.robot.autonomous.sub_sequences;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.WaitAndThen;
import org.usfirst.frc.team3339.robot.commands.cube_arm.SetCubeArmAngle;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Finishes with cube inside, arm down
 */
public class SideCloseScaleFirstReleaseAndPrepareToCollect extends CommandGroup {

	public SideCloseScaleFirstReleaseAndPrepareToCollect() {
		addParallel(new WaitAndThen(2.5, new SetCubeArmAngle(CubeArmState.MIDDLE)));
		addSequential(new DriveSideToScaleAndExtendLift());

		addSequential(new CubeIntakeRelease(false, true)); // powerRelease - false, useTimeout - true // TODO :WHY IS
															// POWER FALSE

		addSequential(new PostFirstSideCloseScaleCubeBackUpAndLowerLift());

		// enable when pixy works
		// addSequential(new PixyAlignToTarget(PowerCubeType.REGULAR_POWER_CUBE));
	}

	@Override
	protected void initialize() {
		Robot.m_cubeIntake.isRegularRelease = true;
	}
}
