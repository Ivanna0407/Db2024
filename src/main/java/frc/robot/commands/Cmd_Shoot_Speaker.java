// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Intake_Shooter;

public class Cmd_Shoot_Speaker extends Command {
  /** Creates a new Cmd_Shoot. */
    private final Sub_Intake_Shooter Shooter;
    private final int mode ;
  public Cmd_Shoot_Speaker(Sub_Intake_Shooter Shooter,int mode) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.Shooter=Shooter;
    this.mode=mode;
    addRequirements(Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (mode ==0){
      Shooter.IntakeSet(.5);
      }
      else {if(mode==1){
        Shooter.IntakeSet(-.5);
      }}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.IntakeSet(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
