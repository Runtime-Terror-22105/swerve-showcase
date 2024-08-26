package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.util.math.Pose2d;
import org.firstinspires.ftc.teamcode.util.pid.PIDTOPOINT;

@Autonomous(name="AIPOWER_Tuning_wheelrotations", group="concept")
@Config
public class SwerveRotationOptimizeTuner extends LinearOpMode {
    public RobotHardware hw = new RobotHardware();
    private FtcDashboard dashboard;
    @Override
    public void runOpMode() {
        hw.init(hardwareMap, LynxModule.BulkCachingMode.AUTO);
        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry()); /// i forgot how to add the motor/pid constants to telemetry lmao
        Robot terrorbot = new Robot();
        terrorbot.init(this, hw, telemetry);

        PIDTOPOINT ptp = new PIDTOPOINT();
        Pose2d p1 = new Pose2d(5, 10, 1);
        Pose2d p2 = new Pose2d(10, 2, 1);
        while(opModeIsActive()) {

        }

    }
}
