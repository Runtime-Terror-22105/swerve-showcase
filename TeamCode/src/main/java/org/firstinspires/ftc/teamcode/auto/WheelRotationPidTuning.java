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
import org.firstinspires.ftc.teamcode.util.math.Angle;

@Autonomous(name="Pid_tuning_wheelrotations", group="concept")
@Config
@Photon
public class WheelRotationPidTuning extends LinearOpMode {
    public RobotHardware hw= new RobotHardware();
    private FtcDashboard dashboard;
    public static double goalAngle = Math.PI/2;

    @Override
    public void runOpMode() {
        hw.init(hardwareMap, LynxModule.BulkCachingMode.AUTO);
        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry()); /// i forgot how to add the motor/pid constants to telemetry lmao
        Robot terrorbot= new Robot();
        terrorbot.init(this, hw, telemetry);

        while (opModeIsActive()) {
            terrorbot.drivetrain.moduleFrontLeft.updateState(0, goalAngle);
            double servoPosition = Angle.angleWrap(Angle.optimize(hw.servoFrontLeft.getPosition(), goalAngle));
            telemetry.addData("Angle", servoPosition);
            telemetry.addData("Goal Angle", Angle.angleWrap(goalAngle));
            telemetry.addData("Error", Angle.angleWrap(servoPosition - goalAngle));
            telemetry.update();
        }
//        terrorbot.drivetrain.moduleFrontRight.updateState(0,Math.PI/2);
//        terrorbot.drivetrain.moduleRearLeft.updateState(0,Math.PI/2);
//        terrorbot.drivetrain.moduleRearRight.updateState(0,Math.PI/2);
    }
}
