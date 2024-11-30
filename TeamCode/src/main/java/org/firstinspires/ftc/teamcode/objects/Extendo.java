package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Extendo {
    public DcMotor motor;
    public enum ExtendoStates {
        INIT,
        TRANSFER,
        EXTENDED;
    }
    private ExtendoStates state, lastState;

    private final int INIT = 0;
    private final int EXTENDED = 240;
    private final int TRANSFER = 50;


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
                    motor.setTargetPosition(EXTENDED);
                    motor.setPower(0.8);

                    break;

                case TRANSFER:
                    motor.setTargetPosition(TRANSFER);
                    motor.setPower(0.5);
            }
        }

        lastState = state;
    }

    public void setState(ExtendoStates state) {
        this.state = state;
    }
}
