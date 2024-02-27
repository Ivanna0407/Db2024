// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Cmd_Climb;
import frc.robot.commands.Cmd_Move;
import frc.robot.commands.Cmd_Shoot_Amp;
import frc.robot.commands.Cmd_Shoot_Shooter;
import frc.robot.commands.Cmd_Shoot_Speaker;
import frc.robot.subsystems.Sub_Chasis;
import frc.robot.subsystems.Sub_Climber;
import frc.robot.subsystems.Sub_Intake_Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
   Sub_Chasis Chasis=new Sub_Chasis();
   Sub_Climber Climber=new Sub_Climber();
   Sub_Intake_Shooter Intake_Shooter = new Sub_Intake_Shooter();
    // Replace with CommandPS4Controller or CommandJoystick if needed
      CommandXboxController Joy_drive= new CommandXboxController(0);
      CommandXboxController Sub_drive=new CommandXboxController(1);

  public RobotContainer() {
    // Configure the trigger bindings
    Chasis.setDefaultCommand(new Cmd_Move(Chasis, () -> Joy_drive.getRightTriggerAxis(),() -> Joy_drive.getLeftTriggerAxis(), () -> Joy_drive.getLeftX(), () -> Joy_drive.b().getAsBoolean()));
    configureBindings();
    Intake_Shooter.setDefaultCommand(new Cmd_Shoot_Shooter(Intake_Shooter, () -> Sub_drive.a().getAsBoolean(), () -> Sub_drive.b().getAsBoolean(), () -> Sub_drive.x().getAsBoolean(),() -> Sub_drive.leftBumper().getAsBoolean(), ()-> Sub_drive.rightBumper().getAsBoolean()));
    Climber.setDefaultCommand(new Cmd_Climb(Climber,() -> Sub_drive.getRightY()));
  }

  private void configureBindings() {
     // Sub_drive.b().whileTrue(new Cmd_Shoot_Speaker(Intake_Shooter, 0));
      //Sub_drive.x().whileTrue(new Cmd_Shoot_Speaker(Intake_Shooter, 1));
      Sub_drive.y().whileTrue(new Cmd_Shoot_Amp(Intake_Shooter));
      
  }

  
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
