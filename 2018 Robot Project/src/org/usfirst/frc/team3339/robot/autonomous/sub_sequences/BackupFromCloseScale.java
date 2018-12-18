package org.usfirst.frc.team3339.robot.autonomous.sub_sequences;

import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.ExecuteFolded;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BackupFromCloseScale extends CommandGroup {

	public BackupFromCloseScale() {
		addSequential(new DrivePath(AutoPaths.sideCloseScaleToSecondBackupPosition));
		addSequential(new ExecuteFolded());
	}
}
