package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Differential {
    private Servo servoLeft, servoRight, servoClaw;
    private double leftAngle = 0, rightAngle = 0;

    public Differential(RobotHardware robot) {
        servoLeft = robot.servoDifferentialLeft;
        servoRight = robot.servoDifferentialRight;

        servoLeft.setPosition(0);
        servoRight.setPosition(0);
    }

    public void update() {
        servoLeft.setPosition(leftAngle);
        servoRight.setPosition(rightAngle);
    }

    public void updatePosition(double linearAngle, double rotationAngle) {
        leftAngle = linearAngle + rotationAngle;
        rightAngle = linearAngle - rotationAngle;
    }

}
