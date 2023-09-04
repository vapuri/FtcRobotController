package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    DriveTrain drivetrain;
    ColorDistanceSensor colorDistanceSensor;

    public Robot(HardwareMap hardwareMap) {
        drivetrain = new DriveTrain(hardwareMap);
        colorDistanceSensor = new ColorDistanceSensor(hardwareMap);
    }

    public DriveTrain getDrivetrain() {
        return drivetrain;
    }
    public ColorDistanceSensor getColorDistanceSensor() {
        return colorDistanceSensor;
    }
}