package frc.robot.util;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class JoystickTrigger extends Trigger {

	public JoystickTrigger(DoubleSupplier joystick, boolean invert) {

	}

	@Override
	public boolean get() {
		return false;
	}

}