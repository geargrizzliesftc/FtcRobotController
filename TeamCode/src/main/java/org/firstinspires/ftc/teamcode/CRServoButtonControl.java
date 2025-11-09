package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "CRServo Button Control", group = "Examples")
@Disabled
public class CRServoButtonControl extends LinearOpMode {

    private CRServo rightServo;
    private CRServo leftServo;
    private CRServo topServo;
    @Override
    public void runOpMode() {
        // Map servo from the hardware configuration
        rightServo = hardwareMap.get(CRServo.class, "servo_right");
        leftServo = hardwareMap.get(CRServo.class, "servo_left");
        topServo = hardwareMap.get(CRServo.class, "servo_top");
        telemetry.addLine("Press A to spin, B to stop");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                // Spin the servo forward
                rightServo.setPower(1.0);
            } else if (gamepad1.y) {
                // Stop the servo
                rightServo.setPower(0.0);
            }
            if (gamepad1.x) {
                // Spin the servo forward
                leftServo.setPower(-1.0);
            } else if (gamepad1.y) {
                // Stop the servo
                leftServo.setPower(0.0);
            }
            if (gamepad1.dpad_up) {
                // Spin the servo forward
                topServo.setPower(-1.0);
            } else if (gamepad1.dpad_down) {
                // Stop the servo
                topServo.setPower(0.0);
            }
            telemetry.addData("Servo Power", rightServo.getPower());
            telemetry.update();
            telemetry.addData("Servo Power", leftServo.getPower());
            telemetry.update();
        }
    }
}