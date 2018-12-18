package org.usfirst.frc.team3339.robot.commands.cube_lift;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.RobotMode;
import org.usfirst.frc.team3339.robot.subsystems.CubeLift.CubeLiftState;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetCubeLiftPosition extends Command {
	private CubeLiftState position;
	private boolean waitForOnTarget = true;

	public SetCubeLiftPosition(CubeLiftState position) {
		requires(Robot.m_cubeLift);
		this.position = position;
	}

	public SetCubeLiftPosition(CubeLiftState position, boolean waitForOnTarget) {
		this(position);
		this.waitForOnTarget = waitForOnTarget;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.m_cubeLift.setPosition(position);

		if (DriverStation.getInstance().isAutonomous()) {
			switch (position) {
			case COLLECT:
				Robot.setRobotMode(RobotMode.COLLECT);
				break;
			case EXCHANGE:
				Robot.setRobotMode(RobotMode.EXCHANGE);
				break;
			case GROUND:
				Robot.setRobotMode(RobotMode.FOLDED);
				break;
			case SCALE:
				Robot.setRobotMode(RobotMode.SCALE);
				break;
			case SWITCH:
				Robot.setRobotMode(RobotMode.SWITCH);
				break;
			default:
				break;
			}
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (waitForOnTarget) {
			return Robot.m_cubeLift.isInPosition(position);
		} else {
			return true;
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
