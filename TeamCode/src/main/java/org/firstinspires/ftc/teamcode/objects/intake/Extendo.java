package org.firstinspires.ftc.teamcode.objects.intake;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.MotionProfileDecceleration;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
@Config
public class Extendo {
    public DcMotor motor;
    public enum ExtendoStates {
        INIT,
        TRANSFER,
        TRANSFER_INTERMEDIATE,
        BASKET_AUTO_LEFT,
        BASKET_AUTO_CENTER,
        BASKET_AUTO_RIGHT,
        EXTENDED;
    }
    private ExtendoStates state, lastState;

    private final int INIT = 0;
    private final int EXTENDED = 280;
    public static int TRANSFER = 50;
    public static int BASKET_AUTO_LEFT = 280;
    public static int BASKET_AUTO_CENTER = 280;
    public static int BASKET_AUTO_RIGHT = 280;

    private double tolerance = 50;

    private MotionProfileDecceleration motionProfile;
    private int targetPosition, lastTargetPosition, currentPosition, startingPosition, direction;
    public static double Kp = 0, Kv = 0.8, Ka = 0.32, A = 0.2, maxVelocity = 6000;
    private double maxAcceleration = maxVelocity / A;

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

        /*motor = robot.motorExtendo;
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        state = ExtendoStates.INIT;
        lastState = ExtendoStates.INIT;

        motionProfile = new MotionProfileDecceleration();*/
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
                    motor.setPower(0.6);

                    break;

                case TRANSFER:
                    motor.setTargetPosition(TRANSFER);
                    motor.setPower(0.6);

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

            /*switch (state) {
                case INIT:
                    targetPosition = INIT;
                    break;

                case TRANSFER:
                    targetPosition = TRANSFER;
                    break;

                case EXTENDED:
                    targetPosition = EXTENDED;
                    break;
            }*/
        }

        lastState = state;
    }

    public boolean targetReached() {
        return(Math.abs(targetPosition - currentPosition) < tolerance);
    }

    public void setState(ExtendoStates state) {
        this.state = state;
    }

    public void setPosition(int position) {
        //targetPosition = position;

        motor.setTargetPosition(position);
        motor.setPower(1);
    }

    public ExtendoStates getState() {
        return state;
    }

}
