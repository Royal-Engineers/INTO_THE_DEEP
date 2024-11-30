package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commands.Commands;
import org.firstinspires.ftc.teamcode.control.AutoFunctions;
import org.firstinspires.ftc.teamcode.objects.drive.Chassis;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;
@Config
@Autonomous (name = "BasketAuto")
public class BasketAuto extends OpMode {
    private RobotHardware robot;
    private AllObjects objects;
    private AutoFunctions operations;

    private Chassis chassis;

    public static double targetX = 0, targetY = 0, maximumSpeed = 1;
    private double lastTargetX = -1, lastTargetY = -1;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        objects = new AllObjects();
        objects.init(robot);

        chassis = objects.chassis;

        operations = new AutoFunctions();
    }

    @Override
    public void loop() {
        if (targetX != lastTargetX || targetY != lastTargetY) {
            operations.setNewTargetPoint(targetX, targetY, maximumSpeed);
        }

        lastTargetX = targetX; lastTargetY = targetY;

        operations.goToPoint();

        chassis.setMovement(operations.getRobotVelocityX(), operations.getRobotVelocityY(), 0);

        objects.update();
        robot.update();
        telemetry.update();
    }
}
