package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.commands.Transfer.initiateTransfer;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotH;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotX;
import static org.firstinspires.ftc.teamcode.robot.StaticVariables.robotY;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.Transfer;
import org.firstinspires.ftc.teamcode.control.AutoFunctions;
import org.firstinspires.ftc.teamcode.objects.intake.Claw;
import org.firstinspires.ftc.teamcode.objects.intake.SensorTrio;
import org.firstinspires.ftc.teamcode.objects.outtake.Differential;
import org.firstinspires.ftc.teamcode.objects.intake.Extendo;
import org.firstinspires.ftc.teamcode.objects.outtake.Lift;
import org.firstinspires.ftc.teamcode.objects.intake.Virtual4Bar;
import org.firstinspires.ftc.teamcode.objects.drive.Chassis;
import org.firstinspires.ftc.teamcode.robot.AllObjects;
import org.firstinspires.ftc.teamcode.robot.RobotHardware;
import org.firstinspires.ftc.teamcode.robot.StaticVariables;
@Config
@Autonomous (name = "BasketAuto")
public class BasketAuto extends OpMode {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    private RobotHardware robot;
    private AllObjects objects;
    private AutoFunctions operations;

    private Chassis chassis;
    private Differential differential;
    private Lift lift;
    private Extendo extendo;
    private Virtual4Bar v4b;
    private Claw claw;
    private SensorTrio sensorTrio;

    private Transfer transfer;

    public static double targetX = 0, targetY = 0, targetH = 90, maximumSpeed = 1;
    private double lastTargetX = -1, lastTargetY = -1, lastTargetH = -1;


    private enum AutoInstructions {
        WAITING,
        INSTRUCTION_1,
        INSTRUCTION_2,
        INSTRUCTION_3,
        INSTRUCTION_4,
        INSTRUCTION_5,
        INSTRUCTION_6,
        INSTRUCTION_7,
        INSTRUCTION_8,
        INSTRUCTION_9,
        INSTRUCTION_9_5,
        INSTRUCTION_10,
        INSTRUCTION_11,
        INSTRUCTION_12,
        INSTRUCTION_13,
        INSTRUCTION_14,
        INSTRUCTION_15,
        INSTRUCTION_16,
        INSTRUCTION_17,
        INSTRUCTION_17_5,
        INSTRUCTION_18,
        INSTRUCTION_19,
        INSTRUCTION_20,
        INSTRUCTION_21,
        INSTRUCTION_22,
        INSTRUCTION_23,
        INSTRUCTION_24,
        INSTRUCTION_25,
        INSTRUCTION_25_5,
        INSTRUCTION_26,
        INSTRUCTION_27,
        INSTRUCTION_28,
        INSTRUCTION_29,
        INSTRUCTION_30,
        INSTRUCTION_31,
        INSTRUCTION_32,
        INSTRUCTION_33,
        INSTRUCTION_34,
        INSTRUCTION_35,
        INSTRUCTION_36,
        INSTRUCTION_37;
    }

    public static double x1 = 30, y1 = 35, h1 = 135; // front of the basket
    public static double x2 = 20, y2 = 40, h2 = 135; // scoring position
    public static double x3 = 24, y3 = 12, h3 = 180; // sample left
    public static double x4 = 24, y4 = 38, h4 = 180; // sample center
    public static double x5 = 92, y5 = -5, h5 = 270; // sample right
    public static double x6 = 140, y6 = -20, h6 = 0; // ready for parking
    public static double x7 = 140, y7 = -40, h7 = 270; // parking

    public static double yIncrease = 0.22;

    private AutoInstructions state, nextState;
    private ElapsedTime timer = new ElapsedTime();
    private double waitingTime;

    @Override
    public void init() {
        StaticVariables.init(hardwareMap, telemetry, gamepad1, gamepad2);

        robot = new RobotHardware();
        robot.init();

        objects = new AllObjects();
        objects.init(robot);

        chassis = objects.chassis;
        differential = objects.differential;
        lift = objects.lift;
        extendo = objects.extendo;
        v4b = objects.v4b;
        claw = objects.claw;
        sensorTrio = objects.sensorTrio;

        operations = new AutoFunctions();
        transfer = new Transfer(objects);

        state = AutoInstructions.INSTRUCTION_1;

        differential.closeClaw();
    }

