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

    private final int INIT_POSITION = 2;
    private final int ST_STAGE_POSITION = 5350;

    public Climb(RobotHardware robot) {
        this.robot = robot;

        motor = robot.motorWinch;
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1);

        state = ClimbStates.INIT;
        lastState = ClimbStates.INIT;
    }

    public void update() {
        if (state != lastState) {
            switch (state) {
                case INIT:
                    motor.setTargetPosition(INIT_POSITION);
                    motor.setPower(1);

                    break;

                case ST_STAGE:
                    motor.setTargetPosition(ST_STAGE_POSITION);
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
