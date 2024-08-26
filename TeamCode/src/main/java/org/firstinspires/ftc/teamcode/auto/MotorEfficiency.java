package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.qualcomm.hardware.lynx.LynxModule;

import com.outoftheboxrobotics.photoncore.Photon;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="MotorEffiencyTest", group="concept")
@Config
@Photon
public class MotorEfficiency extends LinearOpMode {
    public RobotHardware hw = new RobotHardware();
    private FtcDashboard dashboard;
    public static double effiency;

    @Override
    public void runOpMode() {
        hw.init(hardwareMap, LynxModule.BulkCachingMode.AUTO);
        waitForStart();

        dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry()); /// i forgot how to add the motor/pid constants to telemetry lmao
        Robot terrorbot = new Robot();
        terrorbot.init(this, hw, telemetry);

        waitForStart();

        terrorbot.drivetrain.moduleFrontLeft.updateState(1.0, hw.servoFrontLeft.getPosition());
//        terror.moduleFrontRight.updateState(1.0,hw.servoFrontLeft.getPosition());
//        terror.moduleRearLeft.updateState(1.0,hw.servoFrontLeft.getPosition());
//        terror.moduleRearRight.updateState(1.0,hw.servoFrontLeft.getPosition());

    
        telemetry.addData("SHould drive",hw.motorFrontLeft.getCurrent(CurrentUnit.AMPS));
        telemetry.update();

        sleep(5000);// let the motor accelerate

        ElapsedTime timer = new ElapsedTime();
        timer.startTime();
        double prevtime = 0;
        double v_average = 0;
        double prev_position = hw.motorFrontLeft.getCurrentPosition();
        double steps=0;
        double total_velocity=0;
        double avg_current_draw=0;
        while (timer.milliseconds() < 3000) {
            double curr_position = hw.motorFrontLeft.getCurrentPosition();
            double curr_time = timer.milliseconds();
            v_average+=(curr_position-prev_position)/(curr_time-prevtime);
            prev_position = curr_position;
            prevtime = curr_time;
            total_velocity+=hw.motorFrontLeft.getVelocity();
            avg_current_draw+=hw.motorFrontLeft.getCurrent(CurrentUnit.AMPS);
            steps+=1;
        }
        total_velocity/=steps;
        avg_current_draw/=steps;
        telemetry.addData("Velocities Average",total_velocity*(60/28));
        telemetry.addData("Velocities Average 2", v_average*600/28);
        telemetry.addData("Motor mode",hw.motorFrontLeft.getMode());
        telemetry.addData("Effiency", avg_current_draw/9.2);
        telemetry.update();


        terrorbot.drivetrain.moduleFrontLeft.updateState(0.0, hw.servoFrontLeft.getPosition());
        sleep(10000);
//        terror.moduleFrontRight.updateState(0.0,hw.servoFrontLeft.getPosition());
//        terror.moduleRearLeft.updateState(0.0,hw.servoFrontLeft.getPosition());
//        terror.moduleRearRight.updateState(0.0,hw.servoFrontLeft.getPosition());

        terrorbot.shutdown();
    }

}
