package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx; // Import DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This is NOT an OpMode.
 * This class defines all the hardware for the robot and handles its initialization.
 */
public class RobotHardware {

    /* Public Hardware Objects - Use DcMotorEx */
    public DcMotorEx  frontLeft   = null;
    public DcMotorEx  frontRight  = null;
    public DcMotorEx  backLeft    = null;
    public DcMotorEx  backRight   = null;
    public DcMotorEx  flywheel    = null;
    public Servo      gate        = null;

    /* Local Members */
    private HardwareMap hwMap = null;

    /**
     * Initializes all the robot's hardware.
     * @param ahwMap The hardware map from the OpMode.
     */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        // --- HARDWARE MAPPING ---
        // Cast the result of .get() to DcMotorEx
        frontLeft   = hwMap.get(DcMotorEx.class, "front_left_drive");
        frontRight  = hwMap.get(DcMotorEx.class, "front_right_drive");
        backLeft    = hwMap.get(DcMotorEx.class, "back_left_drive");
        backRight   = hwMap.get(DcMotorEx.class, "back_right_drive");
        flywheel    = hwMap.get(DcMotorEx.class, "sky_motor");
        gate        = hwMap.get(Servo.class, "servo_open");

        // --- SET MOTOR DIRECTIONS ---
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        gate.setDirection(Servo.Direction.REVERSE);

        // --- SET MOTOR MODES & INITIAL STATES ---
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        flywheel.setPower(0);

        // Set flywheel to use encoders for consistent speed
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set zero power behavior to BRAKE to resist movement when stopped
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
