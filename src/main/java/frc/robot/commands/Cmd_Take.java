// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Intake_Shooter;
import frc.robot.subsystems.Sub_LEDs;


public class Cmd_Take extends Command {


  private final Sub_Intake_Shooter Intake;
  private final Sub_LEDs Leds;
  /** Creates a new Cmd_TakeAndShoot_Intake. */
  public Cmd_Take(Sub_Intake_Shooter intake,Sub_LEDs leds) {
    this.Intake = intake;
    this.Leds=leds;
    addRequirements(intake);
    addRequirements(leds);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Intake.SetIntakeS(.5);
    
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Leds.setwaiting();
    if(Intake.piesa==false){
      Intake.IntakeSet(-0.6);
      Leds.setwaiting();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Intake.IntakeSet(0);
    Intake.SetIntakeS(0);
    Leds.setconfirm();;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Intake.piesa==true){
      Leds.setconfirm(); 
      Intake.IntakeSet(0);
      Intake.SetIntakeS(0);
      return true;
    }else{return false;}
  }
}
