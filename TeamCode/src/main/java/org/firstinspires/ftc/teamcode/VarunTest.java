package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Varun: Test Git", group="Linear Opmode")

public class VarunTest extends LinearOpMode {

    private DcMotor motorfrontleft;
    private DcMotor motorrearleft;
    private DcMotor motorfrontright;
    private DcMotor motorrearright;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        float left_y, left_x, right_x;
        int power_cap = 3;

        motorfrontleft  = hardwareMap.get(DcMotor.class, "motor-front-left");
        motorrearleft   = hardwareMap.get(DcMotor.class, "motor-rear-left");
        motorfrontright = hardwareMap.get(DcMotor.class, "motor-front-right");
        motorrearright  = hardwareMap.get(DcMotor.class, "motor-rear-right");

        // Put initialization blocks here.
        // reverse direction of the left motors for mecanum wheels
        motorfrontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorrearleft.setDirection (DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (opModeIsActive()) {
            // Put run blocks here.

            while (opModeIsActive()) {
                // Put loop blocks here.
                left_y = -gamepad1.left_stick_y / power_cap;
                left_x = gamepad1.left_stick_x / power_cap;
                right_x = gamepad1.right_stick_x / power_cap;
                // put code here
                if (right_x != 0) {
                    motorfrontright.setPower(-right_x);
                    motorrearright.setPower(-right_x);
                    motorfrontleft.setPower(right_x);
                    motorrearleft.setPower  (right_x);
                }

                if (left_x != 0) {
                    motorfrontright.setPower(-left_x);
                    motorrearright.setPower(left_x);
                    motorfrontleft.setPower(left_x);
                    motorrearleft.setPower  (-left_x);
                }


                motorfrontright.setPower(left_y);
                motorrearright.setPower (left_y);
                motorfrontleft.setPower (left_y);
                motorrearleft.setPower  (left_y);


                telemetry.addData("Left y", left_y);
                telemetry.addData("Left x", left_x);
                telemetry.addData("Right x", right_x);
                telemetry.update();
            }
        }
    }
}