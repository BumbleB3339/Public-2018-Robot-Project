package org.usfirst.frc.team3339.robot.autonomous.sequences;

import org.usfirst.frc.team3339.robot.autonomous.auto_commands.DriveByTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode in which the ROBOT drives forward for 1.5 seconds to cross
 * the Auto Line.
 */
public class CrossLineTimed extends CommandGroup {

	private final double TIMEOUT_TO_DRIVE = 1.5;

	public CrossLineTimed() {
		addSequential(new DriveByTime(TIMEOUT_TO_DRIVE));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
