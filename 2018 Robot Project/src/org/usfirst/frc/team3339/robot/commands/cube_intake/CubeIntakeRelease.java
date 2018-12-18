package org.usfirst.frc.team3339.robot.commands.cube_intake;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CubeIntakeRelease extends Command {

	private final double RELEASE_TIME = 0.8;
	private boolean powerReleaseOverride = false;
	private boolean useTimeout = true;
	private double power = -999;

	public CubeIntakeRelease() {
		requires(Robot.m_cubeIntake);
	}

	public CubeIntakeRelease(boolean useTimeout) {
		requires(Robot.m_cubeIntake);
		this.useTimeout = useTimeout;
	}

	public CubeIntakeRelease(boolean powerReleaseOverride, boolean useTimeout) {
		this(useTimeout);
		this.powerReleaseOverride = powerReleaseOverride;
	}

	public CubeIntakeRelease(double power, boolean useTimeout) {
		this(useTimeout);
		this.power = power;
	}

	public CubeIntakeRelease(boolean powerReleaseOverride, boolean useTimeout, boolean isRegularPower) {
		this(useTimeout);
		this.powerReleaseOverride = powerReleaseOverride;
	}

	@Override
	protected void initialize() {
		if (useTimeout) {
			super.setTimeout(RELEASE_TIME);
		}
	}

	@Override
	protected void execute() {
		if (powerReleaseOverride) {
			Robot.m_cubeIntake.releaseCube(true);
		} else if (power != -999) {
			Robot.m_cubeIntake.releaseCube(power);
		} else {
			Robot.m_cubeIntake.releaseCube();
		}
	}

	@Override
	protected boolean isFinished() {
		if (useTimeout) {
			return super.isTimedOut();
		} else {
			return false;
		}
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
