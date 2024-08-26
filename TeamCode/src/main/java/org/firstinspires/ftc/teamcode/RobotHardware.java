package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.outoftheboxrobotics.photoncore.Photon;
import com.outoftheboxrobotics.photoncore.hardware.PhotonLynxVoltageSensor;
import com.outoftheboxrobotics.photoncore.hardware.motor.PhotonDcMotor;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonCRServo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorAxonServo;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorEncoder;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorMotor;

import java.util.List;

@Config
@Photon

public class RobotHardware {

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
    public IMU imu;
    public PhotonLynxVoltageSensor voltageSensor;

    // Lynx stuff
    public List<LynxModule> allHubs = null;
    public LynxModule controlHub = null;

    // Other
    public HardwareMap hwMap;

    public void init(@NonNull HardwareMap hwMap, @NonNull LynxModule.BulkCachingMode bulkCachingMode) {
        this.hwMap = hwMap;

        // Initialize the drivetrain motors
        this.motorFrontLeft  = new TerrorMotor(((PhotonDcMotor)hwMap.get(DcMotor.class, "motorFrontLeft")));
        this.motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        this.motorFrontRight = new TerrorMotor(((PhotonDcMotor)hwMap.get(DcMotor.class, "motorFrontRight")));
        this.motorRearRight  = new TerrorMotor(((PhotonDcMotor)hwMap.get(DcMotor.class, "motorRearRight")));
        this.motorRearLeft   = new TerrorMotor(((PhotonDcMotor)hwMap.get(DcMotor.class, "motorRearLeft")));
        this.motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motorRearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motorRearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Initialize the drivetrain servos
        this.servoFrontLeft  = new TerrorAxonServo((PhotonCRServo) hwMap.get(CRServo.class, "servoFrontLeft"));
        this.servoFrontRight = new TerrorAxonServo((PhotonCRServo) hwMap.get(CRServo.class, "servoFrontRight"));
        this.servoRearRight  = new TerrorAxonServo((PhotonCRServo) hwMap.get(CRServo.class, "servoRearRight"));
        this.servoRearLeft   = new TerrorAxonServo((PhotonCRServo) hwMap.get(CRServo.class, "servoRearLeft"));
        AnalogInput servoFrontLeftEncoder  = hwMap.get(AnalogInput.class, "encoderFrontLeft");
        AnalogInput servoFrontRightEncoder = hwMap.get(AnalogInput.class, "encoderFrontRight");
        AnalogInput servoRearRightEncoder  = hwMap.get(AnalogInput.class, "encoderRearRight");
        AnalogInput servoRearLeftEncoder   = hwMap.get(AnalogInput.class, "encoderRearLeft");

        this.servoFrontLeft.setServoEncoder(servoFrontLeftEncoder);
        this.servoFrontRight.setServoEncoder(servoFrontRightEncoder);
        this.servoRearLeft.setServoEncoder(servoRearLeftEncoder);
        this.servoRearRight.setServoEncoder(servoRearRightEncoder);

        this.imu = this.hwMap.get(IMU.class, "imu");


        // Odometry
//        this.odoLeftEncoder  = new TerrorEncoder();
//        this.odoRightEncoder = new TerrorEncoder();
//        this.odoBackEncoder = new TerrorEncoder();


        // Camera
        this.cameraMonitorViewId = hwMap
                .appContext
                .getResources()
                .getIdentifier(
                        "cameraMonitorViewId",
                        "id",
                        hwMap.appContext.getPackageName()
                );
        this.cameraName = hwMap.get(WebcamName.class, "Webcam 1");


        // Lynx
        this.initLynx(bulkCachingMode);

        // Voltage sensor
        this.voltageSensor = hwMap.getAll(PhotonLynxVoltageSensor.class).iterator().next();
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
