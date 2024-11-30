package org.firstinspires.ftc.teamcode.control;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotX;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotY;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class AutoFunctions {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    private PIDF distancePID = new PIDF();
    public static double distanceP = 0.05, distanceI = 0.001, distanceD = 0.0005, distanceF = 0;

    private double targetX, targetY, maximumSpeed;
    private double robotVelocityX, robotVelocityY;

    public void setNewTargetPoint(double targetX, double targetY, double maximumSpeed) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.maximumSpeed = maximumSpeed;

        distancePID.setCoefficients(distanceP, distanceI, distanceD, distanceF);
        distancePID.setTargetSpeed(maximumSpeed);
        distancePID.resetReference();
    }
    public void goToPoint() {
        double deltaX = targetX - robotX;
        double deltaY = targetY - robotY;

        double distance = Math.sqrt((Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));

        double absoluteAngle = Math.atan2(deltaY, deltaX) + Math.toRadians(90);

        distancePID.setCoefficients(distanceP, distanceI, distanceD, distanceF);
        double speed = Math.min(distancePID.getOutput(distance), maximumSpeed);

        if (distance < 2)
            speed = 0;

        robotVelocityX = Math.cos(absoluteAngle) * speed;
        robotVelocityY = Math.sin(absoluteAngle) * speed;

        dashboardTelemetry.addData("Speed", speed);
        dashboardTelemetry.addData("X", robotX);
        dashboardTelemetry.addData("Y", robotY);

        dashboardTelemetry.update();
    }

    public double getRobotVelocityX() { return robotVelocityX; }
    public double getRobotVelocityY() { return robotVelocityY; }

}
