package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.objects.Chassis;
import org.firstinspires.ftc.teamcode.objects.Claw;
import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.Lift;
import org.firstinspires.ftc.teamcode.objects.Virtual4Bar;

public class AllObjects {
    public Chassis chassis;
    //public Extendo extendo;
    public Lift lift;
    public Differential differential;
    public Claw claw;
    public Virtual4Bar v4b;

    public void init(RobotHardware robot) {
        chassis = new Chassis(robot);
        //extendo = new Extendo(robot);
        lift = new Lift(robot);
        differential = new Differential(robot);
        v4b = new Virtual4Bar(robot);
        claw = new Claw(robot);
    }

    public void update() {
        chassis.update();
        //extendo.update();
        lift.update();
        differential.update();
        v4b.update();
        claw.Update();
    }
}
