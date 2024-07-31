// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Chasis;
import edu.wpi.first.wpilibj.Timer;

public class Cmd_PID extends Command {
  /** Creates a new Cmd_PID. */
  private final Sub_Chasis chasis;
  private final double setpoint;
  private double Dt, LastDt;
  private double RightErrorP,  RightErrorD, RightLastError, RightSpeed;
  private double LeftErrorP,  LeftErrorD, LeftLastError, LeftSpeed;
  private double kP, kD;
  public Cmd_PID(Sub_Chasis chasis,double setpoint) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.chasis=chasis;
    this.setpoint=setpoint;
    addRequirements(chasis);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
     kP = 0.0137; kD = 0.0; 
    chasis.resetPosition();
    chasis.OpenLoopS(1.5);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Delta de tiempo
    Dt = Timer.getFPGATimestamp() - LastDt;
    RightErrorP = setpoint - chasis.getRightPosition();
    LeftErrorP = setpoint  - chasis.getLeftPosition();

    RightErrorD = (RightErrorP-RightLastError)/Dt;
    LeftErrorD = (LeftErrorP-LeftLastError)/Dt;
   

    //Control de velocidad
    RightSpeed = (RightErrorP * kP) + (RightErrorD * kD);
    LeftSpeed = (LeftErrorP * kP) + (LeftErrorD * kD);

    chasis.setSpeed(-RightSpeed, -LeftSpeed);
    RightLastError = RightErrorP;
    LeftLastError = LeftErrorP;
    LastDt = Timer.getFPGATimestamp();

     System.out.println(RightSpeed);

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
  
    if(Math.abs((RightErrorP+LeftErrorP)/2) <= 5){ 
      chasis.setSpeed(0, 0);
      return true; }else{ return false; }
      
      
  }
  
}
