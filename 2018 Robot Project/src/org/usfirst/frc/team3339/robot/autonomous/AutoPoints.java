package org.usfirst.frc.team3339.robot.autonomous;

import static org.usfirst.frc.team3339.robot.InitProfiles.FIELD_PROFILE;
import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;

import jaci.pathfinder.Pathfinder;
import util.BumbleWaypoint;

/**
 * A class containing static variables which are autonomous points calculated
 * using the field and robot profiles.
 * 
 * @author BumbleB 3339
 *
 */
public class AutoPoints {

	private static AutoSide m_side;
	private static AllianceColor m_color;

	private final static double CUBE_WIDTH = 0.33;

	public static final BumbleWaypoint zeroWaypoint = new BumbleWaypoint(0, 0, 0);

	// Place switch and collect
	public final static double distanceToReleasePosition = 0.90;
	public final static double distanceToPreCollectPosition = 0.45;
	public final static double distanceToCollectPosition = -0.45;
	public static double totalDistanceToSwitchCollect = -(distanceToCollectPosition + distanceToPreCollectPosition
			+ distanceToReleasePosition);
	public static final BumbleWaypoint switchCubePreCollectPosition_Relative = new BumbleWaypoint(
			distanceToPreCollectPosition, 0, Pathfinder.d2r(0));
	public static final BumbleWaypoint SwitchReleasePosition_Relative = new BumbleWaypoint(distanceToReleasePosition, 0,
			Pathfinder.d2r(0));
	public static final BumbleWaypoint switchCubeCollectPosition_Relative = new BumbleWaypoint(
			distanceToCollectPosition, 0, Pathfinder.d2r(0));
	public static final BumbleWaypoint collectPositionFromCubePreApproach = new BumbleWaypoint(
			-totalDistanceToSwitchCollect, 0, Pathfinder.d2r(0));

	public static BumbleWaypoint midStartPos, midReturnPos, sideStartPos, switchFront, nullTerritoryCenterReverse,
			sideCloseScaleFirstCubeReleasePosition, cubeApproach, farScaleWithRotatePreReleasePosition,
			ScaleCubeCollect, switchPassageForFarScale, switchFrontCubePreCollect, beforeCableProtectorForFarScale,
			afterCableProtectorForFarScale, farSwitchPreRotation, beforeCableProtectorForFarSwitch,
			middleSwitchCubeReleasePosition, middleSwitchSecondCubePreCollectPosition,
			middleSwitchPreThirdCubeCollectPosition, farScalePreReleasePosition,
			farScalePostCollectPreReleaseRelativePosition;

