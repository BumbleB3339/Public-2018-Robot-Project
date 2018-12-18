package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerCubeArmGoToSemiAutomatic extends Trigger {

	@Override
	public boolean get() {
		return Math.abs(OI.operatorController.getY(Hand.kRight)) > 0.7
				|| Math.abs(OI.driverController.getY(Hand.kRight)) > 0.7;
	}
}
