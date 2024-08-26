package org.firstinspires.ftc.teamcode;

import android.util.Size;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveDrivetrain;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveDrivetrainConfig;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveModule;
import org.firstinspires.ftc.teamcode.drive.swerve.SwerveModuleConfig;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorCamera;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorIMU;
import org.firstinspires.ftc.teamcode.util.math.Coordinate;
import org.firstinspires.ftc.teamcode.util.pid.PidfController;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Config
public class Robot {
    // Swerve config
    public static SwerveDrivetrainConfig swerveConfig = new SwerveDrivetrainConfig(
            227.8,
            227.8
    );
    public static PidfController.PidfCoefficients moduleFrontLeftAnglePidCoefficients = new PidfController.PidfCoefficients(0.33, 0.0, 0.009,0);
    public static volatile double moduleFrontLeftOffset = -4.554;
    public static SwerveModuleConfig moduleFrontLeftConfig  = new SwerveModuleConfig(
            moduleFrontLeftAnglePidCoefficients,
            false,
            new Coordinate(-6.25,6.25),
            moduleFrontLeftOffset
    );
    public static PidfController.PidfCoefficients moduleFrontRightAnglePidCoefficients = new PidfController.PidfCoefficients(0.33, 0.0, 0.009,0);
    public static volatile double moduleFrontRightOffset = -3.8936708949037135;
    public static SwerveModuleConfig moduleFrontRightConfig = new SwerveModuleConfig(
            moduleFrontRightAnglePidCoefficients,
            false,
            new Coordinate(6.25,6.25),
            moduleFrontRightOffset
    );
    public static PidfController.PidfCoefficients moduleRearLeftAnglePidCoefficients = new PidfController.PidfCoefficients(0.33, 0.0, 0.009,0);
    public static volatile double moduleRearLeftOffset = -1.55;
    public static SwerveModuleConfig moduleRearLeftConfig   = new SwerveModuleConfig(
            moduleRearLeftAnglePidCoefficients,
            false,
            new Coordinate(-6.25,-6.25),
            moduleRearLeftOffset
    );
    public static PidfController.PidfCoefficients moduleRearRightAnglePidCoefficients = new PidfController.PidfCoefficients(0.33, 0.0, 0.009,0);
    public static volatile double moduleRearRightOffset = -2.63545;
    public static SwerveModuleConfig moduleRearRightConfig  = new SwerveModuleConfig(
            moduleRearRightAnglePidCoefficients,
            false,
            new Coordinate(6.25,-6.25),
            moduleRearRightOffset
    );

    // Drivetrain
    public SwerveDrivetrain drivetrain = null;

    // Camera stuff
    public TerrorCamera camera;

    // Odometry
//    public static TerrorOdometryPod leftOdometryPod = new TerrorOdometryPod(new Vector2(0, 0));
//    public static TerrorOdometryPod rightOdometryPod = new TerrorOdometryPod(new Vector2(0, 0));
//    public static TerrorOdometryPod backOdometryPod = new TerrorOdometryPod(new Vector2(0, 0));
    public TerrorIMU imu;

    // Other misc public objects
    public FtcDashboard dashboard;
    public MultipleTelemetry telemetry;
    public RobotHardware hardware;

    public void init(@NonNull LinearOpMode opMode, @NonNull RobotHardware hardware, Telemetry tele) {
        // Save local copy of RobotHardware class
        this.hardware = hardware;

        // Set up dashboard stuff
        this.dashboard = FtcDashboard.getInstance();
        this.telemetry = new MultipleTelemetry(tele, dashboard.getTelemetry());

        // Initialize the drivetrain
        this.drivetrain = new SwerveDrivetrain(this.telemetry,  Robot.swerveConfig,
                hardware.motorRearLeft,   hardware.servoRearLeft,   Robot.moduleRearLeftConfig,
                hardware.motorFrontLeft,  hardware.servoFrontLeft,  Robot.moduleFrontLeftConfig,
                hardware.motorRearRight,  hardware.servoRearRight,  Robot.moduleRearRightConfig,
                hardware.motorFrontRight, hardware.servoFrontRight, Robot.moduleFrontRightConfig);

        // Set up some camera stuff
        this.camera = new TerrorCamera.VisionPortalInitialization()
                .setCamera(hardware.cameraName)
                .setCameraResolution(new Size(1280, 800))
                .detectAprilTags()
                .init();

        assert this.camera.tagProcessor != null;
//        this.camera.tagProcessor.setDecimation(???); // TODO: tune decimation value
        this.camera.tagProcessor.setPoseSolver(AprilTagProcessor.PoseSolver.APRILTAG_BUILTIN);

        // Poll the IMU every 500 ms
        this.imu = new TerrorIMU(opMode, hardware.imu, 500);
        this.imu.initIMU(RevHubOrientationOnRobot.LogoFacingDirection.UP,
                         RevHubOrientationOnRobot.UsbFacingDirection.FORWARD); // this may be changed
        this.imu.startIMUThread();

        //
//        leftOdometryPod.setEncoder(this.hardware.odoLeftEncoder);
//        rightOdometryPod.setEncoder(this.hardware.odoRightEncoder);
//        backOdometryPod.setEncoder(this.hardware.odoBackEncoder);
    }


    /**
     * Shut down various extra things.
     */
    public void shutdown() {
        this.imu.stopIMUThread();
    }

}
