package org.usfirst.frc.team3339.robot.subsystems;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.PIDPreset;
import util.SmartdashboardDebugging;

public class CubeLift extends Subsystem implements SmartdashboardDebugging {

	public enum CubeLiftState {
		SCALE, SWITCH, EXCHANGE, COLLECT, GROUND, MANUAL
	}

	public enum ScaleHeightMode {
		SCALE_HIGH, SCALE_CUSTOM, SCALE_LOW
	}

	public enum CollectHeightMode {
		THIRD_LEVEL, SECOND_LEVEL, FIRST_LEVEL
	}

	// Motors
	private WPI_TalonSRX liftMotor;
	private WPI_TalonSRX liftMotorFollower;

	// Manual operation power
	private final double MANUAL_UP_POWER = 0.8;
	private final double MANUAL_DOWN_POWER = -0.4;
	private final double BASE_POWER = ROBOT_PROFILE.cubeLiftParams.base_power; // Power to apply so that lift does not
																				// fall down

	// PID Related
	private final PIDPreset UP_MOVEMENT_PID_PRESET = ROBOT_PROFILE.cubeLiftParams.up_movement_pid_preset;
	private final PIDPreset DOWN_MOVEMENT_PID_PRESET = ROBOT_PROFILE.cubeLiftParams.down_movement_pid_preset;
	private final double RAMP_RATE = ROBOT_PROFILE.cubeLiftParams.ramp_rate;

	private final double NOMINAL_OUTPUT_FORWARD = BASE_POWER;
	private final double PEAK_OUTPUT_FORWARD = 1;
	private final double NOMINAL_OUTPUT_REVERSE = BASE_POWER;
	private final double PEAK_OUTPUT_REVERSE = -1;

	private final int MAX_ALLOWABLE_LIFT_HEIGHT_TICKS = ROBOT_PROFILE.cubeLiftParams.max_allowable_lift_height_ticks;

	// For logical aspects of the subsystem
	private boolean manualOverrideMode = false; // TODO: This should be false, only true for testing

	// Height Values (cm from lowest position) // TODO: Tune
	private static final double GROUND_HEIGHT = 0, COLLECT_LEVEL1_HEIGHT = 0, COLLECT_LEVEL2_HEIGHT = 30,
			COLLECT_LEVEL3_HEIGHT = 60, EXCHANGE_HEIGHT = 5, SWITCH_HEIGHT = 80, SWITCH_AUTONOMOUS_HEIGHT = 85,
			SCALE_LOW_HEIGHT = 155, SCALE_HIGH_HEIGHT = ROBOT_PROFILE.cubeLiftParams.scale_high_height;

	// Ticks to cm parameters
	private final int COUNTS_PER_REVOLUTION = 4096;
	private final double GEAR_RATIO = 18.0 / 22;
	private final double HTD_TIMING_PULLEY_DIAMETER = 6; // In cm

	private final ScaleHeightMode DEFAULT_SCALE_HEIGHT_MODE = ScaleHeightMode.SCALE_HIGH;
	private final CubeLiftState DEFAULT_CUBE_LIFT_STATE = CubeLiftState.GROUND;
	private final CollectHeightMode DEFAULT_COLLECT_HEIGHT_MODE = CollectHeightMode.FIRST_LEVEL;

	// Save current lift states
	public CubeLiftState cubeLiftState = DEFAULT_CUBE_LIFT_STATE; // TODO: shouldn't it be private with a public getter?
	public ScaleHeightMode scaleHeightMode = DEFAULT_SCALE_HEIGHT_MODE; // TODO: shouldn't it be private with a public
	// getter?
	public CollectHeightMode collectHeightMode = DEFAULT_COLLECT_HEIGHT_MODE; // TODO: shouldn't it be private with a
	// public getter?

	private final double CUSTOM_HEIGHT_CHANGE_VALUE = 0.5; // cm per 20ms

	private double adjustedSwitchHeight = SWITCH_HEIGHT;

	// Minimum speed in sensorValue per 100ms to determine lift is on target
	private final int SPEED_FOR_ON_TARGET = 800;
	private final int RANGE_FOR_ON_TARGET = 20; // cm
	private final int MIN_ITERATIONS_STABLE = 5;

	public CollectHeightMode getCollectHeightMode() {
		return collectHeightMode;
	}

