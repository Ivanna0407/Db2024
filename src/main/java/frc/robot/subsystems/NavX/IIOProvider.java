package frc.robot.subsystems.NavX;

interface IIOProvider {
    public boolean  isConnected();
    public double   getByteCount();
    public double   getUpdateCount();
    public void     setUpdateRateHz(byte update_rate);
    public void     zeroYaw();
    public void     zeroDisplacement();
    public void     run();
    public void     stop();
    public void		enableLogging(boolean enable);
}
