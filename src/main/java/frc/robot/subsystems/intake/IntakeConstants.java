// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.lib.io.MotorIOSpark;
import frc.lib.io.MotorIOSpark.MotorIOSparkConfig;

/** Add your docs here. */
public class IntakeConstants {
  public static final int kMotorId = 12;

  public static final double kS = 0.5;
  public static final double kV = 0.015;
  public static final double kP = 0.0;
  public static final double kI = 0.0;
  public static final double kD = 0.0;

  public static final AngularVelocity kEpsilonThreshold =
      Units.RadiansPerSecond.of(10); // Acceptable speed error

  public static final double kGearRatio = (28.0 / 26.0) * (24.0 / 21.0);

  public static final AngularVelocity kIntakeSpeed = Units.RadiansPerSecond.of(250);
  public static final AngularVelocity kEjectSpeed = Units.RadiansPerSecond.of(-250);

  public static SparkBaseConfig getSparkConfig() {
    SparkBaseConfig config = new SparkFlexConfig();

    config.inverted(false);
    config.smartCurrentLimit(80);
    config.idleMode(IdleMode.kCoast);

    // Configure PID and feedforward for slot 2 (velocity control)
    config.closedLoop.feedForward.kS(kS, ClosedLoopSlot.kSlot2);
    config.closedLoop.feedForward.kV(kV, ClosedLoopSlot.kSlot2);
    config.closedLoop.p(kP, ClosedLoopSlot.kSlot2);
    config.closedLoop.i(kI, ClosedLoopSlot.kSlot2);
    config.closedLoop.d(kD, ClosedLoopSlot.kSlot2);

    return config;
  }

  public static MotorIOSparkConfig getIOConfig() {
    MotorIOSparkConfig config = new MotorIOSparkConfig();

    config.mainConfig = getSparkConfig();
    config.mainID = kMotorId;

    config.unit = Units.Radians;
    config.time = Units.Seconds;

    return config;
  }

  public static MotorIOSpark getMotorIO() {
    return new MotorIOSpark(getIOConfig());
  }
}