	public boolean IsInCollectHeightMode(CollectHeightMode collectHeightMode) {
		return this.collectHeightMode == collectHeightMode;
	}

	public ScaleHeightMode getScaleHeightMode() {
		return scaleHeightMode;
	}

	public boolean IsInScaleHeightMode(ScaleHeightMode scaleheightMode) {
		return this.scaleHeightMode == scaleheightMode;
	}

	public CubeLift() {
		super("Cube Lift");

		// Motors
		liftMotor = new WPI_TalonSRX(RobotMap.LIFT_MOTOR);
		liftMotorFollower = new WPI_TalonSRX(RobotMap.LIFT_MOTOR_FOLLOWER);

		// Followers
		liftMotorFollower.set(ControlMode.Follower, RobotMap.LIFT_MOTOR);

		// Encoder
		liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

		// Sensor Phase
		liftMotor.setSensorPhase(ROBOT_PROFILE.cubeLiftParams.sensor_phase);

		// Ramp rate
		liftMotor.configClosedloopRamp(RAMP_RATE, 10);
		liftMotor.configOpenloopRamp(RAMP_RATE, 10);
		liftMotorFollower.configClosedloopRamp(RAMP_RATE, 10);
		liftMotorFollower.configOpenloopRamp(RAMP_RATE, 10);

		// Disable Limit Switch
		liftMotor.overrideLimitSwitchesEnable(false);
		liftMotorFollower.overrideLimitSwitchesEnable(false);

		// Voltage compensation
		liftMotor.configVoltageCompSaturation(12, 10);
		liftMotor.enableVoltageCompensation(true);
		liftMotorFollower.configVoltageCompSaturation(12, 10);
		liftMotorFollower.enableVoltageCompensation(true);

		// Output regulation
		liftMotor.configNominalOutputForward(NOMINAL_OUTPUT_FORWARD, 10);
		liftMotor.configPeakOutputForward(PEAK_OUTPUT_FORWARD, 10);
		liftMotor.configNominalOutputReverse(NOMINAL_OUTPUT_REVERSE, 10);
		liftMotor.configPeakOutputReverse(PEAK_OUTPUT_REVERSE, 10);

		// Neutral mode
		liftMotor.setNeutralMode(NeutralMode.Brake);
		liftMotorFollower.setNeutralMode(NeutralMode.Brake);

		// PID default values
		ApplyPID_Preset(UP_MOVEMENT_PID_PRESET);

		// Reset sensor position
		resetEncoderPosition();

		// Set inverted
		liftMotor.setInverted(ROBOT_PROFILE.cubeLiftParams.is_main_inverted);
		liftMotorFollower.setInverted(ROBOT_PROFILE.cubeLiftParams.is_follower_inverted);

		super.addChild("Lift Motor", liftMotor);
		super.addChild("Lift Motor Follower", liftMotorFollower);
	}

	private int getHeightTicksForLiftState(CubeLiftState cubeLiftState) {
		int chosenHeightTicks = 0;
		switch (cubeLiftState) {
		case SCALE:
			chosenHeightTicks = convertHeightToTicks(currentAdjustedScaleHeight);
			break;
		case SWITCH:
			chosenHeightTicks = convertHeightToTicks(
					DriverStation.getInstance().isAutonomous() ? SWITCH_AUTONOMOUS_HEIGHT : adjustedSwitchHeight);
			break;
		case EXCHANGE:
			chosenHeightTicks = convertHeightToTicks(EXCHANGE_HEIGHT);
			break;
		case COLLECT:
			chosenHeightTicks = currentAdjustedCollectHeight;
			break;
		case GROUND:
			chosenHeightTicks = convertHeightToTicks(GROUND_HEIGHT);
			break;
		case MANUAL:
			break;
		}
		return chosenHeightTicks;
	}

	private int getHeightTicksForHeightMode(ScaleHeightMode scaleHeightMode) {
		switch (scaleHeightMode) {
		case SCALE_HIGH:
			return convertHeightToTicks(SCALE_HIGH_HEIGHT);
		case SCALE_LOW:
			return convertHeightToTicks(SCALE_LOW_HEIGHT);
		case SCALE_CUSTOM:
			return convertHeightToTicks(currentAdjustedScaleHeight);
		}
		return 0;
	}

