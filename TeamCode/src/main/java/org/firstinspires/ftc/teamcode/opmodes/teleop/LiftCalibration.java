package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.outtake.Differential;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;

@Config
@TeleOp (name = "CalibrateLift")
public class LiftCalibration extends OpMode {
    private RobotHardware robot;
    private Differential differential;

    public static double linearAngle = 0.06, rotationAngle = 0;
    public static int liftPosition = 0;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        differential = new Differential(robot);

    }

    @Override
    public void loop() {
        differential.setServoPosition(linearAngle, rotationAngle);
        robot.motorLiftUp.setTargetPosition(liftPosition);
        robot.motorLiftDown.setTargetPosition(liftPosition);

        robot.motorLiftUp.setPower(1);
        robot.motorLiftDown.setPower(1);

        robot.update();
        telemetry.update();
    }
}
