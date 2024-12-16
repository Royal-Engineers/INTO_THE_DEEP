package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.Climb;
import org.firstinspires.ftc.teamcode.objects.intake.Extendo;
import org.firstinspires.ftc.teamcode.objects.outtake.Lift;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;

@TeleOp(name = "CalibratePositions")
public class PositionsCalibration extends OpMode {
    private RobotHardware robot;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();
    }

    @Override
    public void loop() {

        telemetry.addData("Pozitie extendo", robot.motorExtendo.getCurrentPosition());
        telemetry.addData("Pozitie lift motorUp", robot.motorLiftUp.getCurrentPosition());
        telemetry.addData("Pozitie lift motorDown", robot.motorLiftDown.getCurrentPosition());
        telemetry.addData("Pozitie climb", robot.motorWinch.getCurrentPosition());

        robot.update();
        telemetry.update();
    }
}
