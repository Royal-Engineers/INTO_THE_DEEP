package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;

import org.firstinspires.ftc.teamcode.robot.AllObjects;

public class Commands extends AllObjects {
    public void update() {
        chassis.setMovement(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x);

    }
}
