package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class StaticVariables {
    public static HardwareMap hardwareMap;
    public static Telemetry telemetry;
    public static Gamepad gamepad, lastgamepad = new Gamepad(), gamepad2, lastgamepad2 = new Gamepad();
    public static double robotX, robotY, robotH;

    public static FtcDashboard dashboard = FtcDashboard.getInstance();
    public static Telemetry dashboardTelemetry = dashboard.getTelemetry();

    public static void init(HardwareMap hm, Telemetry tm, Gamepad gm, Gamepad gm2) {
        hardwareMap = hm;
        telemetry = tm;
        gamepad = gm;
        gamepad2 = gm2;
        robotX = 0; robotY = 0; robotH = Math.PI / 2;
    }
}
