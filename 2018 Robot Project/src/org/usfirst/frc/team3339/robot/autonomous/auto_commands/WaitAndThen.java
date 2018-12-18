package org.usfirst.frc.team3339.robot.autonomous.auto_commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WaitAndThen extends CommandGroup {

	public WaitAndThen(double time, Command andThen) {
		addSequential(new Wait(time));
		addSequential(andThen);
	}
}
