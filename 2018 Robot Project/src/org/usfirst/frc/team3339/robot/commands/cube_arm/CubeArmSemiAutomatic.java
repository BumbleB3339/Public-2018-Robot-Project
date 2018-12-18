package org.usfirst.frc.team3339.robot.commands.cube_arm;

import org.usfirst.frc.team3339.robot.OI;
import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.subsystems.CubeArm.CubeArmState;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CubeArmSemiAutomatic extends Command {

	private double magnitude;
	private final double deadbandToListenToDriver = 0.4;

	public CubeArmSemiAutomatic() {
		requires(Robot.m_cubeArm);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (Math.abs(OI.driverController.getY(Hand.kRight)) > deadbandToListenToDriver) {
			magnitude = OI.driverController.getY(Hand.kRight);
		} else {
			magnitude = OI.operatorController.getY(Hand.kRight);
		}
		Robot.m_cubeArm.changeSemiAutomaticSetpointAngle(magnitude);
	}

	@Override
	protected boolean isFinished() {
		return !Robot.m_cubeArm.isInCubeArmState(CubeArmState.SEMI_AUTOMATIC);
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
