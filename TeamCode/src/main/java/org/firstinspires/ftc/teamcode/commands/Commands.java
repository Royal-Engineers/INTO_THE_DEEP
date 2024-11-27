package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.commands.Intake.initiateIntake;
import static org.firstinspires.ftc.teamcode.commands.Transfer.initiateTransfer;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad2;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.objects.Chassis;
import org.firstinspires.ftc.teamcode.objects.Claw;
import org.firstinspires.ftc.teamcode.objects.Differential;
import org.firstinspires.ftc.teamcode.objects.Extendo;
import org.firstinspires.ftc.teamcode.objects.Lift;
import org.firstinspires.ftc.teamcode.objects.Virtual4Bar;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;

@Config
public class Commands {
    public Chassis chassis;
    public Extendo extendo;
    public Lift lift;
    public Differential differential;
    public Virtual4Bar v4b;
    public Claw claw;

    public Transfer transfer;
    public Intake intake;

    private double Ktrigger = 2;
    public static double Krotation = 0.003;
    private double intakeClawRotation;

    public void init(AllObjects objects) {
        chassis = objects.chassis;
        extendo = objects.extendo;
        lift = objects.lift;
        differential = objects.differential;
        v4b = objects.v4b;
        claw = objects.claw;

        transfer = new Transfer(objects);
        intake = new Intake(objects);
    }

    public void update() {
        //chassis.setMovement(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x);

        if (gamepad.left_trigger > 0.1) {
            lift.decreasePosition((int)(gamepad.left_trigger * Ktrigger));
        }

        if (gamepad.right_trigger > 0.1) {
            lift.increasePosition((int)(gamepad.right_trigger * Ktrigger));
        }

        if (gamepad.triangle && !lastgamepad.triangle) {
            if (lift.state == Lift.LiftStates.INIT) lift.state = Lift.LiftStates.LOW_CHAMBER;
            else lift.state = Lift.LiftStates.INIT;
        }

        if (gamepad.cross && !lastgamepad.cross) {
            if (differential.clawState)
                differential.openClaw();
            else
                differential.closeClaw();
        }

        if (gamepad.square && !lastgamepad.square) {
            initiateTransfer = true;
        }

        if (gamepad.triangle && !lastgamepad.triangle) {
            differential.setState(Differential.DifferentialStates.INIT);
        }

        if (gamepad.right_stick_button && !lastgamepad.right_stick_button) {
            initiateIntake = true;
        }

        if (gamepad.circle && !lastgamepad.circle) {
            if (intake.getState() == Intake.IntakeStates.SCANNING)
                intake.setState(Intake.IntakeStates.PICK_UP);
        }

        if (Math.abs(gamepad.right_stick_x) > 0.2) {
            intakeClawRotation = claw.getClawRotation();
            intakeClawRotation += gamepad.right_stick_x * Krotation;
            claw.setClawRotation(intakeClawRotation);
        }

        transfer.update();
        intake.update();
    }
}
