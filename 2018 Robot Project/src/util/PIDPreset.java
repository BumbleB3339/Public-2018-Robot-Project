package util;

public class PIDPreset {
	private double pValue = 0;
	private double iValue = 0;
	private double dValue = 0;
	private double fValue = 0;
	private int accelerationValue = 0;
	private int velocityValue = 0;

	/**
	 * PID Config:
	 * 
	 * @param pValue
	 * @param iValue
	 * @param dValue
	 */
	public PIDPreset(double pValue, double iValue, double dValue) {
		this.pValue = pValue;
		this.iValue = iValue;
		this.dValue = dValue;
	}

	/**
	 * PIDF and Motion Magic Config
	 * 
	 * @param pValue
	 * @param iValue
	 * @param dValue
	 * @param fValue
	 * @param accelerationValue
	 * @param velocityValue
	 */
	public PIDPreset(double pValue, double iValue, double dValue, double fValue, int accelerationValue,
			int velocityValue) {
		this.pValue = pValue;
		this.iValue = iValue;
		this.dValue = dValue;
		this.fValue = fValue;
		this.accelerationValue = accelerationValue;
		this.velocityValue = velocityValue;
	}

	public double getP() {
		return pValue;
	}

	public void setP(double p) {
		this.pValue = p;
	}

	public double getI() {
		return iValue;
	}

	public void setI(double i) {
		this.iValue = i;
	}

	public double getD() {
		return dValue;
	}

	public void setD(double d) {
		this.dValue = d;
	}

	public double getF() {
		return fValue;
	}

	public void setF(double f) {
		this.fValue = f;
	}

	public int getAcceleration() {
		return accelerationValue;
	}

	public void setAcceleration(int acceleration) {
		this.accelerationValue = acceleration;
	}

	public int getVelocity() {
		return velocityValue;
	}

	public void setVelocity(int velocity) {
		this.velocityValue = velocity;
	}
}