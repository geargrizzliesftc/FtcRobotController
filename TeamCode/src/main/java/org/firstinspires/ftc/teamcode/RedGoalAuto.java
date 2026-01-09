package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Red Goal Auto", group = "Autonomous")
public class RedGoalAuto extends LinearOpMode {

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

            // 1. Move backward off the launch zone
            frontLeft.setPower(-0.5);
            backLeft.setPower(-0.5);
            frontRight.setPower(-0.5);
            backRight.setPower(-0.5);
            sleep(1500); // adjust to clear the triangle


            // 2.  Stop all movement
            frontLeft.setPower(0);
            frontRight.setPower(0);

            backLeft.setPower(0);
            backRight.setPower(0);

            // 3. Spin up flywheel
            flywheel.setPower(0.8);
            sleep(1570); // reach speed

            // 4. Fire 3 balls
            for (int i = 0; i < 3; i++) {
                gate.setPosition(0.27); // open
                sleep(400);
                gate.setPosition(0.5); // close
                sleep(1700);


            }

            // Stop flywheel
            flywheel.setPower(0);

            //below is the code for going to human player
            // 5. Turn 90 degrees to face human player
            //frontLeft.setPower(-0.5);
            //backLeft.setPower(-0.5);
            //frontRight.setPower(0.5);
            //backRight.setPower(0.5);
            //sleep(750);
            // 6. go forward towards the human player
            //frontLeft.setPower(0.5);
            //backLeft.setPower(0.5);
            //frontRight.setPower(0.5);
            //backRight.setPower(0.5);
            //sleep(2000);

            // 7. Stop all movement
            //frontLeft.setPower(0);
            //frontRight.setPower(0);
            //backLeft.setPower(0);
            //backRight.setPower(0);
            //above is the code for going to human player

            //below is the code for going to park zone ate end of auto period

            //5. Move onto the launch zone from sweet spot
            frontLeft.setPower(0.5);
            backLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backRight.setPower(0.5);
            sleep(1560); // adjust to clear the triangle

            //above is the code for going to park zone ate end of auto period
        }
    }
}
