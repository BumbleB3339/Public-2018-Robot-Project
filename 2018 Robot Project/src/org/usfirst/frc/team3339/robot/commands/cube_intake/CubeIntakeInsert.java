package org.usfirst.frc.team3339.robot.commands.cube_intake;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CubeIntakeInsert extends Command {

	private boolean isTimed = false;
	private double timeoutToDetermineCubeIn = 0.4;
	private double cubeTimeoutCounter = 0.0;
	private boolean wasCubeDetectedIn = false;
	private double lastFPGATimestamp = 0;
	private double customPower = -99;

	public CubeIntakeInsert() {
		requires(Robot.m_cubeIntake);
	}

	public CubeIntakeInsert(double timeout, double customPower) {
		super(timeout);
		requires(Robot.m_cubeIntake);
		isTimed = true;
		this.customPower = customPower;
	}

	public CubeIntakeInsert(double timeout) {
		super(timeout);
		requires(Robot.m_cubeIntake);
		isTimed = true;
	}

	@Override
	protected void initialize() {
		cubeTimeoutCounter = 0.0;
		wasCubeDetectedIn = false;
		lastFPGATimestamp = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		double currentFPGATimestamp = Timer.getFPGATimestamp();
		if (customPower != -99) {
			Robot.m_cubeIntake.insertCube(customPower);
		} else {
			Robot.m_cubeIntake.insertCube();
		}
		wasCubeDetectedIn = Robot.m_cubeIntake.isCubeIn() ? true : wasCubeDetectedIn;
		cubeTimeoutCounter = wasCubeDetectedIn ? cubeTimeoutCounter + (currentFPGATimestamp - lastFPGATimestamp)
				: cubeTimeoutCounter;
		lastFPGATimestamp = currentFPGATimestamp;
	}

	@Override
	protected boolean isFinished() {
		if (isTimed)
			return cubeTimeoutCounter >= timeoutToDetermineCubeIn || super.isTimedOut();
		return false;// cubeTimeoutCounter >= timeoutToDetermineCubeIn;
	}

	@Override
	protected void end() {
		Robot.m_cubeIntake.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
