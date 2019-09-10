package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.LiftSubsystem;

/**
 * Drives lift based on joystick command. Is the default command for
 * LiftSubsystem
 */
public class LiftDriveCommand extends Command {

	private final double SCALE = 0.5; // value between 0 and 1 by which input to the lift is scaled

	private LiftSubsystem m_subsystem;

	public LiftDriveCommand(LiftSubsystem subsystem) {
		this.m_subsystem = subsystem;
		requires(this.m_subsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		this.m_subsystem.setSpeed(OI.liftDrive.getAsDouble() * SCALE);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		this.m_subsystem.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}