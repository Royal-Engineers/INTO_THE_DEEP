package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Lift {
    private DcMotor motorUp, motorDown;
    public enum LiftStates {
        INIT,
        LOW_BASKET,
        HIGH_BASKET,
        LOW_CHAMBER,
        HIGH_CHAMBER;
    }
    public LiftStates state, lastState;

    private final int INIT = 0;
    private final int LOW_BASKET = 1000;
    private final int HIGH_BASKET = 2000;
    private final int LOW_CHAMBER = 500;
    private final int HIGH_CHAMBER = 1500;
    private int position;

    public Lift(RobotHardware robot) {
        motorUp = robot.motorLiftUp;
        motorDown = robot.motorLiftDown;

        motorUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorUp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorDown.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDown.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorDown.setDirection(DcMotorSimple.Direction.REVERSE);

        motorUp.setTargetPosition(INIT);
        motorDown.setTargetPosition(INIT);

        motorUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDown.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorUp.setPower(1);
        motorDown.setPower(1);

        state = LiftStates.INIT;
        lastState = LiftStates.INIT;
        position = INIT;
    }

    public void update() {

        if (lastState != state) {
            switch (state) {
                case INIT:
                    motorUp.setTargetPosition(INIT);
                    motorDown.setTargetPosition(INIT);

                    position = INIT;

                    motorUp.setPower(1);
                    motorDown.setPower(1);
                    break;

                case LOW_BASKET:
                    motorUp.setTargetPosition(LOW_BASKET);
                    motorDown.setTargetPosition(LOW_BASKET);

                    position = LOW_BASKET;

                    motorUp.setPower(1);
                    motorDown.setPower(1);
                    break;

                case HIGH_BASKET:
                    motorUp.setTargetPosition(HIGH_BASKET);
                    motorDown.setTargetPosition(HIGH_BASKET);

                    position = HIGH_BASKET;

                    motorUp.setPower(1);
                    motorDown.setPower(1);
                    break;

                case LOW_CHAMBER:
                    motorUp.setTargetPosition(LOW_CHAMBER);
                    motorDown.setTargetPosition(LOW_CHAMBER);

                    position = LOW_CHAMBER;

                    motorUp.setPower(1);
                    motorDown.setPower(1);
                    break;

                case HIGH_CHAMBER:
                    motorUp.setTargetPosition(HIGH_CHAMBER);
                    motorDown.setTargetPosition(HIGH_CHAMBER);

                    position = HIGH_CHAMBER;

                    motorUp.setPower(1);
                    motorDown.setPower(1);
                    break;

            }
        }

        lastState = state;
    }

    public void increasePosition(int value) {
        position += value;

        motorUp.setTargetPosition(position);
        motorDown.setTargetPosition(position);

        motorUp.setPower(1);
        motorDown.setPower(1);
    }

    public void decreasePosition(int value) {
        position -= value;

        motorUp.setTargetPosition(position);
        motorDown.setTargetPosition(position);

        motorUp.setPower(1);
        motorDown.setPower(1);
    }

}
