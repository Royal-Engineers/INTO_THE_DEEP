package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.commands.Transfer.initiateTransfer;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.objects.Claw;
import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.SensorTrio;
import org.firstinspires.ftc.teamcode.objects.Virtual4Bar;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
@Config
public class Intake {
    private Extendo extendo;
    private Virtual4Bar v4b;
    private Claw claw;
    private SensorTrio sensorTrio;
    public static boolean initiateIntake = false;

    public enum IntakeStates {
        WAITING,
        DISABLED,
        SCANNING,
        PICK_UP,
        FINISH;
    }

    private IntakeStates state, nextState, lastState;
    private ElapsedTime timer;
    private double waitingTime, rotationPosition;
    public static double K = 0.00001;
    private boolean direction = true;

    public Intake(AllObjects objects) {
        v4b = objects.v4b;
        claw = objects.claw;
        extendo = objects.extendo;
        sensorTrio = objects.sensorTrio;

        timer = new ElapsedTime();

        state = IntakeStates.DISABLED;
        nextState = IntakeStates.DISABLED;
        lastState = IntakeStates.DISABLED;
    }

    public void update() {
        if (initiateIntake) {
            state = IntakeStates.SCANNING;
            initiateIntake = false;
        }

        /*if (state == lastState && state == IntakeStates.SCANNING) {
            if (direction) {
                rotationPosition += K;
                claw.setClawRotation(rotationPosition);

                if (rotationPosition >= 0.57)
                    direction = false;
            }
            else {
                rotationPosition -= K;
                claw.setClawRotation(rotationPosition);

                if (rotationPosition <= 0.03)
                    direction = true;
            }

            if (sensorTrio.getCenterRed() > 100 && sensorTrio.getLeftRed() > 70 && sensorTrio.getRightRed() > 140)
                state = IntakeStates.PICK_UP;
            else
                return;
        }*/

        switch (state) {
            case WAITING:
                if (timer.seconds() > waitingTime)
                    state = nextState;

                break;

            case SCANNING:
                v4b.setState(Virtual4Bar.V4BStates.SCANNING);
                claw.setWristState(Claw.WristState.SCAN);
                claw.setIntakeState(Claw.IntakeState.OFF);
                extendo.setState(Extendo.ExtendoStates.EXTENDED);


                rotationPosition = claw.getClawRotation();
                lastState = IntakeStates.SCANNING;
                break;

            case PICK_UP:
                v4b.setState(Virtual4Bar.V4BStates.PICK_UP);
                claw.setWristState(Claw.WristState.PICK_UP);
                claw.setIntakeState(Claw.IntakeState.Intake);

                break;

            case FINISH:
                initiateTransfer = true;

                state = IntakeStates.DISABLED;
                break;
        }

        lastState = state;
    }

    public IntakeStates getState() {
        return state;
    }

    public void setState (IntakeStates state) {
        this.state = state;
    }
}
