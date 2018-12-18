package org.usfirst.frc.team3339.robot.commands;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.Robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.followers.EncoderFollower;
import util.Path;

/**
 *	
 */
public class DrivePath extends Command {

	private EncoderFollower[] followers;
	private Path path;
	private Notifier iter;
	private boolean relativePatchUsed = false;

	private double currentTimeStamp;
	private double lastTimeStamp;
	private int iterCount = 0;
	private double intervalSum = 0.0;
	@SuppressWarnings("unused")
	private double intervalAvg = 0.0;
	private double currentTimeIntervalMs = 0.0;

	private double startingAngle = 0.0;

	public DrivePath(Path path) {
		requires(Robot.m_drivetrain);
		this.path = path;

		followers = Robot.m_drivetrain.pathSetup(path);

		setInterruptible(false);
	}

	@Override
	protected void initialize() {

		if (Robot.IS_CALIBRATION_MODE)
			followers = Robot.m_drivetrain.pathSetup(path);

		relativePatchUsed = false;
		if (path.isRelative() && Robot.m_drivetrain.getStartedFacingWall()) {
			Robot.m_drivetrain.setStartedFacingWall(false);
			relativePatchUsed = true;
		}

		startingAngle = Robot.m_drivetrain.getNavXYaw();

		Robot.m_drivetrain.setNavXYawAdjustment(path.isRelative() ? -this.startingAngle : 0);

		Robot.m_drivetrain.resetForPath(followers, path.getIsReverse());

		currentTimeStamp = Timer.getFPGATimestamp();
		lastTimeStamp = currentTimeStamp;
		iterCount = 0;
		intervalSum = 0.0;

		iter = new Notifier(new Thread() {
			@Override
			public void run() {

				currentTimeStamp = Timer.getFPGATimestamp();

				iterCount++;
				currentTimeIntervalMs = (currentTimeStamp - lastTimeStamp) * 1000; // Multiplied by 1000 to convert
																					// seconds to miliseconds
				intervalSum += currentTimeIntervalMs;
				intervalAvg = intervalSum / iterCount;

				lastTimeStamp = currentTimeStamp;

				Robot.m_drivetrain.pathFollow(followers, path.getIsReverse());
			}
		});
		iter.startPeriodic(ROBOT_PROFILE.generalParams.dt);
	}

	@Override
	protected void execute() {
		// SmartDashboard.putNumber("Average Interval Time (ms)", intervalAvg);
		// SmartDashboard.putNumber("Current Interval Time (ms)",
		// currentTimeIntervalMs);
	}

	@Override
	protected boolean isFinished() {
		return Robot.m_drivetrain.getIsProfileFinished();
	}

	@Override
	protected void end() {
		iter.stop();
		Robot.m_drivetrain.stopMotors();
		Robot.m_drivetrain.setNavXYawAdjustment(0);
		if (relativePatchUsed)
			Robot.m_drivetrain.setStartedFacingWall(true);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
