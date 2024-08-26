package org.firstinspires.ftc.teamcode.drive.swerve;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.Drivetrain;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorMotor;
import org.firstinspires.ftc.teamcode.hardwareDevices.TerrorAxonServo;
import org.firstinspires.ftc.teamcode.util.math.Coordinate;
import org.firstinspires.ftc.teamcode.util.math.NumCompare;

/**
 * The SwerveDriveTrain class provides simple methods to easily control a swerve drivetrain in FTC.
 * The goal is that this is simply a "black box" that will allow coders to focus on other things.
 * 4 PID controllers total are used for the rotation of each module.
 */
@Config
public class SwerveDrivetrain implements Drivetrain {
    //region    Swerve Modules
    public final SwerveModule moduleRearLeft;
    public final SwerveModule moduleFrontLeft;
    public final SwerveModule moduleRearRight;
    public final SwerveModule moduleFrontRight;
    //endregion Swerve Modules

    private final Telemetry telemetry;

    // public vars
    public double RearLeftPower;
    public double frontLeftPower;
    public double RearRightPower;
    public double frontRightPower;
    public SwerveDrivetrainConfig config;
    public SwerveModuleConfig[] module_configs = new SwerveModuleConfig[4];

    /**
     * Initializes a swerve drivetrain.
     * @param telemetry The telemetry
     * @param motorRearLeft Self explanatory
     * @param servoRearLeft Self explanatory
     * @param moduleRearLeftConfig Self explanatory
     * @param motorFrontLeft Self explanatory
     * @param servoFrontLeft Self explanatory
     * @param moduleFrontLeftConfig Self explanatory
     * @param motorRearRight Self explanatory
     * @param servoRearRight Self explanatory
     * @param moduleRearRightConfig Self explanatory
     * @param motorFrontRight Self explanatory
     * @param servoFrontRight Self explanatory
     * @param moduleFrontRightConfig Self explanatory
     */
    public SwerveDrivetrain(Telemetry telemetry, SwerveDrivetrainConfig swerveConfig,
                            TerrorMotor motorRearLeft,   TerrorAxonServo servoRearLeft,   SwerveModuleConfig moduleRearLeftConfig,
                            TerrorMotor motorFrontLeft,  TerrorAxonServo servoFrontLeft,  SwerveModuleConfig moduleFrontLeftConfig,
                            TerrorMotor motorRearRight,  TerrorAxonServo servoRearRight,  SwerveModuleConfig moduleRearRightConfig,
                            TerrorMotor motorFrontRight, TerrorAxonServo servoFrontRight, SwerveModuleConfig moduleFrontRightConfig) {
        this.telemetry = telemetry;
        this.config = swerveConfig;
        this.moduleRearLeft   = new SwerveModule(motorRearLeft, servoRearLeft, moduleRearLeftConfig);
        this.moduleFrontLeft  = new SwerveModule(motorFrontLeft, servoFrontLeft, moduleFrontLeftConfig);
        this.moduleRearRight  = new SwerveModule(motorRearRight, servoRearRight, moduleRearRightConfig);
        this.moduleFrontRight = new SwerveModule(motorFrontRight, servoFrontRight, moduleFrontRightConfig);


        this.module_configs[0] = moduleFrontLeftConfig;
        this.module_configs[1] = moduleRearLeftConfig;
        this.module_configs[2] = moduleFrontRightConfig;
        this.module_configs[3] = moduleRearRightConfig;
        // set RUN_WITHOUT_ENCODER and reset the encoders here
    }

    /**
     * Sets a new target position
     * @param velocity The x and y
     * @param rotation The rotation
     */
    @Override
    public void move(@NonNull Coordinate velocity, double rotation, double speed) {
        if (velocity.x == 0 && velocity.y == 0 && rotation == 0) {
            this.moduleFrontRight.dontMove();
            this.moduleFrontLeft.dontMove();
            this.moduleRearLeft.dontMove();
            this.moduleRearRight.dontMove();
        }

        velocity.mult(speed);
        rotation *= speed;

        double A = velocity.x - rotation*(this.config.wheelBase /this.config.R); // L/R
        double B = velocity.x + rotation*(this.config.wheelBase /this.config.R); // L/R
        double C = velocity.y - rotation*(this.config.trackWidth/this.config.R); // W/R
        double D = velocity.y + rotation*(this.config.trackWidth/this.config.R); // W/R

        // divide by the max val in order to make sure the wheels go in the correct direction and
        // don't have their powers clipped
        double wheelFrontRightPower = Math.sqrt(B*B + C*C);
        double wheelFrontLeftPower = Math.sqrt(B*B + D*D);
        double wheelRearLeftPower = Math.sqrt(A*A + D*D);
        double wheelRearRightPower = Math.sqrt(A*A + C*C);
        double scale = NumCompare.max(Math.abs(wheelRearRightPower), Math.abs(wheelRearLeftPower),
                Math.abs(wheelFrontLeftPower), Math.abs(wheelFrontRightPower), 1);
        if(scale==0){
            scale=1;
        }
        this.moduleFrontRight.updateState(wheelFrontRightPower/scale, (Math.PI-Math.atan2(B, C)));
        this.moduleFrontLeft.updateState(wheelFrontLeftPower/scale, (Math.PI-Math.atan2(B, D)));
        this.moduleRearLeft.updateState(wheelRearLeftPower/scale, (Math.PI-Math.atan2(A, D)));
        this.moduleRearRight.updateState(wheelRearRightPower/scale, (Math.PI-Math.atan2(A, C)));

        this.moduleRearLeft.move();
        this.moduleFrontLeft.move();
        this.moduleRearRight.move();
        this.moduleFrontRight.move();
    }
    public SwerveModule[] getModules(){
        return new SwerveModule[] {
                moduleFrontLeft,
                moduleRearLeft,
                moduleFrontRight,
                moduleRearRight
        };
    }
    public SwerveModuleConfig[] getConfigs(){
        return module_configs;
    }

    public Coordinate[] getOffsets(){
        return new Coordinate[] {
                moduleFrontLeft.offset,
                moduleRearLeft.offset,
                moduleFrontRight.offset,
                moduleRearRight.offset
        };
    }

    public SwerveModuleState[] getStates(){
        return new SwerveModuleState[] {
                moduleFrontLeft.getInitialState(),
                moduleRearLeft.getInitialState(),
                moduleFrontRight.getInitialState(),
                moduleRearRight.getInitialState()
        };
    }
}
