package util.pixy;

import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.SmartdashboardDebugging;

@SuppressWarnings("unused")
public class PixyVision extends Subsystem implements PIDSource, SmartdashboardDebugging {

	// These values are the default if you instantiate a PixySPI without arguments.
	// To create multiple PixySPI objects and thus use multiple Pixy cameras via SPI
	// Copy the items below, change variable names as needed and especially change
	// the SPI port used eg; Port.kOnboardCS[0-3] or Port.kMXP
	public PixySPI pixy1;
	Port port = Port.kOnboardCS0;
	String print;
	public HashMap<Integer, ArrayList<PixyPacket>> packets = new HashMap<Integer, ArrayList<PixyPacket>>();
	private int target1X = 0, target1Y = 0, target1Width = 0, target1Height = 0; // Variables to save regular Power Cube
																					// values into
	private int target2X = 0, target2Y = 0, target2Width = 0, target2Height = 0; // Variables to save custom Power Cube
																					// values into

	private final double DESTINATION_X = 160; // Variable to save the destination where the cube is suppose to be
	private final int SCREEN_WIDTH = 320, CAMERA_FOV = 78; // Pixy specifications

	private PIDSourceType pidSource = PIDSourceType.kDisplacement;
	private PowerCubeType PIDCubeType = PowerCubeType.REGULAR_POWER_CUBE;

	public enum PowerCubeType {
		REGULAR_POWER_CUBE, CUSTOM_POWER_CUBE;
	}

	public PixyVision() {
		// Open a pipeline to a Pixy camera.
		pixy1 = new PixySPI(new SPI(port), packets, new PixyException(print));
	}

	public void setPixyDataToNeutral() {
		target1X = -99;
		target1Y = -99;
		target1Width = -99;
		target1Height = -99;
		target2X = -99;
		target2Y = -99;
		target2Width = -99;
		target2Height = -99;
	}

	public void updatePixyData() {
		@SuppressWarnings("unused") // Variable left as supplied by library maker
		int ret = -1;
		// Get the packets from the pixy.
		try {
			ret = pixy1.readPackets();
		} catch (PixyException e) {
			e.printStackTrace();
		}

		for (int i = 1; i <= PixySPI.PIXY_SIG_COUNT; i++) {

			if (i == 1) {
				for (int j = 0; j < packets.get(i).size(); j++) {
					target1X = packets.get(i).get(j).X;
					target1Y = packets.get(i).get(j).Y;
					target1Width = packets.get(i).get(j).Width;
					target1Height = packets.get(i).get(j).Height;
				}
			} else if (i == 2) {
				for (int j = 0; j < packets.get(i).size(); j++) {
					target2X = packets.get(i).get(j).X;
					target2Y = packets.get(i).get(j).Y;
					target2Width = packets.get(i).get(j).Width;
					target2Height = packets.get(i).get(j).Height;
				}
			}
		}
	}

	public double getCenterX(PowerCubeType powerCube) {
		return (powerCube == PowerCubeType.REGULAR_POWER_CUBE) ? target1X : target2X;
	}

	public double getCenterY(PowerCubeType powerCube) {
		return (powerCube == PowerCubeType.REGULAR_POWER_CUBE) ? target1Y : target2Y;
	}

	public double getWidth(PowerCubeType powerCube) {
		return (powerCube == PowerCubeType.REGULAR_POWER_CUBE) ? target1Width : target2Width;
	}

	public double getHeight(PowerCubeType powerCube) {
		return (powerCube == PowerCubeType.REGULAR_POWER_CUBE) ? target1Height : target2Height;
	}

	public double getDestinationX() {
		return this.DESTINATION_X;
	}

	public double getAngle(PowerCubeType powerCube) {
		int targetX = powerCube == PowerCubeType.REGULAR_POWER_CUBE ? target1X : target2X;
		double deltaX = (targetX - DESTINATION_X) / SCREEN_WIDTH;
		return CAMERA_FOV * deltaX;
	}

	@Override
	public void sendSmartdashboardGameTab() {
	}

	public void setPIDCubeType(PowerCubeType PIDCubeType) {
		this.PIDCubeType = PIDCubeType;
	}

	@Override
	public void sendSmartdashboardDebuggingHardware() {
		// Pixy FRC Cube
		SmartDashboard.putNumber("Hardware/Pixy FRC Cube/Center X", getCenterX(PowerCubeType.REGULAR_POWER_CUBE));
		SmartDashboard.putNumber("Hardware/Pixy FRC Cube/Center Y", getCenterY(PowerCubeType.REGULAR_POWER_CUBE));
		SmartDashboard.putNumber("Hardware/Pixy FRC Cube/Width", getWidth(PowerCubeType.REGULAR_POWER_CUBE));
		SmartDashboard.putNumber("Hardware/Pixy FRC Cube/Height", getHeight(PowerCubeType.REGULAR_POWER_CUBE));

		// Pixy Custom Cube
		SmartDashboard.putNumber("Hardware/Pixy Custom Cube/Center X", getCenterX(PowerCubeType.CUSTOM_POWER_CUBE));
		SmartDashboard.putNumber("Hardware/Pixy Custom Cube/Center Y", getCenterY(PowerCubeType.CUSTOM_POWER_CUBE));
		SmartDashboard.putNumber("Hardware/Pixy Custom Cube/Width", getWidth(PowerCubeType.CUSTOM_POWER_CUBE));
		SmartDashboard.putNumber("Hardware/Pixy Custom Cube/Height", getHeight(PowerCubeType.CUSTOM_POWER_CUBE));
	}

	@Override
	public void sendSmartdashboardDebuggingLogic() {
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSource = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSource;
	}

	@Override
	public double pidGet() {
		return getCenterX(PIDCubeType);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	@Override
	public void periodic() {
		updatePixyData();
	}

	// Supplied test code by library maker:
	// public void testPixy1(){
	// int ret = -1;
	// // Get the packets from the pixy.
	// try {
	// ret = pixy1.readPackets();
	// } catch (PixyException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// SmartDashboard.putNumber("Pixy Vision: packets size: ", packets.size());
	//
	// for(int i = 1; i <= PixySPI.PIXY_SIG_COUNT ; i++) {
	// SmartDashboard.putString("Pixy Vision: Signature: ", Integer.toString(i));
	//
	// SmartDashboard.putNumber("Pixy Vision: packet: " + Integer.toString(i) + ":
	// size: ", packets.get(i).size());
	//
	// // Loop through the packets for this signature.
	// for(int j=0; j < packets.get(i).size(); j++) {
	// SmartDashboard.putNumber("Pixy Vision: " + Integer.toString(i) + ": X: ",
	// packets.get(i).get(j).X);
	// SmartDashboard.putNumber("Pixy Vision: " + Integer.toString(i) + ": Y: ",
	// packets.get(i).get(j).Y);
	// SmartDashboard.putNumber("Pixy Vision: " + Integer.toString(i) + ": Width: ",
	// packets.get(i).get(j).Width);
	// SmartDashboard.putNumber("Pixy Vision: " + Integer.toString(i) + ": Height:
	// ", packets.get(i).get(j).Height);
	// }
	// }
	// }
}
