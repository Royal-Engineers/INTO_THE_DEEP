package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.objects.Chassis;

public class AllObjects {
    private RobotHardware robot;
    public Chassis chassis;

    public void init(RobotHardware robot) {
        this.robot = robot;

        chassis = new Chassis(robot);
    }

    public void update() {
        chassis.update();
    }
}