	private int getHeightTicksForCollectMode(CollectHeightMode collectHeightMode) {
		switch (collectHeightMode) {
		case FIRST_LEVEL:
			return convertHeightToTicks(COLLECT_LEVEL1_HEIGHT);
		case SECOND_LEVEL:
			return convertHeightToTicks(COLLECT_LEVEL2_HEIGHT);
		case THIRD_LEVEL:
			return convertHeightToTicks(COLLECT_LEVEL3_HEIGHT);
		}
		return 0;
	}

	public int convertHeightToTicks(double height) {
		double circumference = Math.PI * HTD_TIMING_PULLEY_DIAMETER;
		double bigWheelRotations = height / circumference;
		double ticks = (bigWheelRotations / GEAR_RATIO) * COUNTS_PER_REVOLUTION;
		return (int) ticks;
	}

	public double convertTicksToHeight(int ticks) {
		double circumference = Math.PI * HTD_TIMING_PULLEY_DIAMETER;
		double bigWheelRotations = (double) ticks / COUNTS_PER_REVOLUTION * GEAR_RATIO;
		double height = bigWheelRotations * circumference;
		return height;
	}

	public void resetEncoderPosition() {
		liftMotor.setSelectedSensorPosition(0, 0, 10);
	}

	private void ApplyPID_Preset(PIDPreset PID_Preset) {
		liftMotor.config_kP(0, PID_Preset.getP(), 0);
		liftMotor.config_kI(0, PID_Preset.getI(), 0);
		liftMotor.config_kD(0, PID_Preset.getD(), 0);
	}

	public int getRawEncoderTicksPosition() {
		return liftMotor.getSelectedSensorPosition(0);
	}

	public int getRawEncoderTicksVelocity() {
		return liftMotor.getSelectedSensorVelocity(0);
	}

	public void setPosition(CubeLiftState position) {
		if (!shouldManualModeBeActive()) {
			if (getRawEncoderTicksPosition() > getHeightTicksForLiftState(position)) {
				ApplyPID_Preset(DOWN_MOVEMENT_PID_PRESET);
			} else {
				ApplyPID_Preset(UP_MOVEMENT_PID_PRESET);
			}
			setCubeLiftState(position);
			liftMotor.set(ControlMode.Position, getHeightTicksForLiftState(position));

			// updateCubeLiftOnTarget to prevent race condition
			// updateCubeLiftOnTarget();
			iterationsCounter = 0;
			onTarget = false;
		}
	}

	private CubeLiftState preInterruptCubeLiftState = DEFAULT_CUBE_LIFT_STATE;
	private boolean isInInterruptedMode = false;

	public void interruptLift() {
		preInterruptCubeLiftState = cubeLiftState;
		isInInterruptedMode = true;
		setCubeLiftState(CubeLiftState.MANUAL);
	}

	public void unInterruptLift() {
		if (isInInterruptedMode) {
			// This is an ugly patch to a bug, blame Aric..
			if (preInterruptCubeLiftState == CubeLiftState.MANUAL) {
				switch (Robot.robotMode) {
				case FOLDED:
					preInterruptCubeLiftState = CubeLiftState.GROUND;
					break;
				case COLLECT:
					preInterruptCubeLiftState = CubeLiftState.COLLECT;
					break;
				case EXCHANGE:
					preInterruptCubeLiftState = CubeLiftState.EXCHANGE;
					break;
				case SCALE:
					preInterruptCubeLiftState = CubeLiftState.SCALE;
					break;
				case SWITCH:
					preInterruptCubeLiftState = CubeLiftState.SWITCH;
					break;
				}
				setPosition(preInterruptCubeLiftState);
			} else {
				setPosition(preInterruptCubeLiftState);
			}
		}
		isInInterruptedMode = false;

	}

	private void setCubeLiftState(CubeLiftState cubeLiftState) {
		this.cubeLiftState = cubeLiftState;
	}

	public boolean isInState(CubeLiftState cubeLiftState) {
		return cubeLiftState == this.cubeLiftState;
	}

	// Called periodically to determine physical state
	// Counter for the number of times to check if on target and the number of
	// iterations to check
	private boolean onTarget = false;
	private int iterationsCounter = 0;

