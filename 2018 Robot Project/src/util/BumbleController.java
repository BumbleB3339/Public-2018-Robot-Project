package util;

import edu.wpi.first.wpilibj.XboxController;

public class BumbleController extends XboxController {

	public BumbleController(int port) {
		super(port);
	}

	public boolean GetPOV_Up() {
		int POV = super.getPOV(0);
		return POV > 270 || (POV >= 0 && POV < 90);
	}

	public boolean GetPOV_Down() {
		int POV = super.getPOV(0);
		return POV > 90 && POV < 270;
	}

	public boolean GetPOV_Right() {
		int POV = super.getPOV(0);
		return POV > 0 && POV < 180;
	}

	public boolean GetPOV_Left() {
		int POV = super.getPOV(0);
		return POV > 180;
	}
}