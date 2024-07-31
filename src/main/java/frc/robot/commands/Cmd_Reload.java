// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Intake_Shooter;

public class Cmd_Reload extends Command {
  private final Sub_Intake_Shooter Intake;
  double error_l;

  /** Creates a new Cmd_Reload. */
  public Cmd_Reload(Sub_Intake_Shooter intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.Intake = intake;
    addRequirements(intake);
    
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Intake.resetEncoderLlantas();
   

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   error_l =-2- Intake.getIntakeEncoder();
    Intake.IntakeSet(0.3*error_l);
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(error_l)>.2){
      return false;
    }
    else{return true;}
  }
}
