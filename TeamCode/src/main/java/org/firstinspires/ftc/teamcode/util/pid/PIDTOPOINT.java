package org.firstinspires.ftc.teamcode.util.pid;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.util.math.Pose2d;
import org.firstinspires.ftc.teamcode.util.math.Coordinate;

public class PIDTOPOINT {
    public static PidController.PidCoefficients x_coeff;
    public static PidController.PidCoefficients y_coeff;
    public static PidController.PidCoefficients heading_coeff;

    public PidController x;
    public PidController y;
    public PidController heading;


    public PIDTOPOINT(){
         x= new PidController(x_coeff);
         y= new PidController(y_coeff);
         heading= new PidController(heading_coeff);
    }

    public void setGoalPoint(Pose2d goal){
        x.setTargetPosition(goal.x);
        y.setTargetPosition(goal.y);
        heading.setTargetPosition(goal.heading);
    }

    public void updatePowers(Robot terrorbot, Pose2d Current){
        x.calculatePower(Current.x);
        y.calculatePower(Current.y);
        heading.calculatePower(Current.heading);
        Coordinate velocity= new Coordinate(x.power,y.power);
        terrorbot.drivetrain.move(velocity,heading.power, 1);
    }

}
