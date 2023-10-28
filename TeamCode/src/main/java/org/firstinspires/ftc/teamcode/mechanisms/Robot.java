package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    DriveTrain drivetrain;
    Arm arm;
    Claw claw;
    // Eyes eyes;

    public Robot(HardwareMap hardwareMap) {
        drivetrain = new DriveTrain(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);
        // eyes = new Eyes(hardwareMap);
    }

    public DriveTrain getDrivetrain() {
        return drivetrain;
    }

    public Arm getArm() {
        return arm;
    }

    public Claw getClaw() {
        return claw;
    }

    /* public Eyes getEyes() {
        return eyes;
    } */
}
