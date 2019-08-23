package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.util.PidConfig;
import frc.robot.util.PidSender;

/**
 * Calculates the amount the robot must rotate and simulates that as joystick
 * movement. The drive can still translate the robot.
 */
public class ResetGyroCommand extends Command {

	private ADXRS450_Gyro m_gyro;

	public ResetGyroCommand(ADXRS450_Gyro gyro) {
		this.m_gyro = gyro;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		this.m_gyro.reset();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}