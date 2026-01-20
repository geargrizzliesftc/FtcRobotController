package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "Red Goal Auto (Refactored)", group = "Autonomous")
public class RedGoalSideLaunchAuto extends LinearOpMode {

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

        // Initial states logic removed (illegal motor power during init)

        waitForStart();

        if (opModeIsActive()) {

            // 1. Move backward off the launch zone
            // Original: Power -0.5 for 1200ms
            robot.driveStraight(-RobotActions.DRIVE_SPEED, RobotActions.GOAL_DRIVE_BACK_MS);

            // 2. Stop (implicit in driveStraight)

            // 3. Spin up flywheel and Fire 3 balls
            // Using standardized velocity and shooting logic.
            robot.shootBalls(3, RobotActions.FLYWHEEL_VELOCITY);

            // 4. Move onto the launch zone from sweet spot (Park)
            // Strafing RIGHT to park (mirrors Blue's Left strafe)
            robot.strafe(RobotActions.DRIVE_SPEED, RobotActions.GOAL_PARK_STRAFE_MS);

            // End of auto
            sleep(1000);
        }
    }
}
