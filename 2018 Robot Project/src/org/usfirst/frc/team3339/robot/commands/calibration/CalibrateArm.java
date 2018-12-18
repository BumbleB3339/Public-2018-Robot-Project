package org.usfirst.frc.team3339.robot.commands.calibration;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrateArm extends Command {

	public CalibrateArm() {
		requires(Robot.m_cubeArm);

	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double manualPower = Math.abs(OI.operatorController.getY(Hand.kRight)) > 0.3
				? OI.operatorController.getY(Hand.kRight) * 0.5
				: 0;
		if (Robot.m_cubeArm.isManual()) {
			Robot.m_cubeArm.setPower(manualPower);
		}

		Robot.m_cubeArm.setP(SmartDashboard.getNumber("Calibration/Cube_Arm/kP", 0.0));
		Robot.m_cubeArm.setBasePowers(SmartDashboard.getNumber("Calibration/Cube_Arm/BasePowerUp", 0.0),
				SmartDashboard.getNumber("Calibration/Cube_Arm/BasePowerDown", 0.0));

		if (OI.operatorController.getAButton()) {
			Robot.m_cubeArm.setPosition(CubeArmState.DOWN); // A
		} else if (OI.operatorController.getYButton()) {
			Robot.m_cubeArm.setPosition(CubeArmState.UP); // Y
		} else if (OI.operatorController.getBButton()) {
			Robot.m_cubeArm.setPosition(CubeArmState.MIDDLE); // B
		} else if (OI.operatorController.getBackButton()) {
			Robot.m_cubeArm.setPosition(CubeArmState.MANUAL); // BACK
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
