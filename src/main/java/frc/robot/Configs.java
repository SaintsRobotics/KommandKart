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
		rightFront(-236), leftFront(-62), leftBack(-335), rightBack(-156.7);

		public final double value;

		WheelOffsets(double value) {
			this.value = value;
		}
	}

	public enum PidConfigs {
		swerveWhels(new PidConfig(0.02, 0, 0, 2.5)), robotHeading(new PidConfig(0.0125, 0, 0.001, 2)),
		arm(new PidConfig(.1, 0, 0, 0)), lift(new PidConfig(.001, 0, 0, 0));

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