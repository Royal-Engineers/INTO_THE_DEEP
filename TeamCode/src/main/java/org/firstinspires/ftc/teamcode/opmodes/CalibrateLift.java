package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.MotionProfile;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;

@TeleOp (name = "CalibrateLift")
public class CalibrateLift extends OpMode {
    private RobotHardware robot;
    private DcMotor motorDown, motorUp;
    private MotionProfile motionProfile;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        motionProfile = new MotionProfile();

        motorDown = robot.motorLiftDown;
        motorUp = robot.motorLiftUp;
    }

    @Override
    public void loop() {
        robot.update();
        telemetry.update();
    }
}
