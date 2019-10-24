package frc.robot;

import frc.robot.util.PidConfig;

public class Configs {
	// Swerve Wheel Locations
	public enum Locations {
		rightFront(12.75, 11), leftFront(-12.75, 11), leftBack(-12.75, -11), rightBack(12.75, -11);

		public final double[] value = new double[2];

		Locations(double x, double y) {
			this.value[0] = x;
			this.value[1] = y;
		}
	}

	// Swerve Wheel Offsets
	public enum WheelOffsets {
		// rightFront(-241.7), leftFront(-144.5), leftBack(-190), rightBack(-158.4);
		rightFront(-306), leftFront(-147), leftBack(-94), rightBack(-152);

		// rightFront(0), leftFront(0), leftBack(0), rightBack(-156.6);

		// rightFront(-234.9), leftFront(138.5), leftBack(204.8), rightBack(160.9);
		public final double value;

		WheelOffsets(double value) {
			this.value = value;
		}
	}

	public enum PidConfigs {
		swerveWhels(new PidConfig(0.02, 0, 0, 2.5)), robotHeading(new PidConfig(0.0125, 0, 0.001, 2)),
		arm(new PidConfig(.1, 0, 0, 0)), lift(new PidConfig(.1, 0, 0, 20));

		public final PidConfig value;

		PidConfigs(PidConfig value) {
			this.value = value;
		}
	}

	// PID Configs
	public PidConfig rightFrontPidConfig;
	public PidConfig leftFrontPidConfig;
	public PidConfig leftBackPidConfig;
	public PidConfig rightBackConfig;
}