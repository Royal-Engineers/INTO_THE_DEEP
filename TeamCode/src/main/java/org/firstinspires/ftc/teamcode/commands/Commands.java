package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad2;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.m_gamepad2;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.objects.Chassis;
import org.firstinspires.ftc.teamcode.objects.Claw;
import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.Lift;
import org.firstinspires.ftc.teamcode.objects.Virtual4Bar;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;

import java.util.Objects;

@Config
public class Commands {
    public Chassis chassis;
    public Extendo extendo;
    public Lift lift;
    public Differential differential;

    public Virtual4Bar v4b;

    public Claw claw;
    private static double Ktrigger = 10;
    private static double differentialLinearAngle = 0, differentialRotationAngle = 0;

    public void init(AllObjects objects) {
        chassis = objects.chassis;
        extendo = objects.extendo;
        lift = objects.lift;
        differential = objects.differential;
        v4b = objects.v4b;
        claw = objects.claw;
    }

    public void update() {
        chassis.setMovement(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x);

        if (gamepad.right_stick_button && !lastgamepad.right_stick_button) {
            if (extendo.state == Extendo.ExtendoStates.INIT) extendo.state = Extendo.ExtendoStates.EXTENDED;
            else extendo.state = Extendo.ExtendoStates.INIT;
        }

        if (gamepad.left_trigger > 0.1) {
            if (extendo.state == Extendo.ExtendoStates.EXTENDED) extendo.increasePosition((int)(gamepad.left_trigger * Ktrigger));
            else if (lift.state != Lift.LiftStates.INIT) lift.increasePosition((int)(gamepad.left_trigger * Ktrigger));
        }

        if (gamepad.right_trigger > 0.1) {
            if (extendo.state == Extendo.ExtendoStates.EXTENDED) extendo.decreasePosition((int)(gamepad.right_trigger * Ktrigger));
            else if (lift.state != Lift.LiftStates.INIT) lift.decreasePosition((int)(gamepad.left_trigger * Ktrigger));
        }

        if (gamepad.cross && !lastgamepad.cross) {
            if (lift.state == Lift.LiftStates.INIT) lift.state = Lift.LiftStates.LOW_BASKET;
            else lift.state = Lift.LiftStates.INIT;
        }

        if ( m_gamepad2.cross && !lastgamepad2.cross)
        {
            v4b.setV4BPos(v4b.getV4BPos() + 0.02 );
        }

        if ( m_gamepad2.triangle && !lastgamepad2.triangle)
        {
            v4b.setV4BPos(v4b.getV4BPos() - 0.02 );
        }
        if (gamepad.triangle && !lastgamepad.triangle) {
         claw.setIntakeState(Claw.IntakeState.Intake);
        }

        if (gamepad.circle && !lastgamepad.circle) {
            claw.setWristState(Claw.WristState.Scan);
            v4b.setState(Virtual4Bar.V4BStates.Scanning);
        }
        if ( gamepad.square && !lastgamepad.square )
        {
            claw.setWristState(Claw.WristState.Scan);
            v4b.setState(Virtual4Bar.V4BStates.Pickup);
        }
    }
}
