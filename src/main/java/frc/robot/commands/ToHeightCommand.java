package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.util.PidConfig;

/**
 * Calculates the amount the lift must move in order to be at a certain height.  Instantiate once for every height.
 */
public class ToHeightCommand extends Command {
	private final double SCALE = 1;

	private LiftSubsystem m_subsystem;
	private double m_targetHeight;
	private PIDController m_pidController;
	private double m_pidOutput;
	
	public ToHeightCommand(LiftSubsystem subsystem, PIDSource encoder, double targetHeight, PidConfig pidConfig) {
		this.m_subsystem = subsystem;
		requires(this.m_subsystem);
		this.m_targetHeight = targetHeight;

		this.m_pidController = new PIDController(pidConfig.kP, pidConfig.kI, pidConfig.kD, encoder, (output) -> this.m_pidOutput = output * this.SCALE);
		this.m_pidController.setAbsoluteTolerance(pidConfig.tolerance);
		this.m_pidController.setContinuous(false);
		this.m_pidController.setOutputRange(-1, 1);
		this.m_pidController.reset();

	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		this.m_pidController.enable();
		this.m_pidController.setSetpoint(this.m_targetHeight);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		this.m_subsystem.drive(this.m_pidOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return this.m_pidController.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		this.m_pidController.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}