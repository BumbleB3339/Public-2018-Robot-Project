package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerScaleInsertCube extends Trigger {

	@Override
	public boolean get() {
		return OI.driverController.getYButton() && Robot.isInRobotMode(RobotMode.SCALE);
	}
}
