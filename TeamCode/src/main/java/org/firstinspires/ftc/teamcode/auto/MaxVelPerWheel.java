package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveDrivetrain;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveDrivetrainConfig;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveModuleConfig;
import org.firstinspires.ftc.teamcode.util.math.Angle;
import org.firstinspires.ftc.teamcode.util.pid.PidController;
import org.firstinspires.ftc.teamcode.util.pid.PidfController;
@Autonomous(name="MaxVelPerWheel", group="concept")
@Config
@Photon
public class MaxVelPerWheel extends LinearOpMode {
    public RobotHardware hw= new RobotHardware();
    private FtcDashboard dashboard;

    @Override
    public void runOpMode() {
        hw.init(hardwareMap, LynxModule.BulkCachingMode.AUTO);
        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry()); /// i forgot how to add the motor/pid constants to telemetry lmao
        Robot terrorbot= new Robot();
        terrorbot.init(this, hw, telemetry);
        double max_vel=-1;
        ElapsedTime timer= new ElapsedTime();
        while (timer.milliseconds()<5000) {
            terrorbot.drivetrain.moduleFrontLeft.updateState(1, 0);
            double motorVelocity = hw.motorFrontLeft.getVelocity();
            telemetry.addData("Motor velocity", motorVelocity);
            max_vel=Math.max(max_vel,motorVelocity);
            telemetry.update();
        }
        telemetry.addData("max_vel_MotorFrontLeft",max_vel);
        telemetry.update();
        terrorbot.drivetrain.moduleFrontLeft.updateState(0, 0);
        sleep(100000);

//        terrorbot.drivetrain.moduleFrontRight.updateState(0,Math.PI/2);
//        terrorbot.drivetrain.moduleRearLeft.updateState(0,Math.PI/2);
//        terrorbot.drivetrain.moduleRearRight.updateState(0,Math.PI/2);


    }
}
