package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerCubeIntakeManualMode extends Trigger {

	@Override
	public boolean get() {
		return OI.operatorController.getBackButton();
	}
}
