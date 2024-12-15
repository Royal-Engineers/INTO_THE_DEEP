package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

import org.firstinspires.ftc.teamcode.objects.drive.Chassis;
import org.firstinspires.ftc.teamcode.objects.intake.Claw;
import org.firstinspires.ftc.teamcode.objects.outtake.Differential;
import org.firstinspires.ftc.teamcode.objects.intake.Extendo;
import org.firstinspires.ftc.teamcode.objects.outtake.Lift;
import org.firstinspires.ftc.teamcode.objects.intake.SensorTrio;
import org.firstinspires.ftc.teamcode.objects.intake.Virtual4Bar;

public class AllObjects {
    public Chassis chassis;
    public Extendo extendo;
    public Lift lift;
    public Differential differential;
    public Claw claw;
    public Virtual4Bar v4b;
    public SensorTrio sensorTrio;

    public void init(RobotHardware robot) {
        chassis = new Chassis(robot);
        extendo = new Extendo(robot);
        lift = new Lift(robot);
        differential = new Differential(robot);
        v4b = new Virtual4Bar(robot);
        claw = new Claw(robot);
        sensorTrio = new SensorTrio(robot.distanceSensorLeft, robot.colorSensorCenter, robot.distanceSensorRight);
    }

    public void update() {
        chassis.update();
        extendo.update();
        lift.update();
        differential.update();
        v4b.update();
        claw.Update();

        telemetry.addData("Pozitie Extendo", extendo.motor.getCurrentPosition());
    }
}
