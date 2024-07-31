package frc.robot.subsystems.NavX;

interface IBoardCapabilities {
    public boolean isOmniMountSupported();
    public boolean isBoardYawResetSupported();
    public boolean isDisplacementSupported();
    public boolean isAHRSPosTimestampSupported();
}
