package org.firstinspires.ftc.teamcode.opmodes;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import org.firstinspires.ftc.teamcode.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.mechanisms.ObjDetection;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;


@TeleOp
public class RobotTeleOpMode extends OpMode {
    Robot robot;
    String CompileDate= "11/19/2023";
    String CompileTime= "9:41pm";

    long init_time_mills=0;


    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        robot.getClaw().release();

        // print compile date & time
        telemetry.addLine("Build: " + CompileDate + " " + CompileTime);

        /* CHECK FOR ARM INIT POSITION */
        // if arm is NOT in correct position ..exit immediately!
        if (0 == robot.getArm().arm_init_ok())
            throw new RuntimeException("Robot Arm NOT in init position .. exiting!");
        else
            telemetry.addLine("Arm  init position Good!");

        // record current time
        init_time_mills = SystemClock.uptimeMillis();
    }

    //https://github.com/alan412/LearnJavaForFTC
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

        ObjDetection objdet = robot.getObjDetector();

        // cut power to drive-train if right_trigger is pressed
        if (gamepad1.right_trigger!=0)
            // || robot.getArm().getArmState() == Arm.ArmStateType.PICK
            robot.getDrivetrain().setPowerCap(FinePwrCap);
        else
            robot.getDrivetrain().setPowerCap(DfltPwrCap);

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

        /* #---------------------- GAMEPAD2 ----------------------# */
        // drone preparation (combination keys) - Gamepad2 (X + Y)
        if (gamepad2.x && gamepad2.y) {
            telemetry.speak("WARNING! Preparing Drone!");
            robot.getDroneLauncher().prepare();
        }
        // drone launcher (combination keys) - Gamepad2 (A + B)
        if (gamepad2.a && gamepad2.b) {
            telemetry.speak("Launching Drone!");
            robot.getDroneLauncher().launch();
        }

        //detect pixel
        double conf=objdet.telemetryTfod(telemetry);

        if (gamepad2.start)
            robot.set_pixel_seek(1);

        if (gamepad2.back)
            robot.set_pixel_seek(0);

        if (robot.pixel_seek()==1 && conf < 80.0) {
            telemetry.addData("FINE!", "(%.0f %% Conf.)",conf);
            double u_forward = -gamepad2.left_stick_y;
            double u_right   = gamepad2.left_stick_x;
            double u_rotate  = gamepad2.right_stick_x;
            robot.getDrivetrain().setPowerCap(5);
            robot.getDrivetrain().drive(u_forward, u_right, u_rotate);
        }

//        if (gamepad2.back) {
//            telemetry.speak("deleting ObjDet");
//            objdet.delobj();
//        }

        // update all telemetry
        telemetry.update();
    }

}