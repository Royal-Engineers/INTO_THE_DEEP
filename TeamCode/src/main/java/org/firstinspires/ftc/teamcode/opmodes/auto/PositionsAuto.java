package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.control.AutoFunctions;
import org.firstinspires.ftc.teamcode.objects.intake.Claw;
import org.firstinspires.ftc.teamcode.objects.intake.Extendo;
import org.firstinspires.ftc.teamcode.objects.intake.Virtual4Bar;
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
    private Extendo extendo;
    private Virtual4Bar v4b;
    private Claw claw;

    public static double targetX = 0, targetY = 0, targetH = 90, maximumSpeed = 1;
    private double lastTargetX = -1, lastTargetY = -1, lastTargetH = -1;

    public static int extendoPosition = 0;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        objects = new AllObjects();
        objects.init(robot);

        chassis = objects.chassis;
        extendo = objects.extendo;
        v4b = objects.v4b;
        claw = objects.claw;

        operations = new AutoFunctions();
    }

    @Override
    public void loop() {
        if (targetX != lastTargetX || targetY != lastTargetY || targetH != lastTargetH) {
            operations.setNewTargetPoint(targetX, targetY, targetH, maximumSpeed);
        }

        lastTargetX = targetX; lastTargetY = targetY; lastTargetH = targetH;

        extendo.setPosition(extendoPosition);

        operations.goToPoint();

        chassis.setMovement(operations.getRobotVelocityX(), operations.getRobotVelocityY(), operations.getRobotVelocityW());

        v4b.setState(Virtual4Bar.V4BStates.SCANNING);
        claw.setWristState(Claw.WristState.SCAN);

        objects.update();
        robot.update();
        telemetry.update();
    }
}
