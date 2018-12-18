package org.usfirst.frc.team3339.robot.triggers;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TriggerSignalCubeIn extends Trigger {

	@Override
	public boolean get() {
		return Robot.m_cubeIntake.isCubeIn() && Robot.isInRobotMode(RobotMode.COLLECT);
	}
}
