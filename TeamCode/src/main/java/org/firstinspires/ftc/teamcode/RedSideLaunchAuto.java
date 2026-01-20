package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
@Autonomous(name = "Red Launch Auto (Refactored)", group = "Autonomous")
public class RedSideLaunchAuto extends LinearOpMode {

    RobotActions robot;

    DcMotor flywheel;
    Servo gate;

    @Override
    public void runOpMode() throws InterruptedException {

        // --- INITIALIZATION ---
        flywheel = hardwareMap.get(DcMotor.class, "sky_motor");
        gate = hardwareMap.get(Servo.class, "servo_open");

        robot = new RobotActions(this);
        robot.init(hardwareMap);

        robot.gate.setPosition(RobotActions.GATE_CLOSED_POS);

        telemetry.addLine("Robot Initialized. Ready for Red Side.");
        telemetry.update();

        waitForStart();

        // --- AUTONOMOUS ROUTINE ---
        if (opModeIsActive()) {

            // Step 1: Drive forward off the launch wall
            robot.driveStraight(RobotActions.DRIVE_SPEED, RobotActions.LAUNCH_DRIVE_FORWARD_MS);

            // Step 2: Turn to face the goal (RED turns LEFT/Negative)
            robot.turn(-RobotActions.TURN_SPEED, RobotActions.LAUNCH_TURN_TO_BASKET_MS);

            // Step 3: Shoot 3 balls
            robot.shootBalls(3, RobotActions.FLYWHEEL_VELOCITY);

            // Step 4: Turn to face away from the goal (RED turns RIGHT/Positive)
            robot.turn(RobotActions.TURN_SPEED, RobotActions.LAUNCH_TURN_TO_PARK_MS);

            // Step 5: Drive backward PARTWAY towards the start line
            robot.driveStraight(-RobotActions.DRIVE_SPEED, RobotActions.LAUNCH_BACKUP_MS);

            // Step 6: Park Turn
            // Blue turned LEFT (-Speed). Red should turn RIGHT (+Speed) to mirror.
            robot.turn(RobotActions.TURN_SPEED, RobotActions.LAUNCH_PARK_TURN_MS);

            // Step 7: Drive Forward to clear the zone
            robot.driveStraight(RobotActions.DRIVE_SPEED, RobotActions.LAUNCH_PARK_FORWARD_MS);

            sleep(2000);
        }
    }
}
