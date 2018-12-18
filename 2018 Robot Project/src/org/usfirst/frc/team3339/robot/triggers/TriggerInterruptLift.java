package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerInterruptLift extends Trigger {

	@Override
	public boolean get() {
		return OI.driverController.getStartButton() && !Robot.m_cubeLift.isInState(CubeLiftState.MANUAL);
	}
}
