package org.firstinspires.ftc.teamcode.hardwareDevices;

import org.firstinspires.ftc.teamcode.util.math.Coordinate;

/**
 * A class for odometry pods which stores info such as the encoder and distance from center of the
 * robot.
 */
public class TerrorOdometryPod {
    /**
     * The encoder that will be used in the odometry pod.
     */
    public TerrorEncoder encoder;

    /**
     * The position of where the odometry pod is relative to the center of the bot.
     */
    public Coordinate podPosition;

    public TerrorOdometryPod(Coordinate position) {
        this.podPosition = position;
    }

    public void setEncoder(TerrorEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Gets the relative position of the odometry pod to the center of the bot.
     * @return the position of the odometry pod.
     */
    public Coordinate getPodPosition() {
        return this.podPosition;
    }

    /**
     * Description copied from TerrorEncoder.java <br />
     * Gets the position from the underlying motor and adjusts for the set direction.
     * Additionally, this method updates the velocity estimates used for compensated velocity
     * @return The current position of the odometry pod, in ticks.
     */
    public double getCurrentPosition() {
        return this.encoder.getCurrentPosition();
    }

    /**
     * Description copied from TerrorEncoder.java <br />
     * Gets the velocity directly from the underlying motor and compensates for the direction
     * See {@link #getCorrectedVelocity} for high (>2^15) counts per second velocities (such as on REV Through Bore)
     * @return The raw velocity of the odometry pod, as measured by the encoder.
     */
    public double getRawVelocity() {
        return this.encoder.getRawVelocity();
    }

    /**
     * Description copied from TerrorEncoder.java <br />
     * Uses velocity estimates gathered in {@link #getCurrentPosition} to estimate the upper bits of velocity
     * that are lost in overflow due to velocity being transmitted as 16 bits.
     * <p>CAVEAT: must regularly call {@link #getCurrentPosition} for the compensation to work correctly</p>
     * @return corrected velocity
     */
    public double getCorrectedVelocity() {
        return this.encoder.getCorrectedVelocity();
    }

}
