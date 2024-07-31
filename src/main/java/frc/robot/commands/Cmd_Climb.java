// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Climber;
public class Cmd_Climb extends Command {
  /** Creates a new Cmd_Climb. */
  private final Sub_Climber Climber;
  private final Supplier<Double> RightY,LeftY;
  public Cmd_Climb(Sub_Climber Climber,Supplier<Double> RightY,Supplier<Double> LeftY) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.Climber=Climber;
    this.RightY=RightY;
    this.LeftY=LeftY;
    addRequirements(Climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  double joystick=RightY.get();
  double joystick_2=LeftY.get();
    if (Math.abs(joystick)<.25){joystick=0;}
    if (Math.abs(joystick_2)<.25){joystick_2=0;}
  Climber.SetSpeedclimb(joystick*.5, joystick_2*.5);
    
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
