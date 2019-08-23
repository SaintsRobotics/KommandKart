package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.util.AngleUtilities;

public class CargoIntakeSubsystem extends Subsystem {
	
	private SpeedController m_motor;
	private DigitalInput m_limitSwitch;

	public CargoIntakeSubsystem(SpeedController motor, DigitalInput limitSwitch) {
		this.m_motor = motor;
		this.m_limitSwitch = limitSwitch;
	}

	/**
	 * @param speed usually one of two values, positive or negative.  positive drives up, negative drives down.  within the range of -1 to 1
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
