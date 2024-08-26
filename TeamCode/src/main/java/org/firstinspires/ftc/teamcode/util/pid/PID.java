package org.firstinspires.ftc.teamcode.util.pid;

import com.qualcomm.robotcore.util.ElapsedTime;
public class PID {
    private double integralSum;
    private double lastError;
    private double error;
    private double threshold;
    private final ElapsedTime timer = new ElapsedTime();
    public double power = 0;
    private double targetPosition = 0;
    public boolean reached = false;
    public Coeffecients k;

    public PID(double kp, double ki, double kd, double maxerror){
        this.k= new Coeffecients(kp,ki,kd); // k constants
        this.threshold=maxerror;
        _resetTempVars();

    }
    private void _resetTempVars() {
        integralSum = 0;
        lastError = 0;
        error = 100; // random number
        timer.reset();
    }

    public void calculatePower(double encoderPosition) {
        if (Math.abs(error) >= threshold) {
            error = targetPosition - encoderPosition;// calculate the error
            double derivative = (error - lastError) / timer.seconds();// rate of change of the error
            integralSum = integralSum + (error * timer.seconds());// sum of all error over time
            // integralSum=
            power= (k.Kp * error) + (k.Ki * integralSum) + (k.Kd * derivative);// calculate the power
            lastError = error;
            timer.reset();// Reset timer for next loop
            reached = false;
        }
        else{ // when the target is reached within error
            _resetTempVars();
            reached = true;
            power = 0;
        }
    }

    public void setTargetPosition(double targetPosition){
        this.targetPosition=targetPosition;
    }
}
