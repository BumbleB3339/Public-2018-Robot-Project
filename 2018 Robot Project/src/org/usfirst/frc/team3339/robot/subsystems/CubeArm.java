package org.usfirst.frc.team3339.robot.subsystems;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.RobotMap;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.ScaleHeightMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.BumblePotentiometer;
import util.CubeArmMotor;
import util.SmartdashboardDebugging;

/**
 *
 */
public class CubeArm extends Subsystem implements SmartdashboardDebugging {

	public enum CubeArmState {
		UP, MIDDLE, DOWN, SWITCH, SCALE_HIGH, SCALE_LOW, SEMI_AUTOMATIC, MANUAL
	}

	// Angle value for all arm states
	public final double UP_ANGLE = -2;
	public final double SWITCH_ANGLE = 92;
	public final double MIDDLE_ANGLE = 40;
	public final double DOWN_ANGLE = 92;
	public final double SCALE_HIGH_ANGLE = 20;
	public final double SCALE_LOW_ANGLE = 40;

	public final double MAX_CURRENT_ALLOWED = 12;
	private static final double DEG_PER_SEC = 90;

	// Motor
	private CubeArmMotor armMotor;

	// Potentiometer related
	private BumblePotentiometer armPotentiometer;
	private final double BOTTOM_VALUE = 0, BOTTOM_VOLTAGE = ROBOT_PROFILE.cubeArmParams.voltage0, HIGH_VALUE = 90,
			HIGH_VOLTAGE = ROBOT_PROFILE.cubeArmParams.voltage90, MIN_DEFECTIVE = 0.5; // TODO Tuning needed

	// PID related
	private final double kP = ROBOT_PROFILE.cubeArmParams.kP, kI = 0.0, kD = 0.0,
			TOLERANCE = ROBOT_PROFILE.cubeArmParams.tolerance;
	private final double PID_OUTPUT_PERIOD = 0.02;
	private final double MINIMUM_INPUT = UP_ANGLE, MAXIMUM_INPUT = DOWN_ANGLE;
	private final double MINIMUM_OUTPUT = -ROBOT_PROFILE.cubeArmParams.max_up,
			MAXIMUM_OUTPUT = ROBOT_PROFILE.cubeArmParams.max_down; // positive power is down
	private PIDController armPID;

	private final CubeArmState DEFAULT_CUBE_ARM_STATE = CubeArmState.UP; // TODO: Should be UP
	private CubeArmState cubeArmState = DEFAULT_CUBE_ARM_STATE;
	private double armSetpoint = getAngleForCubeArmState(DEFAULT_CUBE_ARM_STATE);

	public CubeArm() {
		super("Cube Arm");

		// Potentiometer
		armPotentiometer = new BumblePotentiometer(RobotMap.ARM_POTENTIOMETER, BOTTOM_VALUE, BOTTOM_VOLTAGE, HIGH_VALUE,
				HIGH_VOLTAGE, MIN_DEFECTIVE);

		// Motor
		armMotor = new CubeArmMotor(RobotMap.ARM_MOTOR, ROBOT_PROFILE.cubeArmParams.base_power_up,
				ROBOT_PROFILE.cubeArmParams.base_power_down);
		armMotor.setInverted(ROBOT_PROFILE.cubeArmParams.is_inverted);

		// PIDController initialization
		armPID = new PIDController(kP, kI, kD, armPotentiometer, armMotor, PID_OUTPUT_PERIOD);
		armPID.setAbsoluteTolerance(TOLERANCE);
		armPID.setInputRange(MINIMUM_INPUT, MAXIMUM_INPUT);
		armPID.setOutputRange(MINIMUM_OUTPUT, MAXIMUM_OUTPUT);
		armPID.setSetpoint(getAngleForCubeArmState(DEFAULT_CUBE_ARM_STATE));
		armPID.enable();

		super.addChild("Arm Motor", armMotor);
		super.addChild("ArmPID", armPID);
	}

