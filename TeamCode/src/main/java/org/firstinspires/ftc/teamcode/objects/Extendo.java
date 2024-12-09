package org.firstinspires.ftc.teamcode.objects;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;
@Config
public class Extendo {
    public DcMotor motor;
    public enum ExtendoStates {
        INIT,
        TRANSFER,
        BASKET_AUTO_LEFT,
        BASKET_AUTO_CENTER,
        BASKET_AUTO_RIGHT,
        EXTENDED;
    }
    private ExtendoStates state, lastState;

    private final int INIT = 0;
    private final int EXTENDED = 280;
    private final int TRANSFER = 50;
    public static int BASKET_AUTO_LEFT = 280;
    public static int BASKET_AUTO_CENTER = 280;
    public static int BASKET_AUTO_RIGHT = 280;

    private double tolerance = 5;


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
                    motor.setPower(0.9);

                    break;

                case BASKET_AUTO_LEFT:
                    motor.setTargetPosition(BASKET_AUTO_LEFT);
                    motor.setPower(1);

                    break;

                case BASKET_AUTO_CENTER:
                    motor.setTargetPosition(BASKET_AUTO_CENTER);
                    motor.setPower(1);

                    break;

                case BASKET_AUTO_RIGHT:
                    motor.setTargetPosition(BASKET_AUTO_RIGHT);
                    motor.setPower(1);

                    break;
            }
        }

        lastState = state;
    }

    public boolean targetReached() {
        return(Math.abs(motor.getCurrentPosition() - motor.getTargetPosition()) < tolerance);
    }

    public void setState(ExtendoStates state) {
        this.state = state;
    }

    public void setPosition(int position) {
        motor.setTargetPosition(position);
        motor.setPower(1);
    }
}
