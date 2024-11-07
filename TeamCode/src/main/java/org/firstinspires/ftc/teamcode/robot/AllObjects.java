package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.objects.Chassis;
import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.Lift;

public class AllObjects {
    public Chassis chassis;
    public Extendo extendo;
    public Lift lift;
    public Differential differential;

    public void init(RobotHardware robot) {
        chassis = new Chassis(robot);
        extendo = new Extendo(robot);
        lift = new Lift(robot);
        differential = new Differential(robot);

    }

    public void update() {
        chassis.update();
        extendo.update();
        lift.update();
        differential.update();
    }
}
