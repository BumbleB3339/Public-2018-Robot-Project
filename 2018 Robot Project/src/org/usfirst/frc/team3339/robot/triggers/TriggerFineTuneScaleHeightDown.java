package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerFineTuneScaleHeightDown extends Trigger {

	@Override
	public boolean get() {
		return OI.operatorController.getY(Hand.kLeft) >= 0.7 && Robot.m_cubeLift.isInState(CubeLiftState.SCALE);
	}
}
