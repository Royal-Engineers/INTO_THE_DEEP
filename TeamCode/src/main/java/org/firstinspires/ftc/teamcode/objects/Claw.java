package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Claw {

    Servo servoClawRotation, servoClawWrist;
    CRServo servoActiveIntake;
    public Claw(RobotHardware robot)
    {
        servoClawRotation = robot.servoClawRotation;
        servoClawWrist = robot.servoClawWrist;
        servoActiveIntake = robot.servoActiveIntake;

        servoClawRotation.setPosition(RotationInit);
        servoClawWrist.setPosition(WristInit);
    }

    public enum IntakeState
    {
        Intake,
        Outake,
        OFF;
    }
    IntakeState m_IntakeState = IntakeState.OFF, m_LastIntakeState = IntakeState.OFF;
    private double IntakePower = 0.3, OutakePower = -0.1, IdlePower = 0.0d;
    public void setIntakeState(IntakeState state)
    {
        m_IntakeState = state;
    }

    public enum WristState
    {
        TRANSFER,
        SCAN,
        PICK_UP,
        INIT;
    }
    public double RotationScan = 0.28, RotationPick_Up = 0.57, RotationInit = 0.57, RotationTransfer = 0.57;
    public double WristScan = 0.65, WristPick_Up = 0.65, WristInit = 0.38, WristTransfer = 0.18;
    private double RotationPos = 0.0d, WristPos = 0.0d;
    WristState m_WristState = WristState.INIT, m_LastWristState = WristState.INIT;

    public void setWristState(WristState state)
    {
        m_WristState = state;
    }

    public void Update()
    {
        UpdateIntake();
        UpdateWrist();
    }

    private void UpdateIntake()
    {
        if ( m_IntakeState == m_LastIntakeState)
            return;
        double power = 0.0d;
        switch( m_IntakeState )
        {
            case Intake:
                power = IntakePower;
                break;
            case Outake:
                power = OutakePower;
                break;
            case OFF:
                power = IdlePower;
                break;
        }

        servoActiveIntake.setPower(power);
        m_LastIntakeState = m_IntakeState;
    }

    private void UpdateWrist()
    {
        if ( m_WristState == m_LastWristState )
            return;

        switch(m_WristState )
        {
            case INIT:
                RotationPos = RotationInit;
                WristPos = WristInit;
                break;
            case PICK_UP:
                WristPos = WristPick_Up;
                break;
            case SCAN:
                RotationPos = RotationScan;
                WristPos = WristScan;
                break;

            case TRANSFER:
                RotationPos = RotationTransfer;
                WristPos = WristTransfer;
                break;

        }
        servoClawRotation.setPosition(RotationPos);
        servoClawWrist.setPosition(WristPos);
        m_LastWristState = m_WristState;
    }

    public void updatePosition(double wrist, double rotation) {
        servoClawWrist.setPosition(wrist);
        servoClawRotation.setPosition(rotation);
    }

    public void setClawRotation(double rotation) {
        RotationPos = rotation;
        servoClawRotation.setPosition(rotation);
    }
    public double getClawRotation() {
        return RotationPos;
    }

    public void setIntakePower(double power) {
        servoActiveIntake.setPower(power);
    }

}
