package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.LiftDriveCommand;

public class LiftSubsystem extends Subsystem {

	private SpeedController m_motor;
	private DigitalInput m_upperLimit;
	private DigitalInput m_lowerLimit;

	public LiftSubsystem(SpeedController motor, DigitalInput upperLimit, DigitalInput lowerLimit) {
		this.m_motor = motor;
		this.m_upperLimit = upperLimit;
		this.m_lowerLimit = lowerLimit;
	}

	/**
	 * @param speed usually one of two values, positive or negative. positive drives
	 *              up, negative drives down. within the range of -1 to 1
	 */
	public void drive(double speed) {
		if (!(this.m_upperLimit.get() && this.m_lowerLimit.get())) {
			this.m_motor.set(speed);
		}
	}

	@Override
	public void initDefaultCommand() {
		// setDefaultCommand(new LiftDriveCommand(this));
	}

}
