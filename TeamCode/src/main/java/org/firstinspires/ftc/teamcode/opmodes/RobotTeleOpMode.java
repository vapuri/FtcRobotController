package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import org.firstinspires.ftc.teamcode.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;


@TeleOp
public class RobotTeleOpMode extends OpMode {
    Robot robot;
    String CompileDate= "11/5/2023";
    String CompileTime= "11:51am";

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        robot.getClaw().release();

        // print compile date & time
        telemetry.addLine("Build: " + CompileDate + " " + CompileTime);

        telemetry.speak("Make sure the arm is in init position");
        telemetry.addLine("Make sure the arm is in init position");
    }

    @Override
    public void loop() {
        // robot.getEyes().detectAprilTags(telemetry);

        // map game-pad controls to specific tasks
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

        // monitor game-pad inputs and take respective action
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