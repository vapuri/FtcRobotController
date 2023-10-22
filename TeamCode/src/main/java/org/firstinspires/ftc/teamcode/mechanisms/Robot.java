package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    DriveTrain drivetrain;
    String name = "FTC New Era";

    public Robot(HardwareMap hardwareMap) {
        drivetrain = new DriveTrain(hardwareMap);

    }

    public DriveTrain getDrivetrain() {
        return drivetrain;
    }

    public String getName() {
        return name;
    }
}
