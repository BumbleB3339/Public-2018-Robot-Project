package org.usfirst.frc.team3339.robot;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;
import org.usfirst.frc.team3339.robot.autonomous.AutoPaths;
import org.usfirst.frc.team3339.robot.autonomous.AutoPoints;
import org.usfirst.frc.team3339.robot.commands.DrivePath;
import org.usfirst.frc.team3339.robot.commands.RotateByDegrees;
import org.usfirst.frc.team3339.robot.commands.calibration.CalibrateArm;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.Path;

public class Calibration {

	public enum CalibrationMode {
		ROTATE_BY_DEGREES, CUBE_LIFT, MOTION_PROFILING, CUBE_ARM, CLIMB_SERVO
	}

	public static final double CALIBRATION_VELOCITY = 3;

	static SendableChooser<CalibrationMode> calibrationModeChooser = new SendableChooser<>();
	static SendableChooser<Path> motionProfilingPathChooser = new SendableChooser<>();

	public static void initDashboardParameters() {
		AutoPoints.createWaypoints(AllianceColor.RED, AutoSide.LEFT);

		calibrationModeChooser.addDefault("Motion Profiling Calibration", CalibrationMode.MOTION_PROFILING);
		calibrationModeChooser.addObject("Lift Calibration", CalibrationMode.CUBE_LIFT);
		calibrationModeChooser.addObject("Rotate by Degrees Calibration", CalibrationMode.ROTATE_BY_DEGREES);
		calibrationModeChooser.addObject("Cube Arm Calibration", CalibrationMode.CUBE_ARM);
		calibrationModeChooser.addObject("Climb Servo Calibration", CalibrationMode.CLIMB_SERVO);

		SmartDashboard.putData("Calibration/Calibration Mode Chooser", calibrationModeChooser);

		// Cube Lift Parameters

		// Rotate by Degrees
		SmartDashboard.putNumber("Calibration/RBD/kP", ROBOT_PROFILE.drivetrainParams.rbd_kP);
		SmartDashboard.putNumber("Calibration/RBD/kI", ROBOT_PROFILE.drivetrainParams.rbd_kI);
		SmartDashboard.putNumber("Calibration/RBD/kD", ROBOT_PROFILE.drivetrainParams.rbd_kD);
		SmartDashboard.putNumber("Calibration/RBD/AngleToRotate", 90.0);

		// Motion Profiling Parameters
		motionProfilingPathChooser.addDefault("THREE_ZERO", AutoPaths.THREE_ZERO);
		motionProfilingPathChooser.addObject("THREE_ZERO_REVERSE", AutoPaths.THREE_ZERO_REVERSE);
		motionProfilingPathChooser.addObject("TWO_ZERO", AutoPaths.TWO_ZERO);
		motionProfilingPathChooser.addObject("TWO_ZERO_REVERSE", AutoPaths.TWO_ZERO_REVERSE);
		motionProfilingPathChooser.addObject("THREE_ONE", AutoPaths.THREE_ONE);
		motionProfilingPathChooser.addObject("THREE_ONE_REVERSE", AutoPaths.THREE_ONE_REVERSE);
		motionProfilingPathChooser.addObject("TWO_ONE", AutoPaths.TWO_ONE);
		motionProfilingPathChooser.addObject("TWO_ONE_REVERSE", AutoPaths.TWO_ONE_REVERSE);
		motionProfilingPathChooser.addObject("FOUR_ZERO", AutoPaths.FOUR_ZERO);
		motionProfilingPathChooser.addObject("FOUR_ZERO_REVERSE", AutoPaths.FOUR_ZERO_REVERSE);
		SmartDashboard.putData("Calibration/Motion Profiling Path Chooser", motionProfilingPathChooser);

		// TODO: Choose path velocity

		SmartDashboard.putNumber("Calibration/MP/kP", ROBOT_PROFILE.autonomousParams.fast_path_pid_preset.getP());
		SmartDashboard.putNumber("Calibration/MP/kD", ROBOT_PROFILE.autonomousParams.fast_path_pid_preset.getD());

		SmartDashboard.putNumber("Calibration/Cube_Arm/kP", ROBOT_PROFILE.cubeArmParams.kP);
		SmartDashboard.putNumber("Calibration/Cube_Arm/BasePowerUp", ROBOT_PROFILE.cubeArmParams.base_power_up);
		SmartDashboard.putNumber("Calibration/Cube_Arm/BasePowerDown", ROBOT_PROFILE.cubeArmParams.base_power_down);

		// climb
		SmartDashboard.putNumber("Calibration/Climb/ServoPosition", 0.0);
	}

	public static void startCalibration() {
		switch (calibrationModeChooser.getSelected()) {
		case MOTION_PROFILING:
			new DrivePath(motionProfilingPathChooser.getSelected()).start();
			break;
		case ROTATE_BY_DEGREES:
			new RotateByDegrees(SmartDashboard.getNumber("Calibration/RBD/AngleToRotate", 0),
					SmartDashboard.getNumber("Calibration/RBD/kP", 0),
					SmartDashboard.getNumber("Calibration/RBD/kI", 0),
					SmartDashboard.getNumber("Calibration/RBD/kD", 0)).start();
			break;
		case CUBE_LIFT:

			break;
		case CUBE_ARM:
			new CalibrateArm().start();
			break;
		case CLIMB_SERVO:
			break;
		}
	}

	public static void calibrationPeriodic() {
		switch (calibrationModeChooser.getSelected()) {
		case MOTION_PROFILING:
			// nothing
			break;
		case ROTATE_BY_DEGREES:
			// nothing
			break;
		case CUBE_LIFT:
			// nothing
			break;
		case CUBE_ARM:
			// nothing
			break;
		case CLIMB_SERVO:
			Robot.m_climbArm.setServoPosition(SmartDashboard.getNumber("Calibration/Climb/ServoPosition", 0.0));
			// System.out.println("angle: " +
			// SmartDashboard.getNumber("Calibration/Climb/ServoPosition", 0.0));
		}
	}
}
