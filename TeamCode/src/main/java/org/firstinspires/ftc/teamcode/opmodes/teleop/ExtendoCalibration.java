package org.firstinspires.ftc.teamcode.opmodes.teleop;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.dashboardTelemetry;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.commands.Commands;
import org.firstinspires.ftc.teamcode.control.MotionProfile;
import org.firstinspires.ftc.teamcode.objects.intake.Claw;
import org.firstinspires.ftc.teamcode.objects.intake.Virtual4Bar;
import org.firstinspires.ftc.teamcode.objects.outtake.Differential;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;
@Config
@TeleOp (name = "ExtendoCalibration")
public class ExtendoCalibration extends OpMode {
    private RobotHardware robot;
    private DcMotor motor;
    private MotionProfile motionProfile;

    private Differential differential;
    private Claw claw;
    private Virtual4Bar v4b;

    public static double Kp = 0, Kv = 1, Ka = 0, A = 1, maxVelocity = 500;
    private double maxAcceleration = maxVelocity / A;
    public static int targetPosition = 0, tolerance = 15;


    private int currentPosition = 0, lastTargetPosition = 0, startingPosition = 0;
    private int direction = 1;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        claw = new Claw(robot);
        v4b = new Virtual4Bar(robot);
        differential = new Differential(robot);

        motor = robot.motorExtendo;
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motionProfile = new MotionProfile();
    }

    @Override
    public void loop() {
        currentPosition = motor.getCurrentPosition();
        maxAcceleration = maxVelocity / A;

        if (targetPosition != lastTargetPosition) {
            if (targetPosition > currentPosition)
                direction = 1;
            else
                direction = -1;

            motionProfile.start(maxVelocity, maxAcceleration, direction * (targetPosition - currentPosition));
            startingPosition = lastTargetPosition;
        }

        motor.setPower(motionProfile.getVelocity() / motionProfile.getMaxVelocity() * Kv * direction + motionProfile.getAcceleration() / maxAcceleration * Ka * direction + (startingPosition + direction * motionProfile.getPosition() - currentPosition) * Kp);

        motionProfile.update();


        lastTargetPosition = targetPosition;

        dashboardTelemetry.addData("EncoderPosition", currentPosition);

        dashboardTelemetry.addData("MotionProfileVelocity", motionProfile.getVelocity());
        dashboardTelemetry.addData("MotionProfileMaxVelocity", motionProfile.getMaxVelocity());

        dashboardTelemetry.addData("MotorPower", motor.getPower());

        robot.update();
        telemetry.update();
        dashboardTelemetry.update();
    }

    private boolean targetReached() {
        return (Math.abs(currentPosition - targetPosition) < tolerance);
    }
}
