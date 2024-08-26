package org.firstinspires.ftc.teamcode.util.math;

import static org.firstinspires.ftc.teamcode.util.paths.followers.PurePursuitController.chooseCloserSolution;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

public class Ellipse extends Geometry{
    private final Coordinate center;
    public double major;// major axis
    public double minor; // minor axis

    public final double ratio=0.5;

    public Ellipse(double major, Coordinate center) {
        this.major=major;
        this.minor=this.major*ratio;
        this.center = center;
    }

    public Ellipse(double major) {
        this.major=major;
        this.minor=this.major*ratio;
        this.center = new Coordinate(0, 0);
    }

    /**
     * Get an intersection between a line segment and a ellipse. If there are two, the point closer
     * to the second point will be chosen. If there are none, null will be returned.
     * @param lineSegment The line segment.
     * @param rotation The rotation of the ellipse.
     * @return A point, can be null.
     */
    @Nullable
    @Contract("_, _-> new")
    public Coordinate rotatedEllipseLineIntersection(@NonNull LineSegment lineSegment, double rotation) {
        // Rather than rotating the ellipse, we can just shift the ellipse to the origin and rotate
        // the entire axis. This means that we end up not rotating the ellipse and we rotate the
        // line clockwise (negative rotation).

        // Rotate the line
        lineSegment.translate(Coordinate.mirror(center)); // shift to origin
        lineSegment.rotate(-rotation); // rotate backwards
        lineSegment.translate(center); // shift back

        return ellipseLineIntersection(lineSegment);
    }

    /**
     * Get an intersection between a line segment and a ellipse. If there are two, the point closer
     * to the second point will be chosen. If there are none, null will be returned.
     * @param lineSegment The line segment.
     * @return A point, can be null.
     */
    @Nullable
    @Contract("_-> new")
    public Coordinate ellipseLineIntersection(@NonNull LineSegment lineSegment) {
        // Math comes from https://youtu.be/4W4BiRPTDKs?si=I7sFWELk39a1NFC9, except we
        // shift the center of the ellipse to the origin since our ellipse won't be centered at the
        // origin. We also add an extra check that the circle intersects with the line segment, not
        // the entire line.
        LineSegment lineSegmentShifted = LineSegment.translate(lineSegment, Coordinate.mirror(center)); // shift to origin

        double minX = Math.min(lineSegment.bound1.x, lineSegment.bound2.x);
        double maxX = Math.max(lineSegment.bound1.x, lineSegment.bound2.x);
        double minY = Math.min(lineSegment.bound1.y, lineSegment.bound2.y);
        double maxY = Math.max(lineSegment.bound1.y, lineSegment.bound2.y);
        // if the case that the bottom is 0 check if the x-axis passes anywhere from center+-minor/2
        if(lineSegment.isVertical()){ // infinite slope caase
            // TODO: Find the intersection here
            throw new Error("whoops!");
        }

        double slope = lineSegment.getSlope();
        double yIntercept = lineSegment.getYIntercept();

        double mjSqr = Math.pow(major, 2); // major axis squared
        double mnSqr = Math.pow(minor, 2); //minor axis squared
        double slSqr = Math.pow(slope, 2); //slope squared
        double intSqr = Math.pow(yIntercept, 2); // intercept squared
        Algebra.QuadraticFormulaResult quadraticRoots = Algebra.quadraticFormula(
                mjSqr + mnSqr*slSqr,
                -2 * mjSqr * slope * yIntercept,
                mjSqr * (intSqr - mnSqr)
        );



        if (quadraticRoots.answerCount == 0) { // no intersection
            return null;

        }

        double x1 = quadraticRoots.root1;
        double x2 = quadraticRoots.root2;

        double y1 = lineSegment.solveForY(x1);
        double y2 = lineSegment.solveForY(x2);

        Coordinate sol1 = new Coordinate(x1, y1).plus(center);
        Coordinate sol2 = new Coordinate(x2, y2).plus(center);

        // Set the solution to null if it is outside of the line segment
        if (!Coordinate.isWithinBounds(sol1, minX, maxX, minY, maxY)) {
            sol1 = null;
        }
        else if (!Coordinate.isWithinBounds(sol2, minX, maxX, minY, maxY)) {
            sol2 = null;
        }

        // Return the valid solution, or if there are two, the one closer to point 2
        return chooseCloserSolution(sol1, sol2, lineSegment.bound2);
    }
}
