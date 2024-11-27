package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.hardware.rev.RevColorSensorV3;

public class SensorTrio {
    private RevColorSensorV3 sensorLeft, sensorCenter, sensorRight;

    public SensorTrio(RevColorSensorV3 sensorLeft, RevColorSensorV3 sensorCenter, RevColorSensorV3 sensorRight) {
        this.sensorLeft = sensorLeft;
        this.sensorCenter = sensorCenter;
        this.sensorRight = sensorRight;

    }

    public int getLeftRed() {
        return sensorLeft.red();
    }
    public int getCenterRed() {
        return sensorCenter.red();
    }
    public int getRightRed() {
        return sensorRight.red();
    }
}
