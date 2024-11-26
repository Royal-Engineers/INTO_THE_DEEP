package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Differential {
    private Servo servoLeft, servoRight, servoClaw;

    public enum DifferentialStates {
        INIT,
        PICK_UP,
        RELEASE;
    }
    private double leftAngle = 0, rightAngle = 0, clawPosition = 0;

    public Differential(RobotHardware robot) {
        servoLeft = robot.servoDifferentialLeft;
        servoRight = robot.servoDifferentialRight;
        servoClaw = robot.servoDifferentialClaw;

        servoLeft.setPosition(0);
        servoRight.setPosition(0);
        servoClaw.setPosition(0.5);
    }

    public void update() {
        servoLeft.setPosition(leftAngle);
        servoRight.setPosition(rightAngle);
        servoClaw.setPosition(clawPosition);
    }

    public void updatePosition(double linearAngle, double rotationAngle, double clawPosition) {
        leftAngle = linearAngle + rotationAngle;
        rightAngle = linearAngle - rotationAngle;
        this.clawPosition = clawPosition;
    }

}
