package util;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import jaci.pathfinder.Waypoint;

public class BumbleWaypoint extends Waypoint {

	public BumbleWaypoint(double x, double y, double angle) {
		// angle is positive in CW direction, but the pathfinder works with CCW positive
		super(x - ROBOT_PROFILE.generalParams.cor_shift * Math.cos(angle), // x
				y - ROBOT_PROFILE.generalParams.cor_shift * (-1) * Math.sin(angle), // y
				-angle); // theta
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.angle + ")";
	}
}
