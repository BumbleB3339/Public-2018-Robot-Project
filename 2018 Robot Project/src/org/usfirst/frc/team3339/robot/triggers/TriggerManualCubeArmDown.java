package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerManualCubeArmDown extends Trigger {

	@Override
	public boolean get() {
		return Robot.m_cubeArm.isManual() && OI.operatorController.getY(Hand.kRight) >= 0.7;
	}
}
