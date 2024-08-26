package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorAxonServo;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorEncoder;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorMotor;

import java.util.List;

@Config
public class TestingHardware {

    // Drivetrain motors & servos
    public TerrorMotor motorFrontLeft = null;
    public TerrorMotor motorRearRight = null;
    public TerrorMotor motorFrontRight = null;
    public TerrorMotor motorRearLeft = null;
    public TerrorAxonServo servoFrontLeft = null;
    public TerrorAxonServo servoRearRight = null;
    public TerrorAxonServo servoFrontRight = null;
    public TerrorAxonServo servoRearLeft = null;

    // Camera
    public int cameraMonitorViewId;
    public WebcamName cameraName = null;

    // Odometry
    public TerrorEncoder odoLeftEncoder;
    public TerrorEncoder odoRightEncoder;
    public TerrorEncoder odoBackEncoder;

    // Sensors
    public BNO055IMU imu;

    // Lynx stuff
    public List<LynxModule> allHubs = null;
    public LynxModule controlHub = null;

    // Other
    public HardwareMap hwMap;

    public void init(@NonNull HardwareMap hwMap) {
        this.hwMap = hwMap;

        // Initialize the drivetrain motors
        this.motorFrontLeft  = (TerrorMotor) hwMap.get(DcMotor.class, "motorFrontLeft");
//        this.motorFrontRight = (TerrorMotor) hwMap.get(DcMotor.class, "motorFrontRight");
//        this.motorRearRight  = (TerrorMotor) hwMap.get(DcMotor.class, "motorRearRight");
//        this.motorRearLeft   = (TerrorMotor) hwMap.get(DcMotor.class, "motorRearLeft");
//
//
//        // Initialize the drivetrain servos
//        this.servoFrontLeft  = (TerrorAxonServo) hwMap.get(CRServo.class, "servoFrontLeft");
//        this.servoFrontRight = (TerrorAxonServo) hwMap.get(CRServo.class, "servoFrontRight");
//        this.servoRearRight  = (TerrorAxonServo) hwMap.get(CRServo.class, "servoRearRight");
//        this.servoRearLeft   = (TerrorAxonServo) hwMap.get(CRServo.class, "servoRearLeft");
        AnalogInput servoFrontLeftEncoder  = hwMap.get(AnalogInput.class, "servoFrontLeft");
//        AnalogInput servoFrontRightEncoder = hwMap.get(AnalogInput.class, "servoFrontRight");
//        AnalogInput servoRearRightEncoder  = hwMap.get(AnalogInput.class, "servoRearRight");
//        AnalogInput servoRearLeftEncoder   = hwMap.get(AnalogInput.class, "servoRearLeft");
//
        this.servoFrontLeft.setServoEncoder(servoFrontLeftEncoder);
//        this.servoFrontRight.setServoEncoder(servoFrontRightEncoder);
//        this.servoRearRight.setServoEncoder(servoRearRightEncoder);
//        this.servoRearLeft.setServoEncoder(servoRearLeftEncoder);


//        this.imu = this.hwMap.get(BNO055IMU.class, "imu");


        // Odometry
//        this.odoLeftEncoder  = new TerrorEncoder();
//        this.odoRightEncoder = new TerrorEncoder();
//        this.odoBackEncoder = new TerrorEncoder();


        // Camera
//        this.cameraMonitorViewId = hwMap
//                .appContext
//                .getResources()
//                .getIdentifier(
//                        "cameraMonitorViewId",
//                        "id",
//                        hwMap.appContext.getPackageName()
//                );
//        this.cameraName = hwMap.get(WebcamName.class, "Webcam 1");
    }

    public void initLynx(LynxModule.BulkCachingMode bulkCachingMode) {
        // Initialize Lynx stuff
        this.allHubs = this.hwMap.getAll(LynxModule.class);
        for (LynxModule hub : this.allHubs) {
            if (hub.isParent() && LynxConstants.isEmbeddedSerialNumber(hub.getSerialNumber())) {
                this.controlHub = hub;
            }
            hub.setBulkCachingMode(bulkCachingMode);
        }

    }
}
