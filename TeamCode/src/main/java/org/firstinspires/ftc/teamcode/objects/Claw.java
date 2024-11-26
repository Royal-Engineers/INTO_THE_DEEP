package org.firstinspires.ftc.teamcode.objects;

import static com.sun.tools.doclint.Entity.and;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
        servoClawRotation.setPosition(RotationIdle);
        servoClawWrist.setPosition(WristIdle);
    }

    public enum IntakeState
    {
        Intake,
        Outake,
        Idle
    }
    IntakeState m_IntakeState = IntakeState.Idle, m_LastIntakeState = IntakeState.Idle;
    private double IntakePower = 1.0d, OutakePower = -1.0d, IdlePower = 0.0d;
    public  void setIntakeState(IntakeState state)
    {
        m_IntakeState = state;
    }

    public enum WristState
    {
        Transfer,

        Scan,

        Intake,

        Idle
    }
    public double RotationTransfer = 0.0d, RotationScan = 0.29d, RotationIntake = 0.0d, RotationIdle = 0.0d;
    public double WristTransfer = 0.0d, WristScan = 0.78d, WristIntake = 0.0d, WristIdle = 0.0d;
    WristState m_WristState = WristState.Idle, m_LastWristState = WristState.Idle;

    public void setWristState(WristState state)
    {
        m_WristState = state;
    }

    public void Update()
    {
        telemetry.addData("WristPos", servoClawWrist.getPosition());
        telemetry.addData("RotationPos", servoClawRotation.getPosition());
        if ( gamepad.dpad_down && !lastgamepad.dpad_down )
        {
            servoClawWrist.setPosition(servoClawWrist.getPosition() + 0.01);
        }
        if ( gamepad.dpad_up && !lastgamepad.dpad_up )
        {
            servoClawWrist.setPosition(servoClawWrist.getPosition() - 0.01);
        }
        if ( gamepad.dpad_left && !lastgamepad.dpad_left )
        {
            servoClawRotation.setPosition(servoClawRotation.getPosition() + 0.01);
        }
        if ( gamepad.dpad_right && !lastgamepad.dpad_right )
        {
            servoClawRotation.setPosition(servoClawRotation.getPosition() - 0.01);
        }
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
        if ( m_WristState == m_LastWristState )
            return;
        double RotationPos = 0.0d, WristPos = 0.0d;

        switch(m_WristState )
        {
            case Idle:
                RotationPos = RotationIdle;
                WristPos = WristIdle;
                break;
            case Intake:
                RotationPos = RotationIntake;
                WristPos = WristIntake;
                break;
            case Transfer:
                RotationPos = RotationTransfer;
                WristPos = WristTransfer;
                break;
            case Scan:
                RotationPos = RotationScan;
                WristPos = WristScan;
                break;
        }
        servoClawRotation.setPosition(RotationPos);
        servoClawWrist.setPosition(WristPos);
        m_LastWristState = m_WristState;
    }

}
