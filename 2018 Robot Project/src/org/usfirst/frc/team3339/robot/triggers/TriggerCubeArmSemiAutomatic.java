package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerCubeArmSemiAutomatic extends Trigger {

	@Override
	public boolean get() {
		return Robot.m_cubeArm.isSemiAutomatic();
	}
}
