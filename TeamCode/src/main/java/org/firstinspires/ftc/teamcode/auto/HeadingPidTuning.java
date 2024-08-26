package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.util.math.Algebra.sign;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.util.math.Angle;
import org.firstinspires.ftc.teamcode.util.math.Coordinate;
import org.firstinspires.ftc.teamcode.util.pid.PidController;

@Autonomous(name="HeadingPidTuning", group="concept")
@Config
@Photon
public class HeadingPidTuning extends LinearOpMode {
    public RobotHardware hw= new RobotHardware();
    private FtcDashboard dashboard;
    public static double targetAngle = Math.PI/2;

    public static PidController.PidCoefficients heading_coeff = new PidController.PidCoefficients(0.5, 0, 0.055);
    public static PidController heading = new PidController(heading_coeff);


    @Override
    public void runOpMode() {
        hw.init(hardwareMap, LynxModule.BulkCachingMode.MANUAL);
        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry()); /// i forgot how to add the motor/pid constants to telemetry lmao
        Robot terrorbot= new Robot();
        terrorbot.init(this, hw, telemetry);
        terrorbot.imu.setPollTime(2);

        waitForStart();
        double headingOffset = terrorbot.imu.readLastAngle().yaw;

        heading.setThreshold(Math.abs(Angle.angleWrap(Math.toRadians(1.25))));

        while (opModeIsActive()) {
            for (LynxModule hub : hw.allHubs) {
                hub.clearBulkCache();
            }

            double goalAngle = Angle.angleWrap(targetAngle);
            double robotAngle = Angle.angleWrap(terrorbot.imu.readLastAngle().yaw - headingOffset);
            heading.setTargetPosition(goalAngle);
            heading.calculatePower(robotAngle);
            double rotation = heading.power;

            if (Math.abs(goalAngle - robotAngle) > Math.toRadians(1.25) && Math.abs(rotation) < 0.15) {
                rotation = sign(rotation) * 0.15;
            }

            terrorbot.drivetrain.move(
                    new Coordinate(0, 0),
                    rotation
            );
            telemetry.addData("Robot Angle", robotAngle);
            telemetry.addData("Goal Angle", goalAngle);
            telemetry.addData("Error", Angle.angleWrap(robotAngle - goalAngle));
            telemetry.update();
        }
//        terrorbot.drivetrain.moduleFrontRight.updateState(0,Math.PI/2);
//        terrorbot.drivetrain.moduleRearLeft.updateState(0,Math.PI/2);
//        terrorbot.drivetrain.moduleRearRight.updateState(0,Math.PI/2);
    }
}
