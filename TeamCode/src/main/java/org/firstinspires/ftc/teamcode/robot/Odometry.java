package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotH;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotX;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotY;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Odometry {
    private DcMotor encoderLeft, encoderRight, encoderFront;
    private final double L = 31.5, W = 19, WHEEL_RADIUS = 1.75, CPR = 8192 ;
    private final double CT = Math.PI * 2 * WHEEL_RADIUS / CPR; // CM / COUNT
    private double ticksLeft, ticksRight, ticksFront;
    private double lastTicksLeft, lastTicksRight, lastTicksFront;
    private double deltaLeft, deltaRight, deltaFront;
    private double deltaX, deltaY, deltaTheta;

    public void init(DcMotor encoderLeft1, DcMotor encoderRight1, DcMotor encoderFront1) {

        encoderLeft = encoderLeft1;
        encoderRight = encoderRight1;
        encoderFront = encoderFront1;
    }

    public void update () {
        ticksLeft = encoderLeft.getCurrentPosition();
        ticksRight = -encoderRight.getCurrentPosition();
        ticksFront = encoderFront.getCurrentPosition();

        deltaLeft = ticksLeft - lastTicksLeft;
        deltaRight = ticksRight - lastTicksRight;
        deltaFront = ticksFront - lastTicksFront;

        deltaX = CT * 0.5 * (deltaLeft + deltaRight);
        deltaY = CT * (deltaFront - W / L * (deltaRight - deltaLeft));
        deltaTheta = CT / L * (deltaRight - deltaLeft);

        robotX = robotX + deltaX * Math.cos(robotH) - deltaY * Math.sin(robotH);
        robotY = robotY + deltaX * Math.sin(robotH) + deltaY * Math.cos(robotH);
        robotH = robotH + deltaTheta;

        if(robotH < 0) robotH = robotH + 2 * Math.PI;
        if(robotH > 2 * Math.PI) robotH = robotH - 2 * Math.PI;

        lastTicksLeft = ticksLeft;
        lastTicksRight = ticksRight;
        lastTicksFront = ticksFront;

        telemetry.addData("HEADING", Math.toDegrees(robotH));
        telemetry.addData("X", robotX);
        telemetry.addData("Y", robotY);
        telemetry.addLine();
    }
}
