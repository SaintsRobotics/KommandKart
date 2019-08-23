package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveDriveCommand extends Command {

    private SwerveSubsystem m_subsystem;
    private DoubleSupplier m_transX;
    private DoubleSupplier m_transY;
    private DoubleSupplier m_rotation;
    private BooleanSupplier m_absolute;
    private boolean m_dynamicGain;

    /**
     * 
     * @param subsystem     swerve drive subsystem
     * @param transX        joystick access method for moving the robot right and
     *                      left
     * @param transY        joystick access method for moving the robot back and
     *                      forth
     * @param rotation      joystick access method for rotating the robot
     * @param absoluteDrive whether or not the robot will drive relative to the
     *                      direction it's facing or the direction it started in.
     *                      Invert this to have absolute drive as default.
     */
    public SwerveDriveCommand(SwerveSubsystem subsystem, DoubleSupplier transX, DoubleSupplier transY,
            DoubleSupplier rotation, BooleanSupplier absoluteDrive) {
        this.m_subsystem = subsystem;
        requires(this.m_subsystem);

        this.m_transX = transX;
        this.m_transY = transY;
        this.m_rotation = rotation;
        this.m_absolute = absoluteDrive;
        this.m_dynamicGain = true;
    }

    /**
     * 
     * @param subsystem     swerve drive subsystem
     * @param transX        joystick access method for moving the robot right and
     *                      left
     * @param transY        joystick access method for moving the robot back and
     *                      forth
     * @param rotation      joystick access method for rotating the robot
     * @param absoluteDrive whether or not the robot will drive relative to the
     *                      direction it's facing or the direction it started in.
     *                      invert this to have absolute drive as default
     * @param dynamicGain   drive with dynamic translation/rotation gain or static
     *                      translation/rotation gain. defaults to true
     */
    public SwerveDriveCommand(SwerveSubsystem subsystem, DoubleSupplier transX, DoubleSupplier transY,
            DoubleSupplier rotation, BooleanSupplier absoluteDrive, boolean dynamicGain) {
        this(subsystem, transX, transY, rotation, absoluteDrive);
        this.m_dynamicGain = dynamicGain;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (m_dynamicGain) {
            m_subsystem.dynamicGainDrive(this.m_transX.getAsDouble(), this.m_transY.getAsDouble(),
                    this.m_rotation.getAsDouble());
        } else {
            m_subsystem.staticGainDrive(this.m_transX.getAsDouble(), this.m_transY.getAsDouble(),
                    this.m_rotation.getAsDouble());
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