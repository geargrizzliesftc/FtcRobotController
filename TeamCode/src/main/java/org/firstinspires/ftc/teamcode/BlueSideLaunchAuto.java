package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Blue Launch Auto (Refactored)", group = "Autonomous")
public class BlueSideLaunchAuto extends LinearOpMode {

    // Create an instance of our robot actions class.
    // This single object will control the robot.
    RobotActions robot;

    // --- CONSTANTS FOR THIS SPECIFIC PATH ---
    private static final double DRIVE_SPEED = 0.5;
    private static final double TURN_SPEED = 0.5;

    private static final long DRIVE_FORWARD_MS = 2300;
    private static final long TURN_TO_BASKET_MS = 200;
    private static final long TURN_TO_PARK_MS = 380;
    private static final long PARK_DRIVE_MS = 2060;

    DcMotor flywheel;
    Servo gate;

    @Override
    public void runOpMode() throws InterruptedException {

        // --- INITIALIZATION ---
        // Create and initialize the robot object.
        flywheel = hardwareMap.get(DcMotor.class, "sky_motor");
        gate = hardwareMap.get(Servo.class, "servo_open");

        robot = new RobotActions(this);
        robot.init(hardwareMap);

        // Set the initial position of the gate.
        robot.gate.setPosition(RobotActions.GATE_CLOSED_POS);

        telemetry.addLine("Robot Initialized. Ready for Blue Side.");
        telemetry.update();

        // Initial states - NOTE: Do not set motor power during init!
        // flywheel.setPower(0.7); // ILLEGAL: Moving motors during init
        // gate.setPosition(0.5); // Redundant: Already set above via
        // robot.gate.setPosition

        waitForStart();

        // --- AUTONOMOUS ROUTINE ---
        if (opModeIsActive()) {

            // Step 1: Drive forward off the launch wall
            robot.driveStraight(DRIVE_SPEED, DRIVE_FORWARD_MS);

            // Step 2: Turn to face the goal
            robot.turn(TURN_SPEED, TURN_TO_BASKET_MS);

            // Step 3: Shoot 3 balls
            robot.shootBalls(3, RobotActions.FLYWHEEL_VELOCITY);

            // Step 4: Turn to face away from the goal (align with return path)
            robot.turn(-TURN_SPEED, TURN_TO_PARK_MS);

            // Step 5: Drive backward PARTWAY towards the start line
            // (Reduced from 2060ms to ~1400ms to stop before the wall)
            robot.driveStraight(-DRIVE_SPEED, 1400);

            // Step 6: Turn LEFT (90 degrees relative to current path)
            // Note: Positive power turns right, Negative turns left.
            // We need to verify 90 deg timing, starting with approx 600ms?
            robot.turn(-TURN_SPEED, 600);

            // Step 7: Drive Forward to clear the zone
            robot.driveStraight(DRIVE_SPEED, 800);

            sleep(2000); // Verify servo position before OpMode ends
        }
    }
}
