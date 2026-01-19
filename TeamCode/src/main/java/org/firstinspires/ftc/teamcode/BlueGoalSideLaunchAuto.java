package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Blue Goal Auto (Refactored)", group = "Autonomous")
public class BlueGoalSideLaunchAuto extends LinearOpMode {

    // Use RobotActions for all hardware control
    RobotActions robot;

    @Override
    public void runOpMode() throws InterruptedException {

        // --- INITIALIZATION ---
        robot = new RobotActions(this);
        robot.init(hardwareMap);

        // Ensure gate is closed for holding the pre-loaded ring/ball
        robot.gate.setPosition(RobotActions.GATE_CLOSED_POS);

        telemetry.addLine("Ready to move and shoot (Refactored)");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            // 1. Move backward off the launch zone
            // Original: Power -0.5 for 1200ms
            robot.driveStraight(-0.5, 1200);

            // 2. Stop all movement (handled by driveStraight, but conceptually here)

            // 3. Spin up flywheel and Fire 3 balls
            // Original: Power 0.72, sleep 1570, then shoot loop.
            // New: Uses encoder-based velocity from RobotActions.
            robot.shootBalls(3, RobotActions.FLYWHEEL_VELOCITY);

            // 4. Move onto the launch zone from sweet spot (Park)
            // Original: Strafe Left (FL -0.5, BL 0.5, FR 0.5, BR -0.5) for 1300ms
            robot.strafe(-0.5, 1300);

            // End of auto
            sleep(1000);
        }
    }
}
