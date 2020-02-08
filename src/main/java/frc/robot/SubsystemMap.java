package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Configs.PidConfigs;
import frc.robot.subsystems.ArmsSubsystem;
import frc.robot.subsystems.CargoIntakeSubsystem;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.SwerveWheel;
import frc.robot.util.CANEncoderPidSource;
import frc.robot.util.PidConfig;

/**
 * A way for all of the subsystems to be publicly available.
 */
public class SubsystemMap {
	public static SwerveSubsystem swerveSubsystem;
	public static LiftSubsystem lift;
	public static CargoIntakeSubsystem cargoIntake;
	public static ArmsSubsystem arms;

	/**
	 * This method must be called inside Robot.robotInit() and it must construct all
	 * of the subsystems.
	 */
	public SubsystemMap() {
		swerveSubsystem = new SwerveSubsystem(
				new SwerveWheel[] { RobotMap.rightFrontWheel, RobotMap.leftFrontWheel, RobotMap.leftBackWheel,
						RobotMap.rightBackWheel },
				new double[] { 0, 0 }, () -> ((RobotMap.gyro.getAngle() % 360) + 360) % 360,
				PidConfigs.robotHeading.value);

		// arms = new ArmsSubsystem(RobotMap.arm, new CANEncoderPidSource(RobotMap.armEncoder), PidConfigs.arm.value);
		// cargoIntake = new CargoIntakeSubsystem(RobotMap.cargoIntake);

		// lift = new LiftSubsystem(RobotMap.lift, RobotMap.liftEncoder, RobotMap.lowerLiftLimit, RobotMap.upperLiftLimit,
		// 		PidConfigs.lift.value);

	}
}