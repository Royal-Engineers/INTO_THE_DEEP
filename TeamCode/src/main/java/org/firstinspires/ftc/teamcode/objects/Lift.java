package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Lift {
    private DcMotor motorLeft, motorRight;
    public enum LiftStates {
        INIT,
        LOW_BASKET,
        HIGH_BASKET,
        LOW_CHAMBER,
        HIGH_CHAMBER,
    }
    public LiftStates state, lastState;

    private final int INIT = 0;
    private final int LOW_BASKET = 1000;
    private final int HIGH_BASKET = 2000;
    private final int LOW_CHAMBER = 500;
    private final int HIGH_CHAMBER = 1500;
    private int position;

    public Lift(RobotHardware robot) {
        motorLeft = robot.motorLiftLeft;
        motorRight = robot.motorLiftRight;

        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);

        motorLeft.setTargetPosition(INIT);
        motorRight.setTargetPosition(INIT);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorLeft.setPower(1);
        motorRight.setPower(1);

        state = LiftStates.INIT;
        lastState = LiftStates.INIT;
        position = INIT;
    }

    public void update() {

        if (lastState != state) {
            switch (state) {
                case INIT:
                    motorLeft.setTargetPosition(INIT);
                    motorRight.setTargetPosition(INIT);

                    position = INIT;

                    motorLeft.setPower(1);
                    motorRight.setPower(1);
                    break;

                case LOW_BASKET:
                    motorLeft.setTargetPosition(LOW_BASKET);
                    motorRight.setTargetPosition(LOW_BASKET);

                    position = LOW_BASKET;

                    motorLeft.setPower(1);
                    motorRight.setPower(1);
                    break;

                case HIGH_BASKET:
                    motorLeft.setTargetPosition(HIGH_BASKET);
                    motorRight.setTargetPosition(HIGH_BASKET);

                    position = HIGH_BASKET;

                    motorLeft.setPower(1);
                    motorRight.setPower(1);
                    break;

                case LOW_CHAMBER:
                    motorLeft.setTargetPosition(LOW_CHAMBER);
                    motorRight.setTargetPosition(LOW_CHAMBER);

                    position = LOW_CHAMBER;

                    motorLeft.setPower(1);
                    motorRight.setPower(1);
                    break;

                case HIGH_CHAMBER:
                    motorLeft.setTargetPosition(HIGH_CHAMBER);
                    motorRight.setTargetPosition(HIGH_CHAMBER);

                    position = HIGH_CHAMBER;

                    motorLeft.setPower(1);
                    motorRight.setPower(1);
                    break;

            }
        }

        lastState = state;
    }

    public void increasePosition(int value) {
        position += value;

        motorLeft.setTargetPosition(position);
        motorRight.setTargetPosition(position);

        motorLeft.setPower(1);
        motorRight.setPower(1);
    }

    public void decreasePosition(int value) {
        position -= value;

        motorLeft.setTargetPosition(position);
        motorRight.setTargetPosition(position);

        motorLeft.setPower(1);
        motorRight.setPower(1);
    }

}
