package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.intake.Extendo;
import org.firstinspires.ftc.teamcode.objects.outtake.Lift;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;

@TeleOp(name = "CalibratePositions")
public class PositionsCalibration extends OpMode {
    private RobotHardware robot;
    private Extendo extendo;
    private Lift lift;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        extendo = new Extendo(robot);
        lift = new Lift(robot);
    }

    @Override
    public void loop() {
        extendo.motor.setPower(0);
        lift.motorUp.setPower(0);
        lift.motorDown.setPower(0);

        telemetry.addData("Pozitie extendo", extendo.motor.getCurrentPosition());
        telemetry.addData("Pozitie lift motorUp", lift.motorUp.getCurrentPosition());
        telemetry.addData("Pozitie lift motorDown", lift.motorDown.getCurrentPosition());

        robot.update();
        telemetry.update();
    }
}
