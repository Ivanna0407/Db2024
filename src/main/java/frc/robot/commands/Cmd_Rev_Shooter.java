// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Intake_Shooter;
import frc.robot.subsystems.Sub_LEDs;

public class Cmd_Rev_Shooter extends Command {
  /** Creates a new Cmd_Shoot_Shooter. */
  private final Sub_Intake_Shooter Shooter;
  private final Sub_LEDs Leds;
  private final Supplier<Double>RT;
  private final Supplier<Boolean> Abutton, Xbutton;
  
  public Cmd_Rev_Shooter(Sub_Intake_Shooter Shooter,Sub_LEDs leds,Supplier<Double> RT,Supplier<Boolean> Abutton,Supplier<Boolean> Xbutton) {
    this.Shooter=Shooter;
    this.Leds=leds;
    this.RT=RT;
    this.Abutton=Abutton;
    this.Xbutton=Xbutton;
    addRequirements(Shooter); 
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double Trigger;
    Trigger=RT.get(); if (Trigger<.15){Trigger=0;}
    Shooter.SetSpeed(Trigger);
    
    if (Abutton.get()){
      Shooter.IntakeSet(-.6);
      }
      else {if(Xbutton.get()){
        Shooter.IntakeSet(.6);
        Leds.setdefault();
      }
      else{
        Shooter.IntakeSet(0);
      }
    }
    

 
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.SetSpeed(0);
    Shooter.IntakeSet(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
