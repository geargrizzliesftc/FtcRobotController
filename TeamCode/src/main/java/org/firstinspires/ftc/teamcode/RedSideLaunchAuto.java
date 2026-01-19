package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("unused")
@Autonomous(name = "Red Launch Auto (Refactored)", group = "Autonomous")
public class RedSideLaunchAuto extends LinearOpMode {

    // Create an instance of our robot actions class.
    // This single object will control the robot.
    RobotActions robot;

    // --- CONSTANTS FOR THIS SPECIFIC PATH ---
    private static final double DRIVE_SPEED = 0.5;
    private static final double TURN_SPEED = 0.5;

    // Timing constants (mirrored or adjusted from Blue side)
    private static final long DRIVE_FORWARD_MS = 2300;

    // RED SIDE TURN LOGIC:
    // To face the goal (which is to the left when starting on Red),
    // we need to turn LEFT (negative power).
    // Original code: FL=0.5, BL=0.5, FR=-0.5, BR=-0.5 (Right Turn?)
    // Wait, let's verify logic.
    // - Positive Power Turn (robot.turn(positive)): Left motors Reverse (-), Right
    // motors Forward (+). This is a RIGHT turn (Clockwise).
    // - Negative Power Turn (robot.turn(negative)): Left motors Forward (+), Right
    // motors Reverse (-). This is a LEFT turn (Counter-Clockwise).

    // Original Red Code Line 62: FL=0.5, BL=0.5, FR=-0.5, BR=-0.5.
    // Left side is POSITIVE power. Right side is NEGATIVE power.
    // This creates a RIGHT turn (Clockwise).
    // The Red goal is usually to the "Right" relative to the robot if it drives
    // forward from the wall?
    // Let's stick to the original code's logic of turning.

    private static final long TURN_TO_BASKET_MS = 245;

    // Turning away to park (Line 115 original: FL=-0.5, BL=-0.5, FR=0.5, BR=0.5)
    // Left side NEGATIVE. Right side POSITIVE.
    // This matches `robot.turn(0.5)` (Positive Power Turn -> Right Turn in
    // RobotActions??)
    // Wait. RobotActions.turn(power):
    // frontLeft.setPower(-power);
    // backLeft.setPower(-power);
    // frontRight.setPower(power);
    // backRight.setPower(power);
    // If power is POSITIVE (0.5):
    // Left gets -0.5. Right gets 0.5.
    // Left goes BACKWARD. Right goes FORWARD.
    // This is a CLOCKWISE (RIGHT) Turn.

    // Original Red Code Line 115: FL=-0.5, Right=0.5.
    // This is indeed a RIGHT Turn.

    // So:
    // 1. Original Turn 1 (Line 62): FL=0.5, R=-0.5.
    // Left Forward, Right Backward. -> LEFT Turn (Counter-Clockwise).
    // RobotActions.turn(-0.5) should yield Left Forward (+0.5), Right Backward
    // (-0.5).
    // So Turn 1 is robot.turn(-TURN_SPEED, ...).

    // 2. Original Turn 2 (Line 115): FL=-0.5, R=0.5.
    // Left Backward, Right Forward. -> RIGHT Turn (Clockwise).
    // RobotActions.turn(0.5) yields Left Backward (-0.5), Right Forward (0.5).
    // So Turn 2 is robot.turn(TURN_SPEED, ...).

    private static final long TURN_TO_PARK_MS = 340;

    private static final long PARK_DRIVE_MS = 2060;
    private static final long PARK_STRAFE_MS = 900;

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

        telemetry.addLine("Robot Initialized. Ready for Red Side.");
        telemetry.update();

        // Initial states - NOTE: Do not set motor power during init!
        // flywheel.setPower(0.7); // ILLEGAL
        // gate.setPosition(0.5); // Redundant

        waitForStart();

        // --- AUTONOMOUS ROUTINE ---
        if (opModeIsActive()) {

            // Step 1: Drive forward off the launch wall
            // Original: Power 0.5, 2300ms
            robot.driveStraight(DRIVE_SPEED, DRIVE_FORWARD_MS);

            // Step 2: Turn to face the goal
            // Original: FL=0.5, FR=-0.5 (Left side forward, Right side back) -> LEFT Turn
            // (CCW)
            // RobotActions.turn(negative_power) -> Left(+), Right(-)
            robot.turn(-TURN_SPEED, TURN_TO_BASKET_MS);

            // Step 3: Shoot 3 balls
            // Original: Flywheel 0.8 power (approx 2100-2200 vel? usually 1700-1900 is
            // enough).
            // Blue used RobotActions.FLYWHEEL_VELOCITY (1900). Let's stick to standard.
            robot.shootBalls(3, RobotActions.FLYWHEEL_VELOCITY);

            // Step 4: Turn to face parking area
            // Original: FL=-0.5, FR=0.5 (Left side back, Right side forward) -> RIGHT Turn
            // (CW)
            // RobotActions.turn(positive_power) -> Left(-), Right(+)
            robot.turn(TURN_SPEED, TURN_TO_PARK_MS);

            // Step 5: Drive backward PARTWAY towards the start line
            // (Reduced from 2060ms to ~1400ms to stop before the wall)
            robot.driveStraight(-DRIVE_SPEED, 1400);

            // Step 6: Turn LEFT (90 degrees relative to current path)
            // Note: Positive power turns right, Negative turns left.
            // Incorporating similar logic to Blue Side (which turns Left here).
            // Checking previous Red code, it also wanted to "Strafe Left".
            robot.turn(-TURN_SPEED, 600);

            // Step 7: Drive Forward to clear the zone
            robot.driveStraight(DRIVE_SPEED, 800);

            sleep(2000); // Verify servo position before OpMode ends
        }
    }
}
