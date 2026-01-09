package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Blue Launch Auto v2 (Refactored)", group = "Autonomous")
public class BlueGoalSideLaunchAutoMain extends LinearOpMode {

    // Create an instance of our robot actions class.
    // This single object will control the robot.
    RobotActions robot;

    // --- CONSTANTS FOR THIS SPECIFIC PATH ---
    private static final double DRIVE_SPEED = 0.5;
    private static final double TURN_SPEED  = 0.5;

    private static final long DRIVE_FORWARD_MS = 1500;
    private static final long DRIVE_BACKWARD_MS = 1500;
    private static final long TURN_TO_BASKET_MS = 245;
    private static final long TURN_TO_PARK_MS = 380;
    private static final long PARK_DRIVE_MS = 2060;

    @Override
    public void runOpMode() throws InterruptedException {

        // --- INITIALIZATION ---
        // Create and initialize the robot object.
        robot = new RobotActions(this);
        robot.init(hardwareMap);

        // Set the initial position of the gate.
        robot.gate.setPosition(RobotActions.GATE_CLOSED_POS);

        telemetry.addLine("Robot Initialized. Ready for Blue Side.");
        telemetry.update();

        waitForStart();

        // --- AUTONOMOUS ROUTINE ---
        if (opModeIsActive()) {

            // Step 1: Drive backward off the launch wall
            robot.driveStraight(-DRIVE_SPEED, DRIVE_FORWARD_MS);


            // Step 3: Shoot 3 balls
            robot.shootBalls(3);

            // Step 5: Drive forward to park
            robot.driveStraight(DRIVE_SPEED, DRIVE_FORWARD_MS);
        }
    }
}