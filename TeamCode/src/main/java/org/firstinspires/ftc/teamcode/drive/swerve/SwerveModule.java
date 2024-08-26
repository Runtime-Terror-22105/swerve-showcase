package org.firstinspires.ftc.teamcode.drive.swerve;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonCRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.util.math.Angle;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorMotor;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorAxonServo;
import org.firstinspires.ftc.teamcode.util.math.Coordinate;
import org.firstinspires.ftc.teamcode.util.pid.PidfController;

public class SwerveModule {
    // the motors
    private final TerrorMotor driveMotor;
    private final TerrorAxonServo rotationServo;

    public double currAngle;

    // the numbers for calculations
    private final PidfController.PidfCoefficients anglePidCoefficients;
    private final PidfController anglePid;
//    private final PidfController.PidfCoefficients velPidCoefficients;
//    private final PidfController velPid;

    public

    // for getting the velocity
    double oldMotorPos;

    private double drivePower;

    public static boolean wheelFlipped=false;

    public Coordinate offset;



    /**
     * Creates a new Swerve Module object to be used by the SwerveDrivetrain class.
     *
     * @param driveMotor           The motor that moves the wheel (controls speed)
     * @param rotationServo        The servo that rotates the module (controls rotation)
     */
    public SwerveModule(@NonNull TerrorMotor driveMotor, @NonNull TerrorAxonServo rotationServo,
                        @NonNull SwerveModuleConfig config) {
        this.driveMotor = driveMotor;
        this.rotationServo = rotationServo;
        this.anglePidCoefficients = config.anglePidfCoefficients;

        this.anglePid = new PidfController(anglePidCoefficients);
//        this.rotationServo.setDirection(config.servoEncoderReversed ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        this.rotationServo.setOffset(config.servoEncoderOffset);

        this.oldMotorPos = this.driveMotor.getCurrentPosition();

        this.offset = config.moduleOffset;
    }

    public void move() {
        // setting the angle
        this.rotationServo.setPower(this.anglePid.power);

        // setting the motor power according to voltage
//        double current_voltage=0;
//        double max_rpm=(current_voltage/12)*6000;
//        double goal_rpm=this.drivePower*6000;
//        double power=goal_rpm/max_rpm;

//        this.driveMotor.setPower(this.drivePowerpower);
         this.driveMotor.setPower(this.drivePower);
    }

    public void updateAngle(@NonNull TerrorAxonServo rotationServo) {
        this.currAngle = rotationServo.getPosition();
    }
    
    /**
     * Set the speed to drive at and the angle for the wheel. PID will be used for the angle.
     * @param speed The power for the speed servo
     * @param goal_angle The desired angle, in radians
     */
    public void updateState(double speed, double goal_angle) {
        double current = this.driveMotor.getCurrent(CurrentUnit.AMPS);
        double idealCurrentDraw = current/9.2*1.47/23.33333333*3.6; // I could simplify this but it's easier to understand the maths this way

        goal_angle = Angle.normalize(goal_angle);
        double curr_angle = this.rotationServo.getPosition();

        if (Angle.canBeOptimized(curr_angle, goal_angle)) {
            this.anglePid.setTargetPosition(Angle.normalize(goal_angle-Math.PI));
            this.anglePid.calculatePower(this.rotationServo.getPosition(), idealCurrentDraw);
            wheelFlipped = true;
        }
        else {
            this.anglePid.setTargetPosition(goal_angle);
            this.anglePid.calculatePower(this.rotationServo.getPosition(), idealCurrentDraw);
            wheelFlipped = false;
        }

        if (wheelFlipped) {
            speed = -speed;
        }
//        this.velPid.setTargetPosition();
        this.drivePower = speed;

        move();
    }


    // if the delta between goal is not in +90 or -90 degrees ranged then use other side of wheel to turn there and reverse speed
    /**
     * Get the actual velocity and angle of the wheel.
     * @param timeElapsed The amount of time since the last measurement of the state. Used to get the velocity.
     * @return
     */
    public SwerveModuleState getCurrentState(double timeElapsed) {
        double vel = (driveMotor.getCurrentPosition() - oldMotorPos)/(timeElapsed);
        return new SwerveModuleState(vel, this.rotationServo.getPosition());
    }

    public SwerveModuleState getInitialState(){
        return new SwerveModuleState(0,rotationServo.getPosition());
    }


    public void dontMove() {
        updateState(0, this.rotationServo.getPosition());
    }
}
