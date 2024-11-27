package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.Lift;
import org.firstinspires.ftc.teamcode.objects.SensorTrio;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;
@TeleOp (name = "Test")
public class Test extends OpMode {
    private RobotHardware robot;
    private SensorTrio sensorTrio;
    private Extendo extendo;
    private Lift lift;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        extendo = new Extendo(robot);
        lift = new Lift(robot);

        sensorTrio = new SensorTrio(robot.colorSensorLeft, robot.colorSensorCenter, robot.colorSensorRight);
    }


    @Override
    public void loop() {
        extendo.motor.setPower(0);
        lift.motorUp.setPower(0);
        lift.motorDown.setPower(0);

        telemetry.addData("Pozitie lift", lift.motorDown.getCurrentPosition());

        telemetry.addData("Pozitie extendo", extendo.motor.getCurrentPosition());

        telemetry.addLine("");

        telemetry.addData("RedLeft", sensorTrio.getLeftRed());
        telemetry.addData("RedCenter", sensorTrio.getCenterRed());
        telemetry.addData("RedRight", sensorTrio.getRightRed());

        robot.update();
        telemetry.update();
    }
}
