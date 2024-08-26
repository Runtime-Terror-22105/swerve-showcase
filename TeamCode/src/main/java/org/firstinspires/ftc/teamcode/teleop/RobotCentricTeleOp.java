package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.util.math.Coordinate;

@Config
@Photon
@TeleOp
public class RobotCentricTeleOp extends LinearOpMode {
    public static final double DRIVESPEED = 1.0;
    private RobotHardware hardware = new RobotHardware();
    private Robot robot = new Robot();
    private FtcDashboard dashboard;

    @Override
    public void runOpMode() {
        hardware.init(hardwareMap, LynxModule.BulkCachingMode.MANUAL);
        robot.init(this, hardware, telemetry);

        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        waitForStart();

        ElapsedTime loopTimer = new ElapsedTime();

        while (opModeIsActive()) {
            // Manually clear the bulk read cache. Deleting this would be catastrophic b/c stale
            // vals would be used.
            for (LynxModule hub : hardware.allHubs) {
                hub.clearBulkCache();
            }

            Coordinate direction = new Coordinate(slr(-gamepad1.left_stick_x), slr(gamepad1.left_stick_y));
            double rotation = Math.pow(slr(-gamepad1.right_stick_x), 5);
//            rotation = (rotation + Math.pow(rotation, 3))/2;
            robot.drivetrain.move(
                    direction,
                    rotation,
                    DRIVESPEED
            );

            telemetry.addData("MotorFrontLeft", "Velocity: " + hardware.motorFrontLeft.getVelocity()*60/28 + "rpm; Current Draw: " + hardware.motorFrontLeft.getCurrent(CurrentUnit.AMPS) + "amps; 100-((9.2-Current Draw)/9.2)" + (100-((9.2-hardware.motorFrontLeft.getCurrent(CurrentUnit.AMPS))/9.2)));
            telemetry.addData("MotorFrontRight", "Velocity: " + hardware.motorFrontRight.getVelocity()*60/28 + "rpm; Current Draw: " + hardware.motorFrontRight.getCurrent(CurrentUnit.AMPS) + "amps; 100-((9.2-Current Draw)/9.2)" + (100-((9.2-hardware.motorFrontRight.getCurrent(CurrentUnit.AMPS))/9.2)));
            telemetry.addData("MotorRearLeft", "Velocity: " + hardware.motorRearLeft.getVelocity()*60/28 + "rpm; Current Draw: " + hardware.motorRearLeft.getCurrent(CurrentUnit.AMPS) + "amps; 100-((9.2-Current Draw)/9.2)" + (100-((9.2-hardware.motorRearLeft.getCurrent(CurrentUnit.AMPS))/9.2)));
            telemetry.addData("MotorRearRight", "Velocity: " + hardware.motorRearRight.getVelocity()*60/28 + "rpm; Current Draw: " + hardware.motorRearRight.getCurrent(CurrentUnit.AMPS) + "amps; 100-((9.2-Current Draw)/9.2)" + (100-((9.2-hardware.motorRearRight.getCurrent(CurrentUnit.AMPS))/9.2)));
            telemetry.addData("y-stick",gamepad1.left_stick_y);
            telemetry.addData("x-stick",gamepad1.left_stick_x);
            telemetry.addData("rightstick",gamepad1.right_stick_x);
            telemetry.addData("Loop time (ms)", loopTimer.milliseconds());
            telemetry.addData("Loop time (hz)", 1000/loopTimer.milliseconds());
            telemetry.update();
            loopTimer.reset();
        }
    }

    public double slr(double joystick_value){
        return (Math.pow(joystick_value,3)+joystick_value)/2;
    }
}
