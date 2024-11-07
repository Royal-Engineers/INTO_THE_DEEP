package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Differential {
    private Servo leftServo, rightServo;
    private double leftAngle = 0, rightAngle = 0;

    public Differential(RobotHardware robot) {
        leftServo = robot.differentialLeftServo;
        rightServo = robot.differentialRightServo;

        leftServo.setPosition(0);
        rightServo.setPosition(0);
    }

    public void update() {
        leftServo.setPosition(leftAngle);
        rightServo.setPosition(rightAngle);
    }

    public void updatePosition(double linearAngle, double rotationAngle) {
        leftAngle = linearAngle + rotationAngle;
        rightAngle = linearAngle - rotationAngle;
    }

}
