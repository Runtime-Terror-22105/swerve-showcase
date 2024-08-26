package org.firstinspires.ftc.teamcode.util.math;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

public class LineSegment extends Geometry {
    public Coordinate bound1;
    public Coordinate bound2;

    public LineSegment(Coordinate point1, Coordinate point2) {
        this.bound1 = point1;
        this.bound2 = point2;
    }

    public double getSlope() {
        return (bound2.y - bound1.y)/(bound2.x - bound1.x);
    }

    public double getYIntercept() {
        return bound1.y - getSlope()*bound1.x;
    }

    public double solveForY(double x) {
        return getSlope() * x + getYIntercept();
    }

    /**
     * Translate a line segment by some x and y.
     * @param x The amount to translate the line segment on the x-axis.
     * @param y The amount to translate the line segment on the y-axis.
     */
    public void translate(double x, double y) {
        this.bound1.translate(x, y);
        this.bound2.translate(x, y);
    }

    /**
     * Translate a line segment by some x and y.
     * @param coord The coordinate to translate by.
     */
    public void translate(@NonNull Coordinate coord) {
        this.bound1.translate(coord.x, coord.y);
        this.bound2.translate(coord.x, coord.y);
    }

    /**
     * Translate a line segment by some x and y.
     * @param x The amount to translate the line segment on the x-axis.
     * @param y The amount to translate the line segment on the y-axis.
     */
    @NonNull
    @Contract("_, _, _ -> new")
    public static LineSegment translate(@NonNull LineSegment lineSegment, double x, double y) {
        return new LineSegment(
            Coordinate.translate(lineSegment.bound1, x, y),
            Coordinate.translate(lineSegment.bound2, x, y)
        );
    }

    /**
     * Translate a line segment by some x and y.
     * @param coord The coordinate to translate by.
     */
    @NonNull
    @Contract("_, _ -> new")
    public static LineSegment translate(@NonNull LineSegment lineSegment, @NonNull Coordinate coord) {
        return new LineSegment(
                Coordinate.plus(lineSegment.bound1, coord),
                Coordinate.plus(lineSegment.bound2, coord)
        );
    }

    /**
     * Rotate the line segment.
     * @param angle An angle, in radians.
     * @return The rotated line segment.
     */
    @NonNull
    @Contract("_, _ -> new")
    public static LineSegment rotate(@NonNull LineSegment segment, double angle) {
        return new LineSegment(
            Coordinate.rotate(segment.bound1, angle),
            Coordinate.rotate(segment.bound2, angle)
        );
    }

    /**
     * Rotate the line segment.
     * @param angle An angle, in radians.
     */
    public void rotate(double angle) {
        this.bound1.rotate(angle);
        this.bound2.rotate(angle);
    }

    public boolean isVertical() {
        return (bound2.x - bound1.x) == 0;
    }
}
