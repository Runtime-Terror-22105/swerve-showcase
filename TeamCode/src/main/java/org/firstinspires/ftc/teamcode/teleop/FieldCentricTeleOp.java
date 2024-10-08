package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.util.localizers.SwerveLocalizer;
import org.firstinspires.ftc.teamcode.util.math.Coordinate;
import org.firstinspires.ftc.teamcode.util.pid.PID;
import org.firstinspires.ftc.teamcode.util.pid.PidController;
import org.firstinspires.ftc.teamcode.util.pid.PidfController;

@Config
@Photon
@TeleOp
public class FieldCentricTeleOp extends LinearOpMode {
    public static final double DRIVESPEED = 1.0;
    private static final double ROTATION_SPEED = 180;
    private final RobotHardware hardware = new RobotHardware();
    private final Robot robot = new Robot();
    private FtcDashboard dashboard;
    private double headingLockAngle = 0; // the angle to lock the heading to, in radians

    public static PidController.PidCoefficients heading_coeff = new PidController.PidCoefficients(0, 0, 0);
    public static PidController heading = new PidController(heading_coeff);

    @Override
    public void runOpMode() {
        hardware.init(hardwareMap, LynxModule.BulkCachingMode.MANUAL);
        robot.init(this, hardware, telemetry);

        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        double initialX = 0;
        double initialY = 0;
        double initialHeading = 0;
//        SwerveLocalizer localizer = new SwerveLocalizer(
//            initialX,
//            initialY,
//            initialHeading,
//            robot
//        );

        waitForStart();
        robot.imu.setPollTime(2);

        ElapsedTime loopTimer = new ElapsedTime();
        while (opModeIsActive()) {
            // Manually clear the bulk read cache. Deleting this would be catastrophic b/c stale
            // vals would be used.
            for (LynxModule hub : hardware.allHubs) {
                hub.clearBulkCache();
            }

            // region Driving
//            localizer.update();
            double robotAngle = robot.imu.readLastAngle().yaw;

            Coordinate direction = new Coordinate(slr(-gamepad1.left_stick_x), slr(gamepad1.left_stick_y))
                                        .rotate(-robotAngle);
            double rotation;
            if (gamepad1.y) { // heading lock
//                rotation = headingLockAngle - robotAngle;
                this.headingLockAngle += Math.pow(slr(-gamepad1.right_stick_x), 11) * ROTATION_SPEED;
                heading.setTargetPosition(this.headingLockAngle);
                heading.calculatePower(robotAngle);
                rotation = heading.power;
            } else {
                rotation = Math.pow(slr(-gamepad1.right_stick_x), 11);
            }

            robot.drivetrain.move(
                    direction,
                    rotation,
                    DRIVESPEED
            );



            // endregion driving

            telemetry.addData("Loop time (ms)", loopTimer.milliseconds());
            telemetry.addData("Loop time (hz)", 1000/loopTimer.milliseconds());
            telemetry.addData("Robot angle (degrees)", Math.toDegrees(robotAngle));
            telemetry.update();
            loopTimer.reset();
        }

        robot.shutdown();
    }
    public double slr(double joystick_value){
        return (Math.pow(joystick_value,3)+joystick_value)/2;
    }
}
