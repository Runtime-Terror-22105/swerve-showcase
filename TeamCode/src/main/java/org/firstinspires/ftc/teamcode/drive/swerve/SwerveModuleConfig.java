package org.firstinspires.ftc.teamcode.drive.swerve;

import org.firstinspires.ftc.teamcode.util.math.Coordinate;
import org.firstinspires.ftc.teamcode.util.pid.PidfController;

/**
 * A class which stores the configuration for a swerve module and can be easily edited through
 * dashboard.
 */
public class SwerveModuleConfig {
    public final PidfController.PidfCoefficients anglePidfCoefficients;
    public final Coordinate moduleOffset; // the offset from the center
    public final boolean servoEncoderReversed;
    public final double servoEncoderOffset;

    /**
     * Initializes a SwerveModuleConfig class.
     * @param anglePidfCoefficients The coefficients for the velocity pid of the module's rotation
     * @param servoEncoderReversed Whether or not to reverse the encoder for the servo which does
     *                             the turning
     * @param servoEncoderOffset   How much to offset the encoder for the turning servo to make it
     *                             be 0.
     */
    public SwerveModuleConfig(PidfController.PidfCoefficients anglePidfCoefficients, boolean servoEncoderReversed,
                              Coordinate moduleOffset, double servoEncoderOffset) {
        this.anglePidfCoefficients = anglePidfCoefficients;
        this.servoEncoderReversed = servoEncoderReversed;
        this.moduleOffset = moduleOffset;
        this.servoEncoderOffset = servoEncoderOffset;
    }
}
