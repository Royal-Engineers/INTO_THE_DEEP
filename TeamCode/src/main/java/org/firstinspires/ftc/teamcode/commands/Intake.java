package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.commands.Transfer.initiateTransfer;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.objects.Claw;
import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.Virtual4Bar;
import org.firstinspires.ftc.teamcode.robot.AllObjects;

public class Intake {
    private Extendo extendo;
    private Virtual4Bar v4b;
    private Claw claw;
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
    private double waitingTime;

    public Intake(AllObjects objects) {
        v4b = objects.v4b;
        claw = objects.claw;
        extendo = objects.extendo;

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

        if (state == lastState && state == IntakeStates.SCANNING)
            return;

        switch (state) {
            case WAITING:
                if (timer.seconds() > waitingTime)
                    state = nextState;

                break;

            case SCANNING:
                v4b.setState(Virtual4Bar.V4BStates.SCANNING);
                claw.setWristState(Claw.WristState.SCAN);
                extendo.setState(Extendo.ExtendoStates.EXTENDED);

                lastState = IntakeStates.SCANNING;
                break;

            case PICK_UP:
                v4b.setState(Virtual4Bar.V4BStates.PICK_UP);
                claw.setWristState(Claw.WristState.PICK_UP);
                claw.setIntakeState(Claw.IntakeState.Intake);

                state = IntakeStates.WAITING;
                nextState = IntakeStates.FINISH;
                timer.reset(); waitingTime = 1;
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
