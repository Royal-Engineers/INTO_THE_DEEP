package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotH;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotX;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotY;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.AutoFunctions;
import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Lift;
import org.firstinspires.ftc.teamcode.objects.drive.Chassis;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;

@Config
@Autonomous (name = "PositionsAuto")
public class PositionsAuto extends OpMode {
    private RobotHardware robot;
    private AllObjects objects;
    private AutoFunctions operations;

    private Chassis chassis;

    public static double targetX = 0, targetY = 0, targetH = 90, maximumSpeed = 1;
    private double lastTargetX = -1, lastTargetY = -1, lastTargetH = -1;

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
        if (targetX != lastTargetX || targetY != lastTargetY || targetH != lastTargetH) {
            operations.setNewTargetPoint(targetX, targetY, targetH, maximumSpeed);
        }

        lastTargetX = targetX; lastTargetY = targetY; lastTargetH = targetH;

        operations.goToPoint();

        chassis.setMovement(operations.getRobotVelocityX(), operations.getRobotVelocityY(), operations.getRobotVelocityW());

        objects.update();
        robot.update();
        telemetry.update();
    }
}
