package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Climb {
    private RobotHardware robot;
    private DcMotor motor;

    public enum ClimbStates {
        INIT,
        ST_STAGE;
    }
    private ClimbStates state, lastState;

    private final int INIT = 5;
    private final int ST_STAGE = 5350;

    public Climb(RobotHardware robot) {
        this.robot = robot;

        motor = robot.motorWinch;
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor.setTargetPosition(INIT);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0);

        state = ClimbStates.INIT;
        lastState = ClimbStates.INIT;
    }

    public void update() {
        if (state != lastState) {
            switch (state) {
                case INIT:
                    motor.setTargetPosition(INIT);
                    motor.setPower(1);

                    break;

                case ST_STAGE:
                    motor.setTargetPosition(ST_STAGE);
                    motor.setPower(1);

                    break;
            }
        }

        lastState = state;
    }

    public void setState(ClimbStates state) {
        this.state = state;
    }
}