    @Override
    public void loop() {
        if (targetX != lastTargetX || targetY != lastTargetY || targetH != lastTargetH) {
            operations.setNewTargetPoint(targetX, targetY, targetH, maximumSpeed);
        }

        lastTargetX = targetX; lastTargetY = targetY; lastTargetH = targetH;

        switch (state) {
            case WAITING:
                if (nextState == AutoInstructions.INSTRUCTION_8 || nextState == AutoInstructions.INSTRUCTION_16) {
                    if (!sensorTrio.detectedSample()) {
                        targetY += yIncrease;

                        if ((nextState == AutoInstructions.INSTRUCTION_8 && robotY > 25) || (nextState == AutoInstructions.INSTRUCTION_16 && robotY > 48))
                            state = nextState;
                    }
                    else
                        state = nextState;
                }
                else if (timer.seconds() > waitingTime)
                    state = nextState;

                break;


            case INSTRUCTION_1:
               targetX = x1; targetY = y1; targetH = h1;

                lift.setState(Lift.LiftStates.HIGH_BASKET);
                differential.setState(Differential.DifferentialStates.BASKET);

               if (targetReached()) {
                   state = AutoInstructions.INSTRUCTION_2;
               }

               break;

            case INSTRUCTION_2:

                state = AutoInstructions.INSTRUCTION_3;
                break;

            case INSTRUCTION_3:
                if (!lift.targetReached()) break;

                targetX = x2; targetY = y2; targetH = h2;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_4;
                }

                break;

            case INSTRUCTION_4:
                differential.openClaw();

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_5;
                waitingTime = 0.4; timer.reset();
                break;

            case INSTRUCTION_5:
                differential.setState(Differential.DifferentialStates.INIT);
                lift.setState(Lift.LiftStates.INIT);

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_6;
                waitingTime = 0; timer.reset();
                break;

            case INSTRUCTION_6:
                targetX = x3; targetY = y3; targetH = h3;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_7;
                }

                break;

            case INSTRUCTION_7:
                extendo.setState(Extendo.ExtendoStates.EXTENDED);
                v4b.setState(Virtual4Bar.V4BStates.SCANNING);
                claw.setWristState(Claw.WristState.SCAN);
                claw.setClawRotation(0.28);

                if (Math.abs(extendo.motor.getCurrentPosition() - extendo.BASKET_AUTO_LEFT) < 10) {
                    state = AutoInstructions.WAITING;
                    nextState = AutoInstructions.INSTRUCTION_8;
                    waitingTime = 0; timer.reset();
                }

                break;

            case INSTRUCTION_8:
                v4b.setState(Virtual4Bar.V4BStates.PICK_UP);
                claw.setWristState(Claw.WristState.PICK_UP);
                claw.setIntakeState(Claw.IntakeState.Intake);

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_9;
                waitingTime = 1; timer.reset();
                break;

            case INSTRUCTION_9:
                initiateTransfer = true;

                state = AutoInstructions.INSTRUCTION_9_5;

                break;

            case INSTRUCTION_9_5:

                targetX = x1; targetY = y1; targetH = h1;

                if (targetReached())
                    state = AutoInstructions.INSTRUCTION_10;

                break;

            case INSTRUCTION_10:
                if (transfer.getState() != Transfer.TransferStates.DISABLED) break;

                lift.setState(Lift.LiftStates.HIGH_BASKET);
                differential.setState(Differential.DifferentialStates.BASKET);

                state = AutoInstructions.INSTRUCTION_11;
                break;

            case INSTRUCTION_11:
                if (!lift.targetReached()) break;

