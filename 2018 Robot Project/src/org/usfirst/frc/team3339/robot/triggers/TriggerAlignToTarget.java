package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerAlignToTarget extends Trigger {

	@Override
	public boolean get() {
		return OI.driverController.getBumper(Hand.kRight)
				&& (Robot.isInRobotMode(RobotMode.COLLECT) || Robot.isInRobotMode(RobotMode.FOLDED));
	}
}
