package org.usfirst.frc.team3339.robot.subsystems;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.Sharp_GP2Y0A41SK0F;
import util.SmartdashboardDebugging;

public class CubeIntake extends Subsystem implements SmartdashboardDebugging {

	private final double CURRENT_THRESHOLD = 16.0; // TODO: Calibrate to real values

	private WPI_VictorSPX intakeMotorRight;
	private WPI_VictorSPX intakeMotorLeft;

	private Sharp_GP2Y0A41SK0F rightIR_Sensor;
	private Sharp_GP2Y0A41SK0F leftIR_Sensor;

	private final double INSERT_POWER = 1.0;
	private final double POWER_RELEASE_POWER = -1.0;
	private final double REGULAR_RELEASE_POWER = -0.7;
	private final double WEAK_RELEASE_POWER = -0.4;

	private final double DISTANCE_FOR_CUBE_IN = 10;

	private boolean manualOverrideMode = false;

	public boolean isPowerRelease = false;
	public boolean isRegularRelease = false;

	public CubeIntake() {
		// Motors
		intakeMotorRight = new WPI_VictorSPX(RobotMap.INTAKE_RIGHT);
		intakeMotorLeft = new WPI_VictorSPX(RobotMap.INTAKE_LEFT);

		// Set Inverted
		intakeMotorRight.setInverted(ROBOT_PROFILE.cubeIntakeParams.is_inverted_right);
		intakeMotorLeft.setInverted(ROBOT_PROFILE.cubeIntakeParams.is_inverted_left);

		// Disable Limit Switch
		intakeMotorLeft.overrideLimitSwitchesEnable(false);
		intakeMotorRight.overrideLimitSwitchesEnable(false);
		intakeMotorLeft.set(ControlMode.PercentOutput, 0);
		intakeMotorRight.set(ControlMode.PercentOutput, 0);

		rightIR_Sensor = new Sharp_GP2Y0A41SK0F(RobotMap.RIGHT_INTAKE_IR);
		leftIR_Sensor = new Sharp_GP2Y0A41SK0F(RobotMap.LEFT_INTAKE_IR);
	}

	private void manualInsertCube() {
		intakeMotorRight.set(INSERT_POWER);
		intakeMotorLeft.set(INSERT_POWER);

	}

	private void manualInsertCube(double customPower) {
		intakeMotorRight.set(customPower);
		intakeMotorLeft.set(customPower);

	}

	private double getDistanceReading() {
		// return rightIR_Sensor.getDistance();
		return (rightIR_Sensor.getDistance() + leftIR_Sensor.getDistance()) / 2.0;
	}

	private double fixCounter = 0;

	private void automaticInsertCube() {
		if (isOverCurrentThreshold() && !isCubeIn() && !isInManualOverride() && fixCounter < 8) {
			fixCounter++;
			intakeMotorRight.set(INSERT_POWER * 0.5);
			intakeMotorLeft.set(-INSERT_POWER * 0.5);
		} else {
			intakeMotorRight.set(INSERT_POWER);
			intakeMotorLeft.set(INSERT_POWER);
		}

	}

	public void insertCube() {
		if (isInManualOverride() || DriverStation.getInstance().isAutonomous())
			manualInsertCube();
		else
			automaticInsertCube();
	}

	public void insertCube(double customPower) {
		if (isInManualOverride())
			manualInsertCube(customPower);
		else
			automaticInsertCube();
	}

	public void startNewInsert() {
		fixCounter = 0;
	}

	public void releaseCube() {
		System.out.println("regular release " + isRegularRelease);
		if (isPowerRelease) {
			intakeMotorRight.set(POWER_RELEASE_POWER);
			intakeMotorLeft.set(POWER_RELEASE_POWER);
		} else if (isRegularRelease) {
			intakeMotorRight.set(REGULAR_RELEASE_POWER);
			intakeMotorLeft.set(REGULAR_RELEASE_POWER);
		} else {
			intakeMotorRight.set(WEAK_RELEASE_POWER);
			intakeMotorLeft.set(WEAK_RELEASE_POWER);
		}
	}

	public void releaseCube(boolean isPowerRelease) {
		intakeMotorRight.set(isPowerRelease ? POWER_RELEASE_POWER : REGULAR_RELEASE_POWER);
		intakeMotorLeft.set(isPowerRelease ? POWER_RELEASE_POWER : REGULAR_RELEASE_POWER);
	}

	public void releaseCube(double power) {
		intakeMotorRight.set(-Math.abs(power));
		intakeMotorLeft.set(-Math.abs(power));
	}

	public void stop() {
		intakeMotorRight.set(0);
		intakeMotorLeft.set(0);
	}

	public void setManualOverride(boolean enable) {
		this.manualOverrideMode = enable;
	}

	public boolean isInManualOverride() {
		return this.manualOverrideMode;
	}

	private boolean isCubeReallyIn = false;
	private boolean lastCubeIn = false;
	private int cubeInCounter = 0;
	private final int ITERATIONS_TO_CONSIDER_CUBE_IN = 5;

	private void updateCubeIn() {
		boolean currentCubeIn = getDistanceReading() < DISTANCE_FOR_CUBE_IN && !isInManualOverride();
		if (lastCubeIn && !currentCubeIn) {
			cubeInCounter = 0;
		}
		if (currentCubeIn) {
			cubeInCounter++;
		}

		lastCubeIn = getDistanceReading() < DISTANCE_FOR_CUBE_IN && !isInManualOverride();
		isCubeReallyIn = cubeInCounter > ITERATIONS_TO_CONSIDER_CUBE_IN;
	}

	public boolean isCubeIn() {
		return isCubeReallyIn;
	}

	private int currentCounter = 0;

	private void checkCurrentThreshold() {
		if (Robot.PDP.getCurrent(RobotMap.INTAKE_LEFT_PDP) > CURRENT_THRESHOLD
				|| Robot.PDP.getCurrent(RobotMap.INTAKE_RIGHT_PDP) > CURRENT_THRESHOLD) {
			currentCounter += 1;
		} else {
			currentCounter = 0;
		}
	}

	public boolean isOverCurrentThreshold() {
		return currentCounter > 15;
	}

	@Override
	public void initDefaultCommand() {
	}

	@Override
	public void sendSmartdashboardGameTab() {
		SmartDashboard.putBoolean("Game/Cube In?", isCubeIn());
		SmartDashboard.putBoolean("Game/Intake Manual", isInManualOverride());
	}

	@Override
	public void sendSmartdashboardDebuggingHardware() {
		SmartDashboard.putNumber("Hardware/Intake/LeftIR", leftIR_Sensor.getDistance());
		SmartDashboard.putNumber("Hardware/Intake/RightIR", rightIR_Sensor.getDistance());
	}

	@Override
	public void sendSmartdashboardDebuggingLogic() {
		SmartDashboard.putBoolean("Logic/CubeIntake/Intake Manual", isInManualOverride());
	}

	@Override
	public void periodic() {
		checkCurrentThreshold();
		updateCubeIn();

		// TODO: Comment when not debugging
		// System.out.println("Right IR Distance: " + rightIR_Sensor.getDistance());
		// System.out.println("Left IR Distance: " + leftIR_Sensor.getDistance());
		// System.out.println("Is Cube In: " + isCubeIn());
		// -----
	}
}
