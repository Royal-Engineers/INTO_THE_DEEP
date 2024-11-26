package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Claw {

    Servo servoClawRotation,
    servoClawWrist;
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
        Idle
    }
    IntakeState m_IntakeState = IntakeState.Idle, m_LastIntakeState = IntakeState.Idle;
    private double IntakePower = 1.0d, OutakePower = -1.0d, IdlePower = 0.0d;
    public void setIntakeState(IntakeState state)
    {
        m_IntakeState = state;
    }

    public enum WristState
    {
        SCAN,
        INTAKE,
        INIT;
    }
    public double RotationScan = 0.29d, RotationIntake = 0.0d, RotationInit = 0.0d;
    public double WristScan = 0.78d, WristIntake = 0.0d, WristInit = 0d;
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
            case Idle:
                power = IdlePower;
                break;
        }

        servoActiveIntake.setPower(power);
        m_LastIntakeState = m_IntakeState;
    }

    private void UpdateWrist()
    {
        /*if ( m_WristState == m_LastWristState )
            return;
        double RotationPos = 0.0d, WristPos = 0.0d;

        switch(m_WristState )
        {
            case INIT:
                RotationPos = RotationIdle;
                WristPos = WristIdle;
                break;
            case INTAKE:
                RotationPos = RotationIntake;
                WristPos = WristIntake;
                break;
            case SCAN:
                RotationPos = RotationScan;
                WristPos = WristScan;
                break;
        }
        servoClawRotation.setPosition(RotationPos);
        servoClawWrist.setPosition(WristPos);
        m_LastWristState = m_WristState;*/
    }

    public void updatePosition(double wrist, double rotation) {
        servoClawWrist.setPosition(wrist);
        servoClawRotation.setPosition(rotation);
    }

}
