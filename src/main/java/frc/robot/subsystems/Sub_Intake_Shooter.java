// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Sub_Intake_Shooter extends SubsystemBase {
  /** Creates a new Sub_Shooter. */
  private final CANSparkMax Motorshoot= new CANSparkMax(9, MotorType.kBrushless);
  private final CANSparkMax MotorIntake = new CANSparkMax(7, MotorType.kBrushless);
  private final CANSparkMax MotorIntakeArm = new CANSparkMax(5, MotorType.kBrushless);
  private final RelativeEncoder Encoder_1 = MotorIntakeArm.getEncoder();
  DigitalInput limitswitch1 = new DigitalInput(0);
  DigitalInput limitswitch2 = new DigitalInput(1);
  DigitalInput limitswitch3 = new DigitalInput(2);
  public Sub_Intake_Shooter() {

     Motorshoot.restoreFactoryDefaults();
    Motorshoot.setIdleMode(IdleMode.kBrake);
    Motorshoot.set(0);
    Motorshoot.setOpenLoopRampRate(0);
    Motorshoot.setInverted(true);

    MotorIntake.restoreFactoryDefaults();
    MotorIntake.setIdleMode(IdleMode.kCoast);
    MotorIntake.set(0);
    MotorIntake.setOpenLoopRampRate(0);
    
    MotorIntakeArm.restoreFactoryDefaults();
    MotorIntakeArm.setIdleMode(IdleMode.kBrake);
    MotorIntakeArm.set(0);
    MotorIntakeArm.setOpenLoopRampRate(.5);

    Encoder_1.setPosition(0);
  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Encoder", Encoder_1.getPosition());
    SmartDashboard.putNumber("Corriente llantas", MotorIntake.getOutputCurrent());
    SmartDashboard.putNumber("Corriente Shooter", Motorshoot.getOutputCurrent());
    SmartDashboard.putBoolean("Limite piso", limitswitch1.get());
    SmartDashboard.putBoolean("Limite Amp", limitswitch2.get());
    SmartDashboard.putBoolean("Limite Shooter", limitswitch3.get());
  }

  public void SetSpeed(double S){
    Motorshoot.set(S);
  }

  public void IntakeSet(double speed){
    MotorIntake.set(speed);
  }

  public void ArmSet(double speed){
    MotorIntakeArm.set(speed);
  }
  public void Resetencoder(){
    Encoder_1.setPosition(0);
  }

}
