package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class StaticVariables {
    public static HardwareMap hardwareMap;
    public static Telemetry telemetry;
    public static Gamepad gamepad, lastgamepad = new Gamepad();

    public static double robotX, robotY, robotH;

    public static void init(HardwareMap hm, Telemetry tm, Gamepad gm) {
        hardwareMap = hm;
        telemetry = tm;
        gamepad = gm;

        robotX = 0; robotY = 0; robotH = Math.PI / 2;
    }
}