	public static void createWaypoints(AllianceColor color, AutoSide side) {
		m_side = side;
		m_color = color;

		AutoSide otherSide = (side == AutoSide.LEFT) ? AutoSide.RIGHT : AutoSide.LEFT;

		double middleSwitchFirstCubeReleasePositionXAdjustment = 0.15;
		double middleSwitchFirstCubeReleasePositionYAdjustment = -0.2;
		middleSwitchCubeReleasePosition = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) + middleSwitchFirstCubeReleasePositionXAdjustment, // x
				adjustToLeftSideY(
						FIELD_PROFILE.getSwitchToWall(color, side) + FIELD_PROFILE.getSwitchSideToPlateBackboard()
								- CUBE_WIDTH / 2.0 + middleSwitchFirstCubeReleasePositionYAdjustment), // y
				adjustToLeftSideAngle(0)); // theta

		midStartPos = new BumbleWaypoint(0.0, // x
				FIELD_PROFILE.getExchangeToFarWall(color) - ROBOT_PROFILE.generalParams.real_width / 2.0, // y
				adjustToLeftSideAngle(0.0)); // theta

		midReturnPos = new BumbleWaypoint(midStartPos.x + 0.20, // x
				midStartPos.y, // y
				adjustToLeftSideAngle(0.0)); // theta

		middleSwitchPreThirdCubeCollectPosition = new BumbleWaypoint(0.70, // x
				adjustToLeftSideY(FIELD_PROFILE.getFieldWidth(color) / 2.0 + 0.2), // y
				adjustToLeftSideAngle(0.0)); // theta

		// Always starts facing the field
		sideStartPos = new BumbleWaypoint(ROBOT_PROFILE.generalParams.robot_length, // x
				adjustToLeftSideY(FIELD_PROFILE.getAllianceWallEdgeToWall(color, side)
						+ ROBOT_PROFILE.generalParams.real_width / 2.0), // y
				adjustToLeftSideAngle(0.0)); // theta

		double switchFrontXAjustment = 0.15;
		switchFront = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) - ROBOT_PROFILE.generalParams.robot_length
						+ switchFrontXAjustment, // x
				adjustToLeftSideY(FIELD_PROFILE.getSwitchToWall(color, side)
						+ FIELD_PROFILE.getSwitchSideToPlateBackboard() / 2.0), // y
				adjustToLeftSideAngle(0)); // theta

		double cubeApproachXAdjustment = 0.0;
		double cubeApproachYAdjustment = 0.0;
		cubeApproach = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) + FIELD_PROFILE.getSwitchDepth(color, side)
						+ CUBE_WIDTH + cubeApproachXAdjustment, // x
				adjustToLeftSideY(FIELD_PROFILE.getSwitchToWall(color, side) + cubeApproachYAdjustment), // y //
				// Calibrate
				// tweaks
				adjustToLeftSideAngle(25));

		nullTerritoryCenterReverse = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToCableProtector(color, side) - ROBOT_PROFILE.generalParams.robot_length
						- 0.4, // x
				adjustToLeftSideY(FIELD_PROFILE.getPlatformToWall(color, side) / 2 + 0.05), // y
				adjustToLeftSideAngle(0));

		double distanceFromCubeForFarSwitch = 0.65; // was 0.15
		double distanceBeforeCableProtectorForFarSwitch = 0.05;
		beforeCableProtectorForFarSwitch = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) + FIELD_PROFILE.getSwitchDepth(color, side)
						+ CUBE_WIDTH + ROBOT_PROFILE.generalParams.real_width / 2.0 + distanceFromCubeForFarSwitch,
				adjustToLeftSideY(FIELD_PROFILE.getFieldWidth(color) / 2.0 - distanceBeforeCableProtectorForFarSwitch),
				adjustToLeftSideAngle(-90));

		double beforeCableProtectorForFarScaleDistanceFromCube = 0.45;
		double beforeCableProtectorForFarScaleDistanceBeforeCableProtector = 0.05;
		beforeCableProtectorForFarScale = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) + FIELD_PROFILE.getSwitchDepth(color, side)
						+ CUBE_WIDTH + ROBOT_PROFILE.generalParams.real_width / 2.0
						+ beforeCableProtectorForFarScaleDistanceFromCube,
				adjustToLeftSideY(FIELD_PROFILE.getCableProtectorToWall(color)
						- beforeCableProtectorForFarScaleDistanceBeforeCableProtector),
				adjustToLeftSideAngle(-90));

		double afterCableProtectorForFarScaleDistanceAfterCableProtector = 0.40;
		afterCableProtectorForFarScale = new BumbleWaypoint(beforeCableProtectorForFarScale.x,
				adjustToLeftSideY(
						FIELD_PROFILE.getCableProtectorToWall(color) + ROBOT_PROFILE.generalParams.robot_length
								+ afterCableProtectorForFarScaleDistanceAfterCableProtector),
				adjustToLeftSideAngle(-90));

		double farScaleWithRotatePreReleasePositionYAdjustment = 1.05;
		farScaleWithRotatePreReleasePosition = new BumbleWaypoint(afterCableProtectorForFarScale.x,
				adjustToLeftSideY(FIELD_PROFILE.getFieldWidth(color) - FIELD_PROFILE.getPlatformToWall(color, otherSide)
						+ farScaleWithRotatePreReleasePositionYAdjustment),
				adjustToLeftSideAngle(-90));

		double farScalePreReleasePositionXAdjustment = -0.15;
		double farScalePreReleasePositionYAdjustment = 1.4;
		farScalePreReleasePosition = new BumbleWaypoint(
				// FIELD_PROFILE.getDriverStationToNullTerritory(color, otherSide) +
				// farScalePreReleasePositionXAdjustment,
				FIELD_PROFILE.getDriverStationToCableProtector(color, otherSide)
						+ farScalePreReleasePositionXAdjustment,
				adjustToLeftSideY(FIELD_PROFILE.getFieldWidth(color) - FIELD_PROFILE.getPlatformToWall(color, otherSide)
						+ ROBOT_PROFILE.generalParams.real_width / 2 + farScalePreReleasePositionYAdjustment),
				adjustToLeftSideAngle(-30));

		double scaleCubeCollectXAjustment = 0.20;
		double scaleCubeCollectYAdjustment = 0.0;
		// Side close scale back up to this point before rotate to cube
		ScaleCubeCollect = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) + FIELD_PROFILE.getSwitchDepth(color, side)
						+ CUBE_WIDTH + ROBOT_PROFILE.generalParams.robot_length + scaleCubeCollectXAjustment,
				adjustToLeftSideY(FIELD_PROFILE.getSwitchToWall(color, side)
						- ROBOT_PROFILE.generalParams.real_width / 2.0 + scaleCubeCollectYAdjustment),
				adjustToLeftSideAngle(0));

		double switchPassageForFarScaleYAdjustment = -0.45;
		double switchPassageForFarScaleXAdjustment = -0.30;
		switchPassageForFarScale = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) + FIELD_PROFILE.getSwitchDepth(color, side)
						+ switchPassageForFarScaleXAdjustment,
				adjustToLeftSideY(FIELD_PROFILE.getAllianceWallEdgeToWall(color, side)
						+ ROBOT_PROFILE.generalParams.real_width / 2.0 + switchPassageForFarScaleYAdjustment),
				adjustToLeftSideAngle(0.0));

		double switchFrontToCollectDistance = 1.5;
		switchFrontCubePreCollect = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) - ROBOT_PROFILE.generalParams.robot_length
						+ switchFrontXAjustment - switchFrontToCollectDistance, // x
				adjustToLeftSideY(FIELD_PROFILE.getSwitchToWall(color, side)
						+ FIELD_PROFILE.getSwitchSideToPlateBackboard() / 2.0), // y
				adjustToLeftSideAngle(0)); // theta

		double distanceFromCube = 0.60;
		double farSwitchPreRotationYAdjustment = -0.15;
		farSwitchPreRotation = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToSwitchFront(color) + FIELD_PROFILE.getSwitchDepth(color, side)
						+ CUBE_WIDTH + ROBOT_PROFILE.generalParams.real_width / 2.0 + distanceFromCube,
				adjustToLeftSideY(FIELD_PROFILE.getFieldWidth(color) - FIELD_PROFILE.getSwitchToWall(color, otherSide)
						- 2.5 * CUBE_WIDTH + ROBOT_PROFILE.generalParams.robot_length / 2.0
						+ farSwitchPreRotationYAdjustment),
				adjustToLeftSideAngle(-90));

		farScalePostCollectPreReleaseRelativePosition = new BumbleWaypoint(-2.2, adjustToLeftSideRelativeY(-0.0),
				adjustToLeftSideAngle(30));

		// New Side Scale Autonomous Waypoints
		double absoluteAngleToSideCloseScaleFirstCubeRelease = -15;
		double scaleFirstCubeDistanceFromPlatform = 0.55;
		double sideScaleFirstCubeXAdjustment = 0.35;
		sideCloseScaleFirstCubeReleasePosition = new BumbleWaypoint(
				FIELD_PROFILE.getDriverStationToNullTerritory(color, side) + sideScaleFirstCubeXAdjustment, // x
				adjustToLeftSideY(FIELD_PROFILE.getPlatformToWall(color, side) - scaleFirstCubeDistanceFromPlatform), // y
				adjustToLeftSideAngle(absoluteAngleToSideCloseScaleFirstCubeRelease)); // theta

		// Fix BumbleWaypoint[] pointers
		AutoPaths.reInitialize();
	}

	private static double adjustToLeftSideY(double y) {
		return (m_side == AutoSide.LEFT) ? FIELD_PROFILE.getFieldWidth(m_color) - y : y;
	}

	private static double adjustToLeftSideRelativeY(double y) {
		return (m_side == AutoSide.LEFT) ? -y : y;
	}

	// original angle is according to the right side
	private static double adjustToLeftSideAngle(double degrees) {
		return adjustSignToLeftSide(Pathfinder.d2r(degrees));
	}

	private static double adjustSignToLeftSide(double value) {
		return value * (m_side == AutoSide.LEFT ? -1 : 1);
	}
}
