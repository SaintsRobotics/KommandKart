package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.CargoIntakeSubsystem;

/**
 * Drives cargo intake based on the value provided in the constructor. To be
 * instantiated in OI.java in a JoystickButton.whileHeld method. Instantiate
 * once per direction/value that gets inputed.
 */
public class CargoIntakeCommand extends Command {

	private CargoIntakeSubsystem m_subsystem;
	private double m_value;
	private BooleanSupplier inButton;
	private BooleanSupplier outButton;

	/**
	 * 
	 * @param subsystem subsystem to access hardware (including limit switch)
	 * @param value     the speed at which the intake will drive. must be between -1
	 *                  and 1
	 */
	public CargoIntakeCommand(CargoIntakeSubsystem subsystem, BooleanSupplier inButton, BooleanSupplier outButton) {
		this.m_subsystem = subsystem;
		requires(this.m_subsystem);
		this.inButton = inButton;
		this.outButton = outButton;

	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (inButton.getAsBoolean()) {
			m_subsystem.drive(1);

		} else if (outButton.getAsBoolean()) {
			m_subsystem.drive(-1);
		} else {
			m_subsystem.drive(0);
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
		this.m_subsystem.drive(0); // there is no default command, so you need to end the driving when the command
									// ends
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}