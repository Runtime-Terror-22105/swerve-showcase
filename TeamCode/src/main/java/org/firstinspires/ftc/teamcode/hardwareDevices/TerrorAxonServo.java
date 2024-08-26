package org.firstinspires.ftc.teamcode.hardwareDevices;

import androidx.annotation.NonNull;

import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonCRServo;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonLynxServoController;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonServoController;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;

import org.firstinspires.ftc.teamcode.util.math.Angle;

/**
 * A wrapper CRServo class for axon servos that has two goals.
 * 1) Provide caching to avoid unnecessary setPower() lynxcommands.
 * 2) Allow for easy usage of the Axon servos.
 */
public class TerrorAxonServo {
    private double offset = 0;
    private double lastPower;
    private AnalogInput servoEncoder = null;
    private final PhotonCRServo crservo;

    private final double powerThreshold = 0.0001;

    public TerrorAxonServo(@NonNull PhotonCRServo crservo) {
        this.crservo = crservo;
        this.lastPower = crservo.getPower();
    }

    /**
     * Sets the servo encoder.
     * @param servoEncoder The analog port of the servo encoder.
     */
    public void setServoEncoder(AnalogInput servoEncoder) {
        this.servoEncoder = servoEncoder;
    }


    synchronized public void setPower(double power) {
        if (Math.abs(this.lastPower - power) > this.powerThreshold) {
            this.lastPower = power;
            this.crservo.setPower(power);
        }
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        this.crservo.setDirection(direction);
    }

    /**
     * Returns the CURRENT position of the axon servo.
     * @return The current, actual position of the servo in radians. This is ABSOLUTE.
     */
    public double getRawPosition() {
        // (read voltage / max voltage) * 2pi + offset
        return this.servoEncoder.getVoltage() / 3.3 * Math.PI*2 + this.offset;
    }

    /**
     * Returns the normalized position of the axon servo.
     * @return The normalized position.
     */
    public double getPosition() {
        return Angle.normalize(getRawPosition());
    }

    /**
     * Sets an offset to be added to the return value of getPosition()
     * @param offset The offset
     */
    public void setOffset(double offset) {
        this.offset = offset;
    }
}
