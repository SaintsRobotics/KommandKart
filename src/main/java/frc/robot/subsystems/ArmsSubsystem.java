package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DriveArmsCommand;

public class ArmsSubsystem extends Subsystem {

	private SpeedController m_motor;

	public ArmsSubsystem(SpeedController motor) {
		this.m_motor = motor;
	}

	/**
	 * @param speed usually one of two values, positive or negative. positive
	 *              intakes, negative outtakes. within the range of -1 to 1
	 */
	public void drive(double speed) {
		this.m_motor.set(speed);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveArmsCommand(this));
	}

}
