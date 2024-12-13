package org.firstinspires.ftc.teamcode.objects.outtake;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;
@Config
public class Lift {
    public DcMotor motorUp, motorDown;
    public enum LiftStates {
        RANDOM,
        INIT,
        LOW_BASKET,
        HIGH_BASKET,
        LOW_CHAMBER,
        HIGH_CHAMBER,
        HIGH_CHAMBER_RELEASE,
        LOW_CHAMBER_RELEASE;
    }
    private LiftStates state, lastState;

    private final int INIT = 0;
    private final int LOW_BASKET = 300;
    private final int HIGH_BASKET = 680;
    private final int LOW_CHAMBER = 35;
    private final int LOW_CHAMBER_RELEASE = 100;
    public static int HIGH_CHAMBER = 250;
    public static int HIGH_CHAMBER_RELEASE = 490;
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

        if (state == LiftStates.INIT && motorUp.getCurrentPosition() < 10) {
            motorUp.setPower(0);
            motorDown.setPower(0);
        }

        if (lastState != state) {
            switch (state) {
                case INIT:
                    motorUp.setTargetPosition(INIT);
                    motorDown.setTargetPosition(INIT);

                    position = INIT;

                    motorUp.setPower(0.6);
                    motorDown.setPower(0.6);
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

                case LOW_CHAMBER_RELEASE:
                    motorUp.setTargetPosition(LOW_CHAMBER_RELEASE);
                    motorDown.setTargetPosition(LOW_CHAMBER_RELEASE);

                    position = LOW_CHAMBER_RELEASE;

                    motorUp.setPower(1);
                    motorDown.setPower(1);
                    break;

                case HIGH_CHAMBER_RELEASE:
                    motorUp.setTargetPosition(HIGH_CHAMBER_RELEASE);
                    motorDown.setTargetPosition(HIGH_CHAMBER_RELEASE);

                    position = HIGH_CHAMBER_RELEASE;

                    motorUp.setPower(1);
                    motorDown.setPower(1);
                    break;

            }
        }

        telemetry.addData("Pozitie lift", motorDown.getCurrentPosition());

        lastState = state;
    }

    public void increasePosition(int value) {
        position += value;

        state = LiftStates.RANDOM;

        motorUp.setTargetPosition(position);
        motorDown.setTargetPosition(position);

        motorUp.setPower(1);
        motorDown.setPower(1);
    }

    public void decreasePosition(int value) {
        position -= value;

        state = LiftStates.RANDOM;

        motorUp.setTargetPosition(position);
        motorDown.setTargetPosition(position);

        motorUp.setPower(1);
        motorDown.setPower(1);
    }

    public int getPosition() {
        return position;
    }

    public void setState(LiftStates state) {
        this.state = state;
    }

    public LiftStates getState() {
        return state;
    }

    public boolean targetReached() {
        return (Math.abs(motorUp.getCurrentPosition() - motorUp.getTargetPosition()) < 15);
    }

}
