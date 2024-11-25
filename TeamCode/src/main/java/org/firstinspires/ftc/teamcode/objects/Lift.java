package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Lift {
    private DcMotor motor1, motor2;
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
        motor1 = robot.motorLift1;
        motor2 = robot.motorLift2;

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        motor1.setTargetPosition(INIT);
        motor2.setTargetPosition(INIT);

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor1.setPower(1);
        motor2.setPower(1);

        state = LiftStates.INIT;
        lastState = LiftStates.INIT;
        position = INIT;
    }

    public void update() {

        if (lastState != state) {
            switch (state) {
                case INIT:
                    motor1.setTargetPosition(INIT);
                    motor2.setTargetPosition(INIT);

                    position = INIT;

                    motor1.setPower(1);
                    motor2.setPower(1);
                    break;

                case LOW_BASKET:
                    motor1.setTargetPosition(LOW_BASKET);
                    motor2.setTargetPosition(LOW_BASKET);

                    position = LOW_BASKET;

                    motor1.setPower(1);
                    motor2.setPower(1);
                    break;

                case HIGH_BASKET:
                    motor1.setTargetPosition(HIGH_BASKET);
                    motor2.setTargetPosition(HIGH_BASKET);

                    position = HIGH_BASKET;

                    motor1.setPower(1);
                    motor2.setPower(1);
                    break;

                case LOW_CHAMBER:
                    motor1.setTargetPosition(LOW_CHAMBER);
                    motor2.setTargetPosition(LOW_CHAMBER);

                    position = LOW_CHAMBER;

                    motor1.setPower(1);
                    motor2.setPower(1);
                    break;

                case HIGH_CHAMBER:
                    motor1.setTargetPosition(HIGH_CHAMBER);
                    motor2.setTargetPosition(HIGH_CHAMBER);

                    position = HIGH_CHAMBER;

                    motor1.setPower(1);
                    motor2.setPower(1);
                    break;

            }
        }

        lastState = state;
    }

    public void increasePosition(int value) {
        position += value;

        motor1.setTargetPosition(position);
        motor2.setTargetPosition(position);

        motor1.setPower(1);
        motor2.setPower(1);
    }

    public void decreasePosition(int value) {
        position -= value;

        motor1.setTargetPosition(position);
        motor2.setTargetPosition(position);

        motor1.setPower(1);
        motor2.setPower(1);
    }

}
