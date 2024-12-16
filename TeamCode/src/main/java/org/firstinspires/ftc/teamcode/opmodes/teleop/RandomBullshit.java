package org.firstinspires.ftc.teamcode.opmodes.teleop;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.dashboardTelemetry;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.MotionProfile;
import org.firstinspires.ftc.teamcode.objects.Climb;
import org.firstinspires.ftc.teamcode.objects.intake.Claw;
import org.firstinspires.ftc.teamcode.objects.intake.Virtual4Bar;
import org.firstinspires.ftc.teamcode.objects.outtake.Differential;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;
@TeleOp (name = "RandomBullshit")
public class RandomBullshit extends OpMode {
    private RobotHardware robot;
    private Climb climb;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        climb = new Climb(robot);
    }

    @Override
    public void loop() {
        if (gamepad.dpad_up && !lastgamepad.dpad_up) {
            climb.setState(Climb.ClimbStates.ST_STAGE);
        }

        if (gamepad.dpad_down && !lastgamepad.dpad_down) {
            climb.setState(Climb.ClimbStates.INIT);
        }

        climb.update();

        robot.update();
        telemetry.update();
        dashboardTelemetry.update();
    }
}
