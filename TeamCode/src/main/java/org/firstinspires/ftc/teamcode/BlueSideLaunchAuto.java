package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Blue Side Launch Auto", group = "Autonomous")
public class BlueSideLaunchAuto extends LinearOpMode {

    // Drive motors
    DcMotor frontLeft, frontRight, backLeft, backRight;

    // Flywheel and gate
    DcMotor flywheel;
    Servo gate;

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware mapping
        frontLeft = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRight = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeft = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRight = hardwareMap.get(DcMotor.class, "back_right_drive");

        flywheel = hardwareMap.get(DcMotor.class, "sky_motor");
        gate = hardwareMap.get(Servo.class, "servo_open");

        // Set motor directions
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        gate.setDirection(Servo.Direction.REVERSE);

        // Initial states
        flywheel.setPower(0.7);
        gate.setPosition(0.5); // closed

        telemetry.addLine("Ready to move and shoot");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            // 1. Move forward off the launch zone
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);
            sleep(3350); // adjust to clear the triangle

            // 2. Strafe left toward center
            frontLeft.setPower(-0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(-0.5);
            sleep(400); // adjust for distance

            // 3. Turn to face blue basket
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);
            sleep(450); // adjust for angle

            // Stop all movement
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // 4. Spin up flywheel
            flywheel.setPower(0.7);
            sleep(1500); // reach speed

            // 5. Fire 3 balls
            for (int i = 0; i < 3; i++) {
                gate.setPosition(0.35); // open
                sleep(400);
                gate.setPosition(0.5); // close
                sleep(1000);


            }

            // Stop flywheel
            flywheel.setPower(0);

            // 4. Turn to face blue parking
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);
            sleep(500); // adjust for angle

            // 5. Move backward off the launch zone
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);
            sleep(2350); // adjust to clear the triangle

            // 6. Strafe right toward park
            frontLeft.setPower(0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(0.5);
            sleep(900); // adjust for distance
        }
    }
}
