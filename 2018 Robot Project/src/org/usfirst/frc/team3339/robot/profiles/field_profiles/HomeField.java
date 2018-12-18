package org.usfirst.frc.team3339.robot.profiles.field_profiles;

import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.AutoChooser.AutoSide;
import org.usfirst.frc.team3339.robot.profiles.FieldProfile;

/**
 * Dimensions for 3339 home field
 * 
 * @author BumbleB 3339
 *
 */
public class HomeField extends FieldProfile {

	@Override
	public double getSwitchSideToPlateBackboard() {
		return 0.95;
	}

	@Override
	public double getSwitchDepth(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 1.43;
			case RIGHT:
				return 1.42;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 1.43;
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
				return 2.16;
			case RIGHT:
				return 2.15;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.16;
			case RIGHT:
				return 2.15;
			}
		}
		return 0;
	}

	@Override
	public double getExchangeToFarWall(AllianceColor color) {
		switch (color) {
		case BLUE:
			return 4.45;
		case RED:
			return 4.45;
		}
		return 0;

	}

	@Override
	public double getRearSwitchToNullTerritory(AllianceColor color, AutoSide side) {
		switch (color) {
		case BLUE:
			switch (side) {
			case LEFT:
				return 2.34;
			case RIGHT:
				return 2.36;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 2.34;
			case RIGHT:
				return 2.36;
			}
		}
		return 0;
	}

	@Override
	public double getSwitchWidth(AllianceColor color) {
		switch (color) {
		case BLUE:
			return 3.90;
		case RED:
			return 3.90;
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
				return 2.47;
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
				return 0.92;
			}
		case RED:
			switch (side) {
			case LEFT:
				return 0.77;
			case RIGHT:
				return 0.92;
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
