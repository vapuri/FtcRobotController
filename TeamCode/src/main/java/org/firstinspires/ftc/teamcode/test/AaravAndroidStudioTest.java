package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//@TeleOp(name="AaravAndroidStudioTest", group="Linear Opmode")
public class AaravAndroidStudioTest extends LinearOpMode {

    private DcMotor motorfrontleft;
    private DcMotor motorrearleft;
    private DcMotor motorfrontright;
    private DcMotor motorrearright;

    private int power_cap = 1;
    private long lastPowerCapAdjustTime = 0;
    private static final long POWER_CAP_ADJUST_INTERVAL = 200;

    @Override
    public void runOpMode() {

        motorfrontleft = hardwareMap.get(DcMotor.class, "motor-front-left");
        motorrearleft = hardwareMap.get(DcMotor.class, "motor-rear-left");
        motorfrontright = hardwareMap.get(DcMotor.class, "motor-front-right");
        motorrearright = hardwareMap.get(DcMotor.class, "motor-rear-right");

        motorfrontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorrearleft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            float left_y = -gamepad1.left_stick_y / power_cap;
            float left_x = gamepad1.left_stick_x / power_cap;
            float right_x = gamepad1.right_stick_x / power_cap;

            if (gamepad1.dpad_up && (System.currentTimeMillis() - lastPowerCapAdjustTime) >= POWER_CAP_ADJUST_INTERVAL) {
                power_cap++;
                lastPowerCapAdjustTime = System.currentTimeMillis();
            }

            if (gamepad1.dpad_down && (System.currentTimeMillis() - lastPowerCapAdjustTime) >= POWER_CAP_ADJUST_INTERVAL) {
                power_cap--;
                if (power_cap < 1) {
                    power_cap = 1;
                }
                lastPowerCapAdjustTime = System.currentTimeMillis();
            }

            if (right_x != 0) {
                motorfrontright.setPower(-right_x);
                motorrearright.setPower(-right_x);
                motorfrontleft.setPower(right_x);
                motorrearleft.setPower(right_x);
            } else if (left_x != 0) {
                motorfrontright.setPower(-left_x);
                motorrearright.setPower(left_x);
                motorfrontleft.setPower(left_x);
                motorrearleft.setPower(-left_x);
            } else {
                motorfrontright.setPower(left_y);
                motorrearright.setPower(left_y);
                motorfrontleft.setPower(left_y);
                motorrearleft.setPower(left_y);
            }

            telemetry.addData("Left Y", left_y);
            telemetry.addData("Left X", left_x);
            telemetry.addData("Right X", right_x);
            telemetry.addData("Power Cap", power_cap);
            telemetry.update();
        }
    }
}
