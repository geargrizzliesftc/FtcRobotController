package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Motor Button Control", group = "Examples")
public class topmotorrotate extends LinearOpMode {

    private DcMotor topmotor;
    private CRServo rightServo;
    private CRServo leftServo;


    @Override
    public void runOpMode() {
        // Map the motor from your hardware configuration
        topmotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        rightServo = hardwareMap.get(CRServo.class, "servo_right");
        leftServo = hardwareMap.get(CRServo.class, "servo_left");
        telemetry.addLine("Press Up to spin, Down to stop");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                // Spin motor forward
                topmotor.setPower(1.0);
            } else if (gamepad1.dpad_down) {
                // Stop the motor
                topmotor.setPower(0.0);
            }

            telemetry.addData("Motor Power", topmotor.getPower());
            telemetry.update();

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

        }
    }
}