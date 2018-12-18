package org.usfirst.frc.team3339.robot.triggers.robotModesTriggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerSetExchangeRobotMode extends Trigger {

	@Override
	public boolean get() {
		return OI.driverController.getBButton()
				&& (Robot.isInRobotMode(Robot.RobotMode.FOLDED) || Robot.isInRobotMode(Robot.RobotMode.COLLECT));
	}
}
