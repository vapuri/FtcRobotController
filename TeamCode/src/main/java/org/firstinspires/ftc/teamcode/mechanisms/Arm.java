package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    private DcMotor motorArm;
    private final double gearRatio = 5.0;
    private final double  PPR = 1425.1 * gearRatio; //pulse per revolution for motor encoder
    private ArmStateType armState;

    public enum ArmStateType {
        FOLD,
        PICK,
        DEPOSIT
    }

    public ArmStateType getArmState() {
        return armState;
    }

    public Arm(HardwareMap hardwareMap) {
        motorArm = hardwareMap.get(DcMotor.class, "motor-arm");
        // Reverse direction of motor if the arm is flipped manually
        // motorArm.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset the motor encoder so that it reads zero ticks
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armState = ArmStateType.FOLD;

    }

    public void fold() {
        // Flip the arm to fold-down position
        int angle = 7; // 7 degrees to the arm does not hit the stopped
        int targetPosition = (int) PPR * angle/360;

        armState = ArmStateType.FOLD;
        motorArm.setTargetPosition(targetPosition);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(0.4);
    }

    public void deposit() {
        int angle = 154; // 219-65=154 degrees
        int targetPosition = (int) PPR * angle/360;

        armState = ArmStateType.DEPOSIT;
        motorArm.setTargetPosition(targetPosition);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(0.4);
    }

    public void pick() {
        int angle = 219;
        int targetPosition = (int) PPR * angle/360;

        if (armState == ArmStateType.DEPOSIT)
            return;
        armState = ArmStateType.PICK;
        motorArm.setTargetPosition(targetPosition);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(0.4);
    }

    public int getCurrentPosition() {
        return motorArm.getCurrentPosition();
    }

    public double getCurrentAngle() {
        int currentPosition = getCurrentPosition();
        double revolutions = currentPosition / PPR;
        double angle = revolutions * 360;
        return angle;
    }


}
