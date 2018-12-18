package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerSwitchReleaseCube extends Trigger {

	@Override
	public boolean get() {
		return Robot.isInRobotMode(RobotMode.SWITCH) && OI.driverController.getBButton();
	}
}
