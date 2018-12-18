
package org.usfirst.frc.team3339.robot.autonomous.sequences.middle;

import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.AutonomousGoToSwitchMode;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.SetStartedFacingWall;
import org.usfirst.frc.team3339.robot.autonomous.auto_commands.Wait;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.ExecuteFolded;
import org.usfirst.frc.team3339.robot.commands.cube_intake.CubeIntakeRelease;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode in which the ROBOT starts in the middle with one POWER CUBE
 * preloaded, places it in the SWITCH and returns to its starting position.
 * 
 * @see <a href="https://www.thebluealliance.com/match/2018isde2_qm4">Quals 4 2018 ISR District Event #2</a>
 */
public class MiddleSwitch extends CommandGroup {

	public MiddleSwitch() {
		addSequential(new SetStartedFacingWall(false));

		addParallel(new AutonomousGoToSwitchMode());
		addSequential(new DrivePath(AutoPaths.middleToSwitchFront));

		addSequential(new CubeIntakeRelease(false, true));

		addParallel(new DrivePath(AutoPaths.switchFrontToMiddle));
		addSequential(new Wait(0.5));
		addSequential(new ExecuteFolded());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
