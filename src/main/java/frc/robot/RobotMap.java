/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code
*/
/* must be accompanied by the FIRST BSD license file in the root directory of
*/
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
	public static SpeedController rightFrontDrive = new CANSparkMax(4, MotorType.kBrushless);
	public static SpeedController rightFrontTurn = new WPI_VictorSPX(5);
	public static AbsoluteEncoder rightFrontEncoder = new AbsoluteEncoder(1, Configs.WheelOffsets.rightFront.value, true);
	public static SwerveWheel rightFrontWheel = new SwerveWheel(rightFrontDrive, rightFrontTurn, rightFrontEncoder,
			Configs.PidConfigs.swerveWhels.value, Configs.Locations.rightFront.value);

	public static SpeedController leftFrontDrive = new CANSparkMax(8, MotorType.kBrushless);
	public static SpeedController leftFrontTurn = new WPI_VictorSPX(1);

	public static AbsoluteEncoder leftFrontEncoder = new AbsoluteEncoder(0, Configs.WheelOffsets.leftFront.value, true);
	public static SwerveWheel leftFrontWheel = new SwerveWheel(leftFrontDrive, leftFrontTurn, leftFrontEncoder,
			Configs.PidConfigs.swerveWhels.value, Configs.Locations.leftFront.value);;

	public static SpeedController leftBackDrive = new CANSparkMax(2, MotorType.kBrushless);
	public static SpeedController leftBackTurn = new WPI_VictorSPX(3);
	public static AbsoluteEncoder leftBackEncoder = new AbsoluteEncoder(2, Configs.WheelOffsets.leftBack.value, true);
	public static SwerveWheel leftBackWheel = new SwerveWheel(leftBackDrive, leftBackTurn, leftBackEncoder,
			Configs.PidConfigs.swerveWhels.value, Configs.Locations.leftBack.value);;

	public static SpeedController rightBackDrive = new CANSparkMax(6, MotorType.kBrushless);
	public static SpeedController rightBackTurn = new WPI_VictorSPX(7);
	public static AbsoluteEncoder rightBackEncoder = new AbsoluteEncoder(3, Configs.WheelOffsets.rightBack.value, true);
	public static SwerveWheel rightBackWheel = new SwerveWheel(rightBackDrive, rightBackTurn, rightBackEncoder,
			Configs.PidConfigs.swerveWhels.value, Configs.Locations.rightBack.value);;

	public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	// Lift
	// public static SpeedController lift = new WPI_VictorSPX(6);
	// public static Encoder liftEncoder = new Encoder(2, 3);
	// public static DigitalInput lowerLiftLimit = new DigitalInput(1);
	// public static DigitalInput upperLiftLimit = new DigitalInput(0);

	// // Cargo Intake
	// public static SpeedController cargoIntake = new WPI_VictorSPX(4);

	// // Arm
	// public static CANSparkMax arm = new CANSparkMax(11, MotorType.kBrushless);
	// public static CANEncoder armEncoder = arm.getEncoder();

	public RobotMap() {
		((CANSparkMax) rightFrontDrive).setSmartCurrentLimit(35, 60, 150);
		((CANSparkMax) leftFrontDrive).setSmartCurrentLimit(35, 60, 150);
		((CANSparkMax) leftBackDrive).setSmartCurrentLimit(35, 60, 150);
		((CANSparkMax) rightFrontDrive).setSmartCurrentLimit(35, 60, 150);
		// ((CANSparkMax) arm).setSmartCurrentLimit(35, 60, 150);
		leftBackDrive.setInverted(true);
		leftFrontDrive.setInverted(true);
		rightBackDrive.setInverted(false);
		rightFrontDrive.setInverted(false);

		leftFrontTurn.setInverted(true);
		leftBackTurn.setInverted(true);
		rightBackTurn.setInverted(true);
		rightFrontTurn.setInverted(true);

	}

}
