package org.firstinspires.ftc.teamcode.objects;

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
        RELEASE;
    }

    public DifferentialStates state, lastState;
    public boolean clawState = false; // false means open
    private double leftAngle = 0, rightAngle = 0, clawPosition = 0;

    private final double LINEAR_ANGLE_INIT = 0.03;
    private final double LINEAR_ANGLE_INTERMEDIATE = 0.53;
    private final double LINEAR_ANGLE_RELEASE = 0.53;

    private final double ROTATION_ANGLE_INIT = 0;
    private final double ROTATION_ANGLE_INTERMEDIATE = 0;
    private final double ROTATION_ANGLE_RELEASE = 0.28;

    private final double CLAW_OPEN = 0.55;
    private final double CLAW_CLOSED = 0.8;

    public void setServoPosition(double linearAngle, double rotationAngle) {
        servoLeft.setPosition(linearAngle + rotationAngle);
        servoRight.setPosition(linearAngle - rotationAngle);
    }

    public Differential(RobotHardware robot) {
        servoLeft = robot.servoDifferentialLeft;
        servoRight = robot.servoDifferentialRight;
        servoClaw = robot.servoDifferentialClaw;
        transferDetection = robot.transferDetection;

        transferDetection.setMode(DigitalChannel.Mode.INPUT);

        state = DifferentialStates.INIT;
        lastState = DifferentialStates.INIT;

        setServoPosition(LINEAR_ANGLE_INIT, ROTATION_ANGLE_INIT);
        servoClaw.setPosition(CLAW_OPEN);
    }

    public void update() {

        if (state != lastState) {
            switch (state) {
                case INIT:
                    setServoPosition(LINEAR_ANGLE_INIT, ROTATION_ANGLE_INIT);
                    openClaw();

                    break;

                case PICK_UP:
                    closeClaw();

                    break;

                case INTERMEDIATE:
                    setServoPosition(LINEAR_ANGLE_INTERMEDIATE, ROTATION_ANGLE_INTERMEDIATE);

                    break;

                case RELEASE:
                    setServoPosition(LINEAR_ANGLE_RELEASE, ROTATION_ANGLE_RELEASE);

                    break;
            }
        }

        lastState = state;
    }

    public void setState(DifferentialStates state) {
        this.state = state;
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
