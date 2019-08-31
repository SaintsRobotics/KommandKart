package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveDriveCommand extends Command {

	private SwerveSubsystem m_subsystem;
	private DoubleSupplier m_gyro;
	private boolean m_dynamicGain;

	/**
	 * 
	 * @param subsystem   swerve drive subsystem
	 * @param gyro        gyroscope for absolute driving
	 * @param dynamicGain drive with dynamic translation/rotation gain or static
	 *                    translation/rotation gain. defaults to true. <br>
	 *                    *note that dynamic gain is used everywhere else in the
	 *                    code
	 */
	public SwerveDriveCommand(SwerveSubsystem subsystem, DoubleSupplier gyro, boolean dynamicGain) {
		this.m_subsystem = subsystem;
		requires(this.m_subsystem);

		this.m_gyro = gyro;

		this.m_dynamicGain = true;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double angle = this.m_gyro.getAsDouble();
		double x = OI.transX.getAsDouble();
		double y = OI.transY.getAsDouble();
		double rotation = OI.rotation.getAsDouble();
		String mode = "robot relative";

		// if (Math.abs(x) < 0.075 && Math.abs(y) < 0.075 && Math.abs(rotation) < 0.075)
		// return;

		if (OI.absoluteDrive.getAsBoolean()) {
			x = (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle)));
			y = (x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle)));
			mode = "field relative";
		}

		SmartDashboard.putString("Mode", mode);

		if (m_dynamicGain) {
			if (Math.abs(OI.rotation.getAsDouble()) < 0.1) {
				m_subsystem.dynamicGainDrive(x, y, 0.0);
				return;
			}
			m_subsystem.dynamicGainDrive(x, y, OI.rotation.getAsDouble());

		} else {
			if (Math.abs(OI.rotation.getAsDouble()) < 0.1) {
				m_subsystem.staticGainDrive(x, y, 0.0);
				return;
			}
			m_subsystem.staticGainDrive(x, y, OI.rotation.getAsDouble());
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}

}