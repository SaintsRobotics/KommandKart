package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.util.PidConfig;

/**
 * Calculates the amount the robot must rotate and simulates that as joystick
 * movement. The drive can still translate the robot, and driving is
 * field-relative. Instantiate once for every heading, pid configs stay the
 * same.
 */
public class ToHeadingCommand extends Command {

	private final double SCALE = 1; // a value between 0 and 1 by which the pid output is scaled

	private SwerveSubsystem m_subsystem;
	private DoubleSupplier m_gyro;
	private double m_pidOutput;

	private double m_heading;
	private PIDController m_pidController;

	/**
	 * 
	 * @param trigger   what triggers this command
	 * @param heading   the direction the robot is to face
	 * @param pidConfig the {@link frc.robot.util.PidConfig PidConfig} that stores
	 *                  p, i , and d coefficients and the absolute tolerance
	 */
	public ToHeadingCommand(SwerveSubsystem subsystem, PIDSource gyro, double heading, PidConfig pidConfig) {
		this.m_subsystem = subsystem;
		requires(this.m_subsystem);

		this.m_gyro = () -> gyro.pidGet();

		this.m_heading = heading;

		this.m_pidController = new PIDController(pidConfig.kP, pidConfig.kI, pidConfig.kD, gyro,
				(output) -> this.m_pidOutput = output * this.SCALE);
		this.m_pidController.setAbsoluteTolerance(pidConfig.tolerance);
		this.m_pidController.setInputRange(0, 360);
		this.m_pidController.setContinuous(true);
		this.m_pidController.setOutputRange(-1, 1);
		this.m_pidController.reset();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		this.m_pidController.enable();
		this.m_pidController.setSetpoint(this.m_heading);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// driving while going to a heading is absolute

		double angle = this.m_gyro.getAsDouble();
		double x = OI.transX.getAsDouble();
		double y = OI.transY.getAsDouble();
		x = (OI.transX.getAsDouble() * Math.cos(Math.toRadians(angle)))
				- (OI.transY.getAsDouble() * Math.sin(Math.toRadians(angle)));
		y = (OI.transX.getAsDouble() * Math.sin(Math.toRadians(angle)))
				+ (OI.transY.getAsDouble() * Math.cos(Math.toRadians(angle)));

		this.m_subsystem.dynamicGainDrive(x, y, this.m_pidOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return OI.rotation.getAsDouble() != 0 || this.m_pidController.onTarget();
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