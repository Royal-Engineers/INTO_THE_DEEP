package org.firstinspires.ftc.teamcode.objects;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robot.RobotHardware;

public class Chassis {
    private DcMotor motorFrontRight, motorFrontLeft, motorBackLeft, motorBackRight;

    private double vx, vy, w;
    private double fr, fl, bl, br;
    private double power, theta, sin, cos;
    private double maxx;

    public Chassis(RobotHardware robot) {

        this.motorFrontRight = robot.motorFrontRight;
        this.motorFrontLeft = robot.motorFrontLeft;
        this.motorBackLeft = robot.motorBackLeft;
        this.motorBackRight = robot.motorBackRight;

        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void update() {
        power = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
        theta = Math.atan2(vy, vx) - Math.PI / 4;

        sin = Math.sin(theta); cos = Math.cos(theta);
        maxx = Math.max(Math.abs(sin), Math.abs(cos));

        fr = power * sin / maxx - w;
        fl = power * cos / maxx + w;
        bl = power * sin / maxx + w;
        br = power * cos / maxx - w;

        maxx = power + Math.abs(w);

        if (maxx > 1) {
            fr /= maxx; fl /= maxx;
            bl /= maxx; br /= maxx;
        }

        motorFrontRight.setPower(fr);
        motorFrontLeft.setPower(fl);
        motorBackLeft.setPower(bl);
        motorBackRight.setPower(br);
    }

    public void setMovement(double vx, double vy, double w) {
        this.vx = vx;
        this.vy = vy;
        this.w = w;
    }
}
