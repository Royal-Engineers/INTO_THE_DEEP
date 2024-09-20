package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.objects.Chassis;
import org.firstinspires.ftc.teamcode.objects.Extendo;

public class AllObjects {
    public Chassis chassis;
    public Extendo extendo;

    public void init(RobotHardware robot) {
        chassis = new Chassis(robot);
        extendo = new Extendo(robot);

    }

    public void update() {
        chassis.update();
        extendo.update();
    }
}
