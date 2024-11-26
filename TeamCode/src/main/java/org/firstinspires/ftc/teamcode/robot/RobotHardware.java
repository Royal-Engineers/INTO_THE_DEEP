package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.hardwareMap;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad2;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.m_gamepad2;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

public class RobotHardware {
    List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

    public DcMotor motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft;
    public DcMotor motorExtendo, motorLiftLeft, motorLiftRight;
    public DcMotor motorWinch;

    public Servo servoDifferentialLeft, servoDifferentialRight, servoDifferentialClaw;
    public Servo servoFirstClimbLeft, servoFirstClimbRight, servoSecondClimbLeft, servoSecondClimbRight;
    public Servo servoV4BLeft, servoV4BRight, servoClawRotation, servoClawWrist;
    public CRServo servoActiveIntake;



    public void init() {
        // HUBS
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        // WINCH
        motorWinch = hardwareMap.get(DcMotor.class, "motorWinch");

        // CHASSIS
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");

        // INTAKE
        motorExtendo = hardwareMap.get(DcMotor.class, "motorExtendo");

        servoV4BLeft = hardwareMap.get(Servo.class, "servoV4BLeft");
        servoV4BRight = hardwareMap.get(Servo.class, "servoV4BRight");
        servoClawRotation = hardwareMap.get(Servo.class, "servoClawRotation");
        servoClawWrist = hardwareMap.get(Servo.class, "servoClawWrist");
        servoActiveIntake = hardwareMap.get(CRServo.class, "servoActiveIntake");

        // OUTTAKE
        motorLiftLeft = hardwareMap.get(DcMotor.class, "motorLiftLeft");
        motorLiftRight = hardwareMap.get(DcMotor.class, "motorLiftRight");

        // DIFFERENTIAL
        servoDifferentialLeft = hardwareMap.get(Servo.class, "servoDifferentialLeft");
        servoDifferentialRight = hardwareMap.get(Servo.class, "servoDifferentialRight");
        servoDifferentialClaw = hardwareMap.get(Servo.class, "servoDifferentialClaw");

        // CLIMB
        servoFirstClimbLeft = hardwareMap.get(Servo.class, "servoFirstClimbLeft");
        servoFirstClimbRight = hardwareMap.get(Servo.class, "servoFirstClimbRight");
        servoSecondClimbLeft = hardwareMap.get(Servo.class, "servoSecondClimbLeft");
        servoSecondClimbRight = hardwareMap.get(Servo.class, "servoSecondClimbRight");

    }

    public void update() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }

        lastgamepad.copy(gamepad);
        lastgamepad2.copy(m_gamepad2);
    }

}
