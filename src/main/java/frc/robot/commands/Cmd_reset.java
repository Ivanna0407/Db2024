// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Intake_Shooter;

public class Cmd_reset extends Command {
  /** Creates a new Cmd_reset. */
  private final Sub_Intake_Shooter Intake;
private final double setpoint;
  public Cmd_reset(Sub_Intake_Shooter intake,double setpoint) {
    this.Intake=intake;
    this.setpoint=setpoint;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Intake.SetEncoderArmIntake(setpoint);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
