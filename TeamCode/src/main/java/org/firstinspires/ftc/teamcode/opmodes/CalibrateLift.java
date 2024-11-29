package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.MotionProfile;
import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Lift;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;

@Config
@TeleOp (name = "CalibrateLift")
public class CalibrateLift extends OpMode {
    private RobotHardware robot;
    private Differential differential;

    public static double linearAngle = 0.1, rotationAngle = 0;

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

        robot.update();
        telemetry.update();
    }
}
