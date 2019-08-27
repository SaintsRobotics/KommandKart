/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code
*/
/* must be accompanied by the FIRST BSD license file in the root directory of
*/
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import frc.robot.subsystems.SwerveWheel;
import frc.robot.util.AbsoluteEncoder;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// ShoppingKart Drive
	public static SpeedController rightFrontDrive = new CANSparkMax(1, MotorType.kBrushless);
	public static SpeedController rightFrontTurn = new Talon(4);
	public static AbsoluteEncoder rightFrontEncoder = new AbsoluteEncoder(0, Configs.WheelOffsets.rightFront.value,
			true);
	public static SwerveWheel rightFrontWheel = new SwerveWheel(rightFrontDrive, rightFrontTurn, rightFrontEncoder,
			Configs.PidConfigs.swerveWhels.value, Configs.Locations.rightFront.value);

	public static SpeedController leftFrontDrive = new CANSparkMax(2, MotorType.kBrushless);
	public static SpeedController leftFrontTurn = new Talon(5);
	public static AbsoluteEncoder leftFrontEncoder = new AbsoluteEncoder(1, Configs.WheelOffsets.leftFront.value, true);
	public static SwerveWheel leftFrontWheel = new SwerveWheel(leftFrontDrive, leftFrontTurn, leftFrontEncoder,
			Configs.PidConfigs.swerveWhels.value, Configs.Locations.leftFront.value);;

	public static SpeedController leftBackDrive = new CANSparkMax(3, MotorType.kBrushless);
	public static SpeedController leftBackTurn = new Talon(6);
	public static AbsoluteEncoder leftBackEncoder = new AbsoluteEncoder(2, Configs.WheelOffsets.leftBack.value, true);
	public static SwerveWheel leftBackWheel = new SwerveWheel(leftBackDrive, leftBackTurn, leftBackEncoder,
			Configs.PidConfigs.swerveWhels.value, Configs.Locations.leftBack.value);;

	public static SpeedController rightBackDrive = new CANSparkMax(4, MotorType.kBrushless);
	public static SpeedController rightBackTurn = new Talon(7);
	public static AbsoluteEncoder rightBackEncoder = new AbsoluteEncoder(3, Configs.WheelOffsets.rightBack.value, true);
	public static SwerveWheel rightBackWheel = new SwerveWheel(rightBackDrive, rightBackTurn, rightBackEncoder,
			Configs.PidConfigs.swerveWhels.value, Configs.Locations.rightBack.value);;

	public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	// Lift
	public static SpeedController lift;
	public static Encoder liftEncoder;
	public static DigitalInput upperLiftLimit;
	public static DigitalInput lowerLiftLimit;

	// Cargo Lift
	public static SpeedController cargoLift;
	public static Encoder cargoLiftEncoder;
	public static DigitalInput cargoUpperLiftLimit;
	public static DigitalInput cargoLowerLiftLimit;

	// Cargo Intake
	public static SpeedController cargoIntake;
	public static DigitalInput cargoLimitSwitch;

	public RobotMap() {
		((CANSparkMax) rightFrontDrive).setSmartCurrentLimit(35, 60, 150);
		((CANSparkMax) leftFrontDrive).setSmartCurrentLimit(35, 60, 150);
		((CANSparkMax) leftBackDrive).setSmartCurrentLimit(35, 60, 150);
		((CANSparkMax) rightFrontDrive).setSmartCurrentLimit(35, 60, 150);
		leftBackDrive.setInverted(true);
		leftFrontDrive.setInverted(true);
		leftFrontTurn.setInverted(true);
		leftBackTurn.setInverted(true);
		rightBackTurn.setInverted(true);
	}

}
