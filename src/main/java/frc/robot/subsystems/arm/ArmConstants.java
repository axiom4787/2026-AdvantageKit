// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Dimensionless;
import frc.lib.io.MotorIOSpark;
import frc.lib.io.MotorIOSpark.MotorIOSparkConfig;

public class ArmConstants {
  public static final int kMainId = 10;

  public static final double kGearRatio = 1;

  public static final Dimensionless kStowPower = Units.Value.of(0.15);
  public static final Dimensionless kDeployPower = Units.Value.of(-0.25);

  public static SparkBaseConfig getSparkConfig() {
    SparkBaseConfig config = new SparkMaxConfig();

    config.inverted(false);
    config.smartCurrentLimit(60);
    config.idleMode(IdleMode.kBrake);

    return config;
  }

  public static MotorIOSparkConfig getIOConfig() {
    MotorIOSparkConfig config = new MotorIOSparkConfig();

    config.mainConfig = getSparkConfig();
    config.mainID = kMainId;

    config.unit = Units.Rotations;
    config.time = Units.Minutes;

    return config;
  }

  public static MotorIOSpark getMotorIO() {
    return new MotorIOSpark(getIOConfig());
  }
}
