package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.objects.intake.Claw;
import org.firstinspires.ftc.teamcode.objects.outtake.Differential;
import org.firstinspires.ftc.teamcode.objects.intake.Extendo;
import org.firstinspires.ftc.teamcode.objects.intake.Virtual4Bar;
import org.firstinspires.ftc.teamcode.robot.AllObjects;

public class Transfer {
    private Extendo extendo;
    private Differential differential;
    private Claw claw;
    private Virtual4Bar v4b;
    public static boolean initiateTransfer = false;

    public enum TransferStates {
        WAITING,
        DISABLED,
        INIT,
        READY_TO_PICK_UP,
        PICK_UP,
        INTERMEDIATE,
        FINISH;
    }

    public TransferStates state, nextState;
    private ElapsedTime timer;
    private double waitingTime;

    public Transfer(AllObjects objects) {
        differential = objects.differential;
        claw = objects.claw;
        v4b = objects.v4b;
        extendo = objects.extendo;

        timer = new ElapsedTime();

        state = TransferStates.DISABLED;
        nextState = TransferStates.DISABLED;
    }

    public void update() {

        if (initiateTransfer) {
            state = TransferStates.INIT;
            initiateTransfer = false;
        }

        switch (state) {
            case WAITING:
                if (timer.seconds() > waitingTime)
                    state = nextState;

                break;

            case INIT:
                differential.setState(Differential.DifferentialStates.INIT);
                v4b.setState(Virtual4Bar.V4BStates.TRANSFER);
                claw.setWristState(Claw.WristState.TRANSFER);
                claw.setIntakeState(Claw.IntakeState.OFF);
                extendo.setState(Extendo.ExtendoStates.TRANSFER);

                state = TransferStates.READY_TO_PICK_UP;
                break;

            case READY_TO_PICK_UP:
                if (!differential.transferDetection.getState()) {
                    state = TransferStates.WAITING;
                    nextState = TransferStates.PICK_UP;
                    timer.reset(); waitingTime = 0.2;
                }

                break;

            case PICK_UP:
                differential.setState(Differential.DifferentialStates.PICK_UP);
                claw.setIntakeState(Claw.IntakeState.Outake);

                state = TransferStates.WAITING;
                nextState = TransferStates.INTERMEDIATE;
                timer.reset(); waitingTime = 0.2;

                break;

            case INTERMEDIATE:
                differential.setState(Differential.DifferentialStates.INTERMEDIATE);
                claw.setWristState(Claw.WristState.INIT);
                v4b.setState(Virtual4Bar.V4BStates.INIT);
                //extendo.setState(Extendo.ExtendoStates.TRANSFER_INTERMEDIATE);

                state = TransferStates.WAITING;
                nextState = TransferStates.FINISH;
                timer.reset(); waitingTime = 0.2;
                break;

            case FINISH:
                claw.setIntakeState(Claw.IntakeState.OFF);
                extendo.setState(Extendo.ExtendoStates.INIT);

                state = TransferStates.WAITING;
                nextState = TransferStates.DISABLED;
                timer.reset(); waitingTime = 0.2;
                break;
        }
    }

    public TransferStates getState() {
        return state;
    }
}
