package org.firstinspires.ftc.teamcode.objects.intake;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;


public class Virtual4Bar {
    private Servo servoV4BLeft , servoV4BRight;


    public Virtual4Bar(RobotHardware robot)
    {
        servoV4BLeft = robot.servoV4BLeft;
        servoV4BRight = robot.servoV4BRight;

        servoV4BLeft.setPosition(InitPos);
        servoV4BRight.setPosition(InitPos);
    }
    public enum V4BStates
    {
        INIT,
        TRANSFER,
        SCANNING,
        PICK_UP,
    }
    private V4BStates state = V4BStates.INIT, lastState = V4BStates.INIT;

    public double InitPos = 0.05, ScanningPos = 0.17, PickupPos = 0.24, TransferPos = 0.02;

    public void setV4BPos(double pos)
    {
        servoV4BLeft.setPosition(pos);
        servoV4BRight.setPosition(pos);
    }

    public double getV4BPos()
    {
        return servoV4BLeft.getPosition();
    }

    public void update()
    {
        if ( state == lastState )
            return;

        double targetPosition = InitPos;
        switch (state)
        {
            case INIT:
                targetPosition = InitPos;
                break;
            case PICK_UP:
                targetPosition = PickupPos;
                break;
            case SCANNING:
                targetPosition = ScanningPos;
                break;
            case TRANSFER:
                targetPosition = TransferPos;
                break;
        }
        setV4BPos(targetPosition);
        lastState = state;
    }

    public void setState(V4BStates state) {
        this.state = state;
    }
}
