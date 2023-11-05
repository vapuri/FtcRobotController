package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import org.firstinspires.ftc.teamcode.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;


@TeleOp
public class RobotTeleOpMode extends OpMode {
    Robot robot;
    String CompileDate= "11/5/2023";
    String CompileTime= "02:00pm";


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

        // set overall power cap here on top!
        int  DfltPwrCap = 2; // default
        int  FinePwrCap = 6; // fine
        int  MidPwrCap  = (FinePwrCap+DfltPwrCap)/2; // intermediate

        // cut power to drive-train if right_trigger is NOT pressed
        if (gamepad1.right_trigger!=0)
            // || robot.getArm().getArmState() == Arm.ArmStateType.PICK
            robot.getDrivetrain().setPowerCap(6);
        else
            robot.getDrivetrain().setPowerCap(2);

        //monitor drive-train controls (i.e., mecanum wheel movement)
        robot.getDrivetrain().drive(forward, right, rotate);

        // monitor game-pad inputs for the arm & claw
        if (gamepad1.y)
            robot.getArm().pick();
        else if (gamepad1.b)
            robot.getArm().fold();
        else if (gamepad1.a)
            robot.getArm().deposit();
        else if (gamepad1.x)
            robot.getArm().stop();
        else if (gamepad1.left_bumper)
            robot.getClaw().grab();
        else if (gamepad1.right_bumper)
            robot.getClaw().release();

        /* Using the D-Pad to do fine controls to help with pickup*/
        // check for fine angle controls (using the d-keys on the left of the game-pad)
        if (gamepad1.dpad_up) // fine control of arm: downwards
            robot.getArm().fine_clk();
        if (gamepad1.dpad_down) // fine control of arm: downwards
            robot.getArm().fine_anti_clk();
        if (gamepad1.dpad_left) { // fine control of robot: left
            robot.getDrivetrain().setPowerCap(MidPwrCap);
            robot.getDrivetrain().drive(0/*forward*/, -1/*right*/, 0/*rotate*/);
        }
        if (gamepad1.dpad_right) { // fine control of robot: right
            robot.getDrivetrain().setPowerCap(MidPwrCap);
            robot.getDrivetrain().drive(0/*forward*/, +1/*right*/, 0/*rotate*/);
        }

        // update all telemetry
        telemetry.update();
    }

}