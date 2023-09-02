package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;

@TeleOp
public class DriveTrainTeleOpMode extends OpMode {
    DriveTrain driveTrain;

    @Override
    public void init() {
        driveTrain = new DriveTrain(hardwareMap);
    }

    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;
        driveTrain.drive(forward, right, rotate);
    }
}
