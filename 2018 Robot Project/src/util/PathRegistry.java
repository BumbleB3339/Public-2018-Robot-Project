package util;

import java.io.File;
import java.util.ArrayList;

import org.usfirst.frc.team3339.robot.Robot;
import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;
import org.usfirst.frc.team3339.robot.autonomous.AutoPoints;

/**
 * The Path Registry holds all paths and generates non-generated paths upon
 * robot initialization.
 * 
 * @author BumbleB #3339
 *
 */
public class PathRegistry {

	private static PathRegistry registryInstance = new PathRegistry();

	private ArrayList<Path> registry = new ArrayList<Path>();

	/**
	 * Add a path to the registry.
	 * 
	 * @param path
	 *            The path to add to the registry.
	 */
	public void add(Path path) {
		registry.add(path);
	}

	/**
	 * Clear the registry.
	 */
	public void clear() {
		registry.clear();
	}

	/**
	 * Generate all paths that are not already generated.
	 */
	public void generate() {
		try {
			File dir = new File("/home/lvuser/paths/");
			if (!dir.exists())
				dir.mkdir();

		} catch (Exception e) {
			System.out.println("Error While Creating Path Directory!");
		}

		if (Robot.IS_AUTO_TEST_MODE_ACTIVE) {
			// add only left and right of red
			AutoPoints.createWaypoints(AllianceColor.RED, AutoSide.RIGHT);
			AutoPoints.createWaypoints(AllianceColor.RED, AutoSide.LEFT);
		} else {
			// Add all paths to registry
			AutoPoints.createWaypoints(AllianceColor.RED, AutoSide.RIGHT);
			AutoPoints.createWaypoints(AllianceColor.BLUE, AutoSide.RIGHT);
			AutoPoints.createWaypoints(AllianceColor.RED, AutoSide.LEFT);
			AutoPoints.createWaypoints(AllianceColor.BLUE, AutoSide.LEFT);
		}

		for (Path path : registry) {
			path.generate();
		}
	}

	/**
	 * Return the PathRegistry instance.
	 * 
	 * @return The PathRegistry
	 */
	public static PathRegistry getInstance() {
		return registryInstance;
	}
}
