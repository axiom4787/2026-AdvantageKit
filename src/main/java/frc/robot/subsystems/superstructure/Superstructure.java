// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.superstructure;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.lib.io.MotorIO.Setpoint;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.conveyor.Conveyor;
import frc.robot.subsystems.drive.DriveBase.AimMode;
import frc.robot.subsystems.indexer.Indexer;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.Shooter;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;

/**
 * This subsystem manages all other mechanism subsystems on the robot. They should not have their
 * setpoints modified outside of the superstructure logic. This subsystem should expose commands for
 * actions that involve the whole robot, i.e. "score game piece", which can be bound to triggers.
 */
public class Superstructure extends SubsystemBase {
  public static final Superstructure mInstance = new Superstructure();

  private SuperstructureState state = SuperstructureState.IDLE;

  /** Creates a new Superstructure. */
  public Superstructure() {
    Intake.mInstance.setDefaultCommand(Intake.mInstance.followSetpointCommand(() -> Intake.IDLE));
    Indexer.mInstance.setDefaultCommand(
        Indexer.mInstance.followSetpointCommand(() -> Indexer.IDLE));
    Conveyor.mInstance.setDefaultCommand(
        Conveyor.mInstance.followSetpointCommand(() -> Conveyor.IDLE));
    Arm.mInstance.setDefaultCommand(Arm.mInstance.followSetpointCommand(() -> Arm.IDLE));
    Shooter.mInstance.setDefaultCommand(
        Shooter.mInstance.followSetpointCommand(() -> Setpoint.withNeutralSetpoint()));
    mInstance.setDefaultCommand(new InstantCommand(() -> state = SuperstructureState.IDLE));
  }

  @Override
  public void periodic() {
    Logger.recordOutput("Superstructure/State", state.toString());
  }

  // Add commands here.
  public Command deployIntakeCommand() {
    return (Arm.mInstance.followSetpointCommand(() -> Arm.DEPLOY))
        .onlyIf(() -> state == SuperstructureState.IDLE);
  }

  public Command stowIntakeCommand() {
    return (Arm.mInstance.followSetpointCommand(() -> Arm.STOW))
        .onlyIf(() -> state == SuperstructureState.IDLE);
  }

  /** Command to eject all fuel from the hopper while ran. */
  public Command exhaustCommand() {
    Command exhaust =
        new InstantCommand(() -> state = SuperstructureState.EXHAUST)
            .andThen(
                new ParallelCommandGroup(
                    Intake.mInstance.followSetpointCommand(() -> Intake.EJECT),
                    Conveyor.mInstance.followSetpointCommand(() -> Conveyor.EJECT),
                    Indexer.mInstance.followSetpointCommand(() -> Indexer.EJECT)))
            .onlyIf(() -> state != SuperstructureState.SCORE);
    exhaust.addRequirements(this);
    return exhaust;
  }

  /** Command to intake fuel from the floor while ran. */
  public Command intakeCommand() {
    Command intake =
        new InstantCommand(() -> state = SuperstructureState.INTAKE)
            .andThen(
                new ParallelCommandGroup(
                    Intake.mInstance.followSetpointCommand(() -> Intake.INTAKE),
                    Conveyor.mInstance.followSetpointCommand(() -> Conveyor.FEED),
                    Indexer.mInstance.followSetpointCommand(
                        () -> Indexer.EJECT) // Keep fuel out of shooter
                    ))
            .onlyIf(() -> state != SuperstructureState.SCORE);
    intake.addRequirements(this);
    return intake;
  }

  /**
   * Command to score fuel while ran. Spins up shooter to target velocity, then feeds from hopper to
   * score. Cannot be interrupted.
   */
  public Command scoreCommand(Supplier<AimMode> aimMode, DoubleSupplier aimDist) {
    Command score =
        new InstantCommand(() -> state = SuperstructureState.SCORE)
            .andThen(
                Shooter.mInstance.followSetpointCommand(
                    () -> getShooterSetpoint(aimMode.get(), aimDist.getAsDouble())))
            .alongWith(
                new WaitUntilCommand(Shooter.mInstance::spunUp)
                    .andThen(
                        new ParallelCommandGroup(
                            Intake.mInstance.followSetpointCommand(() -> Intake.IDLE),
                            Conveyor.mInstance.followSetpointCommand(() -> Conveyor.FEED),
                            Indexer.mInstance.followSetpointCommand(() -> Indexer.FEED))))
            .withInterruptBehavior(InterruptionBehavior.kCancelIncoming)
            .onlyIf(() -> state != SuperstructureState.EXHAUST);
    score.addRequirements(this);
    return score;
  }

  public Setpoint getShooterSetpoint(AimMode aimMode, double aimDist) {
    double aimDistIn = edu.wpi.first.math.util.Units.metersToInches(aimDist);
    double desiredSpeed;
    if (aimMode == AimMode.SCORE) {
      desiredSpeed = 37.2844 * Math.pow(aimDistIn, 0.455459); // Regression for hub score
    } else {
      desiredSpeed = 15.2189 * Math.pow(aimDistIn, 0.62); // Regression for passing
    }
    return Setpoint.withVelocitySetpoint(Units.RadiansPerSecond.of(desiredSpeed));
  }

  /**
   * Use this enum to represent the possible states of the robot's mechanisms. The state is just for
   * logging purposes in AdvantageScope, not an actual state machine.
   */
  public enum SuperstructureState {
    IDLE,
    INTAKE,
    EXHAUST,
    SCORE,
  }
}