	private void updateCubeLiftOnTarget() {
		if (Math.abs(liftMotor.getClosedLoopError(0)) < convertHeightToTicks(RANGE_FOR_ON_TARGET)) {
			if (Math.abs(liftMotor.getSelectedSensorVelocity(0)) < SPEED_FOR_ON_TARGET) {
				iterationsCounter++;
				if (iterationsCounter > MIN_ITERATIONS_STABLE) {
					onTarget = true;
				}
			} else {
				iterationsCounter = 0;
				onTarget = false;
			}
		} else {
			iterationsCounter = 0;
			onTarget = false;
		}
	}

	private boolean isOnTarget() {
		return onTarget;
	}

	public boolean isInPosition(CubeLiftState position) {
		return (isInState(position) && isOnTarget()) || isInState(CubeLiftState.MANUAL);
	}

	@Override
	public void initDefaultCommand() {

	}

	public void setManualOverride(boolean enable) {
		this.manualOverrideMode = enable;
	}

	public boolean isInManualOverride() {
		return this.manualOverrideMode;
	}

	private boolean shouldManualModeBeActive() {
		return isInManualOverride();
	}

	private boolean isManualMoving = false;
	private boolean lastManualModeBeActive = false;

	private void updateManualMode() {
		if (shouldManualModeBeActive()) {
			setCubeLiftState(CubeLiftState.MANUAL);
		} else if (lastManualModeBeActive) {
			interruptLift();
		}
		if (!isManualMoving && isInState(CubeLiftState.MANUAL))
			liftMotor.set(ControlMode.PercentOutput, BASE_POWER);
		lastManualModeBeActive = shouldManualModeBeActive();
	}

	public void startManualMovingMode() {
		this.isManualMoving = true;
	}

	public void stopManualMovingMode() {
		this.isManualMoving = false;
	}

	public void manualCubeLiftUp() {
		if (isInState(CubeLiftState.MANUAL))
			liftMotor.set(ControlMode.PercentOutput, MANUAL_UP_POWER);
	}

	public void manualCubeLiftDown() {
		if (isInState(CubeLiftState.MANUAL))
			liftMotor.set(ControlMode.PercentOutput, MANUAL_DOWN_POWER);
	}

	public void setScaleHeightMode(ScaleHeightMode scaleHeightMode) {
		this.scaleHeightMode = scaleHeightMode;
	}

	private double currentAdjustedScaleHeight = convertTicksToHeight(
			getHeightTicksForHeightMode(DEFAULT_SCALE_HEIGHT_MODE));
	private double lastAdjustedScaleHeight = currentAdjustedScaleHeight;

	private void updateScaleHeight() {
		currentAdjustedScaleHeight = convertTicksToHeight(getHeightTicksForHeightMode(scaleHeightMode));
		if (convertHeightToTicks(currentAdjustedScaleHeight) > MAX_ALLOWABLE_LIFT_HEIGHT_TICKS)
			currentAdjustedScaleHeight = convertTicksToHeight(MAX_ALLOWABLE_LIFT_HEIGHT_TICKS);
		else if (currentAdjustedScaleHeight < 0)
			currentAdjustedScaleHeight = 0;
		if (currentAdjustedScaleHeight != lastAdjustedScaleHeight && isInState(CubeLiftState.SCALE))
			setPosition(CubeLiftState.SCALE);
		lastAdjustedScaleHeight = currentAdjustedScaleHeight;
	}

	public void fineTuneScaleHeightUp() {
		if (isInState(CubeLiftState.SCALE)) {
			setScaleHeightMode(ScaleHeightMode.SCALE_CUSTOM);
			currentAdjustedScaleHeight += CUSTOM_HEIGHT_CHANGE_VALUE;
		}
	}

	public void fineTuneScaleHeightDown() {
		if (isInState(CubeLiftState.SCALE)) {
			setScaleHeightMode(ScaleHeightMode.SCALE_CUSTOM);
			currentAdjustedScaleHeight -= CUSTOM_HEIGHT_CHANGE_VALUE;
		}
	}

	public void fineTuneSwitchHeightUp() {
		adjustedSwitchHeight += CUSTOM_HEIGHT_CHANGE_VALUE;
		adjustedSwitchHeight = Math.min(adjustedSwitchHeight, SWITCH_HEIGHT + 30);
		setPosition(CubeLiftState.SWITCH);
	}

