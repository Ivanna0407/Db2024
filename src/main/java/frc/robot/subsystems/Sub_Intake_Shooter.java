// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cameraserver.CameraServer;

public class Sub_Intake_Shooter extends SubsystemBase {
  /** Creates a new Sub_Shooter. */
  private final CANSparkMax Motorshoot= new CANSparkMax(6, MotorType.kBrushless);
  private final CANSparkMax MotorIntake = new CANSparkMax(7, MotorType.kBrushless);
  private final CANSparkMax MotorIntakeArm = new CANSparkMax(5, MotorType.kBrushless);
  private final RelativeEncoder Encoder_1 = MotorIntakeArm.getEncoder();
  private final RelativeEncoder EncoderIntake_Llantas =MotorIntake.getEncoder();

  public boolean piesa = false;
  public double setpoint;
  public double error;


  public Sub_Intake_Shooter() {
    CameraServer.startAutomaticCapture("Duck vision", 0);

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
    Encoder_1.setPositionConversionFactor(360/49);

    EncoderIntake_Llantas.setPosition(0);
    EncoderIntake_Llantas.getInverted();
  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Velocidad llantas", MotorIntake.getOutputCurrent());
    SmartDashboard.putNumber("Encoder", Encoder_1.getPosition());
    SmartDashboard.putNumber("Llantas", EncoderIntake_Llantas.getPosition());
    SmartDashboard.putBoolean("LIMIT PIEZA", piesa);
    SmartDashboard.putNumber("POSICION BRAZO", getArmPosition());
    SmartDashboard.putNumber("Setpoint", getSetpoint());
    if (MotorIntake.getOutputCurrent()>=70){piesa=true;}
  }

  public void SetSpeed(double S){Motorshoot.set(S);}


  public double getSetpoint(){
    return setpoint;
  }

  public void setSetpoint(double Set){
    setpoint=Set;
  }




  public void IntakeSet(double speed){if (piesa==true && speed>0){piesa=false;}MotorIntake.set(speed);}

  public void ArmSet(double speed){
    if (Math.abs(speed)>.95){speed=(Math.abs(speed)/speed)*.95;}
    if(Encoder_1.getPosition()>175 ){if(speed>0){speed=0;}}
    if(Encoder_1.getPosition()<10 ){if (speed<0){speed=0;}}
    MotorIntakeArm.set(speed);
  }
  public void Resetencoder(){Encoder_1.setPosition(0);}
  public double getArmPosition(){return Encoder_1.getPosition();}

  public double getIntakeCurrent(){
    return MotorIntake.getOutputCurrent();
  }

public double getIntakeEncoder(){
  return EncoderIntake_Llantas.getPosition();
}

public void resetEncoderLlantas(){
  EncoderIntake_Llantas.setPosition(0);
}

public void ReloadNote(){
  resetEncoderLlantas();
  double error_l =-2- getIntakeEncoder();
  double speed = 0;
  while (Math.abs(error_l)>0.25){
    error_l=-2-getIntakeEncoder();
    speed =error_l*0.4;
    IntakeSet(speed);
  }
  System.out.println("END ADJUST");
  IntakeSet(0);

}


  public void goHome(){
    System.out.println("GOING HOME");
    resetEncoderLlantas();
    double error= 0 - getArmPosition();
    while(Math.abs(error)>=5){
      error= 0 - getArmPosition();
      double speed=error*0.004;
      if(Math.abs(error) <=7){speed = 0;}
      ArmSet(speed);
    }  
  }
  

  public void DeployIntake(){
    double error= 180 - getArmPosition();
    while(Math.abs(error)>=5){
      error= 180 - getArmPosition();
      double speed=error*0.004;
      if(Math.abs(error) <=7){speed = 0;}
      ArmSet(speed);
    }
  }

  public double MotorShootergetspeed(){
    return Motorshoot.get();
  }

  public double MotorIntakegetspeed(){
    return MotorIntake.get();
  }

  public void SetIntakeS(double s){
    MotorIntake.setOpenLoopRampRate(s);
  }

  public void SetEncoderArmIntake(double encoder){
    Encoder_1.setPosition(encoder);
  }
  
}
