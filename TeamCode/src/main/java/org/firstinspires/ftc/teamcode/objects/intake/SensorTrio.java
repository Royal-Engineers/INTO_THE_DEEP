package org.firstinspires.ftc.teamcode.objects.intake;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class SensorTrio {
    private RobotHardware robot;
    private RevColorSensorV3 sensorCenter;
    private Rev2mDistanceSensor sensorLeft, sensorRight;

    public SensorTrio(RobotHardware robot) {
        this.sensorLeft = robot.distanceSensorLeft;
        this.sensorCenter = robot.colorSensorCenter;
        this.sensorRight = robot.distanceSensorRight;

    }

    public void update() {
        telemetry.addData("Sensor left", sensorLeft.getDistance(DistanceUnit.MM));
        telemetry.addData("Sensor right", sensorRight.getDistance(DistanceUnit.MM));
        telemetry.addData("Sensor center", sensorCenter.red());
    }

    public boolean detectedSample() {
        if (sensorCenter.red() > 90)
            return true;
        else
            return false;
    }
}
