package org.usfirst.frc.team3339.robot.autonomous;

import org.usfirst.frc.team3339.robot.Robot.AllianceColor;
import org.usfirst.frc.team3339.robot.autonomous.sequences.CrossLineTimed;
import org.usfirst.frc.team3339.robot.autonomous.sequences.middle.MiddleSwitch;
import org.usfirst.frc.team3339.robot.autonomous.sequences.middle.MiddleSwitchX2_5;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideCloseScale;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideCloseScaleCloseSwitch;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideCloseScaleX2;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideCloseSwitchCloseScale;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideCloseSwitchX2;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideFarScaleFarSwitch;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideFarScaleWithRotate;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideFarScaleX2;
import org.usfirst.frc.team3339.robot.autonomous.sequences.side.SideFarSwitchX2;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {

	public enum SideFieldState {
		CC, CF, FC, FF
	}

	public enum AutoMode_Center {
		NONE, SWITCH, SWITCH_SCALE, SWITCH_X2
	}

	public enum AutoMode_Side {
		NONE, CROSS_LINE, SCALE, SWITCH, SCALE_X2, SWITCH_X2, SWITCH_SCALE, SCALE_SWITCH
	}

	public enum StartingPosition {
		CENTER, RIGHT, LEFT
	}

	public enum AutoSide {
		LEFT, RIGHT
	}

	static SendableChooser<String> gameDataSourceChooser = new SendableChooser<>();

	static SendableChooser<AutoMode_Side> autoChooserFF = new SendableChooser<>();
	static SendableChooser<AutoMode_Side> autoChooserCC = new SendableChooser<>();
	static SendableChooser<AutoMode_Side> autoChooserCF = new SendableChooser<>();
	static SendableChooser<AutoMode_Side> autoChooserFC = new SendableChooser<>();
	static SendableChooser<AutoMode_Center> autoChooserCenter = new SendableChooser<>();

	static SendableChooser<StartingPosition> startingPosChooser = new SendableChooser<>();

	public static void updateDashboardChoosersConfirmation() {

		SmartDashboard.putString("Chosen 'Auto for Center'", autoChooserCenter.getSelected().toString());
		SmartDashboard.putString("Chosen 'Auto for FF'", autoChooserFF.getSelected().toString());
		SmartDashboard.putString("Chosen 'Auto for CC'", autoChooserCC.getSelected().toString());
		SmartDashboard.putString("Chosen 'Auto for FC'", autoChooserFC.getSelected().toString());
		SmartDashboard.putString("Chosen 'Auto for CF'", autoChooserCF.getSelected().toString());
		SmartDashboard.putString("Chosen 'Starting Pos'", startingPosChooser.getSelected().toString());
		SmartDashboard.putBoolean("Autonomoous/Is game data read from FMS?",
				gameDataSourceChooser.getSelected().equals("FMS"));
	}

	public static void initDashboardChoosers() {
		autoChooserCenter.addDefault("CENTER_NONE", AutoMode_Center.NONE);
		autoChooserCenter.addObject("SWITCH", AutoMode_Center.SWITCH);
		autoChooserCenter.addObject("SWITCH_X2", AutoMode_Center.SWITCH_X2);
		SmartDashboard.putData("Auto for Center", autoChooserCenter);

		autoChooserFF.addDefault("FAR_FAR_NONE", AutoMode_Side.NONE);
		autoChooserFF.addObject("CROSS_LINE", AutoMode_Side.CROSS_LINE);
		autoChooserFF.addObject("FAR_SCALE", AutoMode_Side.SCALE);
		autoChooserFF.addObject("FAR_SWITCH_X2", AutoMode_Side.SWITCH_X2);
		autoChooserFF.addObject("FAR_SCALE_X2", AutoMode_Side.SCALE_X2);
		autoChooserFF.addObject("FAR_SCALE_FAR_SWITCH", AutoMode_Side.SCALE_SWITCH);
		SmartDashboard.putData("Auto for FF", autoChooserFF);

		autoChooserCC.addDefault("CLOSE_CLOSE_NONE", AutoMode_Side.NONE);
		autoChooserCC.addObject("CROSS_LINE", AutoMode_Side.CROSS_LINE);
		autoChooserCC.addObject("CLOSE_SCALE", AutoMode_Side.SCALE);
		autoChooserCC.addObject("CLOSE_SCALE_X2", AutoMode_Side.SCALE_X2);
		autoChooserCC.addObject("CLOSE_SCALE_CLOSE_SWITCH", AutoMode_Side.SCALE_SWITCH);
		autoChooserCC.addObject("CLOSE_SWITCH_X2", AutoMode_Side.SWITCH_X2);
		autoChooserCC.addObject("CLOSE_SWITCH_SCALE", AutoMode_Side.SWITCH_SCALE);
		SmartDashboard.putData("Auto for CC", autoChooserCC);

		autoChooserFC.addDefault("FAR_CLOSE_NONE", AutoMode_Side.NONE);
		autoChooserFC.addObject("CROSS_LINE", AutoMode_Side.CROSS_LINE);
		autoChooserFC.addObject("CLOSE_SCALE", AutoMode_Side.SCALE);
		autoChooserFC.addObject("CLOSE_SCALE_X2", AutoMode_Side.SCALE_X2);
		autoChooserFC.addObject("FAR_SWITCH_X2", AutoMode_Side.SWITCH_X2);
		SmartDashboard.putData("Auto for FC", autoChooserFC);

		autoChooserCF.addDefault("CLOSE_FAR_NONE", AutoMode_Side.NONE);
		autoChooserCF.addObject("CROSS_LINE", AutoMode_Side.CROSS_LINE);
		autoChooserCF.addObject("FAR_SCALE", AutoMode_Side.SCALE);
		autoChooserCF.addObject("CLOSE_SWITCH_X2", AutoMode_Side.SWITCH_X2);
		autoChooserCF.addObject("FAR_SCALE_X2", AutoMode_Side.SCALE_X2);
		SmartDashboard.putData("Auto for CF", autoChooserCF);

		startingPosChooser.addDefault("CENTER", StartingPosition.CENTER);
		startingPosChooser.addObject("RIGHT", StartingPosition.RIGHT);
		startingPosChooser.addObject("LEFT", StartingPosition.LEFT);
		SmartDashboard.putData("Starting Pos", startingPosChooser);

		gameDataSourceChooser.addDefault("FMS", "FMS");
		gameDataSourceChooser.addObject("RRR", "RRR");
		gameDataSourceChooser.addObject("LLL", "LLL");
		gameDataSourceChooser.addObject("RLR", "RLR");
		gameDataSourceChooser.addObject("LRL", "LRL");
		SmartDashboard.putData("Game Data Source", gameDataSourceChooser);
	}

	public static CommandGroup getAutoCommand() {
		CommandGroup autonomousCommand;

		Alliance alliance = DriverStation.getInstance().getAlliance();
		AllianceColor allianceColor;

		if (alliance == Alliance.Invalid) {
			System.out.println("Invalid Alliance");
			allianceColor = AllianceColor.RED;
		} else {
			allianceColor = (alliance == Alliance.Blue) ? AllianceColor.BLUE : AllianceColor.RED;
		}

		StartingPosition startingPosition = startingPosChooser.getSelected();
		AutoSide autoSide;

		String gameData = getGameData();

		if (startingPosition == StartingPosition.CENTER) {
			autoSide = getCenterAutoSide(gameData);
			AutoParams.adjustAnglesToLeft(autoSide);
			AutoPoints.createWaypoints(allianceColor, autoSide);

			autonomousCommand = getCenterCommand(gameData);
		} else {
			autoSide = (startingPosition == StartingPosition.LEFT) ? AutoSide.LEFT : AutoSide.RIGHT;
			AutoParams.adjustAnglesToLeft(autoSide);
			AutoPoints.createWaypoints(allianceColor, autoSide);

			autonomousCommand = getSideAutoCommand(gameData, startingPosition);
		}

		return autonomousCommand;
	}

	private static String getGameData() {
		String gameData = "";

		if (gameDataSourceChooser.getSelected().equals("FMS")) {
			while (gameData.length() != 3) {
				gameData = DriverStation.getInstance().getGameSpecificMessage();
			}
		} else {
			gameData = gameDataSourceChooser.getSelected();
		}
		return gameData;
	}

	private static SideFieldState getSideFieldState(String gameData, boolean isLeft) {
		char switchSide = gameData.charAt(0);
		char scaleSide = gameData.charAt(1);

		if (switchSide == 'L' && scaleSide == 'L') {
			return isLeft ? SideFieldState.CC : SideFieldState.FF;
		} else if (switchSide == 'L' && scaleSide == 'R') {
			return isLeft ? SideFieldState.CF : SideFieldState.FC;
		} else if (switchSide == 'R' && scaleSide == 'L') {
			return isLeft ? SideFieldState.FC : SideFieldState.CF;
		} else {
			return isLeft ? SideFieldState.FF : SideFieldState.CC;
		}
	}

	private static AutoMode_Side getSideAutoEnum(SideFieldState fieldState) {
		switch (fieldState) {
		case CC:
			return autoChooserCC.getSelected();
		case CF:
			return autoChooserCF.getSelected();
		case FC:
			return autoChooserFC.getSelected();
		case FF:
			return autoChooserFF.getSelected();
		default:
			return AutoMode_Side.NONE;
		}
	}

	private static CommandGroup getSideAutoCommand(String gameData, StartingPosition startingPos) {
		SideFieldState fieldState = getSideFieldState(gameData, startingPos == StartingPosition.LEFT);
		AutoMode_Side autoMode = getSideAutoEnum(fieldState);

		switch (autoMode) {
		case CROSS_LINE:
			return new CrossLineTimed();
		case SCALE:
			if (fieldState == SideFieldState.CC || fieldState == SideFieldState.FC) {
				return new SideCloseScale();
			} else {
				return new SideFarScaleWithRotate();
			}
		case SCALE_X2:
			if (fieldState == SideFieldState.CC || fieldState == SideFieldState.FC) {
				return new SideCloseScaleX2();
			} else {
				return new SideFarScaleX2();
			}
		case SCALE_SWITCH:
			if (fieldState == SideFieldState.CC || fieldState == SideFieldState.FC) {
				return new SideCloseScaleCloseSwitch();
			} else if (fieldState == SideFieldState.FF) {
				return new SideFarScaleFarSwitch();
			} else {
				return new CommandGroup();
			}
		case SWITCH_SCALE:
			if (fieldState == SideFieldState.CC) {
				return new SideCloseSwitchCloseScale();
			} else {
				return new CommandGroup();
			}
		case SWITCH_X2:
			if (fieldState == SideFieldState.CC || fieldState == SideFieldState.CF) {
				return new SideCloseSwitchX2();
			} else {
				return new SideFarSwitchX2();
			}
		case SWITCH:
		case NONE:
		default:
			return new CommandGroup();
		}
	}

	private static AutoSide getCenterAutoSide(String gameData) {
		char switchSide = gameData.charAt(0);

		return (switchSide == 'L') ? AutoSide.LEFT : AutoSide.RIGHT;
	}

	private static CommandGroup getCenterCommand(String gameData) {
		switch (autoChooserCenter.getSelected()) {
		case SWITCH:
			return new MiddleSwitch();
		case SWITCH_X2:
			return new MiddleSwitchX2_5();
		case NONE:
		default:
			return new CommandGroup();
		}
	}
}
