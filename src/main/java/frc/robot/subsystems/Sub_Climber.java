// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Sub_Climber extends SubsystemBase {
  /** Creates a new Sub_Climber. */
  private final CANSparkMax Motorclimb= new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax Motorclimb_2 = new CANSparkMax(8, MotorType.kBrushless);
  private final RelativeEncoder Encoder_Climber_1 = Motorclimb.getEncoder();
  private final RelativeEncoder Encoder_Climber_2 =Motorclimb_2.getEncoder();
  private double Lim_S =100;
   private double Lim_I =0;
  public Sub_Climber() {
     Motorclimb.restoreFactoryDefaults();
    Motorclimb.setIdleMode(IdleMode.kBrake);
    Motorclimb.set(0);
    Motorclimb.setOpenLoopRampRate(0);
    Motorclimb.setInverted(true);

    Motorclimb_2.restoreFactoryDefaults();
    Motorclimb_2.setIdleMode(IdleMode.kBrake);
    Motorclimb_2.set(0);
    Motorclimb_2.setOpenLoopRampRate(0);
    Motorclimb_2.setInverted(false);

    Encoder_Climber_1.setPosition(0);
    Encoder_Climber_2.setPosition(0);

  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  public void SetSpeedclimb(double speed_1, double speed_2){
    if (-Encoder_Climber_1.getPosition()> Lim_S){
      if (speed_1<0){
        speed_1=0;
      }
    }
    if (-Encoder_Climber_1.getPosition()<Lim_I){
      if (speed_1>0){
        speed_1=0;
      }
    }
    if (-Encoder_Climber_2.getPosition()> Lim_S){
      if (speed_2<0){
        speed_2=0;
      }
    }
    if (-Encoder_Climber_2.getPosition()<Lim_I){
      if (speed_2>0){
        speed_2=0;
      }
    }

     

    Motorclimb.set(speed_1);
    Motorclimb_2.set(speed_2);
  }
}
