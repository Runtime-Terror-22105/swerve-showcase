package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotHardware;

@Autonomous(name="MotorTesting", group="concept")
@Config
@Photon
public class MotorTesting extends LinearOpMode {
    public RobotHardware hw= new RobotHardware();
    private FtcDashboard dashboard;
    @Override
    public void runOpMode() {
        waitForStart();
        hw.init(hardwareMap, LynxModule.BulkCachingMode.AUTO);
        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry()); /// i forgot how to add the motor/pid constants to telemetry lmao
        Robot terrorbot= new Robot();
        terrorbot.init(this, hw, telemetry);
//        hw.servoFrontRight.setPower(1.0);
//        hw.servoFrontLeft.setPower(1.0);
//        hw.servoRearLeft.setPower(1.0);
//        hw.servoRearRight.setPower(1.0);
//        ElapsedTime timer = new ElapsedTime();
//        while (timer.milliseconds() < 50000) {
        while (opModeIsActive()) {
            telemetry.addData("servoFrontRight Encoder_value", hw.servoFrontRight.getPosition()); // // front left
            telemetry.addData("servoFrontLeft Encoder_value", hw.servoFrontLeft.getPosition()); // front right
            telemetry.addData("servoRearRight Encoder_value", hw.servoRearRight.getPosition()); // servo Rear Left
            telemetry.addData("servoRearLeft Encoder_value", hw.servoRearLeft.getPosition()); // servo Rear Right
            telemetry.update();
        }
        hw.servoFrontRight.setPower(0.0);
        hw.servoFrontLeft.setPower(0.0);
        hw.servoRearLeft.setPower(0.0);
        hw.servoRearRight.setPower(0.0);


    }
}
