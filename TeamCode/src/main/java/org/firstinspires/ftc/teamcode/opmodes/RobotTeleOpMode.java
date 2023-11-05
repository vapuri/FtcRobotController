package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

@TeleOp
public class RobotTeleOpMode extends OpMode {
    Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        robot.getClaw().release();
        telemetry.speak("Make sure the arm is in init position");
        telemetry.addLine("Make sure the arm is in init position");
    }

    @Override
    public void loop() {
        // robot.getEyes().detectAprilTags(telemetry);

        // map gamepad controls to specific tasks
        double forward = -gamepad1.left_stick_y;
        double right   = gamepad1.left_stick_x;
        double rotate  = gamepad1.right_stick_x;

        // cut power to drive-train if right_trigger is NOT pressed
        if (gamepad1.right_trigger!=0)
            // || robot.getArm().getArmState() == Arm.ArmStateType.PICK
            robot.getDrivetrain().setPowerCap(6);
        else
            robot.getDrivetrain().setPowerCap(2);

        //monitor drive-train controls (i.e., mecanum wheel movement)
        robot.getDrivetrain().drive(forward, right, rotate);


        if (gamepad1.y)
            robot.getArm().pick();
        else if (gamepad1.b)
            robot.getArm().fold();
        else if (gamepad1.a)
            robot.getArm().deposit();
        else if (gamepad1.left_bumper)
            robot.getClaw().grab();
        else if (gamepad1.right_bumper)
            robot.getClaw().release();

        telemetry.update();
    }

}