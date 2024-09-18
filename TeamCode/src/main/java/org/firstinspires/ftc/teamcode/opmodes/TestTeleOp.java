package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.Commands;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;
@TeleOp (name = "TestTeleOp")
public class TestTeleOp extends OpMode {
    private RobotHardware robot;
    private AllObjects objects;
    private Commands commands;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1);
        robot.init(); objects.init(robot);
    }

    @Override
    public void loop() {
        commands.update();
        objects.update();
        robot.update();
        telemetry.update();
    }
}
