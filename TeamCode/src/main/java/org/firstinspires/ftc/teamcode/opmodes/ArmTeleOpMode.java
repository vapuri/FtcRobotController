package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Arm;

@TeleOp
public class ArmTeleOpMode extends OpMode {
    Arm arm;

    @Override
    public void init() {
        arm = new Arm(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.y)  // Check if the 'Y' button is pressed
            arm.rotateUp();
        else if (gamepad1.b)  // Check if the 'B' button is pressed
            arm.rotateDown();

        telemetry.addData("Current Position", arm.getCurrentPosition());
        telemetry.addData("Current Angle", arm.getCurrentAngle());
        telemetry.update();
    }
}
