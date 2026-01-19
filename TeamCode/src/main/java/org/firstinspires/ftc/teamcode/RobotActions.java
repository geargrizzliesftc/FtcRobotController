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
    public static final double FLYWHEEL_VELOCITY = 1900; // TUNE THIS VALUE
    public static final double GATE_OPEN_POS = 0.25;
    public static final double GATE_CLOSED_POS = 0.5;

    /**
     * Constructor for the RobotActions class.
     * 
     * @param opMode The LinearOpMode that is using these actions (needed for
     *               sleep() and opModeIsActive()).
     */
    public RobotActions(LinearOpMode opMode) {
        myOpMode = opMode;
    }

    // --- HIGH-LEVEL ACTIONS ---

    /**
     * Spins up the flywheel, shoots a specified number of balls, and stops the
     * flywheel.
     * 
     * @param numberOfBalls  The number of balls to shoot.
     * @param targetVelocity The target velocity for the flywheel.
     */
    public void shootBalls(int numberOfBalls, double targetVelocity) {
        // Spin up flywheel to the target velocity
        flywheel.setVelocity(targetVelocity);

        for (int i = 0; i < numberOfBalls; i++) {
            if (!myOpMode.opModeIsActive())
                break;

            waitForFlywheelVelocity(targetVelocity, i + 1);

            // FIRE
            gate.setPosition(GATE_OPEN_POS);
            myOpMode.sleep(500);
            gate.setPosition(GATE_CLOSED_POS);
            myOpMode.sleep(2000); // Allow time for the gate to fully close
        }

        flywheel.setVelocity(0); // Stop the flywheel
    }

    // --- BASIC MOVEMENT ACTIONS ---

    /**
     * Drives the robot straight forward or backward for a given time.
     * 
     * @param power  The power to set the motors (-1.0 to 1.0).
     * @param timeMs The duration of the movement in milliseconds.
     */
    public void driveStraight(double power, long timeMs) {
        if (!myOpMode.opModeIsActive())
            return;
        setDriveMotorPower(power);
        myOpMode.sleep(timeMs);
        stopDriving();
    }

    /**
     * Turns the robot on the spot for a given time.
     * 
     * @param power  The power for turning. Positive power turns right, negative
     *               turns left.
     * @param timeMs The duration of the turn in milliseconds.
     */
    public void turn(double power, long timeMs) {
        if (!myOpMode.opModeIsActive())
            return;
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(power);
        backRight.setPower(power);
        myOpMode.sleep(timeMs);
        stopDriving();
    }

    /**
     * Strafes the robot (moves sideways) for a given time.
     *
     * @param power  The power for strafing. Positive power strafes right, negative
     *               strafes left.
     * @param timeMs The duration of the strafe in milliseconds.
     */
    public void strafe(double power, long timeMs) {
        if (!myOpMode.opModeIsActive())
            return;
        // Strafe Right: FL+, FR-, BL-, BR+
        // Strafe Left: FL-, FR+, BL+, BR-
        frontLeft.setPower(power);
        backLeft.setPower(-power);
        frontRight.setPower(-power);
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
     * Private helper to wait for the flywheel to reach the target velocity.
     * 
     * @param targetVelocity The target velocity to reach.
     * @param ballNumber     The current ball number (for telemetry).
     */
    private void waitForFlywheelVelocity(double targetVelocity, int ballNumber) {
        long checkStart = System.currentTimeMillis();
        // Wait up to 1.5 seconds for velocity to recover/reach target
        while (myOpMode.opModeIsActive() && (System.currentTimeMillis() - checkStart < 1500)) {
            double currentVelocity = flywheel.getVelocity();

            // Show telemetry status
            myOpMode.telemetry.addData("Shooting Ball", ballNumber);
            myOpMode.telemetry.addData("Target Velocity", targetVelocity);
            myOpMode.telemetry.addData("Actual Velocity", currentVelocity);
            myOpMode.telemetry.update();

            // Ready to shoot if within 2% of target
            if (currentVelocity >= targetVelocity * 0.98) {
                break;
            }
            myOpMode.sleep(20);
        }
        // Stabilization delay
        myOpMode.sleep(250);
    }

    /**
     * private helper method to set power for all four drive motors at once.
     * 
     * @param power The power to set for each drive motor.
     */
    private void setDriveMotorPower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }
}
