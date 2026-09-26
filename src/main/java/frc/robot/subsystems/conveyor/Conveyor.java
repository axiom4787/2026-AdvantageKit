// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.conveyor;

import frc.lib.io.MotorIOSpark;
import frc.lib.io.MotorIO.Setpoint;
import frc.lib.subsystems.MotorSubsystem;

public class Conveyor extends MotorSubsystem<MotorIOSpark> {
  public static final Conveyor mInstance = new Conveyor();

  public static final Setpoint IDLE = Setpoint.withNeutralSetpoint();
  public static final Setpoint FEED = Setpoint.withDutyCycleSetpoint(ConveyorConstants.kFeedPower);
  public static final Setpoint INTAKE = Setpoint.withDutyCycleSetpoint(ConveyorConstants.kIntakePower);
  public static final Setpoint EJECT = Setpoint.withDutyCycleSetpoint(ConveyorConstants.kEjectPower);

  /** Creates a new Conveyor. */
  public Conveyor() {
    super(ConveyorConstants.getMotorIO(), "Conveyor", ConveyorConstants.kGearRatio);
  }

  @Override
  public void periodic() {
    super.periodic();
  }
}
