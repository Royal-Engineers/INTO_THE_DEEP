package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.intake.SensorTrio;
import org.firstinspires.ftc.teamcode.objects.outtake.Differential;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;

@TeleOp (name = "IntakeClawCalibration")
@Config
public class IntakeClawCalibration extends OpMode {
    private RobotHardware robot;
    private Differential differential;
    private SensorTrio sensorTrio;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        sensorTrio = new SensorTrio(robot);

        //differential = new Differential(robot);
    }

    public static double v4bPosition = 0.05, wristPosition = 0.38, rotationPosition = 0.57, diffClaw = 0.6, diffLinearAngle = 0.06, diffRotationalAngle = 0;

    @Override
    public void loop() {
        robot.servoClawWrist.setPosition(wristPosition);
        robot.servoClawRotation.setPosition(rotationPosition);
        robot.servoV4BLeft.setPosition(v4bPosition);
        robot.servoV4BRight.setPosition(v4bPosition);

        //robot.servoDifferentialClaw.setPosition(diffClaw);
        //differential.setServoPosition(diffLinearAngle, diffRotationalAngle);

        telemetry.addData("Pozitie extendo", robot.motorExtendo.getCurrentPosition());

        sensorTrio.update();

        robot.update();
        telemetry.update();
    }
}
