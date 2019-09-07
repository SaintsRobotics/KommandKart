package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.ArmsSubsystem;

/**
 * Drives cargo intake based on the value provided in the constructor. To be
 * instantiated in OI.java in a JoystickButton.whileHeld method. Instantiate
 * once per direction/value that gets inputed.
 */
public class DriveArmsCommand extends Command {
	private final double GAIN = 0.15;
	private ArmsSubsystem m_subsystem;

	/**
	 * 
	 * @param subsystem subsystem to access hardware (including limit switch)
	 * @param value     the speed at which the intake will drive. must be between -1
	 *                  and 1
	 */
	public DriveArmsCommand(ArmsSubsystem subsystem) {
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
		// m_subsystem.drive();
		this.m_subsystem.setSpeed(OI.armDrive.getAsDouble() * 0.2);

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// return OI.transX.getAsDouble() == 0;
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// this.m_subsystem.drive(-0.02);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}