                targetX = x2; targetY = y2; targetH = h2;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_12;
                }

                break;

            case INSTRUCTION_12:
                differential.openClaw();

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_13;
                waitingTime = 0.4; timer.reset();
                break;

            case INSTRUCTION_13:
                differential.setState(Differential.DifferentialStates.INIT);
                lift.setState(Lift.LiftStates.INIT);

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_14;
                waitingTime = 0; timer.reset();
                break;

            case INSTRUCTION_14:
                targetX = x4; targetY = y4; targetH = h4;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_15;
                }

                break;

            case INSTRUCTION_15:
                extendo.setState(Extendo.ExtendoStates.EXTENDED);
                v4b.setState(Virtual4Bar.V4BStates.SCANNING);
                claw.setWristState(Claw.WristState.SCAN);
                claw.setClawRotation(0.28);

                if (Math.abs(extendo.motor.getCurrentPosition() - extendo.BASKET_AUTO_CENTER) < 10) {
                    state = AutoInstructions.WAITING;
                    nextState = AutoInstructions.INSTRUCTION_16;
                    waitingTime = 0; timer.reset();
                }

                break;

            case INSTRUCTION_16:
                v4b.setState(Virtual4Bar.V4BStates.PICK_UP);
                claw.setWristState(Claw.WristState.PICK_UP);
                claw.setIntakeState(Claw.IntakeState.Intake);

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_17;
                waitingTime = 1; timer.reset();
                break;

            case INSTRUCTION_17:
                initiateTransfer = true;

                state = AutoInstructions.INSTRUCTION_17_5;
                break;

            case INSTRUCTION_17_5:
                targetX = x1; targetY = y1; targetH = h1;

                if (targetReached())
                    state = AutoInstructions.INSTRUCTION_18;

                break;


            case INSTRUCTION_18:
                if (transfer.getState() != Transfer.TransferStates.DISABLED) break;

                lift.setState(Lift.LiftStates.HIGH_BASKET);
                differential.setState(Differential.DifferentialStates.BASKET);

                state = AutoInstructions.INSTRUCTION_19;
                break;

            case INSTRUCTION_19:
                if (!lift.targetReached()) break;

                targetX = x2; targetY = y2; targetH = h2;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_20;
                }

                break;

            case INSTRUCTION_20:
                differential.openClaw();

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_21;
                waitingTime = 0.4; timer.reset();
                break;

            case INSTRUCTION_21:
                differential.setState(Differential.DifferentialStates.INIT);
                lift.setState(Lift.LiftStates.INIT);

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_22;
                waitingTime = 0; timer.reset();
                break;

            case INSTRUCTION_22:
                targetX = x5; targetY = y5; targetH = h5;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_23;
                }

                break;

            case INSTRUCTION_23:
                extendo.setState(Extendo.ExtendoStates.EXTENDED);
                v4b.setState(Virtual4Bar.V4BStates.SCANNING);
                claw.setWristState(Claw.WristState.SCAN);
                claw.setClawRotation(0.03);

                if (Math.abs(extendo.motor.getCurrentPosition() - extendo.BASKET_AUTO_RIGHT) < 10) {
                    state = AutoInstructions.WAITING;
                    nextState = AutoInstructions.INSTRUCTION_24;
                    waitingTime = 0.5; timer.reset();
                }

                break;

            case INSTRUCTION_24:
                v4b.setState(Virtual4Bar.V4BStates.PICK_UP);
                claw.setWristState(Claw.WristState.PICK_UP);
                claw.setIntakeState(Claw.IntakeState.Intake);

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_25;
                waitingTime = 1; timer.reset();
                break;

            case INSTRUCTION_25:
                initiateTransfer = true;

                state = AutoInstructions.INSTRUCTION_25_5;
                break;

            case INSTRUCTION_25_5:
                targetX = x1; targetY = y1; targetH = h1;

                if (targetReached())
                    state = AutoInstructions.INSTRUCTION_26;

                break;

            case INSTRUCTION_26:
                if (transfer.getState() != Transfer.TransferStates.DISABLED) break;

                targetX = x1; targetY = y1; targetH = h1;

                if (targetReached())
                    state = AutoInstructions.INSTRUCTION_27;

                break;

            case INSTRUCTION_27:
                lift.setState(Lift.LiftStates.HIGH_BASKET);
                differential.setState(Differential.DifferentialStates.BASKET);

                state = AutoInstructions.INSTRUCTION_28;
                break;

            case INSTRUCTION_28:
                if (!lift.targetReached()) break;

                targetX = x2; targetY = y2; targetH = h2;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_29;
                }

                break;

            case INSTRUCTION_29:
                differential.openClaw();

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_30;
                waitingTime = 0.4; timer.reset();
                break;

            case INSTRUCTION_30:
                differential.setState(Differential.DifferentialStates.INIT);
                lift.setState(Lift.LiftStates.INIT);

                state = AutoInstructions.WAITING;
                nextState = AutoInstructions.INSTRUCTION_31;
                waitingTime = 0; timer.reset();
                break;

            case INSTRUCTION_31:
                targetX = x6; targetY = y6; targetH = h6;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_32;
                }

                break;

            case INSTRUCTION_32:
                targetX = x7; targetY = y7; targetH = h7;

                if (targetReached()) {
                    state = AutoInstructions.INSTRUCTION_33;
                }

                break;

            case INSTRUCTION_33:
                differential.setState(Differential.DifferentialStates.PARKING_AUTO);

                state = AutoInstructions.INSTRUCTION_34;

                break;

            case INSTRUCTION_34:
                extendo.setPosition(-5);

                break;

        }

        operations.goToPoint();

        chassis.setMovement(operations.getRobotVelocityX(), operations.getRobotVelocityY(), operations.getRobotVelocityW());

        transfer.update();

        dashboardTelemetry.addData("Pozitie extendo", extendo.motor.getCurrentPosition());

        objects.update();
        robot.update();
        telemetry.update();
        dashboardTelemetry.update();
    }

    private boolean targetReached() {
        return (Math.sqrt(Math.pow(robotX - targetX, 2) + Math.pow(robotY - targetY, 2)) < 3 && Math.abs(robotH - targetH) < 3);
    }
}
