package org.firstinspires.ftc.teamcode.testing;

import org.firstinspires.ftc.teamcode.util.math.Coordinate;
import org.firstinspires.ftc.teamcode.util.math.NumCompare;

public class TestingSwerveMath {
    public static final double wheelBase = 227.8;
    public static final double trackWidth = 227.8;
    public static final double R = Math.sqrt(Math.pow(wheelBase,2) + Math.pow(trackWidth,2));

    public static void main(String[] args) {
        Coordinate velocity = new Coordinate(0, 0.8);
        double rotation = Math.toRadians(0);


        double A = velocity.x - rotation*(wheelBase /R); // L/R
        double B = velocity.x + rotation*(wheelBase /R); // L/R
        double C = velocity.y - rotation*(trackWidth/R); // W/R
        double D = velocity.y + rotation*(trackWidth/R); // W/R

        System.out.println(A+" "+B+" "+C+" "+D);
        double wheelFrontRightPower = Math.sqrt(B*B + C*C);
        double wheelFrontLeftPower = Math.sqrt(B*B + D*D);
        double wheelRearLeftPower = Math.sqrt(A*A + D*D);
        double wheelRearRightPower = Math.sqrt(A*A + C*C);

        System.out.println("moduleFrontRight unscaled: power - " + wheelFrontRightPower + " - angle - " + Math.toDegrees(Math.atan2(B, C)));
        System.out.println("moduleFrontLeft unscaled:  power - " + wheelFrontLeftPower + " - angle - " + Math.toDegrees(Math.atan2(B, D)));
        System.out.println("moduleRearLeft unscaled:   power - " + wheelRearLeftPower + " - angle - " + Math.toDegrees(Math.atan2(A, D)));
        System.out.println("moduleRearRight unscaled:  power - " + wheelRearRightPower + " - angle - " + Math.toDegrees(Math.atan2(A, C)));
//        double scale = Math.max(Math.max(Math.max(Math.max(Math.abs(wheelRearRightPower), Math.abs(wheelRearLeftPower)),
//                Math.abs(wheelFrontLeftPower)), Math.abs(wheelFrontRightPower)), 1);
        double scale = NumCompare.max(Math.abs(wheelRearRightPower), Math.abs(wheelRearLeftPower),
                Math.abs(wheelFrontLeftPower), Math.abs(wheelFrontRightPower), 1);
        if(scale == 0) {
            scale = 1;
        }

        System.out.println("moduleFrontRight: power - " + wheelFrontRightPower/scale + " - angle - " + Math.toDegrees(Math.atan2(B, C)));
        System.out.println("moduleFrontLeft:  power - " + wheelFrontLeftPower/scale + " - angle - " + Math.toDegrees(Math.atan2(B, D)));
        System.out.println("moduleRearLeft:   power - " + wheelRearLeftPower/scale + " - angle - " + Math.toDegrees(Math.atan2(A, D)));
        System.out.println("moduleRearRight:  power - " + wheelRearRightPower/scale + " - angle - " + Math.toDegrees(Math.atan2(A, C)));

    }
}
