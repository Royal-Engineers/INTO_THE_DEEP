package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
@Config
public class Commands extends AllObjects {
    private static double Ktrigger = 10;
    public void update() {
        chassis.setMovement(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x);

        if (gamepad.right_stick_button && !lastgamepad.right_stick_button) {
            if (extendo.state == Extendo.ExtendoStates.INIT) extendo.state = Extendo.ExtendoStates.EXTENDED;
            else extendo.state = Extendo.ExtendoStates.INIT;
        }

        if (gamepad.left_trigger > 0.1) {
            if (extendo.state == Extendo.ExtendoStates.EXTENDED) extendo.increasePosition((int)(gamepad.left_trigger * Ktrigger));
        }

        if (gamepad.right_trigger > 0.1) {
            if (extendo.state == Extendo.ExtendoStates.EXTENDED) extendo.decreasePosition((int)(gamepad.right_trigger * Ktrigger));
        }
    }
}
