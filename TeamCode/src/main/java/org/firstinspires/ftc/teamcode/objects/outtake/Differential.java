package org.firstinspires.ftc.teamcode.objects.outtake;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Differential {
    private Servo servoLeft, servoRight, servoClaw;
    public DigitalChannel transferDetection;

    public enum DifferentialStates {
        INIT,
        PICK_UP,
        INTERMEDIATE,
        BASKET,
        FENCE,
        SPECIMEN_RELEASE;
    }

    public DifferentialStates state, lastState;
    public boolean clawState = false; // false means open
    private final double offsetRight = 0.3;

    private double linearAngle, rotationAngle, lastLinearAngle, lastRotationAngle;


    private final double LINEAR_ANGLE_INIT = 0.06;
    private final double LINEAR_ANGLE_INTERMEDIATE = 0.13;
    private final double LINEAR_ANGLE_BASKET = 0.45;
    private final double LINEAR_ANGLE_FENCE = 0.625;

    private final double ROTATION_ANGLE_INIT = 0;
    private final double ROTATION_ANGLE_INTERMEDIATE = 0;
    private final double ROTATION_ANGLE_FORWARD = 0.28;
    private final double ROTATION_ANGLE_REVERSE = -0.28;

    private final double CLAW_OPEN = 0.45;
    private final double CLAW_TRANSFER = 0.6;
    private final double CLAW_CLOSED = 0.8;

    public void setServoPosition(double linearAngle, double rotationAngle) {
        servoLeft.setPosition(linearAngle + rotationAngle);
        servoRight.setPosition(linearAngle - rotationAngle + offsetRight);
    }

    public Differential(RobotHardware robot) {
        servoLeft = robot.servoDifferentialLeft;
        servoRight = robot.servoDifferentialRight;
        servoClaw = robot.servoDifferentialClaw;
        transferDetection = robot.transferDetection;

        transferDetection.setMode(DigitalChannel.Mode.INPUT);

        state = DifferentialStates.INIT;
        lastState = DifferentialStates.INIT;

        linearAngle = LINEAR_ANGLE_INIT; lastLinearAngle  = linearAngle;
        rotationAngle = ROTATION_ANGLE_INIT; lastRotationAngle = rotationAngle;
        setServoPosition(linearAngle, rotationAngle);
        servoClaw.setPosition(CLAW_TRANSFER);
    }

    public void update() {

        if (state != lastState) {
            switch (state) {
                case INIT:
                    linearAngle = LINEAR_ANGLE_INIT;
                    rotationAngle = ROTATION_ANGLE_INIT;
                    servoClaw.setPosition(CLAW_TRANSFER);

                    break;

                case PICK_UP:
                    closeClaw();

                    break;

                case INTERMEDIATE:
                    linearAngle = LINEAR_ANGLE_INTERMEDIATE;
                    rotationAngle = ROTATION_ANGLE_INTERMEDIATE;

                    break;

                case BASKET:
                    linearAngle = LINEAR_ANGLE_BASKET;
                    rotationAngle = ROTATION_ANGLE_FORWARD;

                    break;

                case FENCE:
                    linearAngle = LINEAR_ANGLE_FENCE;
                    rotationAngle = ROTATION_ANGLE_FORWARD;

                    break;

                case SPECIMEN_RELEASE:
                    linearAngle = LINEAR_ANGLE_INIT;
                    rotationAngle = ROTATION_ANGLE_FORWARD;

                    break;
            }
        }

        if (lastLinearAngle != linearAngle || lastRotationAngle != rotationAngle) {
            setServoPosition(linearAngle, rotationAngle);
        }

        lastState = state;

        lastLinearAngle = linearAngle;
        lastRotationAngle = rotationAngle;
    }

    public void setState(DifferentialStates state) {
        this.state = state;
    }

    public void rotateDifferentialRight() {
        rotationAngle = ROTATION_ANGLE_FORWARD;
    }

    public void rotateDifferentialLeft() {
        rotationAngle = ROTATION_ANGLE_REVERSE;
    }

    public void closeClaw() {
        servoClaw.setPosition(CLAW_CLOSED);
        clawState = true;
    }
    public void openClaw() {
        servoClaw.setPosition(CLAW_OPEN);
        clawState = false;
    }

}
