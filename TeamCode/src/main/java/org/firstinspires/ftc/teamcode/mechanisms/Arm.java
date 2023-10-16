package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    private DcMotor motorArm;
    private final double gearRatio = 5.0;
    private final double  PPR = 1425.1 * gearRatio; //pulse per revolution for motor encoder

    public Arm(HardwareMap hardwareMap) {
        motorArm = hardwareMap.get(DcMotor.class, "motor-rear-right");
        // Reset the motor encoder so that it reads zero ticks
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void rotateUp() {
        int targetPosition = (int) PPR/6; // turn 60 degrees
        motorArm.setTargetPosition(targetPosition);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(0.3);
    }

    public void rotateDown() {
        int targetPosition = 0; // turn -180 degrees
        motorArm.setTargetPosition(targetPosition);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(0.3);
    }

    public int getCurrentPosition() {
        return motorArm.getCurrentPosition();
    }

    public double getCurrentAngle() {
        int currentPosition = getCurrentPosition();
        double revolutions = currentPosition / PPR;
        double angle = revolutions * 360;
        double angleNormalized = angle % 360;
        return angleNormalized;
    }
}
