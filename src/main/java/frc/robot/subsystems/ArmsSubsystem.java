package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.DriveArmsCommand;
import frc.robot.util.CANEncoderPidSource;
import frc.robot.util.PidConfig;

public class ArmsSubsystem extends Subsystem {

	private SpeedController m_motor;
	private CANEncoderPidSource m_encoder;
	private PIDController m_pidArmController;
	private double m_PidArmOutput;

	private double m_inputSpeed;
	private boolean m_isMoving;

	public ArmsSubsystem(CANSparkMax motor, CANEncoderPidSource encoder, PidConfig pidArmConfig) {
		this.m_motor = motor;
		this.m_encoder = encoder;

		this.m_pidArmController = new PIDController(pidArmConfig.kP, pidArmConfig.kI, pidArmConfig.kD, this.m_encoder,
				(output) -> this.m_PidArmOutput = output);
		this.m_pidArmController.setAbsoluteTolerance(pidArmConfig.tolerance);
		this.m_pidArmController.setOutputRange(-0.2, 0.2);
		// this.m_pidArmController.setInputRange(-30.0, 10.0);
		this.m_pidArmController.reset();
		this.m_pidArmController.enable();

		this.m_pidArmController.setSetpoint(this.m_encoder.pidGet());

	}

	@Override
	public void periodic() {
		double output = this.m_PidArmOutput;
		if (this.m_inputSpeed != 0.0) {
			this.m_isMoving = true;
			output = this.m_inputSpeed;
		} else if (this.m_inputSpeed == 0.0 && this.m_isMoving) {
			this.m_pidArmController.setSetpoint(this.m_encoder.pidGet());
			this.m_isMoving = false;
		}
		this.m_motor.set(output);
	}

	public void runPid() {
		this.m_motor.set(this.m_PidArmOutput);
	}

	public void setPidSetpoint(double setpoint) {

	}

	public void setSpeed(double speed) {
		this.m_inputSpeed = speed;
	}

	public PIDController getPidController() {
		return this.m_pidArmController;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveArmsCommand(this));

	}

}
