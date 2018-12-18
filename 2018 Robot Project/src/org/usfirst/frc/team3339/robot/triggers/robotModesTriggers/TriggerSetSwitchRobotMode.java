package org.usfirst.frc.team3339.robot.triggers.robotModesTriggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerSetSwitchRobotMode extends Trigger {

	public static boolean isY_ButtonRePressed = false;

	@Override
	public boolean get() {
		boolean driverTrigger = false;
		if (Robot.isInRobotMode(RobotMode.COLLECT)) {
			if (!OI.driverController.getYButton())
				isY_ButtonRePressed = true;
			if (OI.driverController.getYButton() && isY_ButtonRePressed) {
				isY_ButtonRePressed = false;
				driverTrigger = true;
			}
		} else
			isY_ButtonRePressed = false;
		return OI.operatorController.getXButton() || driverTrigger;
	}
}
