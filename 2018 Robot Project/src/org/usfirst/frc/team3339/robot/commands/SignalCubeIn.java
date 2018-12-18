package org.usfirst.frc.team3339.robot.commands;

import org.usfirst.frc.team3339.robot.OI;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SignalCubeIn extends Command {

	private double iterationCounter = 0;
	private final int ITERATIONS_BETWEEN_RUMBLES = 5;
	private final int MAX_RUMBLES = 20;
	private boolean isRumbling = true;
	private int rumbleTimes = 0;

	public SignalCubeIn() {
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		iterationCounter = 0;
		isRumbling = true;
		rumbleTimes = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (iterationCounter < ITERATIONS_BETWEEN_RUMBLES) {
			iterationCounter++;
			if (isRumbling) {
				OI.driverController.setRumble(RumbleType.kLeftRumble, 0);
				OI.driverController.setRumble(RumbleType.kRightRumble, 0);

				OI.operatorController.setRumble(RumbleType.kLeftRumble, 1);
				OI.operatorController.setRumble(RumbleType.kRightRumble, 1);
			} else {
				OI.driverController.setRumble(RumbleType.kLeftRumble, 0);
				OI.driverController.setRumble(RumbleType.kRightRumble, 0);

				OI.operatorController.setRumble(RumbleType.kLeftRumble, 0);
				OI.operatorController.setRumble(RumbleType.kRightRumble, 0);
			}
		} else {
			iterationCounter = 0;
			isRumbling = !isRumbling;
			rumbleTimes++;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return rumbleTimes > MAX_RUMBLES;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		OI.driverController.setRumble(RumbleType.kLeftRumble, 0);
		OI.driverController.setRumble(RumbleType.kRightRumble, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
