package org.usfirst.frc.team3339.robot.subsystems;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbArm extends Subsystem {
	private Servo climbArmServo = new Servo(RobotMap.CLIMB_ARM_SERVO);
	private VictorSP climbArm = new VictorSP(RobotMap.CLIMB_ARM_MOTOR);
	private final double CLIMB_ARM_POWER_OPEN = ROBOT_PROFILE.climbArmParams.climb_arm_power_open;
	private final double CLIMB_ARM_FORCE_POWER_OPEN = Math.signum(ROBOT_PROFILE.climbArmParams.climb_arm_power_open)
			* 1;
	private final double CLIMB_ARM_POWER_CLOSE = ROBOT_PROFILE.climbArmParams.climb_arm_power_close;
	private final double SERVO_OPEN_DEG = 170; // TODO: Tuning
	private final double SERVO_CLOSE_DEG = 0;

	public ClimbArm() {
		climbArm.setInverted(ROBOT_PROFILE.climbArmParams.is_inverted);
	}

	public void openArm(boolean isForce) {
		climbArm.set(isForce ? CLIMB_ARM_FORCE_POWER_OPEN : CLIMB_ARM_POWER_OPEN);
	}

	public void openServo() {
		climbArmServo.setPosition(SERVO_OPEN_DEG);
	}

	public void closeServo() {
		climbArmServo.setPosition(SERVO_CLOSE_DEG);
	}

	public void setServoPosition(double position) {
		climbArmServo.setPosition(position);
	}

	public void closeArm() {
		climbArm.set(CLIMB_ARM_POWER_CLOSE);
	}

	public void stopArm() {
		climbArm.set(0);
	}

	@Override
	public void initDefaultCommand() {

	}
}
