package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.util.PidConfig;

/**
 * Directly interracts with motors and encoder and stores unique attributes
 * about each wheel.
 */
public class SwerveWheel {

    private SpeedController m_driveMotor;
    private PIDSource m_encoder;
    private PIDController m_pidController;

    private double[] m_rotationVector;
    private double m_radius;

    /**
     * @param driveMotor   Motor that drives the wheel.
     * @param turnMotor    Motor that rotates the wheel. Must be a PIDOutput.
     * @param encoder      Encoder that tracks the position of the turn motor, in
     *                     degrees. Must be a PIDSource. An absolute encoder is
     *                     highly recommended.
     * @param pidConfig    A {@link frc.robot.util.PidConfig PidConfig} holding the
     *                     values for setting up a PIDController.
     * @param radiusVector The x and y coordinates of the wheel relative to the
     *                     origin.
     */
    public SwerveWheel(SpeedController driveMotor, PIDOutput turnMotor, PIDSource encoder, PidConfig pidConfig,
            double[] radiusVector) {

        this.m_driveMotor = driveMotor;
        this.m_encoder = encoder;

        this.m_pidController = new PIDController(pidConfig.kP, pidConfig.kI, pidConfig.kD, this.m_encoder,
                (output) -> turnMotor.pidWrite(output));
        this.m_pidController.setAbsoluteTolerance(pidConfig.tolerance);
        this.m_pidController.setOutputRange(-01, 01);
        this.m_pidController.setInputRange(0, 360);
        this.m_pidController.setContinuous();
        this.m_pidController.reset();
        this.m_pidController.enable();

        this.m_rotationVector = new double[] { -radiusVector[1], radiusVector[0] }; // perpendicular to the radius line
                                                                                    // (negative reciprocal)
        this.m_radius = Math.sqrt(Math.pow(radiusVector[0], 2) + Math.pow(radiusVector[1], 2));
    }

    /**
     * 
     * @param targetHead     The direction the wheel is to be facing, in degrees. It
     *                       calls
     *                       {@link edu.wpi.first.wpilibj.PIDController#setSetpoint(double)
     *                       PIDController.setSetpoint(double setpoint)} on the
     *                       given target.
     * @param targetVelocity The velocity at which the wheel is to be turning. Input
     *                       is in the element of [-1, 1].
     */
    public void setHeadAndVelocity(double targetHead, double targetVelocity) {
        /*
         * smart inversion logic: if turning to the angle 180 degrees away from the set
         * angle is a shorter turn, go to that angle instead and invert the drive motor
         * speed
         */

        double diff = 0.0;
        double currentHead = this.m_encoder.pidGet();
        if (Math.abs(targetHead - currentHead) > 180) {
            diff = 360 - Math.abs(targetHead - currentHead);
        } else {
            diff = Math.abs(targetHead - currentHead);
        }
        if (diff > 90) {
            targetHead += 180;
            targetHead %= 360;
            targetVelocity = -targetVelocity;
        }

        this.setVelocity(targetVelocity);
        this.m_pidController.setSetpoint(targetHead);
    }

    /**
     * Directly sets the given speed controller.
     * 
     * @param velocity Input is in the element of [-1, 1].
     */
    public void setVelocity(double velocity) {
        this.m_driveMotor.set(velocity);
    }

    /**
     * Used for calculating the swerve wheel's heading and velocity. It's the
     * distance from the wheel to the pivot point.
     */
    public double getRadius() {
        return this.m_radius;
    }

    /**
     * This value is unique to every wheel. The calculation is based on the pivot
     * point and wheel location. It is a two-dimensional vector with the length
     * equal to the radius and a slope perpendicular to the radius. It's also known
     * as the direction that the wheel will face when the robot is turning in place.
     */
    public double[] getRotationVector() {
        return this.m_rotationVector;
    }

    public double getDriveSpeed() {
        return this.m_driveMotor.get();
    }

    public double getTurnSpeed() {
        return this.m_pidController.get();
    }

}