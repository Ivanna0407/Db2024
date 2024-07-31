// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Intake_Shooter;
import frc.robot.subsystems.Sub_LEDs;

public class Cmd_Auto_Move_Intake extends Command {
  /** Creates a new Cmd_Auto_Move_Intake. */
  private final Sub_Intake_Shooter Intake;
  private final Sub_LEDs Leds;
   double speed;
  public Cmd_Auto_Move_Intake(Sub_Intake_Shooter intake,Sub_LEDs leds,double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.Intake=intake;
    this.Leds=leds;
    this.speed=speed;
    addRequirements(Intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Intake.IntakeSet(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (speed>0){
      Leds.setdefault();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Intake.MotorIntakegetspeed()== speed){
      return true;
    }
    else{return false;}
  }
}
