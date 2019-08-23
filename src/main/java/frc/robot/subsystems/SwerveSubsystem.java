package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.util.AngleUtilities;
import frc.robot.util.PidConfig;

public class SwerveSubsystem extends Subsystem {
    private static double DYNAMIC_SPEED_COEF = 1;
    private static double STATIC_TRANS_COEF = .1;
    private static double STATIC_ROT_COEF = .9;

    private SwerveWheel[] m_wheels;;
    private double m_maxWheelDistance;

    public SwerveSubsystem(SwerveWheel[] wheels, double[] pivotLoc, PIDSource gyro, PidConfig pidConfig,
            double dynSpeedCoef, double stcTransCoef, double stcRotCoef) {
        this(wheels, pivotLoc, gyro, pidConfig);

        DYNAMIC_SPEED_COEF = dynSpeedCoef;
        STATIC_TRANS_COEF = stcTransCoef;
        STATIC_ROT_COEF = stcRotCoef;
    }

    public SwerveSubsystem(SwerveWheel[] wheels, double[] pivotLoc, PIDSource gyro, PidConfig pidConfig) {
        this.m_wheels = wheels;

        for (SwerveWheel s : wheels) {
            if (s.getRadius() > this.m_maxWheelDistance) {
                this.m_maxWheelDistance = s.getRadius();
            }
        }

    }

    @Override
    public void initDefaultCommand() {
        // setDefaultCommand(new SwerveDriveCommand(() -> Robot.m_oi.xbox.getAButton(),
        // () -> Robot.m_oi.xbox.getX(Hand.kLeft), () ->
        // Robot.m_oi.xbox.getY(Hand.kLeft), () -> Robot.m_oi.xbox.getX(Hand.kRight)));
    }

    /**
     * The translation and rotation speeds, shift according to how much speed is
     * input.
     * 
     * @param translationX movement in the left and right direction
     * @param translationY movement in the forward and backward direction
     * @param rotation     the speed at which the robot is to rotate
     */
    public void dynamicGainDrive(double translationX, double translationY, double rotation) {

        // Doing math with each of the vectors for the SwerveWheels
        // Calculating the rotation vector, then adding that to the translation vector
        // Converting them to polar vectors
        double[][] vectors = new double[m_wheels.length][2];
        for (int i = 0; i < m_wheels.length; i++) {
            vectors[i][0] = m_wheels[i].getRotationVector()[0] * (1 / this.m_maxWheelDistance)
                    * (rotation * STATIC_ROT_COEF) + (translationX * STATIC_TRANS_COEF);
            vectors[i][1] = m_wheels[i].getRotationVector()[1] * (1 / this.m_maxWheelDistance)
                    * (rotation * STATIC_ROT_COEF) + (translationY * STATIC_TRANS_COEF);
            vectors[i] = AngleUtilities.cartesianToPolar(vectors[i]);
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
     * @param translationX movement in the left and right direction
     * @param translationY movement in the forward and backward direction
     * @param rotation     the speed at which the robot is to rotate
     */
    public void staticGainDrive(double translationX, double translationY, double rotation) {

        // Doing math with each of the vectors for the SwerveWheels
        // Calculating the rotation vector, then adding that to the translation vector
        // Converting them to polar vectors
        double[][] vectors = new double[m_wheels.length][2];
        for (int i = 0; i < m_wheels.length; i++) {
            vectors[i][0] = m_wheels[i].getRotationVector()[0] * (1 / this.m_maxWheelDistance)
                    * (rotation * STATIC_ROT_COEF) + (translationX * STATIC_TRANS_COEF);
            vectors[i][1] = m_wheels[i].getRotationVector()[1] * (1 / this.m_maxWheelDistance)
                    * (rotation * STATIC_ROT_COEF) + (translationY * STATIC_TRANS_COEF);
            vectors[i] = AngleUtilities.cartesianToPolar(vectors[i]);
        }

        for (int i = 0; i < m_wheels.length; i++) {
            m_wheels[i].setHeadAndVelocity(vectors[i][0], vectors[i][1]);
        }
    }

}
