package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.NavX.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.networktables.NetworkTableInstance;

//import com.ctre.phoenix;
//Librerias de Phoenix para el movimiento de los motores TALON
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;

public class Sub_Chasis extends SubsystemBase {
  private final double Kfactor = (6*Math.PI/(9.52));

  private final TalonFX m_leftLeader = new TalonFX(9);
  private final TalonFX m_leftFollower = new TalonFX(10);
  private final TalonFX m_rightLeader = new TalonFX(11);
  private final TalonFX m_rightFollower = new TalonFX(12);



  // GYRO
  AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte) 66);

  public double DifYaw,PartialYaw;


  // VISION
  public double Tx, Ty, Ta;


  public Sub_Chasis() {
    // Aplicamos Factory defaults 
    var factorydefaults = new MotorOutputConfigs();

    m_leftLeader.getConfigurator().apply(factorydefaults);
    m_leftFollower.getConfigurator().apply(factorydefaults);
    m_rightLeader.getConfigurator().apply(factorydefaults);
    m_rightFollower.getConfigurator().apply(factorydefaults);

    //Aplicar el modo IDLE en este caso brake
    m_leftLeader.setNeutralMode(NeutralModeValue.Brake);
    m_rightLeader.setNeutralMode(NeutralModeValue.Brake);

    m_rightLeader.setPosition(0);
    m_leftLeader.setPosition(0);
    PartialYaw=0;
  


  }

  @Override
  public void periodic() {
   
  }

  public void OpenLoopS(double S) {
    var td = new OpenLoopRampsConfigs();
    td.DutyCycleOpenLoopRampPeriod = S;
    m_leftLeader.getConfigurator().apply(td);
    m_leftFollower.getConfigurator().apply(td);
    m_rightLeader.getConfigurator().apply(td);
    m_rightFollower.getConfigurator().apply(td);
  }

  ////////////////////////ENCODERS////////////////////////

  public void resetPosition() {
    m_leftLeader.setPosition(0);
    m_rightLeader.setPosition(0);
  }

  public double getRightVelocity(){
    return m_rightLeader.getVelocity().getValueAsDouble()  * Kfactor;
  }

   public double getLeftVelocity(){
    return m_leftLeader.getVelocity().getValueAsDouble()  * Kfactor;
  }

  public double getLeftPosition() {
    return (-m_leftLeader.getPosition().getValueAsDouble()) * Kfactor;
  }

  public double getRightPosition() {
    return (m_rightLeader.getPosition().getValueAsDouble())  * Kfactor;
  }

  public double getpromencoders() {
    return ((getRightPosition() + getLeftPosition()) / 2);
  }

  public void setSpeed(double RightSpeed, double LeftSpeed) {
    if (Math.abs(LeftSpeed) >= 0.8) {
      LeftSpeed = (LeftSpeed / Math.abs(LeftSpeed)) * 0.8;
    }
    if (Math.abs(RightSpeed) >= 0.8) {
      RightSpeed = (RightSpeed / Math.abs(RightSpeed)) * 0.8;
    }
    m_leftFollower.set(LeftSpeed);
    m_leftLeader.set(LeftSpeed);
    m_rightFollower.set(-RightSpeed);
     m_rightLeader.set(-RightSpeed);
  }

  ////////////////////////LIMELIGHT////////////////////////
  public double getTx() {
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("tx").getDouble(0);
  }

  public double getTy() {
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("ty").getDouble(0);
  }

  public double getTa() {
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("ta").getDouble(10);
  }

  public double getTid() {
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("tid").getDouble(0);
  }

  public void SetVisionMode(Double m) {
    NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("pipeline").setNumber(m);
  }


  public void resetYaw() {
    PartialYaw = ahrs.getYaw();
  }

  public double getTotalYaw() {
    return ahrs.getYaw();
  }

  public double getYaw() {
    DifYaw = (ahrs.getYaw()) - PartialYaw;
    return DifYaw;
  }



}
