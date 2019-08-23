package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoIntakeSubsystem extends Subsystem {
	
	private SpeedController m_motor;
	private DigitalInput m_limitSwitch;

	public CargoIntakeSubsystem(SpeedController motor, DigitalInput limitSwitch) {
		this.m_motor = motor;
		this.m_limitSwitch = limitSwitch;
	}

	/**
	 * @param speed usually one of two values, positive or negative.  positive intakes, negative outtakes.  within the range of -1 to 1
	 */
	public void drive(double speed) {
		if (!this.m_limitSwitch.get()) {
			this.m_motor.set(speed);
		}
	}

	@Override
	public void initDefaultCommand() {
	}

}
