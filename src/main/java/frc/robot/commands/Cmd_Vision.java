// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Chasis;
import frc.robot.subsystems.Sub_LEDs;
import edu.wpi.first.wpilibj.Timer;

public class Cmd_Vision extends Command {
  /** Creates a new Cmd_Vision. */
  private final Sub_Chasis chasis;
  private final Sub_LEDs Leds;
  private double tx, ty, tid, ErrorX, ErrorY, ErrorXI, dt, LastDT = 0,LastConection;
  private double RefY, RefX;
  private double kY, kX, kiY, kiX, kdY, kdX, LimS, LimI;
  private double XErrorD, YErrorD, XLastError, YLastError;

  public Cmd_Vision(Sub_Chasis chasis, Sub_LEDs leds) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.chasis = chasis;
    this.Leds=leds;
    addRequirements(chasis);
    Leds.setwaiting();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    chasis.OpenLoopS(1);

    kY = 0.017;
    kX = 0.017;
    kdY = 0.0001;
    kdX = 0.001;
    kiY = 0.005;
    kiX = 0.005;
    LimI = 0;
    LimS = 0;
    ErrorXI = 0;
    LastConection=Timer.getFPGATimestamp();
    
   

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     
    dt = Timer.getFPGATimestamp() - LastDT;
    Updatevalues();
    if (tid == 7 || tid==4) {
      RefX = -2.5;
      RefY = 15.5;
    }
    else{
       ErrorX = 0;
      ErrorY = 0;
      XErrorD = 0;
      YErrorD = 0;

    }
    dt = Timer.getFPGATimestamp() - LastDT;
    ErrorX = RefX - tx;
    ErrorY = RefY - ty;
    XErrorD = (ErrorX - XLastError) / dt;
    YErrorD = (ErrorY - YLastError) / dt;
    if (Math.abs(ErrorY) <= 4) {
      ErrorXI += ErrorX * dt;
    } else {
      ErrorXI = 0;
    }
/* 
    if (tid == -1) {
      ErrorX = 0;
      ErrorY = 0;
      XErrorD = 0;
      YErrorD = 0;
      NonDetCond = true;
    }
    */


    double foward = (-ErrorY) * kY - (kdY * YErrorD);
    double turn = (ErrorX) * (kX) * (5 / (ty + 20)) + (ErrorXI * kiX); // (kdX * XErrorD); // + (ErrorXI*0.008);

    double RightSpeed = foward - turn;
    double LeftSpeed = foward + turn;

    Leds.setwaiting(); 
    chasis.setSpeed(RightSpeed, LeftSpeed);
    LastDT = Timer.getFPGATimestamp();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Leds.setconfirm();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(ErrorY) < 2 && Math.abs(ErrorX) < 2 ) {
      chasis.OpenLoopS(0);
      System.out.println("Jala");
      Leds.setconfirm();
      return true;
    } else {
      return false;
    }
  }

  public void Updatevalues() {
    this.tx = chasis.getTx();
    this.ty = chasis.getTy();
    this.tid = chasis.getTid();
  }
}