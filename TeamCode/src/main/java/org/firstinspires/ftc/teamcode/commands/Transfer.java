package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.init;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.objects.Claw;
import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Virtual4Bar;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Transfer {
    private Differential differential;
    private Claw claw;
    private Virtual4Bar v4b;
    public boolean initiate = false;

    public enum TransferStates {
        WAITING,
        DISABLED,
        INIT,
        PICK_UP,
        INTERMEDIATE,
        RELEASE;
    }

    public TransferStates state, nextState;
    private ElapsedTime timer;
    private double waitingTime;
    private boolean ok = false;

    public Transfer(Differential differential, Claw claw, Virtual4Bar v4b) {
        this.differential = differential;
        this.claw = claw;
        this.v4b = v4b;

        timer = new ElapsedTime();

        state = TransferStates.DISABLED;
        nextState = TransferStates.DISABLED;
    }

    public void update() {

        if (initiate) {
            state = TransferStates.INIT;
            initiate = false;
        }

        switch (state) {
            case WAITING:
                if (timer.seconds() > waitingTime)
                    state = nextState;

                break;

            case INIT:
                differential.setState(Differential.DifferentialStates.INIT);
                v4b.setState(Virtual4Bar.V4BStates.INIT);
                claw.setWristState(Claw.WristState.TRANSFER);

                state = TransferStates.WAITING;
                nextState = TransferStates.PICK_UP;
                timer.reset(); waitingTime = 0.5;
                break;

            case PICK_UP:
                if (!differential.transferDetection.getState()) {
                    differential.setState(Differential.DifferentialStates.PICK_UP);
                    claw.setIntakeState(Claw.IntakeState.Outake);

                    state = TransferStates.WAITING;
                    nextState = TransferStates.INTERMEDIATE;
                    timer.reset(); waitingTime = 0.5;
                }

                break;

            case INTERMEDIATE:
                differential.setState(Differential.DifferentialStates.INTERMEDIATE);
                claw.setWristState(Claw.WristState.INIT);

                state = TransferStates.WAITING;
                nextState = TransferStates.RELEASE;
                timer.reset(); waitingTime = 0.5;
                break;

            case RELEASE:
                differential.setState(Differential.DifferentialStates.RELEASE);
                claw.setIntakeState(Claw.IntakeState.INIT);

                state = TransferStates.WAITING;
                nextState = TransferStates.DISABLED;
                timer.reset(); waitingTime = 0.5;
                break;
        }
    }
}
