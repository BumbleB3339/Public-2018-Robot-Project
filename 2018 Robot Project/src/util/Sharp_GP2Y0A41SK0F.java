package util;

import edu.wpi.first.wpilibj.AnalogInput;

public class Sharp_GP2Y0A41SK0F extends AnalogInput {

	/**
	 * Construct a Sharp-GP2Y0A41SK0F IR distance sensor
	 * 
	 * @param channel
	 *            The channel number to represent. 0-3 are on-board 4-7 are on the
	 *            MXP port.
	 */
	public Sharp_GP2Y0A41SK0F(int channel) {
		super(channel);
	}

	/**
	 * Get distance reading from the sensor.
	 * 
	 * @return A double representing the distance reading in cm (4-40 cm range).
	 */
	public double getDistance() {
		return 12.691 * Math.pow(this.getVoltage(), -1.089);
	}
}
