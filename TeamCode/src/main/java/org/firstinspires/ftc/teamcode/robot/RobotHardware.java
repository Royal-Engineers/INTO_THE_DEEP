package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.hardwareMap;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;

public class RobotHardware {
    List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
    Odometry odometry;

    public DcMotor motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft;
    public DcMotor motorExtendo;



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

        odometry.init(motorFrontRight, motorBackRight, motorFrontLeft);

        // INTAKE
        motorExtendo = hardwareMap.get(DcMotor.class, "motorExtendo");

    }

    public void update() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }

        odometry.update();

        lastgamepad.copy(gamepad);
    }

}
