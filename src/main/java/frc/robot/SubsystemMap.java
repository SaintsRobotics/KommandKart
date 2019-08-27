package frc.robot;

import frc.robot.commands.CargoIntakeCommand;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.SwerveWheel;

/**
 * A way for all of the subsystems to be publicly available.
 */
public class SubsystemMap {
	public static SwerveSubsystem swerveSubsystem;
	public static LiftSubsystem lift;
	public static CargoIntakeCommand cargoIntake;

	/**
	 * This method must be called inside Robot.robotInit() and it must construct all
	 * of the subsystems.
	 */
	public SubsystemMap() {
		swerveSubsystem = new SwerveSubsystem(
				new SwerveWheel[] { RobotMap.rightFrontWheel, RobotMap.leftFrontWheel, RobotMap.leftBackWheel,
						RobotMap.rightBackWheel },
				new double[] { 0, 0 }, () -> ((RobotMap.gyro.getAngle() + 360) % 360) + 360);
	}
}