package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Motor Button Control", group = "Examples")
public class topmotorrotate extends LinearOpMode {


    private CRServo rightServo;
    private CRServo leftServo;
    private DcMotor LowMotor;
    private Servo openServo;


    @Override
    public void runOpMode() {
        // Map the motor from your hardware configuration
        LowMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        rightServo = hardwareMap.get(CRServo.class, "servo_right");
        leftServo = hardwareMap.get(CRServo.class, "servo_left");
        openServo = hardwareMap.get(Servo.class, "servo_open");
        telemetry.addLine("Press Up to spin, Down to stop");
        telemetry.update();
        openServo.setDirection(Servo.Direction.REVERSE);


        waitForStart();

        while (opModeIsActive()) {


            if (gamepad1.x) {
                rightServo.setPower(-1.0);
            } else if (gamepad1.y) {
                rightServo.setPower(0.0);
            }
            if (gamepad1.x) {
                leftServo.setPower(1.0);

            } else if (gamepad1.y) {
                leftServo.setPower(0.0);
            }
            if (gamepad1.dpad_left) {
                // Spin motor forward
                LowMotor.setPower(-0.35);
            } else if (gamepad1.dpad_right) {
                // Stop the motor
                LowMotor.setPower(0.0);
            }
            if (gamepad2.x) {
                openServo.setPosition(0.5);
            } else if (gamepad2.y) {
                openServo.setPosition(0.15);
            }
        }
    }
}