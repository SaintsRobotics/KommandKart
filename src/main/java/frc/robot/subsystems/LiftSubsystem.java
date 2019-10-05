package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.LiftDriveCommand;
import frc.robot.util.PidConfig;

public class LiftSubsystem extends Subsystem {

	private SpeedController m_motor;
	private Encoder m_encoder;
	private DigitalInput m_lowerLimit;

	private PIDController m_pidControler;
	private double m_pidOutput;
	private boolean m_isMoving = false;
	private double m_inputSpeed;

	public LiftSubsystem(SpeedController motor, Encoder encoder, DigitalInput lowerLimit, PidConfig pidConfig) {

		this.m_motor = motor;
		this.m_lowerLimit = lowerLimit;
		this.m_encoder = encoder;

		this.m_pidControler = new PIDController(pidConfig.kP, pidConfig.kI, pidConfig.kD, encoder,
				(output) -> this.m_pidOutput = output);
		this.m_pidControler.setAbsoluteTolerance(pidConfig.tolerance);

	}

	public void periodic() {
		double output = this.m_pidOutput;
		if (this.m_inputSpeed != 0) {
			this.m_isMoving = true;
			output = this.m_inputSpeed;
		} else if (this.m_inputSpeed == 0.0 && this.m_isMoving) {
			this.m_pidControler.setSetpoint(this.m_encoder.pidGet());
			this.m_isMoving = false;
		}

		if (this.m_lowerLimit.get() == false) {
			this.m_encoder.reset();
			this.m_pidControler.setSetpoint(0);
			if (output < 0) {
				this.m_motor.set(0);
			} else {
				this.m_motor.set(output);
			}
		} else {
			this.m_motor.set(output);
		}
	}

	/**
	 * @param speed usually one of two values, positive or negative. positive drives
	 *              up, negative drives down. within the range of -1 to 1
	 */
	public void setSpeed(double speed) {
		this.m_inputSpeed = speed;
	}

	public PIDController getPidController() {
		return this.m_pidControler;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new LiftDriveCommand(this));
	}

}
