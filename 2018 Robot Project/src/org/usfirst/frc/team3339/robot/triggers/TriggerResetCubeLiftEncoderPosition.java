package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerResetCubeLiftEncoderPosition extends Trigger {

	@Override
	public boolean get() {
		return OI.operatorController.getStartButton() && Robot.m_cubeLift.isInManualOverride();
	}
}
