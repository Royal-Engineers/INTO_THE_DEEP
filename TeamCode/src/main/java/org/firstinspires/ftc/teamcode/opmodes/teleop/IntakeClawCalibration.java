package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.Claw;
import org.firstinspires.ftc.teamcode.objects.Virtual4Bar;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;

@TeleOp (name = "IntakeClawCalibration")
@Config
public class IntakeClawCalibration extends OpMode {
    private RobotHardware robot;
    private Claw claw;
    private Virtual4Bar v4b;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        claw = new Claw(robot);
        v4b = new Virtual4Bar(robot);
    }

    public static double v4bPosition = 0.03, wristPosition = 0.38, rotationPosition = 0.57, activeIntakePower = 0, diffClaw = 0.55;

    @Override
    public void loop() {
        claw.updatePosition(wristPosition, rotationPosition);
        claw.setIntakePower(activeIntakePower);
        v4b.setV4BPos(v4bPosition);

        robot.servoDifferentialClaw.setPosition(diffClaw);

        robot.update();
        telemetry.update();
    }
}
