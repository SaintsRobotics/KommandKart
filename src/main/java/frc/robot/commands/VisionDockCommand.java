package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.util.PidConfig;
import frc.robot.util.PidSender;

/**
 * Calculates the amount the robot must rotate and simulates that as joystick movement.  The drive can still translate the robot.
 */
public class VisionDockCommand extends Command {

	private final double X_SCALE = 1;  //a value between 0 and 1 by which the pid output for x-axis (translation) movement is scaled
	private final double Y_SCALE = 1;  //a value between 0 and 1 by which the pid output for y-axis (distance) movement is scaled

	private SwerveSubsystem m_subsystem;

	private NetworkTable m_limelight;

	private double m_translationTarget;
	private double m_distanceTarget;
	private PIDController m_translationPid;
	private PIDController m_distancePid;
	private double m_translationOutput;
	private double m_distanceOutput;

	public VisionDockCommand(SwerveSubsystem subsystem, double translationTarget, double distanceTaraget, PidConfig translationConfig, PidConfig distanceConfig) {
		this.m_subsystem = subsystem;
		requires(this.m_subsystem);

		this.m_limelight = NetworkTableInstance.getDefault().getTable("limelight");

		this.m_translationTarget = translationTarget;
		this.m_distanceTarget = distanceTaraget;

		this.m_translationPid = new PIDController(translationConfig.kP, translationConfig.kI, translationConfig.kD, new PidSender(() -> this.m_limelight.getEntry("tx").getDouble(0)), (output) -> this.m_translationOutput = output * X_SCALE);
		this.m_distancePid = new PIDController(distanceConfig.kP, distanceConfig.kI, distanceConfig.kD, new PidSender(() -> this.m_limelight.getEntry("ty").getDouble(0)), (output) -> this.m_distanceOutput = output * Y_SCALE);

		this.m_translationPid.setAbsoluteTolerance(translationConfig.tolerance);
		this.m_translationPid.setOutputRange(-1, 1);
		this.m_translationPid.setInputRange(-20, 20);
		this.m_translationPid.setContinuous(false);
		this.m_translationPid.reset();

		this.m_distancePid.setAbsoluteTolerance(distanceConfig.tolerance);
		this.m_distancePid.setOutputRange(-1, 1);
		this.m_distancePid.setInputRange(-20, 20);
		this.m_distancePid.setContinuous(false);
		this.m_distancePid.reset();
		
		/* NOTE: The ranges and pid coefficients need to be tuned, along with scales */
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

		/* copy-pasted (more or less) from
			SwerveInput.doStartDocking()
			DockTask.init()
			DockTask.doWarmupCamera()
			DockTask.resetPid()

			in this order
			all from original ShoppingKart2019
		*/
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
		this.m_limelight.getEntry("pipeline").setNumber(2);

		this.m_translationPid.enable();
		this.m_distancePid.enable();

		this.m_translationPid.setSetpoint(this.m_translationTarget);
		this.m_distancePid.setSetpoint(this.m_distanceTarget);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		this.m_subsystem.staticGainDrive(this.m_translationOutput, this.m_distanceOutput, 0);
		/* NOTE: It doesn't matter static vs dynamic gain drive because we're not rotating while docking.  We are already aligned properly. */
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		boolean joysticksAreZero = (OI.transX.getAsDouble() == 0) || (OI.transY.getAsDouble() == 0) || (OI.rotation.getAsDouble() == 0);
		boolean pidsDone = this.m_translationPid.onTarget() && this.m_distancePid.onTarget();
		return pidsDone || !joysticksAreZero;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		this.m_distancePid.reset();
		this.m_translationPid.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
