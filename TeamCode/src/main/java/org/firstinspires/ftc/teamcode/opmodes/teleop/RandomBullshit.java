package org.firstinspires.ftc.teamcode.opmodes.teleop;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.dashboardTelemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.MotionProfile;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;
@TeleOp (name = "RandomBullshit")
public class RandomBullshit extends OpMode {
    private RobotHardware robot;
    private DcMotor motor;

    private ElapsedTime timer;
    private double maxx = 0;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        motor = robot.motorExtendo;
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        timer = new ElapsedTime();
    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void loop() {
        if (motor.getCurrentPosition() < 1500)
            motor.setPower(1);
        else {
            motor.setPower(0);
            maxx = Math.max(motor.getCurrentPosition()/timer.seconds(), maxx);
        }

        telemetry.addData("Maximum Speed", maxx);


        robot.update();
        telemetry.update();
        dashboardTelemetry.update();
    }
}
