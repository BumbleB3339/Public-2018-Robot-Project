package util;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;

public class BumblePotentiometer extends AnalogInput implements PIDSource {
	private double firstValue = 0;
	private double firstVoltage = 0;
	private double secondValue = 0;
	private double secondVoltage = 0;
	private double minValueToDetermineDefective = 0;

	public BumblePotentiometer(int analogChannel, double firstValue, double firstVoltage, double secondValue,
			double secondVoltage, double minValueToDetermineDefective) {
		super(analogChannel);
		this.firstValue = firstValue;
		this.firstVoltage = firstVoltage;
		this.secondValue = secondValue;
		this.secondVoltage = secondVoltage;
		this.minValueToDetermineDefective = minValueToDetermineDefective;
	}

	public BumblePotentiometer(int analogChannel) {
		super(analogChannel);
	}

	private double potConvert(double voltage) {
		double m = (firstValue - secondValue) / (firstVoltage - secondVoltage);
		return (m * (voltage - firstVoltage) + firstValue);
	}

	public double getPotentiometerValue() {
		return potConvert(getAverageVoltage());
	}

	public boolean isPotentiometerDefective() {
		return getAverageVoltage() < minValueToDetermineDefective;
	}

	@Override
	public double pidGet() {
		return getPotentiometerValue();
	}
}
