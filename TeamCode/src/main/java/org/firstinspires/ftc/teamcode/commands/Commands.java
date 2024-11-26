package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.objects.Chassis;
import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.Lift;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;

@Config
public class Commands {
    public Chassis chassis;
    public Extendo extendo;
    public Lift lift;
    public Differential differential;
    public Transfer transfer;
    private static double Ktrigger = 10;

    public void init(AllObjects objects) {
        chassis = objects.chassis;
        extendo = objects.extendo;
        lift = objects.lift;
        differential = objects.differential;

        transfer = new Transfer(differential);
    }

    public void update() {
        chassis.setMovement(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x);

        if (gamepad.right_stick_button && !lastgamepad.right_stick_button) {
            if (extendo.state == Extendo.ExtendoStates.INIT) extendo.state = Extendo.ExtendoStates.EXTENDED;
            else extendo.state = Extendo.ExtendoStates.INIT;
        }

        if (gamepad.left_trigger > 0.1) {
            lift.increasePosition((int)(gamepad.left_trigger * Ktrigger));
        }

        if (gamepad.right_trigger > 0.1) {
            lift.decreasePosition((int)(gamepad.left_trigger * Ktrigger));
        }

        if (gamepad.cross && !lastgamepad.cross) {
            if (lift.state == Lift.LiftStates.INIT) lift.state = Lift.LiftStates.LOW_CHAMBER;
            else lift.state = Lift.LiftStates.INIT;
        }

        if (gamepad.square && !lastgamepad.square) {
            transfer.initiate = true;
        }

        transfer.update();
    }
}
