package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain {
    private DcMotor motorFrontLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorRearLeft;
    private DcMotor motorRearRight;

    public DriveTrain(HardwareMap hardwareMap) {
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motor-front-left");
        motorFrontRight = hardwareMap.get(DcMotor.class, "motor-front-right");
        motorRearLeft = hardwareMap.get(DcMotor.class, "motor-rear-left");
        motorRearRight = hardwareMap.get(DcMotor.class, "motor-rear-right");

        // Reverse direction of motors based on the orientation
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorRearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setPowers(double frontLeftPower, double frontRightPower, double rearLeftPower, double rearRightPower) {
        int power_cap = 3;

        frontLeftPower /= power_cap;
        frontRightPower /= power_cap;
        rearLeftPower /= power_cap;
        rearRightPower /= power_cap;

        motorFrontLeft.setPower(frontLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorRearLeft.setPower(rearLeftPower);
        motorRearRight.setPower  (rearRightPower);
    }
    public void drive (double forward, double right, double rotate) {
        double frontLeftPower = forward;
        double frontRightPower = forward;
        double rearLeftPower = forward;
        double rearRightPower = forward;

        setPowers(frontLeftPower, frontRightPower, rearLeftPower, rearRightPower);
   }


}