	public void fineTuneSwitchHeightDown() {
		adjustedSwitchHeight -= CUSTOM_HEIGHT_CHANGE_VALUE;
		adjustedSwitchHeight = Math.max(adjustedSwitchHeight, SWITCH_HEIGHT);
		setPosition(CubeLiftState.SWITCH);
	}

	public void setCollectHeightMode(CollectHeightMode collectHeightMode) {
		this.collectHeightMode = collectHeightMode;
	}

	private int currentAdjustedCollectHeight = getHeightTicksForCollectMode(DEFAULT_COLLECT_HEIGHT_MODE);
	private int lastAdjustedCollectHeight = currentAdjustedCollectHeight;

	private void updateCollectHeight() {
		currentAdjustedCollectHeight = getHeightTicksForCollectMode(collectHeightMode);
		if (currentAdjustedCollectHeight != lastAdjustedCollectHeight && isInState(CubeLiftState.COLLECT))
			setPosition(CubeLiftState.COLLECT);
		lastAdjustedCollectHeight = currentAdjustedCollectHeight;
	}

	public void raiseCollectHeightMode() {
		if (collectHeightMode == CollectHeightMode.FIRST_LEVEL)
			collectHeightMode = CollectHeightMode.SECOND_LEVEL;
		else if (collectHeightMode == CollectHeightMode.SECOND_LEVEL)
			collectHeightMode = CollectHeightMode.THIRD_LEVEL;
	}

	public void lowerCollectHeightMode() {
		if (collectHeightMode == CollectHeightMode.THIRD_LEVEL)
			collectHeightMode = CollectHeightMode.SECOND_LEVEL;
		else if (collectHeightMode == CollectHeightMode.SECOND_LEVEL)
			collectHeightMode = CollectHeightMode.FIRST_LEVEL;
	}

	@Override
	public void periodic() {
		updateCubeLiftOnTarget();
		updateManualMode();
		updateScaleHeight();
		updateCollectHeight();
		// System.out.println("Cube Lift State:" + cubeLiftState);
	}

	@Override
	public void sendSmartdashboardGameTab() {
		// Scale Height Mode
		SmartDashboard.putBoolean("Game/Scale Height/High", IsInScaleHeightMode(ScaleHeightMode.SCALE_HIGH));
		SmartDashboard.putBoolean("Game/Scale Height/Low", IsInScaleHeightMode(ScaleHeightMode.SCALE_LOW));
		SmartDashboard.putBoolean("Game/Scale Height/Custom", IsInScaleHeightMode(ScaleHeightMode.SCALE_CUSTOM));

		// Collect Height Mode
		SmartDashboard.putBoolean("Game/Collect Height/First Level",
				IsInCollectHeightMode(CollectHeightMode.FIRST_LEVEL));
		SmartDashboard.putBoolean("Game/Collect Height/Second Level",
				IsInCollectHeightMode(CollectHeightMode.SECOND_LEVEL));
		SmartDashboard.putBoolean("Game/Collect Height/Third Level",
				IsInCollectHeightMode(CollectHeightMode.THIRD_LEVEL));
	}

	@Override
	public void sendSmartdashboardDebuggingHardware() {
		SmartDashboard.putNumber("Hardware/Cube Lift/Encoder Velocity", liftMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Hardware/Cube Lift/Encoder Position", liftMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Hardware/Cube Lift/Lift Height",
				convertTicksToHeight(liftMotor.getSelectedSensorPosition(0)));
		// SmartDashboard.putNumber("Hardware/Cube Lift/Error",
		// liftMotor.getClosedLoopError(0));
		// SmartDashboard.putNumber("Hardware/Cube Lift/Output",
		// liftMotor.getMotorOutputPercent());
		// SmartDashboard.putNumber("Hardware/Cube Lift/Target",
		// liftMotor.getClosedLoopTarget(0));
	}

	@Override
	public void sendSmartdashboardDebuggingLogic() {
		// Scale Height Mode
		SmartDashboard.putString("Logic/CubeLift/Scale Height Mod", scaleHeightMode.toString());
		SmartDashboard.putString("Logic/CubeLift/CubeLiftState", cubeLiftState.toString());
		SmartDashboard.putString("Logic/CubeLift/Collect Height Mod", collectHeightMode.toString());

		SmartDashboard.putBoolean("Logic/CubeLift/Manual Lift", isInManualOverride());

	}
}
