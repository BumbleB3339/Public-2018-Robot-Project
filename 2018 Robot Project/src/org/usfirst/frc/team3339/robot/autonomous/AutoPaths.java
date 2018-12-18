
package org.usfirst.frc.team3339.robot.autonomous;

import static org.usfirst.frc.team3339.robot.InitProfiles.ROBOT_PROFILE;

import java.lang.reflect.Field;

import org.usfirst.frc.team3339.robot.Calibration;

import util.BumbleWaypoint;
import util.Path;
import util.PathRegistry;

public class AutoPaths {

	public static Path TWO_ONE, TWO_ONE_REVERSE, THREE_ZERO, TWO_ZERO, THREE_ONE, THREE_ZERO_REVERSE, TWO_ZERO_REVERSE,
			THREE_ONE_REVERSE, FOUR_ZERO_REVERSE, FOUR_ZERO, middleToSwitchFront, switchFrontToMiddle,
			toSwitchReleasePosition, switchToMiddleNullTerritory, sideToCloseScale, sideToFarScaleWithRotate,
			sideCloseScalePostFirstCubeToCubeInsert, sideFarScaleToFirstReleasePosition,
			cubePreApproachToCubeCollectPositionForward, cubePreApproachToCubeCollectPositionBackward,
			sideCloseScaleToSecondReleasePosition, sideCloseScaleToSecondBackupPosition, sideStartingPosToCubeCollect,
			cubeCollectSecondSwitchCubeReleaseBackward, cubeCollectSecondSwitchCubeReleaseForward,
			switchFrontToPreCollectPosition, middlePostSwitchCollectToSwitchFront,
			frontSwitchPreCollectToCollectPosition, sideToFarSwitchPreRotation, frontSwitchCollectToPostCollectPosition,
			farScaleToCubeCollect, middleSwitchFirstCubeReleaseToPreSecondCubeCollect, middleSwitchThirdCubeCollect,
			middleSwitchThirdCubePostCollect, sideToFarScale, farScalePostCollectBackUpToReleasePosition,
			backOffFromSwitchCubeCollect, sideCloseSwitchToThirdCubeCollect,
			sideCloseScaleCloseSwitchPostSecondCubeReleaseBackoff, sideFarScaleBackoffFromSecondCubeRelease,
			sideFarSwitchToFirstCubeReleasePosition, sideFarSwitchCubeCollectSecondSwitchCubeReleaseForward,
			middleSwitchFrontToPreThirdCubeCollect;

	private static double calibrationVelocity = Calibration.CALIBRATION_VELOCITY;

