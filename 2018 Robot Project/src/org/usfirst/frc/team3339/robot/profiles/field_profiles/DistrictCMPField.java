package org.usfirst.frc.team3339.robot.profiles.field_profiles;

import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;
import org.usfirst.frc.team3339.robot.profiles.FieldProfile;

public class DistrictCMPField extends FieldProfile {
	@Override
	public double getSwitchSideToPlateBackboard() {
		return 1.05;
	}

	@Override
	public double getSwitchDepth(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 1.42;
			case RIGHT:
				return 1.42;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 1.42;
			case RIGHT:
				return 1.42;
			}
		}
		return 0;
	}

	@Override
	public double getDriverStationToSwitchFront(AllianceColor color) {
		switch (color) {
		case BLUE:
			return 3.58;
		case RED:
			return 3.575;
		}
		return 0;
	}

	@Override
	public double getSwitchToWall(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.13;
			case RIGHT:
				return 2.15;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.135;
			case RIGHT:
				return 2.125;
			}
		}
		return 0;
	}

	@Override
	public double getExchangeToFarWall(AllianceColor color) {
		switch (color) {
		case BLUE:
			return 4.4;
		case RED:
			return 4.385;
		}
		return 0;

	}

	@Override
	public double getRearSwitchToNullTerritory(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.32;
			case RIGHT:
				return 2.3;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.335;
			case RIGHT:
				return 2.3;
			}
		}
		return 0;
	}

	@Override
	public double getSwitchWidth(AllianceColor color) {
		switch (color) {
		case BLUE:
			return 3.91;
		case RED:
			return 3.92;
		}
		return 0;
	}

	@Override
	public double getPlatformToWall(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.44;
			case RIGHT:
				return 2.455;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.45;
			case RIGHT:
				return 2.44;
			}
		}
		return 0;
	}

	@Override
	public double getAllianceWallEdgeToWall(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 0.785;
			case RIGHT:
				return 0.77;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 0.745;
			case RIGHT:
				return 0.775; // Pro
			}
		}
		return 0;
	}

	@Override
	public double getSwitchToCableProtector(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 3.25;
			case RIGHT:
				return 3.26;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 3.215;
			case RIGHT:
				return 3.235;
			}
		}
		return 0;
	}
}
