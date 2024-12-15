package org.firstinspires.ftc.teamcode.objects.intake;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;

public class SensorTrio {
    private RevColorSensorV3 sensorCenter;
    private Rev2mDistanceSensor sensorLeft, sensorRight;

    public SensorTrio(Rev2mDistanceSensor sensorLeft, RevColorSensorV3 sensorCenter, Rev2mDistanceSensor sensorRight) {
        this.sensorLeft = sensorLeft;
        this.sensorCenter = sensorCenter;
        this.sensorRight = sensorRight;

    }
}
