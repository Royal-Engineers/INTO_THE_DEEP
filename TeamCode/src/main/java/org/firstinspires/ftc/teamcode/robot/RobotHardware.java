package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.hardwareMap;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

public class RobotHardware {
    List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

    public DcMotor motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft;
    public DcMotor motorExtendo, motorLift1, motorLift2;

    public Servo differentialLeftServo, differentialRightServo;



    public void init() {
        // HUBS
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        // CHASSIS
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");

        // INTAKE
        motorExtendo = hardwareMap.get(DcMotor.class, "motorExtendo");

        // OUTTAKE
        motorLift1 = hardwareMap.get(DcMotor.class, "motorLift1");
        motorLift2 = hardwareMap.get(DcMotor.class, "motorLift2");

        // DIFFERENTIAL
        differentialLeftServo = hardwareMap.get(Servo.class, "differentialLeftServo");
        differentialRightServo = hardwareMap.get(Servo.class, "differentialRightServo");

    }

    public void update() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }

        lastgamepad.copy(gamepad);
    }

}
