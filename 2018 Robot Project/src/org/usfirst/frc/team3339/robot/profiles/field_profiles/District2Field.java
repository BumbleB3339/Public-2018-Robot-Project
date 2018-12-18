package org.usfirst.frc.team3339.robot.profiles.field_profiles;

import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;
import org.usfirst.frc.team3339.robot.profiles.FieldProfile;

public class District2Field extends FieldProfile {
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
				return 1.415;
			}
		}
		return 0;
	}

	@Override
	public double getDriverStationToSwitchFront(AllianceColor color) {
		switch (color) {
		case BLUE:
			return 3.56;
		case RED:
			return 3.56;
		}
		return 0;
	}

	@Override
	public double getSwitchToWall(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.15;
			case RIGHT:
				return 2.16;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.17;
			case RIGHT:
				return 2.12;
			}
		}
		return 0;
	}

	@Override
	public double getExchangeToFarWall(AllianceColor color) {
		switch (color) {
		case BLUE:
			return 4.38;
		case RED:
			return 4.28;
		}
		return 0;

	}

	@Override
	public double getRearSwitchToNullTerritory(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.325;
			case RIGHT:
				return 2.33;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.31;
			case RIGHT:
				return 2.33;
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
				return 2.47;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.50;
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
				return 0.715;
			case RIGHT:
				return 0.76;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 0.73;
			case RIGHT:
				return 0.685;
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
				return 3.339; // TODO: need to be measured
			case RIGHT:
				return 3.339;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 3.339; // TODO: need to be measured
			case RIGHT:
				return 3.339;
			}
		}
		return 0;
	}
}
