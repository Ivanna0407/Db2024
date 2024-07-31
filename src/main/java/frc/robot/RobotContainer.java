// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Cmd_AutoShoot;
import frc.robot.commands.Cmd_Auto_Move_Intake;
import frc.robot.commands.Cmd_Auto_Move_Shooter;
import frc.robot.commands.Cmd_Climb;
import frc.robot.commands.Cmd_DeployIntake;
import frc.robot.commands.Cmd_Move;
import frc.robot.commands.Cmd_PID;
import frc.robot.commands.Cmd_Reload;
import frc.robot.commands.Cmd_Rev_Shooter;
import frc.robot.commands.Cmd_Take;
import frc.robot.commands.Cmd_Vision;
import frc.robot.commands.Cmd_reset;
import frc.robot.commands.Cmd_wait;
import frc.robot.subsystems.Sub_Chasis;
import frc.robot.subsystems.Sub_Climber;
import frc.robot.subsystems.Sub_Intake_Shooter;
import frc.robot.subsystems.Sub_LEDs;

import org.opencv.imgproc.Subdiv2D;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  Sub_Chasis Chasis=new Sub_Chasis();
  Sub_Climber Climber=new Sub_Climber();
  Sub_Intake_Shooter Intake_Shooter = new Sub_Intake_Shooter();
  Sub_LEDs LEDs=new Sub_LEDs();

  CommandXboxController Joy_drive= new CommandXboxController(0);
  CommandXboxController Sub_drive=new CommandXboxController(1);


  SendableChooser <Command> Autochooser = new SendableChooser<>();

  public RobotContainer() {
    Autochooser.setDefaultOption("Lado",new SequentialCommandGroup(
      new Cmd_Auto_Move_Shooter(Intake_Shooter, 1),
      new Cmd_wait(1),
      new Cmd_Auto_Move_Intake(Intake_Shooter,LEDs, .6),
      new Cmd_wait(0.5),
      new Cmd_Auto_Move_Shooter(Intake_Shooter, 0),
      new Cmd_Auto_Move_Intake(Intake_Shooter,LEDs, 0),
      new Cmd_PID(Chasis, -10)) );

    Autochooser.addOption("Medio", new SequentialCommandGroup(
      new Cmd_Auto_Move_Shooter(Intake_Shooter, 1),
      new Cmd_wait(1),
      new Cmd_Auto_Move_Intake(Intake_Shooter,LEDs, .6),
      new Cmd_wait(0.5),
      new Cmd_Auto_Move_Shooter(Intake_Shooter, 0),
      new Cmd_Auto_Move_Intake(Intake_Shooter,LEDs, 0),
      new Cmd_DeployIntake(Intake_Shooter,LEDs, 180),
      new ParallelCommandGroup(new Cmd_PID(Chasis, -55),new Cmd_Take(Intake_Shooter, LEDs)),
      new ParallelCommandGroup(new Cmd_Auto_Move_Shooter(Intake_Shooter, 1),new Cmd_Vision(Chasis,LEDs),new Cmd_DeployIntake(Intake_Shooter,LEDs, 0)),
      new Cmd_Reload(Intake_Shooter),
      new Cmd_Auto_Move_Intake(Intake_Shooter,LEDs, .6),
      new Cmd_wait(1), 
      new Cmd_Auto_Move_Intake(Intake_Shooter,LEDs,0),
      new Cmd_Auto_Move_Shooter(Intake_Shooter, 0)
    ));
    Autochooser.addOption("Do Nothing", getAutonomousCommand());
    SmartDashboard.putData(Autochooser);

    Chasis.setDefaultCommand(new Cmd_Move(Chasis, () -> Joy_drive.getRightTriggerAxis(),() -> Joy_drive.getLeftTriggerAxis(), () -> Joy_drive.getLeftX(), () -> Joy_drive.b().getAsBoolean(),() -> Joy_drive.x().getAsBoolean()));
    Intake_Shooter.setDefaultCommand(new Cmd_Rev_Shooter(Intake_Shooter,LEDs, () -> Sub_drive.getRightTriggerAxis(),() -> Sub_drive.a().getAsBoolean(),() -> Sub_drive.x().getAsBoolean()));
    Climber.setDefaultCommand(new Cmd_Climb(Climber,() -> Sub_drive.getRightY(),() -> Sub_drive.getLeftY()));
    configureBindings();
  }

  private void configureBindings() {
     Sub_drive.y().whileTrue(new SequentialCommandGroup(new Cmd_Auto_Move_Shooter(Intake_Shooter, 1),new Cmd_wait(1.2),new Cmd_Auto_Move_Intake(Intake_Shooter,LEDs, .6),new Cmd_wait(.5), new Cmd_Auto_Move_Intake(Intake_Shooter,LEDs,0),new Cmd_Auto_Move_Shooter(Intake_Shooter, 0)));
     Sub_drive.b().whileTrue(new SequentialCommandGroup(new Cmd_DeployIntake(Intake_Shooter,LEDs, 180), new Cmd_Take(Intake_Shooter,LEDs), new Cmd_DeployIntake(Intake_Shooter,LEDs, 0), new Cmd_Reload(Intake_Shooter)));
     Joy_drive.start().whileTrue(new Cmd_reset(Intake_Shooter, 0));
     Joy_drive.back().whileTrue(new Cmd_reset(Intake_Shooter, 180));
     Joy_drive.y().whileTrue(new Cmd_Vision(Chasis,LEDs));
     Sub_drive.rightBumper().whileTrue(new Cmd_DeployIntake(Intake_Shooter,LEDs, 180));
     Sub_drive.leftBumper().whileTrue(new Cmd_DeployIntake(Intake_Shooter,LEDs, 0));
  }
  
  public Command getAutonomousCommand() {
    return Autochooser.getSelected();

  }
}
