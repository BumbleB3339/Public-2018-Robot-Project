package util;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class BumbleDrive extends DifferentialDrive implements PIDOutput {

	public enum PID_Type {
		ROTATE_BY_DEGREES, PIXY_ALIGN_TO_CUBE
	}

	private PID_Type currentPID_Type = null;

	private double m_leftPower = 0;
	private double m_rightPower = 0;

	private double aligningXFactor = 0.0;

	public void setAligningXFactor(double aligningXFactor) {
		this.aligningXFactor = aligningXFactor;
	}

	// Acceleration Factors
	private final double POWER_PER_CYCLE = 0.02;
	private final double LOWER_LIMIT = 0.3;

	// Deadband of y movement during aligning
	public static final double DRIVING_DEADBAND = 0.2;

	// TankDrive Factors
	private final double TANK_DRIVE_DEADBAND = 0.2;

	// TRXDrive Factor
	private final double TRX_DEADBAND = 0.2;
	private final double TRX_POWER = 1.5;
	private final double TRX_STEERING = 3;

	// GTADrive Factors
	private final double GTA_DEADBAND = 0.2;
	private final double GTA_POWER = 1.5;
	private final double GTA_STEERING = 3;
	private final double GTA_TRIGGER_DEADBAND = 0.1;

	public BumbleDrive(SpeedController leftMotor, SpeedController rightMotor) {
		super(leftMotor, rightMotor);
	}

	@Override
	public void tankDrive(double rawLeftYStickValue, double rawRightStickYValue) {
		if (Math.abs(rawRightStickYValue) < TANK_DRIVE_DEADBAND)
			rawRightStickYValue = 0;
		if (Math.abs(rawLeftYStickValue) < TANK_DRIVE_DEADBAND)
			rawLeftYStickValue = 0;
		setLeftRightMotorOutputs(-rawLeftYStickValue, -rawRightStickYValue, true);
	}

	public void TRXDrive(double rawLeftTrigger, double rawRightTrigger, double rawLeftYStickValue,
			boolean turnInPlaceButton) {
		if (Math.abs(rawLeftYStickValue) < BumbleDrive.DRIVING_DEADBAND)
			rawLeftYStickValue = 0;

		double leftPower = Math.pow(rawLeftTrigger, TRX_STEERING);
		double rightPower = Math.pow(rawRightTrigger, TRX_STEERING);
		double finalPower = 0;
		if (turnInPlaceButton) {
			finalPower = rightPower - leftPower;
			setLeftRightMotorOutputs(finalPower, -finalPower, true);
		} else {
			finalPower = (Math.abs(rawLeftYStickValue) > TRX_DEADBAND)
					? (Math.signum(rawLeftYStickValue) * Math.pow(Math.abs(rawLeftYStickValue), TRX_POWER))
					: 0;
			finalPower *= -1; // Because raw joystick input is inverted
			setLeftRightMotorOutputs(finalPower * (1 - leftPower), finalPower * (1 - rightPower), true);
		}
	}

	public void GTADrive(double rawLeftStickXValue, double rawRightTriggerValue, double rawLeftTriggerValue,
			boolean turnInPlaceButton) {
		double power = rawRightTriggerValue - rawLeftTriggerValue;
		double leftPower = Math.signum(power) * Math.pow(Math.abs(power), GTA_POWER);
		double rightPower = Math.signum(power) * Math.pow(Math.abs(power), GTA_POWER);

		if (turnInPlaceButton) {
			if (rawLeftTriggerValue > GTA_TRIGGER_DEADBAND) { // Inverted turn in place if left trigger is pressed
				leftPower = -rawLeftStickXValue;
				rightPower = rawLeftStickXValue;
			} else {
				leftPower = rawLeftStickXValue;
				rightPower = -rawLeftStickXValue;
			}
		} else {
			if (Math.abs(rawLeftStickXValue) > GTA_DEADBAND) {
				if (rawLeftStickXValue < 0)
					leftPower *= (1 - Math.abs(rawLeftStickXValue)) * GTA_STEERING;
				else {
					rightPower *= (1 - Math.abs(rawLeftStickXValue)) * GTA_STEERING;
				}
			}
		}
		setLeftRightMotorOutputs(leftPower, rightPower, true);
	}

	public void setLeftRightMotorOutputs(double leftOutput, double rightOutput, boolean isAccelerationActive) {
		if (isAccelerationActive) {
			m_leftPower = limitPowerAcc(m_leftPower, leftOutput);
			m_rightPower = limitPowerAcc(m_rightPower, rightOutput);
		} else {
			m_leftPower = leftOutput;
			m_rightPower = rightOutput;
		}

		super.tankDrive(m_leftPower, -m_rightPower, false);
	}

	// Accelerated Drive Methods
	private double limitPowerAcc(double currentPower, double newPower) {
		if (newPower >= LOWER_LIMIT) {
			if (currentPower >= LOWER_LIMIT) {
				return limitEdges(newPower, LOWER_LIMIT, currentPower + POWER_PER_CYCLE);
			} else {
				return LOWER_LIMIT;
			}
		} else if (newPower <= -LOWER_LIMIT) {
			if (currentPower <= -LOWER_LIMIT) {
				return limitEdges(newPower, currentPower - POWER_PER_CYCLE, -LOWER_LIMIT);
			} else {
				return -LOWER_LIMIT;
			}
		} else {
			return newPower;
		}
	}

	private double limitEdges(double value, double lowerLimit, double upperLimit) {
		if (value > upperLimit)
			return upperLimit;
		if (value < lowerLimit)
			return lowerLimit;
		return value;
	}

	public void setCurrentPID_Type(PID_Type currentPID_Type) {
		this.currentPID_Type = currentPID_Type;
	}

	@Override
	public void pidWrite(double output) {
		switch (currentPID_Type) {
		case ROTATE_BY_DEGREES:
			setLeftRightMotorOutputs(output, -output, true);
			break;
		case PIXY_ALIGN_TO_CUBE:
			if (0.05 < output && output < 0.6)
				arcadeDrive(-0.6, aligningXFactor * 0.8);
			else if (-0.6 < output && output < -0.05)
				arcadeDrive(0.6, aligningXFactor * 0.8);
			else
				arcadeDrive(-output, aligningXFactor * 0.8);
			break;
		default:
		}
	}

}