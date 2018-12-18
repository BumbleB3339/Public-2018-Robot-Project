package org.usfirst.frc.team3339.robot.autonomous;

import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;

public class AutoParams {
	private static boolean adjustedToLeft = false;

	public static double angleToScaleCollectCube = -148;
	public static double angleToFrontSwitchCollectCube = -60;

	public static double absoluteAngleToSideScaleSecondCubeReleasePosition = -20;

	public static double absoluteAngleToFarSwitchPreReleasePosition = -170;
	public static double absoluteAngleToFarSwitchCorrectToCenterOfSwitch = -160;

	public static double absoluteAngleToFarScaleReleasePosition = 60;

	public static double absoluteAngleToFarScaleCubeCollectPosition = 158;
	public static double absoluteAngleToFarScaleSecondReleasePosition = 70;

	public static double absoluteAngleToSideSwitchThirdCubeCollect = -124;

	public static double absoluteAngleToCloseScaleThirdCubeCollect = -118;

	public static double absoluteAngleToFarSwitchThirdCubeCollect = 155;

	public static double absoluteAngleToSideFarScaleFirstCubeReleasePosition = 7;

	public static void adjustAnglesToLeft(AutoSide side) {
		if ((side == AutoSide.LEFT && !adjustedToLeft) || (side == AutoSide.RIGHT && adjustedToLeft)) {
			angleToScaleCollectCube *= -1;
			absoluteAngleToSideScaleSecondCubeReleasePosition *= -1;
			angleToFrontSwitchCollectCube *= -1;
			absoluteAngleToFarSwitchPreReleasePosition *= -1;
			absoluteAngleToFarSwitchCorrectToCenterOfSwitch *= -1;
			absoluteAngleToFarScaleReleasePosition *= -1;
			absoluteAngleToSideFarScaleFirstCubeReleasePosition *= -1;
			absoluteAngleToFarScaleCubeCollectPosition *= -1;
			absoluteAngleToFarScaleSecondReleasePosition *= -1;
			absoluteAngleToSideSwitchThirdCubeCollect *= -1;
			absoluteAngleToCloseScaleThirdCubeCollect *= -1;
			absoluteAngleToFarSwitchThirdCubeCollect *= -1;
			adjustedToLeft = true;
		}
	}
}
