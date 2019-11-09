package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.LiftDriveCommand;
import frc.robot.util.PidConfig;

public class LiftSubsystem extends Subsystem {

	private SpeedController m_motor;
	private Encoder m_encoder;
	private DigitalInput m_lowerLimit;
	private DigitalInput m_upperLimit;
	private double UPPER_ENCODER_VALUE = 7000;
	private double LOWER_ENCODER_VALUE = 750;
	private PIDController m_pidControler;
	private double m_pidOutput;
	private boolean m_isMoving = false;
	private double SAFETYZONE_THROTTLE = 0.3;
	private double m_inputSpeed;

	public LiftSubsystem(SpeedController motor, Encoder encoder, DigitalInput lowerLimit, DigitalInput upperLimit,
			PidConfig pidConfig) {

		this.m_motor = motor;
		this.m_lowerLimit = lowerLimit;
		this.m_upperLimit = upperLimit;
		this.m_encoder = encoder;

		this.m_pidControler = new PIDController(pidConfig.kP, pidConfig.kI, pidConfig.kD, encoder,
				(output) -> this.m_pidOutput = output);
		this.m_pidControler.setAbsoluteTolerance(pidConfig.tolerance);

	}
	public double safetyChecks(double input){
		if (this.m_encoder.get() > UPPER_ENCODER_VALUE || this.m_encoder.get() < LOWER_ENCODER_VALUE){
			if (input > 0) {
				input = SAFETYZONE_THROTTLE * input;
			}
		}
		
		if (this.m_lowerLimit.get() == false) {
			this.m_encoder.reset();
			this.m_pidControler.setSetpoint(0);
			if (input < 0) {
				input = 0;
			}
		}

		if (this.m_upperLimit.get() == false) {
			if (input > 0) {
				input = 0;
			}
		}
		return input;
	}
	public void drive(double speed) {
		this.m_inputSpeed = speed;
		double output = this.m_pidOutput;
		if (this.m_inputSpeed != 0) {
			this.m_isMoving = true;
			output = this.m_inputSpeed;
		} else if (this.m_inputSpeed == 0.0 && this.m_isMoving) {
			this.m_pidControler.setSetpoint(this.m_encoder.pidGet());
			this.m_isMoving = false;
		}
		
		
		this.m_motor.set(safetyChecks(output));
	}

	/**
	 * @param speed usually one of two values, positive or negative. positive drives
	 *              up, negative drives down. within the range of -1 to 1
	 */
	

	public PIDController getPidController() {
		return this.m_pidControler;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new LiftDriveCommand(this));
	}

}
