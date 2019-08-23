/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
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
	public static SpeedController rightFrontDrive;
	public static SpeedController rightFrontTurn;
	public static AbsoluteEncoder rightFrontEncoder;
	public static SwerveWheel rightFrontWheel;

	public static SpeedController leftFrontDrive;
	public static SpeedController leftFrontTurn;
	public static AbsoluteEncoder leftFrontEncoder;
	public static SwerveWheel leftFrontWheel;

	public static SpeedController leftBackDrive;
	public static SpeedController leftBackTurn;
	public static AbsoluteEncoder leftBackEncoder;
	public static SwerveWheel leftBackWheel;

	public static SpeedController rightBackDrive;
	public static SpeedController rightBackTurn;
	public static AbsoluteEncoder rightBackEncoder;
	public static SwerveWheel rightBackWheel;

	public static ADXRS450_Gyro gyro;

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

}
