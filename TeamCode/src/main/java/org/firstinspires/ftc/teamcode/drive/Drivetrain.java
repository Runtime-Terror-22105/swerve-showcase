package org.firstinspires.ftc.teamcode.drive;

import org.firstinspires.ftc.teamcode.util.math.Coordinate;

public interface Drivetrain {
    /**
     * Drive at a certain speed.
     * @param velocity The x and y.
     * @param rotation The rotation
     * @param speed    The robot's speed, a value between 0 and 1 (this is a multiplier, will which
     *                 basically sets the max speed).
     */
    public void move(Coordinate velocity, double rotation, double speed);

    /**
     * Drive at max speed (1).
     * @param velocity The x and y.
     * @param rotation The rotation.
     */
    default void move(Coordinate velocity, double rotation) {
        move(velocity, rotation, 1);
    }
}
