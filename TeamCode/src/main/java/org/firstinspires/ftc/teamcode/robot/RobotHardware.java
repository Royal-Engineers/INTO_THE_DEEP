package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad2;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.hardwareMap;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad2;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotH;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotX;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotY;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.objects.drive.GoBildaPinpointDriver;

import java.util.List;

public class RobotHardware {
    List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

    public DcMotor motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft;
    public DcMotor motorExtendo, motorLiftUp, motorLiftDown;
    public DcMotor motorWinch;

    public Servo servoDifferentialLeft, servoDifferentialRight, servoDifferentialClaw;
    public Servo servoFirstClimbLeft, servoFirstClimbRight, servoSecondClimbLeft, servoSecondClimbRight;
    public Servo servoV4BLeft, servoV4BRight, servoClawRotation, servoClawWrist;
    public CRServo servoActiveIntake;

    public DigitalChannel transferDetection;

    public RevColorSensorV3 colorSensorCenter;
    public Rev2mDistanceSensor distanceSensorLeft, distanceSensorRight;

    public GoBildaPinpointDriver odometry;
    private Pose2D pos;


    public void init() {
        // HUBS
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        // WINCH
        motorWinch = hardwareMap.get(DcMotor.class, "motorWinch");

        // CHASSIS
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");

        // ODOMETRY
        odometry = hardwareMap.get(GoBildaPinpointDriver.class, "odometry");
        odometry.setOffsets(109,103);
        odometry.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odometry.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odometry.resetPosAndIMU();

        // INTAKE
        motorExtendo = hardwareMap.get(DcMotor.class, "motorExtendo");

        servoV4BLeft = hardwareMap.get(Servo.class, "servoV4BLeft");
        servoV4BRight = hardwareMap.get(Servo.class, "servoV4BRight");
        servoClawRotation = hardwareMap.get(Servo.class, "servoClawRotation");
        servoClawWrist = hardwareMap.get(Servo.class, "servoClawWrist");
        servoActiveIntake = hardwareMap.get(CRServo.class, "servoActiveIntake");

        distanceSensorLeft = hardwareMap.get(Rev2mDistanceSensor.class, "colorSensorLeft");
        colorSensorCenter = hardwareMap.get(RevColorSensorV3.class, "colorSensorCenter");
        distanceSensorRight = hardwareMap.get(Rev2mDistanceSensor.class, "colorSensorRight");

        // OUTTAKE
        motorLiftUp = hardwareMap.get(DcMotor.class, "motorLiftUp");
        motorLiftDown = hardwareMap.get(DcMotor.class, "motorLiftDown");

        // DIFFERENTIAL
        servoDifferentialLeft = hardwareMap.get(Servo.class, "servoDifferentialLeft");
        servoDifferentialRight = hardwareMap.get(Servo.class, "servoDifferentialRight");
        servoDifferentialClaw = hardwareMap.get(Servo.class, "servoDifferentialClaw");
        transferDetection = hardwareMap.get(DigitalChannel.class, "transferDetection");

        // CLIMB
        servoFirstClimbLeft = hardwareMap.get(Servo.class, "servoFirstClimbLeft");
        servoFirstClimbRight = hardwareMap.get(Servo.class, "servoFirstClimbRight");
        servoSecondClimbLeft = hardwareMap.get(Servo.class, "servoSecondClimbLeft");
        servoSecondClimbRight = hardwareMap.get(Servo.class, "servoSecondClimbRight");

    }

    public void update() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }

        odometry.update();

        pos = odometry.getPosition();

        robotX = - pos.getY(DistanceUnit.CM);
        robotY = pos.getX(DistanceUnit.CM);
        robotH = pos.getHeading(AngleUnit.DEGREES) + 90;

        lastgamepad.copy(gamepad);
        lastgamepad2.copy(gamepad2);

        telemetry.addData("X:", robotX);
        telemetry.addData("Y:", robotY);
        telemetry.addData("H:", robotH);
    }

}
