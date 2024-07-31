package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Sub_Intake_Shooter;
import frc.robot.subsystems.Sub_LEDs;

public class Cmd_DeployIntake extends Command {
  /** Creates a new Cmd_DeployIntake. */
  private final Sub_Intake_Shooter Intake;
  private final Sub_LEDs Leds;
  //private final Sub_LEDs Leds;
  double kp = 0.004;
  double error = 0;
  double setpoint;
  double speed = 0;

  public Cmd_DeployIntake(Sub_Intake_Shooter Intake, Sub_LEDs leds, double setpoint) {
    this.Intake = Intake;
    this.Leds=leds;
    this.setpoint = setpoint;
    //this.Leds=leds;
    //addRequirements(Intake);
  }

  @Override
  public void initialize(){
    error = setpoint-Intake.getArmPosition();
  }

  @Override
  public void execute() {
    error=setpoint-Intake.getArmPosition();
    speed=error*kp;
    Intake.ArmSet(speed);
  }

  @Override
  public void end(boolean interrupted) {
    Intake.ArmSet(0);
  }

  @Override
  public boolean isFinished() {
    if(Math.abs(error) < 5){
      /* 
      if (setpoint==0){
        Leds.setdefault();
      }
      */
      Leds.setdefault();
      Intake.ArmSet(0);
      return true;
    }else{return false;}
  }
}
