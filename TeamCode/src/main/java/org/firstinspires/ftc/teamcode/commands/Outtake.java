package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.objects.outtake.Differential;
import org.firstinspires.ftc.teamcode.objects.outtake.Lift;
import org.firstinspires.ftc.teamcode.robot.AllObjects;

public class Outtake {
    private Lift lift;
    private Differential differential;

    public enum OuttakeStates {
        WAITING,
        DISABLED,
        LOW_CHAMBER,
        HIGH_CHAMBER,
        LOW_BASKET,
        HIGH_BASKET,
        FENCE,
        READY_TO_RELEASE,
        RELEASE,
        FINISH;
    }

    private OuttakeStates state, lastState, nextState;
    private ElapsedTime timer;
    private double waitingTime;

    public Outtake(AllObjects objects) {
        lift = objects.lift;
        differential = objects.differential;

        state = OuttakeStates.DISABLED;
        lastState = OuttakeStates.DISABLED;
        nextState = OuttakeStates.DISABLED;

        timer = new ElapsedTime();
    }

    public void update() {

        if (state != lastState || nextState != OuttakeStates.DISABLED) {
            switch(state) {
                case WAITING:
                    if (timer.seconds() > waitingTime) {
                        state = nextState;
                    }

                    break;

                case LOW_CHAMBER:
                    differential.setState(Differential.DifferentialStates.SPECIMEN_RELEASE);
                    lift.setState(Lift.LiftStates.LOW_CHAMBER);

                    break;

                case HIGH_CHAMBER:
                    differential.setState(Differential.DifferentialStates.SPECIMEN_RELEASE);
                    lift.setState(Lift.LiftStates.HIGH_CHAMBER);

                    break;

                case LOW_BASKET:
                    differential.setState(Differential.DifferentialStates.BASKET);
                    lift.setState(Lift.LiftStates.LOW_BASKET);

                    break;

                case HIGH_BASKET:
                    differential.setState(Differential.DifferentialStates.BASKET);
                    lift.setState(Lift.LiftStates.HIGH_BASKET);

                    break;

                case FINISH:
                    differential.setState(Differential.DifferentialStates.INIT);
                    lift.setState(Lift.LiftStates.INIT);

                    state = OuttakeStates.DISABLED;
                    break;

                case FENCE:
                    differential.setState(Differential.DifferentialStates.FENCE);
                    lift.setState(Lift.LiftStates.INIT);

                    nextState = OuttakeStates.DISABLED;

                    break;

                case READY_TO_RELEASE:
                    if (lastState == OuttakeStates.LOW_CHAMBER)
                        lift.setState(Lift.LiftStates.LOW_CHAMBER_RELEASE);

                    if (lastState == OuttakeStates.HIGH_CHAMBER)
                        lift.setState(Lift.LiftStates.HIGH_CHAMBER_RELEASE);

                    state = OuttakeStates.WAITING;
                    nextState = OuttakeStates.RELEASE;
                    waitingTime = 0.4; timer.reset();
                    break;

                case RELEASE:
                    differential.openClaw();

                    state = OuttakeStates.WAITING;
                    nextState = OuttakeStates.FENCE;
                    waitingTime = 0.2; timer.reset();
                    break;
            }
        }

        lastState = state;
    }

    public void setState(OuttakeStates state) {
        this.state = state;
    }

    public OuttakeStates getState() {
        return state;
    }
}
