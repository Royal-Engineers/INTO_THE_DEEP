package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;

import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.robot.AllObjects;

public class Commands extends AllObjects {
    public void update() {
        chassis.setMovement(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x);

        if (gamepad.right_stick_button && !lastgamepad.right_stick_button) {
            if (extendo.state == Extendo.ExtendoStates.INIT) extendo.state = Extendo.ExtendoStates.EXTENDED;
            else extendo.state = Extendo.ExtendoStates.INIT;
        }
    }
}
