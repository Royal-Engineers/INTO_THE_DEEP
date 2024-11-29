package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Lift;
import org.firstinspires.ftc.teamcode.robot.AllObjects;

public class Outtake {
    private Lift lift;
    private Differential differential;

    public enum OuttakeStates {
        DISABLED,
        LOW_CHAMBER,
        HIGH_CHAMBER,
        LOW_BASKET,
        HIGH_BASKET,
        FENCE,
        FINISH;
    }

    private OuttakeStates state, lastState;

    public Outtake(AllObjects objects) {
        lift = objects.lift;
        differential = objects.differential;

        state = OuttakeStates.DISABLED;
        lastState = OuttakeStates.DISABLED;
    }

    public void update() {

        if (state != lastState) {
            switch(state) {
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
