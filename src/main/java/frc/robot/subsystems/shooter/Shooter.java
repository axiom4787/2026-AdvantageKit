// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import frc.lib.io.MotorIO.Setpoint;
import frc.lib.subsystems.FlywheelMotorSubsystem;

public class Shooter extends FlywheelMotorSubsystem {
  public static final Shooter mInstance = new Shooter();

  public static final Setpoint IDLE = Setpoint.withNeutralSetpoint();

  /** Creates a new Shooter. */
  public Shooter() {
    super(ShooterConstants::getMotorIO, "Shooter", 1.0, ShooterConstants.kEpsilonThreshold);
  }

  @Override
  public void periodic() {
    super.periodic();
  }
}
