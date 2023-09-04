package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain {
    private DcMotor motorFrontLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorRearLeft;
    private DcMotor motorRearRight;
    private int powerCap = 3;

    public DriveTrain(HardwareMap hardwareMap) {
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motor-front-left");
        motorFrontRight = hardwareMap.get(DcMotor.class, "motor-front-right");
        motorRearLeft = hardwareMap.get(DcMotor.class, "motor-rear-left");
        motorRearRight = hardwareMap.get(DcMotor.class, "motor-rear-right");

        // Reverse direction of motors based on the orientation
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorRearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive (double forward, double right, double rotate) {
        forward /= powerCap;
        right /= powerCap;
        rotate /= powerCap;

        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double rearLeftPower = forward - right + rotate;
        double rearRightPower = forward + right - rotate;
        setPowers(frontLeftPower, frontRightPower, rearLeftPower, rearRightPower);
   }

    private void setPowers(double frontLeftPower, double frontRightPower, double rearLeftPower, double rearRightPower) {
        double maxSpeed = 1.0;

        maxSpeed = Math.max(maxSpeed, Math.abs(frontLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(frontRightPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(rearLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(rearRightPower));

        frontLeftPower /= maxSpeed;
        frontRightPower /= maxSpeed;
        rearLeftPower /= maxSpeed;
        rearRightPower /= maxSpeed;

        motorFrontLeft.setPower(frontLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorRearLeft.setPower(rearLeftPower);
        motorRearRight.setPower  (rearRightPower);
    }

    public void stop() {
        setPowers(0,0,0,0);
    }

    public void setPowerCap(int powerCap) {
        this.powerCap = powerCap;
    }

    public int getPowerCap() {
        return this.powerCap;
    }

}
