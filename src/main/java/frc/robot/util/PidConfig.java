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
	 * 
	 * @param kP            pid controller p coefficient
	 * @param kI            pid controller i coefficient
	 * @param kD            pid controller d coefficient
	 * @param kTolerance    the absolute error which is considered tolerable to then exit the pid loop.  defaults to 0.05
	 */
	public PidConfig(double kP, double kI, double kD, double tolerance) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.tolerance = tolerance;
	}
}