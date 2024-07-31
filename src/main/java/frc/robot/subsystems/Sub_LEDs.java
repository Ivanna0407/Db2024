// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.PWM;

public class Sub_LEDs extends SubsystemBase {
  /** Creates a new Sub_LEDs. */
  private int m_handle;
  private int m_handle2;

  public Sub_LEDs() {
      m_handle = DIOJNI.initializeDIOPort(HAL.getPort((byte) 0), false);
      m_handle2 = DIOJNI.initializeDIOPort(HAL.getPort((byte) 1), false);
       DIOJNI.setDIO(m_handle, true);
      DIOJNI.setDIO(m_handle2, true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    

    
    
  }

  public void setdefault(){
    DIOJNI.setDIO(m_handle, false);
    DIOJNI.setDIO(m_handle2, false);
  }

  public void setwaiting(){
    DIOJNI.setDIO(m_handle, false);
    DIOJNI.setDIO(m_handle2, true);
  }

  public void setconfirm(){
    DIOJNI.setDIO(m_handle, true);
    DIOJNI.setDIO(m_handle2, false);
  }

  public void setfade(){
    DIOJNI.setDIO(m_handle, true);
    DIOJNI.setDIO(m_handle2, true);
  }
}
