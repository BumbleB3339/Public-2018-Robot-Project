package org.usfirst.frc.team3339.robot.subsystems;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.RobotMap;
import org.usfirst.frc.team3339.robot.commands.Drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import util.BumbleDrive;
import util.BumbleDrive.PID_Type;
import util.BumbleHash;
import util.Path;
import util.SmartdashboardDebugging;
import util.pixy.PixyVision.PowerCubeType;

public class Drivetrain extends Subsystem implements SmartdashboardDebugging {

	// Actuators
	private WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.FRONT_RIGHT);
	private WPI_TalonSRX middleRight = new WPI_TalonSRX(RobotMap.MIDDLE_RIGHT);
	private WPI_TalonSRX rearRight = new WPI_TalonSRX(RobotMap.REAR_RIGHT);
	private WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.FRONT_LEFT);
	private WPI_TalonSRX middleLeft = new WPI_TalonSRX(RobotMap.MIDDLE_LEFT);

	private WPI_TalonSRX rearLeft = new WPI_TalonSRX(RobotMap.REAR_LEFT);
	private WPI_TalonSRX masterLeft = frontLeft, masterRight = frontRight;

	public BumbleDrive bumbleDrive = new BumbleDrive(masterLeft, masterRight);

	private AHRS navx = new AHRS(Port.kMXP);
	private final double NAVX_STOP_RATE = 0.05;

	// Turn By Degrees PID Related
	private PIDController rotateByDegrees_PIDController;
	private final double RBD_kP = ROBOT_PROFILE.drivetrainParams.rbd_kP, RBD_kI = ROBOT_PROFILE.drivetrainParams.rbd_kI,
			RBD_kD = ROBOT_PROFILE.drivetrainParams.rbd_kD, RBD_TOLERANCE = 8,
			RBD_MINIMUM_OUTPUT = ROBOT_PROFILE.drivetrainParams.rbd_minimum_output,
			RBD_MAXIMUM_OUTPUT = ROBOT_PROFILE.drivetrainParams.rbd_maximum_output;

	// Cube Aligning (with Pixy) PID Related
	private PIDController pixyAlignToCube_PIDController;
	private final double PIXY_kP = ROBOT_PROFILE.drivetrainParams.pixy_kP,
			PIXY_kI = ROBOT_PROFILE.drivetrainParams.pixy_kI, PIXY_kD = ROBOT_PROFILE.drivetrainParams.pixy_kD,
			PIXY_TOLERANCE = 4, PIXY_MINIMUM_OUTPUT = -1, PIXY_MAXIMUM_OUTPUT = 1;
	private final int PIXY_SETPOINT = 175;

	private final double PID_OUTPUT_PERIOD = 0.02;

	// Profiling
	private double currentPathLength;
	private double pathDistanceCovered;
	private double pathEncoderOffset;
	private boolean isProfileFinished = false;
	private double last_gyro_error = 0.0;

	private boolean startedFacingWall = false;

	public void setStartedFacingWall(boolean startedFacingWall) {
		this.startedFacingWall = startedFacingWall;
	}

	public boolean getStartedFacingWall() {
		return this.startedFacingWall;
	}

	private boolean usingEncodersInMP = true;

	private double left_encoder_offset = 0;
	private double right_encoder_offset = 0;

	private static final int CONTINOUS_CURRENT_LIMIT = 20;
	private static final int PEAK_CURRENT_LIMIT = 38;

	private static final int PEAK_CURRENT_DURATION = 60;
	private static final boolean ENABLE_CURRENT_LIMIT = false;

	private static final double VOLTAGE_COMPENSATION_SAT = 12;
	private static final boolean ENABLE_VOLTAGE_COMPENSATION = false;

	public Drivetrain() {
		super("Drivetrain");
		// Brake Mode Enabled
		frontLeft.setNeutralMode(NeutralMode.Brake);
		frontRight.setNeutralMode(NeutralMode.Brake);
		middleLeft.setNeutralMode(NeutralMode.Brake);
		middleRight.setNeutralMode(NeutralMode.Brake);
		rearLeft.setNeutralMode(NeutralMode.Brake);
		rearRight.setNeutralMode(NeutralMode.Brake);

		frontLeft.configContinuousCurrentLimit(CONTINOUS_CURRENT_LIMIT, 10);
		frontRight.configContinuousCurrentLimit(CONTINOUS_CURRENT_LIMIT, 10);
		middleLeft.configContinuousCurrentLimit(CONTINOUS_CURRENT_LIMIT, 10);
		middleRight.configContinuousCurrentLimit(CONTINOUS_CURRENT_LIMIT, 10);
		rearLeft.configContinuousCurrentLimit(CONTINOUS_CURRENT_LIMIT, 10);
		rearRight.configContinuousCurrentLimit(CONTINOUS_CURRENT_LIMIT, 10);

		frontLeft.configPeakCurrentDuration(PEAK_CURRENT_DURATION, 10);
		frontRight.configPeakCurrentDuration(PEAK_CURRENT_DURATION, 10);
		middleLeft.configPeakCurrentDuration(PEAK_CURRENT_DURATION, 10);
		middleRight.configPeakCurrentDuration(PEAK_CURRENT_DURATION, 10);
		rearLeft.configPeakCurrentDuration(PEAK_CURRENT_DURATION, 10);
		rearRight.configPeakCurrentDuration(PEAK_CURRENT_DURATION, 10);

		frontLeft.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, 10);
		frontRight.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, 10);
		middleLeft.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, 10);
		middleRight.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, 10);
		rearLeft.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, 10);
		rearRight.configPeakCurrentLimit(PEAK_CURRENT_LIMIT, 10);

		frontLeft.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		frontRight.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		middleLeft.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		middleRight.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		rearLeft.enableCurrentLimit(ENABLE_CURRENT_LIMIT);
		rearRight.enableCurrentLimit(ENABLE_CURRENT_LIMIT);

		// setInverted right side
		frontRight.setInverted(ROBOT_PROFILE.drivetrainParams.is_inverted_right);
		middleRight.setInverted(ROBOT_PROFILE.drivetrainParams.is_inverted_right);
		rearRight.setInverted(ROBOT_PROFILE.drivetrainParams.is_inverted_right);

		// setInverted left side
		frontLeft.setInverted(ROBOT_PROFILE.drivetrainParams.is_inverted_left);
		middleLeft.setInverted(ROBOT_PROFILE.drivetrainParams.is_inverted_left);
		rearLeft.setInverted(ROBOT_PROFILE.drivetrainParams.is_inverted_left);

		// setSafetyEnabled
		bumbleDrive.setSafetyEnabled(true);

		// Set Encoders
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

		// reverseSensor
		frontRight.setSensorPhase(ROBOT_PROFILE.drivetrainParams.right_sensor_phase);
		frontLeft.setSensorPhase(ROBOT_PROFILE.drivetrainParams.left_sensor_phase);

		// Disable Limit Switch
		frontLeft.overrideLimitSwitchesEnable(false);
		frontRight.overrideLimitSwitchesEnable(false);
		middleLeft.overrideLimitSwitchesEnable(false);
		middleRight.overrideLimitSwitchesEnable(false);
		rearLeft.overrideLimitSwitchesEnable(false);
		rearRight.overrideLimitSwitchesEnable(false);

		// set following mode to motors
		middleLeft.set(ControlMode.Follower, RobotMap.FRONT_LEFT);
		middleRight.set(ControlMode.Follower, RobotMap.FRONT_RIGHT);
		rearLeft.set(ControlMode.Follower, RobotMap.FRONT_LEFT);
		rearRight.set(ControlMode.Follower, RobotMap.FRONT_RIGHT);

		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);
		frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);

		frontRight.configVoltageCompSaturation(VOLTAGE_COMPENSATION_SAT, 10);
		middleRight.configVoltageCompSaturation(VOLTAGE_COMPENSATION_SAT, 10);
		rearRight.configVoltageCompSaturation(VOLTAGE_COMPENSATION_SAT, 10);
		frontLeft.configVoltageCompSaturation(VOLTAGE_COMPENSATION_SAT, 10);
		middleLeft.configVoltageCompSaturation(VOLTAGE_COMPENSATION_SAT, 10);
		rearLeft.configVoltageCompSaturation(VOLTAGE_COMPENSATION_SAT, 10);
		setVoltageCompensation(ENABLE_VOLTAGE_COMPENSATION);

		// Rotate By Degrees PID Related
		rotateByDegrees_PIDController = new PIDController(RBD_kP, RBD_kI, RBD_kD, navx, bumbleDrive, PID_OUTPUT_PERIOD);
		rotateByDegrees_PIDController.setAbsoluteTolerance(RBD_TOLERANCE);
		rotateByDegrees_PIDController.setInputRange(-180, 180);
		rotateByDegrees_PIDController.setOutputRange(RBD_MINIMUM_OUTPUT, RBD_MAXIMUM_OUTPUT);
		rotateByDegrees_PIDController.setContinuous(true);

		// pixyAlignToCube PID Related
		pixyAlignToCube_PIDController = new PIDController(PIXY_kP, PIXY_kI, PIXY_kD, Robot.m_pixyVision, bumbleDrive,
				PID_OUTPUT_PERIOD);
		pixyAlignToCube_PIDController.setAbsoluteTolerance(PIXY_TOLERANCE);
		pixyAlignToCube_PIDController.setOutputRange(PIXY_MINIMUM_OUTPUT, PIXY_MAXIMUM_OUTPUT);
		pixyAlignToCube_PIDController.setSetpoint(PIXY_SETPOINT);

		super.addChild("Front Right Motor", frontRight);
		super.addChild("Front Left Motor", frontLeft);
		super.addChild("Rear Right Motor", rearRight);
		super.addChild("Rear Left Motor", rearLeft);
		super.addChild("Middle Right Motor", middleRight);
		super.addChild("Middle Left Motor", middleLeft);
	}

	public void setVoltageCompensation(boolean enable) {
		frontRight.enableVoltageCompensation(enable);
		middleRight.enableVoltageCompensation(enable);
		rearRight.enableVoltageCompensation(enable);
		frontLeft.enableVoltageCompensation(enable);
		middleLeft.enableVoltageCompensation(enable);
		rearRight.enableVoltageCompensation(enable);
	}

	public void setDrivetrainNeutralMode(NeutralMode neutralMode) {
		frontLeft.setNeutralMode(neutralMode);
		frontRight.setNeutralMode(neutralMode);
		middleLeft.setNeutralMode(neutralMode);
		middleRight.setNeutralMode(neutralMode);
		rearLeft.setNeutralMode(neutralMode);
		rearRight.setNeutralMode(neutralMode);
	}

	private static final double SECONDS_FROM_NEUTRAL_TO_FULL = 0.1;

	public void enableRampRate(boolean enable) {
		double rampRate = enable ? SECONDS_FROM_NEUTRAL_TO_FULL : 0;
		frontRight.configOpenloopRamp(rampRate, 10);
		middleRight.configOpenloopRamp(rampRate, 10);
		rearRight.configOpenloopRamp(rampRate, 10);
		frontLeft.configOpenloopRamp(rampRate, 10);
		middleLeft.configOpenloopRamp(rampRate, 10);
		rearRight.configOpenloopRamp(rampRate, 10);
	}

	/**
	 * Generate a path and configure EncoderFollowers
	 * 
	 * @param path
	 *            The path to follow
	 * @return An array of EncoderFollowers where left is i=0 and right is i=1
	 * @throws IOException
	 */
	public EncoderFollower[] pathSetup(Path path) {
		EncoderFollower left;
		EncoderFollower right;
		Trajectory trajectory;

		// Create a hash for the path object and check if the path exists on RoboRIO
		String hash = new BumbleHash<Path>(path).getHash();
		File pathFile = new File("/home/lvuser/paths/" + hash + ".csv");

		// This part is for safety only, all paths should be generated by the path
		// registry.
		if (pathFile.exists()) { // Read from the path file
			System.out.println("Requested path exists.");
			trajectory = Pathfinder.readFromCSV(pathFile);
		} else { // Create path and write to file
			System.out.println("Requested path does not exist, generating path.");
			Trajectory.Config cfg = new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC,
					Trajectory.Config.SAMPLES_HIGH, ROBOT_PROFILE.generalParams.dt, path.getMaxVelocity(),
					ROBOT_PROFILE.autonomousParams.max_acceleration, ROBOT_PROFILE.autonomousParams.max_jerk);
			trajectory = Pathfinder.generate(path.getWaypoints(), cfg);
			try {
				pathFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Pathfinder.writeToCSV(pathFile, trajectory);
		}

		// Update current path length
		currentPathLength = trajectory.get(trajectory.segments.length - 1).position;
		// Update path distance covered
		pathDistanceCovered = 0;

		TankModifier modifier = new TankModifier(trajectory).modify((ROBOT_PROFILE.generalParams.wheel_base_width));
		last_gyro_error = 0.0;
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());

		double kP, kD;
		if (Robot.IS_CALIBRATION_MODE) {
			kP = SmartDashboard.getNumber("Calibration/MP/kP", 0.0);
			kD = SmartDashboard.getNumber("Calibration/MP/kD", 0.0);
			System.out.println("------------- Path Setup " + kP + ", " + kD); // TODO: Why Does It Exist?!?
		} else {
			kP = path.getPIDPreset().getP();
			kD = path.getPIDPreset().getD();
		}

		left.configurePIDVA(kP, 0.0, kD, ROBOT_PROFILE.autonomousParams.kV, ROBOT_PROFILE.autonomousParams.kA);
		right.configurePIDVA(kP, 0.0, kD, ROBOT_PROFILE.autonomousParams.kV, ROBOT_PROFILE.autonomousParams.kA);

		return new EncoderFollower[] { left, // 0
				right, // 1
		};
	}

	public void resetForPath(EncoderFollower[] followers, boolean reverse) {
		EncoderFollower left = followers[0];
		EncoderFollower right = followers[1];

		int leftPosition = reverse ? -frontLeft.getSelectedSensorPosition(0) : frontLeft.getSelectedSensorPosition(0);
		int rightPosition = reverse ? -frontRight.getSelectedSensorPosition(0)
				: frontRight.getSelectedSensorPosition(0);

		left_encoder_offset = leftPosition;
		right_encoder_offset = rightPosition;

		left.configureEncoder(leftPosition, ROBOT_PROFILE.generalParams.ticks_per_rev,
				ROBOT_PROFILE.generalParams.wheel_diameter);
		right.configureEncoder(rightPosition, ROBOT_PROFILE.generalParams.ticks_per_rev,
				ROBOT_PROFILE.generalParams.wheel_diameter);

		isProfileFinished = false;
		left.reset();
		right.reset();
		bumbleDrive.setSafetyEnabled(false);
	}

	public boolean getIsProfileFinished() {
		return isProfileFinished;
	}

	private int distanceToTicks(double distance, double encoder_offset) {
		return (int) (distance / (ROBOT_PROFILE.generalParams.wheel_diameter * Math.PI) //
				* ROBOT_PROFILE.generalParams.ticks_per_rev + encoder_offset);
	}

	public void pathFollow(EncoderFollower[] followers, boolean reverse) {
		EncoderFollower left = followers[0];
		EncoderFollower right = followers[1];

		int leftPosition = reverse ? -frontLeft.getSelectedSensorPosition(0) : frontLeft.getSelectedSensorPosition(0);
		int rightPosition = reverse ? -frontRight.getSelectedSensorPosition(0)
				: frontRight.getSelectedSensorPosition(0);

		// Update path distance covered
		pathDistanceCovered = ((leftPosition - pathEncoderOffset) / ROBOT_PROFILE.generalParams.ticks_per_rev);

		double l;
		double r;

		if (!usingEncodersInMP) {
			leftPosition = distanceToTicks(left.getSegment().position, left_encoder_offset);
			rightPosition = distanceToTicks(right.getSegment().position, right_encoder_offset);
		}

		l = left.calculate(leftPosition);
		r = right.calculate(rightPosition);

		// Add 180 if the following cases occur: the robot started facing the wall and
		// the path is straight or the robot didn't start facing the wall and the path
		// is reverse.
		double gyro_heading = ((startedFacingWall && !reverse) || (!startedFacingWall && reverse)) ? -getNavXYaw() + 180 // true
				: -getNavXYaw(); // false
		double angle_setpoint = Pathfinder.r2d(left.getHeading());
		double angleDifference = Pathfinder.boundHalfDegrees(angle_setpoint - gyro_heading);

		double turn = ROBOT_PROFILE.autonomousParams.gP * angleDifference + (ROBOT_PROFILE.autonomousParams.gD
				* ((angleDifference - last_gyro_error) / ROBOT_PROFILE.generalParams.dt));

		// turn = 0; // TODO: This is only temporary!

		last_gyro_error = angleDifference;
		if (!reverse) {
			bumbleDrive.setLeftRightMotorOutputs(l - turn, r + turn, false);
		} else {
			bumbleDrive.setLeftRightMotorOutputs(-l - turn, -r + turn, false);
		}

		isProfileFinished = left.isFinished() && right.isFinished();

		// SmartDashboard.putNumber("Left Calculated", l);
		// SmartDashboard.putNumber("Right Calculated", r);
		//
		// SmartDashboard.putNumber("Left Calculated with Turn", l - turn);
		// SmartDashboard.putNumber("Right Calculated with Turn", r + turn);

		// System.out.println("R: " + (r - turn));
		// System.out.println("L: " + (l + turn));
		// System.out.println("Turn: " + turn);
		// System.out.println("Angle Error: " + angleDifference);

	}

	public double getPathDistanceCovered() {
		return pathDistanceCovered;
	}

	public double getCurrentPathLength() {
		return currentPathLength;
	}

	public double getPercentOfPath() {
		return pathDistanceCovered / currentPathLength;
	}

	public double getPathDistanceLeft() {
		return currentPathLength - pathDistanceCovered;
	}

	// Initialize TalonSRX motor controllers to driving mode
	public void initDriveMode() {
		masterRight.set(ControlMode.PercentOutput, 0);
		masterLeft.set(ControlMode.PercentOutput, 0);
		bumbleDrive.setSafetyEnabled(true);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	public double getAverageVoltage() {
		return (frontLeft.getMotorOutputVoltage() + middleLeft.getMotorOutputVoltage()
				+ rearLeft.getMotorOutputVoltage() + frontRight.getMotorOutputVoltage()
				+ middleRight.getMotorOutputVoltage() + rearRight.getMotorOutputVoltage()) / 6;
	}

	// stops the motors
	public void stopMotors() {
		masterRight.set(ControlMode.PercentOutput, 0);
		masterLeft.set(ControlMode.PercentOutput, 0);
	}

	public void initPixyAlignToCube(PowerCubeType PIDCubeType) {
		initDriveMode();
		Robot.m_pixyVision.setPIDCubeType(PIDCubeType);
		bumbleDrive.setCurrentPID_Type(PID_Type.PIXY_ALIGN_TO_CUBE);
		bumbleDrive.setAligningXFactor(0.0);
		pixyAlignToCube_PIDController.enable();
	}

	public boolean isPixyAlignToCubeOnTarget() {
		return pixyAlignToCube_PIDController.onTarget();
	}

	public void initRotateByDegrees(double setpoint, boolean isAbsolute) {
		initDriveMode();
		bumbleDrive.setCurrentPID_Type(PID_Type.ROTATE_BY_DEGREES);

		rotateByDegrees_PIDController.setSetpoint(Pathfinder.boundHalfDegrees(
				isAbsolute ? (startedFacingWall ? setpoint + 180 : setpoint) : getNavXYaw() + setpoint));
		rotateByDegrees_PIDController.enable();
	}

	public boolean isRotateByDegreesOnTarget() {
		return rotateByDegrees_PIDController.onTarget();
	}

	public void endDrivetrainPID() {
		pixyAlignToCube_PIDController.disable();
		rotateByDegrees_PIDController.disable();
		initDriveMode();
	}

	public double getNavXAngle() {
		return navx.getAngle();
	}

	public double getNavXRate() {
		return navx.getRate();
	}

	public int getRawLeftEncoderTicksVelocity() {
		return frontLeft.getSelectedSensorVelocity(0);
	}

	public int getRawLeftEncoderTicksPosition() {
		return frontLeft.getSelectedSensorPosition(0);
	}

	public int getRawRightEncoderTicksVelocity() {
		return frontRight.getSelectedSensorVelocity(0);
	}

	public int getRawRightEncoderTicksPosition() {
		return frontRight.getSelectedSensorPosition(0);
	}

	public double getVoltage() {
		return frontLeft.getMotorOutputVoltage();
	}

	public double getLeftVelocity() {
		return frontLeft.getSelectedSensorVelocity(0);
	}

	public double getNavxRate() {
		return navx.getRate();
	}

	public boolean getNavxIsRotating() {
		return navx.getRate() > NAVX_STOP_RATE;
	}

	public double getNavXYaw() {
		return Pathfinder.boundHalfDegrees(navx.getYaw() + yawAdjustment);
	}

	public double getTBDError() {
		return rotateByDegrees_PIDController.getError();
	}

	public void resetNavx() {
		navx.reset();
	}

	private double yawAdjustment = 0;

	public void setNavXYawAdjustment(double adjustment) {
		this.yawAdjustment = adjustment;
	}

	@Override
	public void sendSmartdashboardGameTab() {
		SmartDashboard.putNumber("Game/NavX", navx.getAngle());

	}

	@Override
	public void sendSmartdashboardDebuggingHardware() {
		SmartDashboard.putNumber("Hardware/Drive Encoders/Left/Velocity", frontLeft.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Hardware/Drive Encoders/Left/Position", frontLeft.getSelectedSensorPosition(0));

		SmartDashboard.putNumber("Hardware/Drive Encoders/Right/Velocity", frontRight.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Hardware/Drive Encoders/Right/Position", frontRight.getSelectedSensorPosition(0));

		SmartDashboard.putNumber("Hardware/NavX/Angle", navx.getAngle());
		SmartDashboard.putNumber("Hardware/NavX/Rate", navx.getRate());

		// SmartDashboard.putNumber("Hardware/Drive Vout/T0-RL",
		// rearLeft.getMotorOutputVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vout/T1-ML",
		// middleLeft.getMotorOutputVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vout/T2-FL",
		// frontLeft.getMotorOutputVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vout/T3-RR",
		// rearRight.getMotorOutputVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vout/T4-MR",
		// middleRight.getMotorOutputVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vout/T5-FR",
		// frontRight.getMotorOutputVoltage());
		//
		// SmartDashboard.putNumber("Hardware/Drive Percent/T0-RL",
		// rearLeft.getMotorOutputPercent());
		// SmartDashboard.putNumber("Hardware/Drive Percent/T1-ML",
		// middleLeft.getMotorOutputPercent());
		// SmartDashboard.putNumber("Hardware/Drive Percent/T2-FL",
		// frontLeft.getMotorOutputPercent());
		// SmartDashboard.putNumber("Hardware/Drive Percent/T3-RR",
		// rearRight.getMotorOutputPercent());
		// SmartDashboard.putNumber("Hardware/Drive Percent/T4-MR",
		// middleRight.getMotorOutputPercent());
		// SmartDashboard.putNumber("Hardware/Drive Percent/T5-FR",
		// frontRight.getMotorOutputPercent());
		//
		// SmartDashboard.putNumber("Hardware/Drive Vin/T0-RL",
		// rearLeft.getBusVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vin/T1-ML",
		// middleLeft.getBusVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vin/T2-FL",
		// frontLeft.getBusVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vin/T3-RR",
		// rearRight.getBusVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vin/T4-MR",
		// middleRight.getBusVoltage());
		// SmartDashboard.putNumber("Hardware/Drive Vin/T5-FR",
		// frontRight.getBusVoltage());
	}

	@Override
	public void sendSmartdashboardDebuggingLogic() {
		// TODO Auto-generated method stub
	}

	public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
		bumbleDrive.setLeftRightMotorOutputs(leftOutput, rightOutput, false);
	}

	public void setRotateByDegreesPID(double kP, double kI, double kD) {
		rotateByDegrees_PIDController.setP(kP);
		rotateByDegrees_PIDController.setI(kI);
		rotateByDegrees_PIDController.setD(kD);
	}

	@Override
	public void periodic() {
	}
}
