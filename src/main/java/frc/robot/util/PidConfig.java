package frc.robot.util;

/**
 * A class to easily pass around PID values
 */

public class PidConfig {
    public final double kP;
    public final double kI;
    public final double kD;
    public final double tolerance;

    /**
     * Constructor
     * 
     * @param kP        Proportional value
     * @param kI        Integral value
     * @param kD        Derivative value
     * @param tolerance Tolerance value
     */
    public PidConfig(double kP, double kI, double kD, double tolerance) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.tolerance = tolerance;
    }
}