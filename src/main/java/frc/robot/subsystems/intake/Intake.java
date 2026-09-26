// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import frc.lib.io.MotorIO.Setpoint;
import frc.lib.subsystems.FlywheelMotorSubsystem;

public class Intake extends FlywheelMotorSubsystem {
  public static final Intake mInstance = new Intake();

  public static final Setpoint IDLE = Setpoint.withNeutralSetpoint();
  public static final Setpoint INTAKE = Setpoint.withVelocitySetpoint(IntakeConstants.kIntakeSpeed);
  public static final Setpoint EJECT = Setpoint.withVelocitySetpoint(IntakeConstants.kEjectSpeed);

  /** Creates a new Intake. */
  public Intake() {
    super(
        IntakeConstants::getMotorIO,
        "Intake",
        IntakeConstants.kGearRatio,
        IntakeConstants.kEpsilonThreshold);
  }

  @Override
  public void periodic() {
    super.periodic();
  }
}
