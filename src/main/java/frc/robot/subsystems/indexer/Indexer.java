// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.indexer;

import frc.lib.io.MotorIO.Setpoint;
import frc.lib.subsystems.MotorSubsystem;

public class Indexer extends MotorSubsystem {
  public static final Indexer mInstance = new Indexer();

  public static final Setpoint IDLE = Setpoint.withNeutralSetpoint();
  public static final Setpoint FEED = Setpoint.withDutyCycleSetpoint(IndexerConstants.kFeedPower);
  public static final Setpoint EJECT = Setpoint.withDutyCycleSetpoint(IndexerConstants.kEjectPower);

  /** Creates a new Indexer. */
  public Indexer() {
    super(IndexerConstants::getMotorIO, "Indexer", IndexerConstants.kGearRatio);
  }

  @Override
  public void periodic() {
    super.periodic();
  }
}
