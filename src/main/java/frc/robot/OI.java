/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Robot;
import frc.robot.Configs.PidConfigs;
import frc.robot.commands.CargoIntakeCommand;
import frc.robot.commands.ResetGyroCommand;
import frc.robot.commands.ToHeightCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  // CONSTANT VALUES
  // use the getRawAxis(int) and getRawButton(int) methods inherited from
  // GeneralHID
  public static int LEFT_STICK_X = 0;
  public static int LEFT_STICK_Y = 1;
  public static int RIGHT_STICK_X = 4;
  public static int RIGHT_STICK_Y = 5;
  public static int LEFT_TRIGGER = 2;
  public static int RIGHT_TRIGGER = 3;
  public static int BUTTON_A = 1;
  public static int BUTTON_B = 2;
  public static int BUTTON_X = 3;
  public static int BUTTON_Y = 4;
  public static int BACK_BUTTON = 7;
  public static int START_BUTTON = 8;
  public static int LEFT_BUMBER = 5;
  public static int RIGHT_BUMPER = 6;
  public static int LEFT_STICK_BUTTON = 9;
  public static int RIGHT_STICK_BUTTON = 10;

  public static Joystick xboxController = new Joystick(0);
  public static Joystick oppBoard = new Joystick(1);

  // DRIVER CONTROLS
  public static DoubleSupplier transX = () -> oddSquare(deadZone(xboxController.getRawAxis(LEFT_STICK_X), 0.05));
  public static DoubleSupplier transY = () -> oddSquare(deadZone(-xboxController.getRawAxis(LEFT_STICK_Y), 0.05));
  public static DoubleSupplier rotation = () -> deadZone(xboxController.getRawAxis(RIGHT_STICK_X), 0.2) * .5;
  public static BooleanSupplier absoluteDrive = () -> xboxController.getRawButton(RIGHT_BUMPER);
  public static Button toHeadingTrigger;

  public static DoubleSupplier liftDrive = () -> deadZone(-oppBoard.getRawAxis(LEFT_STICK_Y), 0.2);
  public static DoubleSupplier armDrive = () -> deadZone(-oppBoard.getRawAxis(RIGHT_STICK_Y), 0.2);

  public static BooleanSupplier intakeIn = () -> oppBoard.getRawButton(LEFT_BUMBER);
  public static BooleanSupplier intakeOut = () -> oppBoard.getRawButton(RIGHT_BUMPER);

  private static Button resetGyro = new JoystickButton(xboxController, START_BUTTON);
  private static Button upperScore = new JoystickButton(oppBoard, BUTTON_Y);
  private static Button lowerScore = new JoystickButton(oppBoard, BUTTON_A);

  public OI() {

    resetGyro.whenPressed(new ResetGyroCommand(RobotMap.gyro));
    upperScore.whenPressed(new ToHeightCommand(SubsystemMap.lift, RobotMap.liftEncoder, 6500, PidConfigs.lift.value));
    lowerScore.whenPressed(new ToHeightCommand(SubsystemMap.lift, RobotMap.liftEncoder, 0, PidConfigs.lift.value));

  }

  private static double deadZone(double input, double absoluteTolerance) {
    if (Math.abs(input) <= absoluteTolerance) {
      return 0;
    } else {
      return input;
    }
  }

  private static double oddSquare(double input) {
    return input * Math.abs(input);
  }
}
