package org.usfirst.frc.team3339.robot.profiles.field_profiles;

import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;
import org.usfirst.frc.team3339.robot.profiles.FieldProfile;

public class District4Field extends FieldProfile {
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
			return 3.555;
		case RED:
			return 3.545;
		}
		return 0;
	}

	@Override
	public double getSwitchToWall(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.17;
			case RIGHT:
				return 2.15;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.16;
			case RIGHT:
				return 2.16;
			}
		}
		return 0;
	}

	@Override
	public double getExchangeToFarWall(AllianceColor color) {
		switch (color) {
		case BLUE:
			return 4.415;
		case RED:
			return 4.42;
		}
		return 0;

	}

	@Override
	public double getRearSwitchToNullTerritory(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.35;
			case RIGHT:
				return 2.36;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.35;
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
			return 3.915;
		}
		return 0;
	}

	@Override
	public double getPlatformToWall(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.47;
			case RIGHT:
				return 2.47;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.46;
			case RIGHT:
				return 2.47;
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
				return 0.77;
			case RIGHT:
				return 0.76;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 0.765;
			case RIGHT:
				return 0.74;
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
				return 3.27;
			case RIGHT:
				return 3.27;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 3.29;
			case RIGHT:
				return 3.27;
			}
		}
		return 0;
	}
}