	public static void reInitialize() {
		THREE_ZERO = new Path("THREE_ZERO",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(3.0, 0, 0) },
				calibrationVelocity, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		FOUR_ZERO_REVERSE = new Path("FOUR_ZERO_REVERSE",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(-4.0, 0, 0) },
				calibrationVelocity, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		FOUR_ZERO = new Path("FOUR_ZERO",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(4.0, 0, 0) },
				calibrationVelocity, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		TWO_ONE_REVERSE = new Path("TWO_ONE_REVERSE",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(-2, -1, 0) },
				calibrationVelocity, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		TWO_ZERO = new Path("TWO_ZERO",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(2, 0, 0) }, calibrationVelocity,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		TWO_ONE = new Path("TWO_ONE", new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(2, 1, 0) },
				calibrationVelocity, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		THREE_ONE = new Path("THREE_ONE",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(3, 1, 0) }, calibrationVelocity,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		THREE_ZERO_REVERSE = new Path("THREE_ZERO_REVERSE",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(-3, 0, 0) }, calibrationVelocity,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		TWO_ZERO_REVERSE = new Path("TWO_ZERO_REVERSE",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(-2, 0, 0) }, calibrationVelocity,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		THREE_ONE_REVERSE = new Path("THREE_ONE_REVERSE",
				new BumbleWaypoint[] { new BumbleWaypoint(0, 0, 0), new BumbleWaypoint(-3, -1, 0) },
				calibrationVelocity, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		middleSwitchFirstCubeReleaseToPreSecondCubeCollect = new Path(
				"middleSwitchFirstCubeReleaseToPreSecondCubeCollect",
				new BumbleWaypoint[] { AutoPoints.middleSwitchCubeReleasePosition,
						AutoPoints.middleSwitchSecondCubePreCollectPosition },
				1.5, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		middleToSwitchFront = new Path("middleToSwitchFront",
				new BumbleWaypoint[] { AutoPoints.midStartPos, AutoPoints.switchFront }, 1.5,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		switchFrontToMiddle = new Path("switchFrontToMiddle",
				new BumbleWaypoint[] { AutoPoints.switchFront, AutoPoints.midReturnPos, }, 1.5,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		middleSwitchFrontToPreThirdCubeCollect = new Path("MiddleSwitchFrontToPreThirdCubeCollect",
				new BumbleWaypoint[] { AutoPoints.switchFront, AutoPoints.middleSwitchPreThirdCubeCollectPosition, },
				1.5, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		toSwitchReleasePosition = new Path("toSwitchReleasePosition",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, AutoPoints.SwitchReleasePosition_Relative }, 2.0,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		switchToMiddleNullTerritory = new Path("switchToMiddleNullTerritory",
				new BumbleWaypoint[] { AutoPoints.cubeApproach, AutoPoints.nullTerritoryCenterReverse }, 1.5,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		sideToCloseScale = new Path("sideToCloseScale",
				new BumbleWaypoint[] { AutoPoints.sideStartPos, AutoPoints.sideCloseScaleFirstCubeReleasePosition }, 3,
				ROBOT_PROFILE.autonomousParams.fast_path_pid_preset, false);

		sideToFarScaleWithRotate = new Path("sideToFarScaleWithRotate",
				new BumbleWaypoint[] { AutoPoints.sideStartPos, AutoPoints.switchPassageForFarScale,
						AutoPoints.beforeCableProtectorForFarScale, AutoPoints.afterCableProtectorForFarScale,
						AutoPoints.farScaleWithRotatePreReleasePosition },
				2.2, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		sideToFarScale = new Path("sideToFarScale",
				new BumbleWaypoint[] { AutoPoints.sideStartPos, AutoPoints.switchPassageForFarScale,
						AutoPoints.beforeCableProtectorForFarScale, AutoPoints.afterCableProtectorForFarScale,
						AutoPoints.farScalePreReleasePosition },
				2.2, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		sideCloseScalePostFirstCubeToCubeInsert = new Path("sideScalePostFirstCubeToCubeInsert",
				new BumbleWaypoint[] { AutoPoints.sideCloseScaleFirstCubeReleasePosition, AutoPoints.ScaleCubeCollect },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		double distanceToSwitch = 0.9;
		cubePreApproachToCubeCollectPositionForward = new Path("cubePreApproachToCubeCollectPositionForward",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(distanceToSwitch, 0, 0) }, 2.0,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		double distanceFromSwitch = -0.9;
		cubePreApproachToCubeCollectPositionBackward = new Path("cubePreApproachToCubeCollectPositionBackward",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(distanceFromSwitch, 0, 0) }, 2.0,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		double sideCloseScaleCloseSwitchPostSecondCubeReleaseBackoffDistanceFromSwitch = -0.9;
		sideCloseScaleCloseSwitchPostSecondCubeReleaseBackoff = new Path(
				"sideCloseScaleCloseSwitchPostSecondCubeReleaseBackoff",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(
						sideCloseScaleCloseSwitchPostSecondCubeReleaseBackoffDistanceFromSwitch, 0, 0) },
				2.0, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		double backOffFromSwitchCubeCollectDistanceFromSwitch = -1.05;
		backOffFromSwitchCubeCollect = new Path("backOffFromSwitchCubeCollect",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint,
						new BumbleWaypoint(backOffFromSwitchCubeCollectDistanceFromSwitch, 0, 0) },
				2.0, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		double distanceToScale = 1.05;
		sideFarScaleToFirstReleasePosition = new Path("sideFarScaleToFirstReleasePosition",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(distanceToScale, 0, 0) }, 1.8,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		double sideCloseScaleToSecondReleasePositionDistance = 1.35;
		sideCloseScaleToSecondReleasePosition = new Path("sideCloseScaleToSecondReleasePosition",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint,
						new BumbleWaypoint(sideCloseScaleToSecondReleasePositionDistance, 0, 0) },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		double sideCloseScaleToSecondBackupPositionDistance = -1.35;
		sideCloseScaleToSecondBackupPosition = new Path("sideCloseScaleToSecondBackupPositionDistance",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint,
						new BumbleWaypoint(sideCloseScaleToSecondBackupPositionDistance, 0, 0) },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		sideStartingPosToCubeCollect = new Path("sideStartingPosToCubeCollect",
				new BumbleWaypoint[] { AutoPoints.sideStartPos, AutoPoints.ScaleCubeCollect }, 2.5,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		double cubeCollectSecondSwitchCubeReleaseForwardDist = 0.45;
		cubeCollectSecondSwitchCubeReleaseForward = new Path("cubeCollectSecondSwitchCubeReleaseForward",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint,
						new BumbleWaypoint(cubeCollectSecondSwitchCubeReleaseForwardDist, 0, 0) },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		double sideFarSwitchCubeCollectSecondSwitchCubeReleaseForwardDistance = 0.65;
		sideFarSwitchCubeCollectSecondSwitchCubeReleaseForward = new Path(
				"sideFarSwitchCubeCollectSecondSwitchCubeReleaseForward",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint,
						new BumbleWaypoint(sideFarSwitchCubeCollectSecondSwitchCubeReleaseForwardDistance, 0, 0) },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		double cubeCollectSecondSwitchCubeReleaseBackwardDist = -0.30;
		cubeCollectSecondSwitchCubeReleaseBackward = new Path("cubeCollectSecondSwitchCubeReleaseBackward",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint,
						new BumbleWaypoint(cubeCollectSecondSwitchCubeReleaseBackwardDist, 0, 0) },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		switchFrontToPreCollectPosition = new Path("switchFrontToPreCollectPosition",
				new BumbleWaypoint[] { AutoPoints.switchFront, AutoPoints.switchFrontCubePreCollect, }, 1.8,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true);

		double distanceToFrontSwitchCube = 1.45;
		frontSwitchPreCollectToCollectPosition = new Path("frontSwitchPreCollectToCollectPosition",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(distanceToFrontSwitchCube, 0, 0) },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		sideToFarSwitchPreRotation = new Path("sideToFarSwitchPreRotation",
				new BumbleWaypoint[] { AutoPoints.sideStartPos, AutoPoints.switchPassageForFarScale,
						AutoPoints.beforeCableProtectorForFarSwitch, AutoPoints.farSwitchPreRotation },
				2.5, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false);

		double distanceFromFrontSwitchCube = -1.45;
		frontSwitchCollectToPostCollectPosition = new Path("frontSwitchCollectToPostCollectPosition",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(distanceFromFrontSwitchCube, 0, 0) },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		double distanceToFinalSwitchFront = 1.8;
		middlePostSwitchCollectToSwitchFront = new Path("middlePostSwitchCollectToSwitchFront",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(distanceToFinalSwitchFront, 0, 0) },
				1.8, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		double farScaleToCubeCollectDistance = 2.45;
		farScaleToCubeCollect = new Path("farScaleToCubeCollect",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint,
						new BumbleWaypoint(farScaleToCubeCollectDistance, 0, 0) },
				2.5, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		middleSwitchThirdCubeCollect = new Path("middleSwitchThirdCubeCollect",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(1.4, 0, 0) }, 1.8,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		middleSwitchThirdCubePostCollect = new Path("middleSwitchThirdCubePostCollect",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(-1.4, 0, 0) }, 1.8,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		farScalePostCollectBackUpToReleasePosition = new Path("farScalePostCollectBackUpToReleasePosition",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint,
						AutoPoints.farScalePostCollectPreReleaseRelativePosition },
				2.2, ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		sideCloseSwitchToThirdCubeCollect = new Path("sideCloseSwitchToThirdCubeCollect",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(1.4, 0, 0) }, 2.0,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		sideFarScaleBackoffFromSecondCubeRelease = new Path("sideFarScaleBackoffFromSecondCubeRelease",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(-0.4, 0, 0) }, 2.0,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, true, true);

		sideFarSwitchToFirstCubeReleasePosition = new Path("sideFarSwitchToFirstCubeReleasePosition",
				new BumbleWaypoint[] { AutoPoints.zeroWaypoint, new BumbleWaypoint(0.8, 0, 0) }, 2.0,
				ROBOT_PROFILE.autonomousParams.slow_path_pid_preset, false, true);

		addPathsToRegistry();
	}

	public static void addPathsToRegistry() {
		Field[] fields = AutoPaths.class.getDeclaredFields();

		for (Field field : fields) {
			try {
				if (field.get(null) instanceof Path) {
					PathRegistry.getInstance().add((Path) field.get(null));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
