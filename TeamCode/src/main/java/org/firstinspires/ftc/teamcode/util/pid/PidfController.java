package org.firstinspires.ftc.teamcode.util.pid;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PidfController {
    // pidf constants
    private final PidfCoefficients pidfCoefficients;

    //region pidf temp vars
    private double integralSum;
    private double lastError;
    private double error;
    private final ElapsedTime timer = new ElapsedTime();
    //endregion

    //region public variables
    public double power = 0;
    private double targetPosition = 0;
    public boolean reached = false;

    public double threshold=0.017;
    //endregion

    public PidfController(PidfCoefficients pidfCoefficients) {
        this.pidfCoefficients = pidfCoefficients;
        _resetTempVars();

    }

    /**
     * NOTE: You must run this function each loop iteration. It will do the PID stuff to calculate
     * the power to be used.
     */
    public void calculatePower(double encoderPosition, double feedforwardReference) {
        double out;
        if (Math.abs(error) >= this.threshold) {

            // calculate the error
            error = targetPosition - encoderPosition;

            // rate of change of the error
            double derivative = (error - lastError) / timer.seconds();

            // sum of all error over time
            integralSum = integralSum + (error * timer.seconds());

            // calculate the power, limit it between 0 and 1
            double outUnclamped = (pidfCoefficients.Kp * error)
                                + (pidfCoefficients.Ki * integralSum)
                                + (pidfCoefficients.Kd * derivative)
                                + (pidfCoefficients.Kv * feedforwardReference);
            out = Math.max(Math.min(outUnclamped, 1.0), -1.0); // out has to be between -1 and 1
            lastError = error;

            // reset the timer for next time
            timer.reset();
            reached = false;
        }
        else {
            _resetTempVars();
            reached = true;
            out = 0;
        }
        this.power = out;
    }

    // region moving

    /**
     * Increase/decrease the target position of the PID by some amount of counts. This is a
     * RELATIVE move unlike setTargetPosition().
     * @param moveAmt The amount of clicks to increase/decrease the position by.
     */
    public void move(double moveAmt) {
        double newTargetPos = this.targetPosition + moveAmt;
        this.setTargetPosition(newTargetPos);
    }

    /**
     * Sets the target position of the slides.
     * @param targetPosition The new target position for the slides.
     */
    public void setTargetPosition(double targetPosition) {
        this.targetPosition = targetPosition;
    }
    // endregion

    /**
     * Gets the current target position of the PID.
     * @return targetPosition - The target position.
     */
    public double getTargetPosition() {
        return this.targetPosition;
    }

    private void _resetTempVars() {
        this.integralSum = 0;
        this.lastError = 0;
        this.error = 100; // random number
        this.timer.reset();
    }
    public static class PidfCoefficients {
        public double Kp;
        public double Ki;
        public double Kd;
        public double Kv;

        public PidfCoefficients(double Kp, double Ki, double Kd, double Kv) {
            this.Kp = Kp;
            this.Ki = Ki;
            this.Kd = Kd;
            this.Kv = Kv;
        }
    }
}
