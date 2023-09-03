package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

@Autonomous
public class RobotAutoOpMode extends OpMode {
    Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a)
            robot.getDrivetrain().drive(1, 0, 0);
        if (gamepad1.b)
            robot.getDrivetrain().drive(-1, 0, 0);
        if (gamepad1.x)
            robot.getDrivetrain().stop();

        double distance = robot.getColorDistanceSensor().getDistance(DistanceUnit.CM);

        if (distance>4 && distance <4.5)
            robot.getDrivetrain().stop();

        telemetry.addData("Distance (CM)", distance);
    }
}
