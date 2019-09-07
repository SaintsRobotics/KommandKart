package frc.robot.util;

import java.lang.reflect.Constructor;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CANEncoderPidSource implements PIDSource {
	private CANEncoder m_CANEncoder;
	private PIDSourceType m_pidSourceType;

	public CANEncoderPidSource(CANEncoder encoder) {
		this.m_CANEncoder = encoder;
		this.m_pidSourceType = PIDSourceType.kDisplacement;

	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.m_pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.m_pidSourceType;
	}

	@Override
	public double pidGet() {
		return m_CANEncoder.getPosition();

	}

}