	private double getAngleForCubeArmState(CubeArmState cubeArmState) {
		switch (cubeArmState) {
		case UP:
			return UP_ANGLE;
		case MIDDLE:
			return MIDDLE_ANGLE;
		case SWITCH:
			return SWITCH_ANGLE;
		case DOWN:
			return DOWN_ANGLE;
		case SCALE_HIGH:
			return SCALE_HIGH_ANGLE;
		case SCALE_LOW:
			return SCALE_LOW_ANGLE;
		case MANUAL:
		case SEMI_AUTOMATIC:
		default:
			return 0;
		}
	}

	public double getCurrentAngle() {
		return armPotentiometer.getPotentiometerValue();
	}

	public CubeArmState getCubeArmState() {
		return cubeArmState;
	}

	public boolean isOnTarget() {
		return armPID.onTarget();
	}

	int currentCount = 0;
	final int MAX_CURRENT_COUNT = 50;

	private void stopOnCurrentOverload() {
		if (armMotor.getOutputCurrent() > MAX_CURRENT_ALLOWED) {
			currentCount += 1;
		} else {
			currentCount = 0;
		}

		if (!isInCubeArmState(CubeArmState.MANUAL) && currentCount > MAX_CURRENT_COUNT) {
			currentCount = 0;
			armPID.disable();
		}
	}

	public boolean isInCubeArmState(CubeArmState cubeArmState) {
		return cubeArmState == this.cubeArmState;
	}

	private void setCubeArmState(CubeArmState cubeArmState) {
		this.cubeArmState = cubeArmState;
	}

	public void setPosition(CubeArmState position) {
		if (position == CubeArmState.MANUAL) {
			armPID.disable();
			setCubeArmState(position);
		} else if (!isInCubeArmState(CubeArmState.MANUAL) && position == CubeArmState.SEMI_AUTOMATIC) {
			setCubeArmState(position);
		} else if (!isInCubeArmState(CubeArmState.MANUAL)) {
			changePIDSetpoint(getAngleForCubeArmState(position));
			setCubeArmState(position);
		}
	}

	public boolean isInPosition(CubeArmState position) {
		return isInCubeArmState(position) && isOnTarget();
	}

	public void changeSemiAutomaticSetpointAngle(double magnitude) {
		if (!Robot.m_cubeArm.isInCubeArmState(CubeArmState.SEMI_AUTOMATIC)) {
			return;
		}

		if (Math.abs(magnitude) < 0.4) {
			return;
		}

		double deltaAngle = magnitude * DEG_PER_SEC / 50;
		double newAngle = armSetpoint + deltaAngle;
		if (newAngle > DOWN_ANGLE) {
			newAngle = DOWN_ANGLE;
		} else if (newAngle < UP_ANGLE) {
			newAngle = UP_ANGLE;
		}
		changePIDSetpoint(newAngle);
	}

	private void changePIDSetpoint(double setpoint) {
		armSetpoint = setpoint;
		armPID.setSetpoint(armSetpoint);
		armPID.enable();
	}

	public boolean isSemiAutomatic() {
		return isInCubeArmState(CubeArmState.SEMI_AUTOMATIC);
	}

	public boolean isManual() {
		return cubeArmState == CubeArmState.MANUAL;
	}

	@Override
	public void initDefaultCommand() {

	}

	public void manualGoDown() {
		armMotor.set(ROBOT_PROFILE.cubeArmParams.down_manual_power);
	}

	public void manualGoUp() {
		armMotor.set(-ROBOT_PROFILE.cubeArmParams.up_manual_power);
	}

	public void setPower(double power) {
		armMotor.set(power);
	}

	public void setP(double p) {
		armPID.setP(p);
	}

	public void toggleManualMode() {
		setCubeArmState(isInCubeArmState(CubeArmState.MANUAL) ? CubeArmState.UP : CubeArmState.MANUAL);
		setPosition(cubeArmState);
	}

	public void setBasePowers(double basePowerUp, double basePowerDown) {
		armMotor.setBasePowerUp(basePowerUp);
		armMotor.setBasePowerDown(basePowerDown);
	}

	private boolean cubeArmScaleStateUpdated = false;
	private boolean lastReadyForScale = false;
	private ScaleHeightMode lastScaleHeightMode = ScaleHeightMode.SCALE_CUSTOM;

