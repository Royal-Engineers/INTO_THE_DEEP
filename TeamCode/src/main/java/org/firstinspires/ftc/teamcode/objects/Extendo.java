package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Extendo {
    private DcMotor motor;
    public enum ExtendoStates {
        INIT,
        EXTENDED;
    }
    public ExtendoStates state, lastState;

    private final int INIT = 0;
    private final int DEFAULT = 1000;
    private int position = 1000;


    public Extendo(RobotHardware robot) {
        motor = robot.motorExtendo;
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        state = ExtendoStates.INIT;
        lastState = ExtendoStates.INIT;
        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1);
    }

    public void update() {
        if (state != lastState) {
            switch (state) {
                case INIT:
                    motor.setTargetPosition(INIT);
                    motor.setPower(1);

                    break;

                case EXTENDED:
                    position = DEFAULT;
                    motor.setTargetPosition(DEFAULT);
                    motor.setPower(1);

                    break;
            }
        }

        lastState = state;
    }

    public void increasePosition(int value) {
        position += value;

        motor.setTargetPosition(position);
        motor.setPower(1);
    }
    public void decreasePosition(int value) {
        position -= value;

        motor.setTargetPosition(position);
        motor.setPower(1);
    }
}
