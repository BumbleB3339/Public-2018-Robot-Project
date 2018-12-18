package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerReleaseCubeExchange extends Trigger {

	public static boolean isB_ButtonRePressed = false; // To ensure that driver released B button after changing robot
														// mode to exchange mode

	@Override
	public boolean get() {
		if (Robot.isInRobotMode(RobotMode.EXCHANGE)) {
			if (!OI.driverController.getBButton())
				isB_ButtonRePressed = true;
			if (OI.driverController.getBButton() && isB_ButtonRePressed) {
				isB_ButtonRePressed = false;
				return true;
			}
		}
		return false;
	}
}
