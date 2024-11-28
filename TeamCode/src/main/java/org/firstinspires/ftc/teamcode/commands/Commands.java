package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.commands.Intake.initiateIntake;
import static org.firstinspires.ftc.teamcode.commands.Transfer.initiateTransfer;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.gamepad2;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.lastgamepad2;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.telemetry;

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
    public Outtake outtake;

    private double Ktrigger = 2;
    public static double Krotation = 0.01;
    private double intakeClawRotation;

    private Outtake.OuttakeStates outtakePosition = Outtake.OuttakeStates.DISABLED;

    public void init(AllObjects objects) {
        chassis = objects.chassis;
        extendo = objects.extendo;
        lift = objects.lift;
        differential = objects.differential;
        v4b = objects.v4b;
        claw = objects.claw;

        transfer = new Transfer(objects);
        intake = new Intake(objects);
        outtake = new Outtake(objects);
    }

    public void update() {
        // CHASISS

        chassis.setMovement(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x);

        // LIFT

        if (gamepad.left_trigger > 0.1) {
            lift.decreasePosition((int)(gamepad.left_trigger * Ktrigger));

            if (lift.getPosition() == 0)
                outtake.setState(Outtake.OuttakeStates.FINISH);
        }

        if (gamepad.right_trigger > 0.1) {
            lift.increasePosition((int)(gamepad.right_trigger * Ktrigger));
        }

        if (gamepad2.dpad_up && !lastgamepad2.dpad_up) {
            outtakePosition = Outtake.OuttakeStates.HIGH_CHAMBER;
        }

        if (gamepad2.dpad_down && !lastgamepad2.dpad_down) {
            outtakePosition = Outtake.OuttakeStates.LOW_CHAMBER;
        }

        if (gamepad2.triangle && !lastgamepad2.triangle) {
            outtakePosition = Outtake.OuttakeStates.HIGH_BASKET;
        }

        if (gamepad2.cross && !lastgamepad2.cross) {
            outtakePosition = Outtake.OuttakeStates.LOW_BASKET;
        }

        if (gamepad.right_bumper && !lastgamepad.right_bumper) {
            outtake.setState(outtakePosition);
        }

        if (gamepad.left_bumper && !lastgamepad.left_bumper) {
            outtake.setState(Outtake.OuttakeStates.FINISH);

            outtakePosition = Outtake.OuttakeStates.DISABLED;
        }

        if (gamepad.triangle && !lastgamepad.triangle) {
            if (lift.getState() == Lift.LiftStates.INIT)
                lift.setState(Lift.LiftStates.LOW_CHAMBER);
            else
                lift.setState(Lift.LiftStates.INIT);
        }

        //DIFFERENTIAL

        if (gamepad.left_stick_button && !lastgamepad.left_stick_button) {
            if (differential.clawState)
                differential.openClaw();
            else
                differential.closeClaw();
        }

        // TRANSFER

        if (gamepad.square && !lastgamepad.square) {
            initiateTransfer = true;
        }

        // INTAKE

        if (gamepad.right_stick_button && !lastgamepad.right_stick_button) {
            initiateIntake = true;
        }

        if (gamepad.circle && !lastgamepad.circle) {
            if (intake.getState() == Intake.IntakeStates.SCANNING)
                intake.setState(Intake.IntakeStates.PICK_UP);
        }

        if (Math.abs(gamepad2.right_stick_x) > 0.2) {
            intakeClawRotation = claw.getClawRotation();
            intakeClawRotation += gamepad2.right_stick_x * Krotation;

            if (intakeClawRotation < 0.03)
                intakeClawRotation = 0.03;

            if (intakeClawRotation > 0.57)
                intakeClawRotation = 0.57;

            claw.setClawRotation(intakeClawRotation);
        }

        telemetry.addData("Target outtake", outtakePosition);

        transfer.update();
        intake.update();
        outtake.update();
    }
}
