package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Transfer {
    private Differential differential;
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

    public Transfer(Differential differential) {
        this.differential = differential;

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

                state = TransferStates.WAITING;
                nextState = TransferStates.PICK_UP;
                timer.reset(); waitingTime = 0.5;
                break;

            case PICK_UP:
                differential.setState(Differential.DifferentialStates.PICK_UP);

                state = TransferStates.WAITING;
                nextState = TransferStates.INTERMEDIATE;
                timer.reset(); waitingTime = 0.5;
                break;

            case INTERMEDIATE:
                differential.setState(Differential.DifferentialStates.INTERMEDIATE);

                state = TransferStates.WAITING;
                nextState = TransferStates.RELEASE;
                timer.reset(); waitingTime = 0.5;
                break;

            case RELEASE:
                differential.setState(Differential.DifferentialStates.RELEASE);

                state = TransferStates.WAITING;
                nextState = TransferStates.DISABLED;
                timer.reset(); waitingTime = 0.5;
                break;
        }
    }
}