	private void adjustCubeArmAngleToScaleHeightMode() {
		if (OI.readyForScale && !(isSemiAutomatic() || isManual())) {
			if (Robot.m_cubeLift.getScaleHeightMode() != lastScaleHeightMode) {
				cubeArmScaleStateUpdated = false;
			}
			if (!cubeArmScaleStateUpdated) {
				switch (Robot.m_cubeLift.getScaleHeightMode()) {
				case SCALE_HIGH:
					setPosition(CubeArmState.SCALE_HIGH);
					break;
				case SCALE_LOW:
					setPosition(CubeArmState.SCALE_LOW);
					break;
				case SCALE_CUSTOM:
					if (!lastReadyForScale)
						setPosition(CubeArmState.SCALE_HIGH);
					break;
				}
			}
			cubeArmScaleStateUpdated = true;
		} else {
			cubeArmScaleStateUpdated = false;
		}
		lastReadyForScale = OI.readyForScale;
		lastScaleHeightMode = Robot.m_cubeLift.getScaleHeightMode();
	}

	private boolean isPotentiometerDefective() {
		// 10 is the acceptable distance from the range to determine whether defective
		return armPotentiometer.getPotentiometerValue() > DOWN_ANGLE + 10
				|| armPotentiometer.getPotentiometerValue() < UP_ANGLE - 10;
	}

	private final int iterationsToRumble = 25;
	private int rumbleCounter = 0;
	private final int iterationsToDetermineDefective = 50;
	private int defectiveCounter = 0;
	private boolean lastIsPotentiometerDefective = false;

	private void checkPotentiometer() {
		defectiveCounter = isPotentiometerDefective() ? defectiveCounter + 1 : 0;
		boolean isDefective = defectiveCounter >= iterationsToDetermineDefective;
		if (isDefective && !lastIsPotentiometerDefective) {
			System.out.println("gone to manual");
			setCubeArmState(CubeArmState.MANUAL);
			setPosition(cubeArmState);
			DriverStation.reportError("Arm Potentiometer out of range, Cube Arm set to manual", false);
		}

		if (isDefective && rumbleCounter <= iterationsToRumble) {
			OI.operatorController.setRumble(RumbleType.kLeftRumble, 1);
			OI.operatorController.setRumble(RumbleType.kRightRumble, 1);
			rumbleCounter++;
		} else {
			OI.operatorController.setRumble(RumbleType.kLeftRumble, 0);
			OI.operatorController.setRumble(RumbleType.kRightRumble, 0);
		}

		lastIsPotentiometerDefective = isDefective;
	}

	@Override
	public void periodic() {
		stopOnCurrentOverload();
		adjustCubeArmAngleToScaleHeightMode();
		if (DriverStation.getInstance().isOperatorControl() && DriverStation.getInstance().isEnabled()) {
			checkPotentiometer();
		}
	}

	@Override
	public void sendSmartdashboardGameTab() {
	}

	@Override
	public void sendSmartdashboardDebuggingHardware() {
		SmartDashboard.putNumber("Hardware/CubeArm/Potentiometer/Voltage", armPotentiometer.getAverageVoltage());
		SmartDashboard.putNumber("Hardware/CubeArm/Potentiometer/Angle", armPotentiometer.getPotentiometerValue());
		SmartDashboard.putBoolean("Hardware/CubeArm/Potentiometer/isDefective",
				armPotentiometer.isPotentiometerDefective());
		SmartDashboard.putNumber("Hardware/CubeArm/Power", armMotor.getMotorOutputPercent());
	}

	@Override
	public void sendSmartdashboardDebuggingLogic() {
		SmartDashboard.putBoolean("Logic/CubeArm/Manual Arm", isInCubeArmState(CubeArmState.MANUAL));
		SmartDashboard.putString("Hardware/CubeArm/State", cubeArmState.toString());
		SmartDashboard.putBoolean("Hardware/CubeArm/onTarget", armPID.onTarget());
		SmartDashboard.putBoolean("Logic/CubeArm/isPIDEnabled", armPID.isEnabled());
	}
}
