// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Climber;
import frc.robot.subsystems.Sub_Intake_Shooter;

public class Cmd_Climb extends Command {
  /** Creates a new Cmd_Climb. */
  private final Sub_Climber Climber;
  private final Supplier<Double> RightY;
  public Cmd_Climb(Sub_Climber Climber,Supplier<Double> RightY) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.Climber=Climber;
    this.RightY=RightY;
    addRequirements(Climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  double joystick=RightY.get();
    if (Math.abs(joystick)<.25){joystick=0;}
  Climber.SetSpeedclimb(joystick*.5);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
