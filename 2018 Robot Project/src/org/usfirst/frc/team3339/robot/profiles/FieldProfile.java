package org.usfirst.frc.team3339.robot.profiles;

import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;

/**
 * An for field dimensions
 * 
 * @author BumbleB 3339
 *
 */
public abstract class FieldProfile {

	// measured from outer line
	public abstract double getExchangeToFarWall(AllianceColor color);

	public abstract double getAllianceWallEdgeToWall(AllianceColor color, AutoSide side);

	public abstract double getDriverStationToSwitchFront(AllianceColor color);

	public abstract double getSwitchDepth(AllianceColor color, AutoSide side);

	public abstract double getSwitchWidth(AllianceColor color);

	public abstract double getSwitchSideToPlateBackboard();

	public abstract double getSwitchToWall(AllianceColor color, AutoSide side);

	// measured from outer line
	public abstract double getRearSwitchToNullTerritory(AllianceColor color, AutoSide side);

	// measured to cable protector center
	public abstract double getSwitchToCableProtector(AllianceColor color, AutoSide side);

	public abstract double getPlatformToWall(AllianceColor color, AutoSide side);

	// --------------------

	public double getDriverStationToCableProtector(AllianceColor color, AutoSide side) {
		return getDriverStationToSwitchFront(color) + getSwitchDepth(color, side)
				+ getSwitchToCableProtector(color, side);
	}

	public double getFieldWidth(AllianceColor color) {
		return getSwitchWidth(color) + getSwitchToWall(color, AutoSide.LEFT) + getSwitchToWall(color, AutoSide.RIGHT);
	}

	public double getDriverStationToNullTerritory(AllianceColor color, AutoSide side) {
		return getDriverStationToSwitchFront(color) + getSwitchDepth(color, side)
				+ getRearSwitchToNullTerritory(color, side);
	}

	public double getCableProtectorToWall(AllianceColor color) {
		return getSwitchToWall(color, AutoSide.RIGHT) + getSwitchWidth(color) / 2.0;
	}
}
