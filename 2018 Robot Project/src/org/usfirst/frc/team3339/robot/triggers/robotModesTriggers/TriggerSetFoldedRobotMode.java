package org.usfirst.frc.team3339.robot.triggers.robotModesTriggers;

import org.usfirst.frc.team3339.robot.OI;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerSetFoldedRobotMode extends Trigger {

	@Override
	public boolean get() {
		return OI.driverController.getXButton();
	}
}
