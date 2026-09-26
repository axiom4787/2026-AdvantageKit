// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import frc.lib.io.MotorIO.Setpoint;
import frc.lib.io.MotorIOSpark;
import frc.lib.subsystems.MotorSubsystem;

public class Arm extends MotorSubsystem<MotorIOSpark> {
  public static final Arm mInstance = new Arm();

  public static final Setpoint IDLE = Setpoint.withNeutralSetpoint();
  public static final Setpoint STOW = Setpoint.withDutyCycleSetpoint(ArmConstants.kStowPower);
  public static final Setpoint DEPLOY = Setpoint.withDutyCycleSetpoint(ArmConstants.kDeployPower);

  /** Creates a new Arm. */
  public Arm() {
    super(ArmConstants.getMotorIO(), "Arm", ArmConstants.kGearRatio);
  }

  @Override
  public void periodic() {
    super.periodic();
  }
}
