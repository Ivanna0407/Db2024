// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Sub_Climber extends SubsystemBase {
  /** Creates a new Sub_Climber. */
  private final CANSparkMax Motorclimb= new CANSparkMax(6, MotorType.kBrushless);
  private final CANSparkMax Motorclimb_2 = new CANSparkMax(8, MotorType.kBrushless);
  public Sub_Climber() {
     Motorclimb.restoreFactoryDefaults();
    Motorclimb.setIdleMode(IdleMode.kBrake);
    Motorclimb.set(0);
    Motorclimb.setOpenLoopRampRate(0);
    Motorclimb.setInverted(false);

    Motorclimb_2.restoreFactoryDefaults();
    Motorclimb_2.setIdleMode(IdleMode.kBrake);
    Motorclimb_2.set(0);
    Motorclimb_2.setOpenLoopRampRate(0);
    Motorclimb_2.setInverted(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void SetSpeedclimb(double S){
    Motorclimb.set(S);
    Motorclimb_2.set(S);
  }
}
