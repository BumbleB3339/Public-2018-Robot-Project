package util;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDOutput;

public class CubeArmMotor extends WPI_TalonSRX implements PIDOutput {

	private double basePowerUp, basePowerDown;

	public CubeArmMotor(int deviceNumber, double basePowerUp, double basePowerDown) {
		super(deviceNumber);
		this.basePowerUp = basePowerUp;
		this.basePowerDown = basePowerDown;
	}

	public void setBasePowerUp(double basePowerUp) {
		this.basePowerUp = basePowerUp;
	}

	public void setBasePowerDown(double basePowerDown) {
		this.basePowerDown = basePowerDown;
	}

	@Override
	public void pidWrite(double output) {
		double power, base;
		if (output != 0) {
			base = (output > 0) ? basePowerDown : -basePowerUp;
			power = output + base;
		} else {
			power = 0;
		}
		this.set(power);
	}

}
