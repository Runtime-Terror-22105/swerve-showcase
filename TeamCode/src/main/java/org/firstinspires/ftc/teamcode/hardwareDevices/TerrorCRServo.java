package org.firstinspires.ftc.teamcode.hardwareDevices;

import androidx.annotation.NonNull;

import com.outoftheboxrobotics.photoncore.hardware.motor.PhotonDcMotor;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonCRServo;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonLynxServoController;
import com.outoftheboxrobotics.photoncore.hardware.servo.PhotonServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * A wrapper servo class that provides caching to avoid unnecessary setPosition() calls.
 */
public class TerrorCRServo {
    private double lastPower;
    private final PhotonCRServo crservo;

    private final double powerThreshold = 0.05;

    public TerrorCRServo(@NonNull PhotonCRServo crservo) {
        this.crservo = crservo;
        this.lastPower = crservo.getPower();
    }

    public void setPower(double power) {
        if (Math.abs(this.lastPower - power) > this.powerThreshold) {
            this.lastPower = power;
            this.crservo.setPower(power);
        }
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        this.crservo.setDirection(direction);
    }

}
