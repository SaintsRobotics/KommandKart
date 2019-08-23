package frc.robot.util;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Sends values from double suppliers.  Provides them in a way that PIDController can access them as PIDSources
 */
public class PidSender implements PIDSource {

	private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	private DoubleSupplier m_supplier;

	public PidSender(DoubleSupplier supplier) {
		this.m_supplier = supplier;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSourceType;
	}

	@Override
	public double pidGet() {
		return this.m_supplier.getAsDouble();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSourceType = pidSource;
	}

 }