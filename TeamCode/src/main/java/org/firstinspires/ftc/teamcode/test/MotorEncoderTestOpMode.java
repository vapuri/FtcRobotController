package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MotorEncoderTestOpMode extends OpMode {
    private DcMotor motorArm;

    @Override
    public void init() {
        motorArm = hardwareMap.get(DcMotor.class, "motor-rear-right");
        // Reset the motor encoder so that it reads zero ticks
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        double gearRatio = 5;
        double PPR = 1425.1*gearRatio;

        // Get the current position of the motor
        int position = motorArm.getCurrentPosition();
        double revolutions = position / PPR;

        double angle = revolutions * 360;
        double angleNormalized = angle % 360;

        // Show the position of the motor on telemetry
        telemetry.addData("Encoder Position", position);
        telemetry.addData("Encoder Revolutions", revolutions);
        telemetry.addData("Encoder Angle (Degrees)", angle);
        telemetry.addData("Encoder Angle - Normalized (Degrees)", angleNormalized);
        telemetry.update();
    }
}
