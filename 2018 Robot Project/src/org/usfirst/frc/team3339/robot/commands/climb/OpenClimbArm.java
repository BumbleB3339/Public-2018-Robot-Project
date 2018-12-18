package org.usfirst.frc.team3339.robot.commands.climb;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenClimbArm extends Command {

	private final double SERVO_DELAY = 1;
	private boolean isFirstTime = true;
	private double servoStartTime = 0;
	private boolean isForce = false;

	public OpenClimbArm() {
		requires(Robot.m_climbArm);
	}

	public OpenClimbArm(boolean isForce) {
		requires(Robot.m_climbArm);
		this.isForce = isForce;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (isFirstTime) {
			Robot.m_climbArm.openServo();
			servoStartTime = Timer.getFPGATimestamp();
			isFirstTime = false;
		}
		if (Timer.getFPGATimestamp() >= servoStartTime + SERVO_DELAY) {
			Robot.m_climbArm.openArm(isForce);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_climbArm.stopArm();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
