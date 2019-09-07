package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.util.AngleUtilities;
import frc.robot.util.PidConfig;

public class SwerveSubsystem extends Subsystem {
	private static double DYNAMIC_SPEED_COEF = 1;
	private static double STATIC_TRANS_COEF = .3;
	private static double STATIC_ROT_COEF = .2;

	private DoubleSupplier m_gyro;
	private SwerveWheel[] m_wheels;
	private double m_maxWheelDistance;
	private PIDController m_pidController;
	private double m_pidOutput;
	private boolean m_isTurning;

	public SwerveSubsystem(SwerveWheel[] wheels, double[] pivotLoc, DoubleSupplier gyro, double dynSpeedCoef,
			double stcTransCoef, double stcRotCoef, PidConfig pidConfig) {
		this(wheels, pivotLoc, gyro, pidConfig);

		DYNAMIC_SPEED_COEF = dynSpeedCoef;
		STATIC_TRANS_COEF = stcTransCoef;
		STATIC_ROT_COEF = stcRotCoef;

	}

	public SwerveSubsystem(SwerveWheel[] wheels, double[] pivotLoc, DoubleSupplier gyro, PidConfig pidConfig) {
		this.m_wheels = wheels;
		this.m_gyro = gyro;

		for (SwerveWheel s : wheels) {
			if (s.getRadius() > this.m_maxWheelDistance) {
				this.m_maxWheelDistance = s.getRadius();
			}
		}
		this.m_pidController = new PIDController(pidConfig.kP, pidConfig.kI, pidConfig.kD, RobotMap.gyro,
				(output) -> this.m_pidOutput = output);
		this.m_pidController.setAbsoluteTolerance(pidConfig.tolerance);
		this.m_pidController.setOutputRange(-1, 1);
		this.m_pidController.setInputRange(0, 360);
		this.m_pidController.setContinuous();
		this.m_pidController.reset();
		this.m_pidController.enable();

	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new SwerveDriveCommand(this, this.m_gyro, true));
	}

	/**
	 * The translation and rotation speeds, shift according to how much speed is
	 * input.
	 * 
	 * @param transX   movement in the left and right direction
	 * @param transY   movement in the forward and backward direction
	 * @param rotation the speed at which the robot is to rotate
	 */
	private int num = 0;

	public void dynamicGainDrive(double transX, double transY, double rotation) {

		if (rotation != 0.0) {
			this.m_isTurning = true;
		} else if (rotation == 0.0 && this.m_isTurning) {
			this.m_pidController.setSetpoint((((RobotMap.gyro.getAngle() % 360) + 360) % 360));
			this.m_isTurning = false;
			rotation = this.m_pidOutput;
			System.out.println("inside here " + num++);
		} else if (transX != 0.0 || transY != 0) {
			rotation = this.m_pidOutput;
		}

		// SmartDashboard.putNumber("swerve setpoint ",
		// this.m_pidController.getSetpoint());

		// Doing math with each of the vectors for the SwerveWheels
		// Calculating the rotation vector, then adding that to the translation vector
		// Converting them to polar vectors
		// SmartDashboard.putNumber("input rotation", rotation);
		double[][] vectors = new double[m_wheels.length][2];
		for (int i = 0; i < m_wheels.length; i++) {
			vectors[i][0] = m_wheels[i].getRotationVector()[0] * (1 / this.m_maxWheelDistance)
					* (rotation * STATIC_ROT_COEF) + (transX * STATIC_TRANS_COEF);
			// SmartDashboard.putNumber("wheel [" + i + "][0] ", vectors[i][0]);
			vectors[i][1] = m_wheels[i].getRotationVector()[1] * (1 / this.m_maxWheelDistance)
					* (rotation * STATIC_ROT_COEF) + (transY * STATIC_TRANS_COEF);
			vectors[i] = AngleUtilities.cartesianToPolar(vectors[i]);
			// SmartDashboard.putNumber("wheel [" + i + "][1] ", vectors[i][1]);

		}

		// If any of the velocities are greater than SPEED_COEF, then scale them all
		// down
		boolean needsScale = false;
		double maxVelocity = 0; // an arbitrary value
		int v = 0; // index used for traversing the vectors array
		while (!needsScale && v < vectors.length) {
			needsScale = vectors[v][1] > SwerveSubsystem.DYNAMIC_SPEED_COEF;
			maxVelocity = Math.max(maxVelocity, vectors[v][1]);
			v++;
		}
		if (needsScale) {
			for (double[] i : vectors) {
				i[1] /= maxVelocity;
				i[1] *= DYNAMIC_SPEED_COEF;
			}
		}

		for (int i = 0; i < m_wheels.length; i++) {
			m_wheels[i].setHeadAndVelocity(vectors[i][0], vectors[i][1]);
		}

	}

	/**
	 * There is a maximum speed at which the robot will rotate, and maximum speed at
	 * which the robot will translate.
	 * 
	 * @param transX   movement in the left and right direction
	 * @param transY   movement in the forward and backward direction
	 * @param rotation the speed at which the robot is to rotate
	 */
	public void staticGainDrive(double transX, double transY, double rotation) {

		// Doing math with each of the vectors for the SwerveWheels
		// Calculating the rotation vector, then adding that to the translation vector
		// Converting them to polar vectors
		double[][] vectors = new double[m_wheels.length][2];
		for (int i = 0; i < m_wheels.length; i++) {
			vectors[i][0] = m_wheels[i].getRotationVector()[0] * (1 / this.m_maxWheelDistance)
					* (rotation * STATIC_ROT_COEF) + (transX * STATIC_TRANS_COEF);
			vectors[i][1] = m_wheels[i].getRotationVector()[1] * (1 / this.m_maxWheelDistance)
					* (rotation * STATIC_ROT_COEF) + (transY * STATIC_TRANS_COEF);
			vectors[i] = AngleUtilities.cartesianToPolar(vectors[i]);
		}

		for (int i = 0; i < m_wheels.length; i++) {
			m_wheels[i].setHeadAndVelocity(vectors[i][0], vectors[i][1]);
		}
	}

}
