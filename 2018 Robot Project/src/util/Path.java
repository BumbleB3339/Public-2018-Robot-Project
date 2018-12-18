package util;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

/**
 * A Path is an object containing an array of Waypoints constructing a path, a
 * maximum velocity to run the path in and a boolean declaring whether or not
 * the path should run in reverse.
 * 
 * @author BumbleB 3339
 *
 */
public class Path {

	private BumbleWaypoint[] waypoints;
	private double maxVelocity;
	private boolean isReverse;
	private boolean isRelative;
	private String pathName;
	private PIDPreset pidPreset;

	public Path(String pathName, BumbleWaypoint[] waypoints, double maxVelocity, PIDPreset pidPreset,
			boolean isReverse) {
		this.pathName = pathName;
		this.waypoints = waypoints;
		this.maxVelocity = maxVelocity;
		this.pidPreset = pidPreset;
		this.isReverse = isReverse;
		this.isRelative = false;
	}

	public Path(String pathName, BumbleWaypoint[] waypoints, double maxVelocity, PIDPreset pidPreset, boolean isReverse,
			boolean isRelative) {
		this.pathName = pathName;
		this.waypoints = waypoints;
		this.maxVelocity = maxVelocity;
		this.pidPreset = pidPreset;
		this.isReverse = isReverse;
		this.isRelative = isRelative;
	}

	public void generate() {
		// Create a hash for the path object and check if the path exists on RoboRIO
		String hash = new BumbleHash<Path>(this).getHash();

		File pathFile = new File("/home/lvuser/paths/" + hash + ".csv");

		if (!pathFile.exists()) { // Create path and write to file
			DriverStation.reportWarning("Path '" + pathName + "' was not found, generating CSV..", false);
			// System.out.println("Path '" + pathName + "' was not found, generating
			// CSV..");
			Trajectory trajectory;
			Trajectory.Config cfg = new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC,
					Trajectory.Config.SAMPLES_HIGH, ROBOT_PROFILE.generalParams.dt, maxVelocity,
					ROBOT_PROFILE.autonomousParams.max_acceleration, ROBOT_PROFILE.autonomousParams.max_jerk);
			trajectory = Pathfinder.generate(waypoints, cfg);
			try {
				pathFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Pathfinder.writeToCSV(pathFile, trajectory);
		} else {
			DriverStation.reportWarning("Path '" + pathName + "' already generated", false);
			// System.out.println("Path '" + pathName + "' already generated");
		}
	}

	public BumbleWaypoint[] getWaypoints() {
		return waypoints;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public boolean getIsReverse() {
		return isReverse;
	}

	public boolean isRelative() {
		return isRelative;
	}

	public PIDPreset getPIDPreset() {
		return pidPreset;
	}

	@Override
	public String toString() {
		String waypointString = "[";
		for (Waypoint waypoint : waypoints) {
			waypointString += waypoint + ", ";
		}
		waypointString = waypointString.substring(0, waypointString.length() - 2) + "]";
		return "waypoints: " + waypointString + ", maxVelocity: " + maxVelocity + ", isReverse: " + isReverse
				+ ", isRelative: " + isRelative;
	}
}
