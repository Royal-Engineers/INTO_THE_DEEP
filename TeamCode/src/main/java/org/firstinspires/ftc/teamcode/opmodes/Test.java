package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.Claw;
import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.Virtual4Bar;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;
@TeleOp (name = "Test")
public class Test extends OpMode {
    private RobotHardware robot;
    private Extendo extendo;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        extendo = new Extendo(robot);
    }


    @Override
    public void loop() {

        telemetry.addData("Pozitie extendo", extendo.motor.getCurrentPosition());

        robot.update();
        telemetry.update();
    }
}
