package org.firstinspires.ftc.teamcode.control;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotH;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotX;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotY;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class AutoFunctions {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    private PIDF distancePID = new PIDF(), turningPID = new PIDF();
    public static double distanceP = 0.06, distanceI = 0.003, distanceD = 0.008, distanceF = 0;
    public static double turningP = 0.05, turningI = 0, turningD = 0.003, turningF = 0;

    private double targetX, targetY, targetH, maximumSpeed;
    private double robotVelocityX, robotVelocityY, robotVelocityW;

    public void setNewTargetPoint(double targetX, double targetY, double targetH, double maximumSpeed) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetH = targetH;
        this.maximumSpeed = maximumSpeed;

        distancePID.setCoefficients(distanceP, distanceI, distanceD, distanceF);
        distancePID.setTargetSpeed(maximumSpeed);
        distancePID.resetReference();

        turningPID.setCoefficients(turningP, turningI, turningD, turningF);
        turningPID.setTargetSpeed(maximumSpeed);
        turningPID.resetReference();
    }
    public void goToPoint() {
        double deltaX = targetX - robotX;
        double deltaY = targetY - robotY;
        double deltaH = targetH - robotH;
        if (deltaH > 180) deltaH -= 360;
        if (deltaH < -180) deltaH += 360;

        double distance = Math.sqrt((Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));

        double absoluteAngle = Math.atan2(deltaY, deltaX);

        distancePID.setCoefficients(distanceP, distanceI, distanceD, distanceF);
        double speed = Math.min(distancePID.getOutput(distance), maximumSpeed);

        turningPID.setCoefficients(turningP, turningI, turningD, turningF);
        double turningSpeed = Math.min(turningPID.getOutput(deltaH), maximumSpeed);

        if (distance < 2)
            speed = 0;

        if (Math.abs(deltaH) < 2)
            turningSpeed = 0;

        robotVelocityX = Math.cos(absoluteAngle) * speed;
        robotVelocityY = Math.sin(absoluteAngle) * speed;
        robotVelocityW = turningSpeed;

        dashboardTelemetry.addData("Speed", speed);
        dashboardTelemetry.addData("deltaX", deltaX);
        dashboardTelemetry.addData("deltaY", deltaY);
        dashboardTelemetry.addData("deltaH", deltaH);

        dashboardTelemetry.update();
    }

    public double getRobotVelocityX() { return robotVelocityX; }
    public double getRobotVelocityY() { return robotVelocityY; }
    public double getRobotVelocityW() { return -robotVelocityW; }

}
