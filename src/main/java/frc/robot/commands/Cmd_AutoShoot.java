// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Chasis;
import frc.robot.subsystems.Sub_Intake_Shooter;



public class Cmd_AutoShoot extends Command {
  private final Sub_Intake_Shooter intake_Shooter;
  private final Sub_Chasis Chasis;
  private boolean Alligned = false;
  private boolean End = false;
  private double tx, ty, tid, ErrorX, ErrorY, ErrorXI, dt, LastDT = 0;
  private double RefY, RefX;
  private double kY, kX, kiY, kiX, kdY, kdX;
  private double XErrorD, YErrorD, XLastError, YLastError;

  public Cmd_AutoShoot(Sub_Intake_Shooter intake_Shooter, Sub_Chasis Chasis) {
    this.intake_Shooter = intake_Shooter;
    this.Chasis = Chasis;
    addRequirements(intake_Shooter);
    addRequirements(Chasis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Chasis.OpenLoopS(1);
    Alligned = false;
    End = false;
    intake_Shooter.SetSpeed(0);
    intake_Shooter.IntakeSet(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake_Shooter.SetSpeed(1);
    while(Alligned == false){VisionMove();}
    Timer.delay(0.5);
    intake_Shooter.IntakeSet(1);
    Timer.delay(0.5);
    intake_Shooter.SetSpeed(0);
    intake_Shooter.IntakeSet(0);
    End = true;
  }

  @Override
  public void end(boolean interrupted) {
    Chasis.OpenLoopS(0);
  }

  @Override
  public boolean isFinished() {
    if(End==true){Chasis.OpenLoopS(0); return true;}else{return false;}
  }

  public void VisionMove(){
    dt = Timer.getFPGATimestamp() - LastDT;
    Updatevalues();
    if (tid == 7) {
      RefX = 7.41;
      RefY = 10.79;
    }
    dt = Timer.getFPGATimestamp() - LastDT;
    ErrorX = RefX - tx;
    ErrorY = RefY - ty;
    XErrorD = (ErrorX - XLastError) / dt;
    YErrorD = (ErrorY - YLastError) / dt;
    if (Math.abs(ErrorY) <= 5) {
      ErrorXI += ErrorX * dt;
    } else {
      ErrorXI = 0;
    }

    if (tid == -1) {
      ErrorX = 0;
      ErrorY = 0;
      XErrorD = 0;
      YErrorD = 0;
    }
    if (ty > 12) {
      ErrorY = 0;
      YErrorD = 0;
    }
    if (ty < -20) {
      ErrorY = 0;
      YErrorD = 0;
    }

    double foward = (-ErrorY) * kY - (kdY * YErrorD);
    double turn = (ErrorX) * (kX) * (5 / (ty + 20)) + (kdX * XErrorD);
    double RightSpeed = foward - turn;
    double LeftSpeed = foward + turn;
    Chasis.setSpeed(RightSpeed, LeftSpeed);
    LastDT = Timer.getFPGATimestamp();

    if (Math.abs(ErrorY * 0.5) < 2 && Math.abs(ErrorX * 0.5) < 1) {
      Chasis.OpenLoopS(0);
      Alligned = true;
    } else {
      Alligned = false;
    }
  }

  public void Updatevalues() {
    this.tx = Chasis.getTx();
    this.ty = Chasis.getTy();
    this.tid = Chasis.getTid();
  }
}
