package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Arm {
    private DcMotor motorArm;
    private TouchSensor initCheckSensor;

    private final double gearRatio = 5.0;
    private final double  PPR = 1425.1 * gearRatio; //pulse per revolution for motor encoder
    private ArmStateType armState;

    // preset angles (in degrees)
    private final int FoldAngle=7;
    private final int PickAngle=219;
    private final int DepositAngle=(219-65);

    private final double ArmMotorPwrDefault = 0.4;

    public enum ArmStateType {
        INIT_OK,
        INIT_NOT_GOOD,
        FOLD,
        PICK,
        DEPOSIT,
        FINE_ADJ,
    }

    public ArmStateType getArmState() {
        return armState;
    }

    public Arm(HardwareMap hardwareMap) {
        motorArm = hardwareMap.get(DcMotor.class, "motor-arm");
        // add a magnetic limit switch (https://docs.revrobotics.com/magnetic-limit-switch/) to make sure the arm is in correct position
        initCheckSensor = hardwareMap.get(TouchSensor.class,"arm-init-check-switch");

        // Reverse direction of motor if the arm is flipped manually
        // motorArm.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset the motor encoder so that it reads zero ticks
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (initCheckSensor.isPressed()) {
            armState = ArmStateType.INIT_OK;
        }
        else
            armState = ArmStateType.INIT_NOT_GOOD; // arm NOT in correct position
    }

    // check if the arm was initialized fine
    public int arm_init_ok () {
        return (armState == ArmStateType.INIT_OK?1:0);
    }

    public int check_limit_state () {
        if (initCheckSensor.isPressed()) return 1;
        else return 0;
    }


    //helper function to translate from angle to position
    private int ang_to_pos(double angle) {
        // float version
        float f_targetPosition = (float) PPR * (float)angle/(float)360;
        int   i_targetPosition = (int) f_targetPosition;
        return i_targetPosition;
    }
    private void set_arm_pos_pwr (int targetPosition, double TargetPwr) {
        motorArm.setTargetPosition(targetPosition);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setPower(TargetPwr);
    }

    private void set_arm_pos (int targetPosition) {
        set_arm_pos_pwr(targetPosition, ArmMotorPwrDefault);
    }

    public void fold() {
        // Flip the arm to fold-down position
        int targetPosition = ang_to_pos(FoldAngle);
        armState = ArmStateType.FOLD;
        set_arm_pos(targetPosition);
    }

    // stop the arm in whichever position it is in
    public void stop() {
        set_arm_pos(getCurrentPosition());
    }

    public void deposit() {
        int targetPosition = ang_to_pos(DepositAngle);
        armState = ArmStateType.DEPOSIT;
        set_arm_pos(targetPosition);
    }

    public void pick() {
        int targetPosition = ang_to_pos(PickAngle);

        //  safety code to prevent going from DEPOSIT to PICK
        if (armState == ArmStateType.DEPOSIT)
            return;

        armState = ArmStateType.PICK;
        set_arm_pos(targetPosition);
    }

    // allow very fine control of the arm (clockwise)
    public int fine_clk() {
        int dir = +1;
        double targetAngle = getCurrentAngle() + dir;
        int tolerance=5;

        // only allow a small range beyond the pickup angle
        if (targetAngle >= (PickAngle+tolerance))
            return -1;
        else {
            // update state and move
            armState = ArmStateType.FINE_ADJ;
            set_arm_pos_pwr(ang_to_pos(targetAngle), ArmMotorPwrDefault / 2);
            return (int) targetAngle;
        }
    }

    // allow very fine control of the arm (anti-clockwise)
    public int fine_anti_clk() {
        int dir = -1;
        double targetAngle = getCurrentAngle() + dir;

        // we will only move if outside of the fold angle region
        if (targetAngle <= FoldAngle)
            return -1;
        else {
            // update state and move
            armState = ArmStateType.FINE_ADJ;
            set_arm_pos_pwr(ang_to_pos(targetAngle), ArmMotorPwrDefault / 2);
            return (int) targetAngle;
        }
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
