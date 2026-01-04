package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * This class contains reusable robot actions (helper methods).
 * It extends RobotHardware to get access to all the robot's hardware.
 */
public class RobotActions extends RobotHardware {

    private LinearOpMode myOpMode; // Reference to the OpMode that is using this class

    // --- CONSTANTS ---
    public static final double FLYWHEEL_VELOCITY = 1600; // TUNE THIS VALUE
    public static final double GATE_OPEN_POS   = 0.27;
    public static final double GATE_CLOSED_POS = 0.5;

    /**
     * Constructor for the RobotActions class.
     * @param opMode The LinearOpMode that is using these actions (needed for sleep() and opModeIsActive()).
     */
    public RobotActions(LinearOpMode opMode) {
        myOpMode = opMode;
    }

    // --- HIGH-LEVEL ACTIONS ---

    /**
     * Spins up the flywheel, shoots a specified number of balls, and stops the flywheel.
     * @param numberOfBalls The number of balls to shoot.
     */
    public void shootBalls(int numberOfBalls) {
        // Spin up flywheel to the target velocity
        flywheel.setVelocity(FLYWHEEL_VELOCITY);
        myOpMode.sleep(1500); // Wait for the flywheel to reach speed

        // --- ADD THIS TELEMETRY BLOCK ---
        // Log the actual velocity right before the first shot
        double currentVelocity = flywheel.getVelocity();
        myOpMode.telemetry.addData("Flywheel Target Velocity", FLYWHEEL_VELOCITY);
        myOpMode.telemetry.addData("Flywheel Actual Velocity", currentVelocity);
        myOpMode.telemetry.update();
        // --- END OF TELEMETRY BLOCK ---

        // Optional: Add a small extra delay to read the telemetry on the phone screen
        myOpMode.sleep(1000);

        for (int i = 0; i < numberOfBalls; i++) {
            if (!myOpMode.opModeIsActive()) break; // Exit if OpMode is stopped

            gate.setPosition(GATE_OPEN_POS);
            myOpMode.sleep(400);
            gate.setPosition(GATE_CLOSED_POS);
            myOpMode.sleep(750); // Shorter, consistent wait for speed recovery
        }

        flywheel.setVelocity(0); // Stop the flywheel
    }

    // --- BASIC MOVEMENT ACTIONS ---

    /**
     * Drives the robot straight forward or backward for a given time.
     * @param power The power to set the motors (-1.0 to 1.0).
     * @param timeMs The duration of the movement in milliseconds.
     */
    public void driveStraight(double power, long timeMs) {
        if (!myOpMode.opModeIsActive()) return;
        setDriveMotorPower(power);
        myOpMode.sleep(timeMs);
        stopDriving();
    }

    /**
     * Turns the robot on the spot for a given time.
     * @param power The power for turning. Positive power turns right, negative turns left.
     * @param timeMs The duration of the turn in milliseconds.
     */
    public void turn(double power, long timeMs) {
        if (!myOpMode.opModeIsActive()) return;
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(power);
        backRight.setPower(power);
        myOpMode.sleep(timeMs);
        stopDriving();
    }

    /**
     * Stops all four drive motors.
     */
    public void stopDriving() {
        setDriveMotorPower(0);
    }

    /**
     * Private helper method to set power for all four drive motors at once.
     * @param power The power to set for each drive motor.
     */
    private void setDriveMotorPower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }
}
