package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    DriveTrain drivetrain;
    Arm arm;
    Claw claw;
    //Eyes eyes;

    DroneLauncher drone;

    ObjDetection objdet;

    private int pixel_seek=0;

    public Robot(HardwareMap hardwareMap) {
        drivetrain = new DriveTrain(hardwareMap);
        arm = new Arm(hardwareMap);
        claw = new Claw(hardwareMap);
        //eyes = new Eyes(hardwareMap);
        drone = new DroneLauncher(hardwareMap);
        //objdet = new ObjDetection(hardwareMap);

        pixel_seek=0;
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

    public DroneLauncher getDroneLauncher () {return drone;}

    public ObjDetection getObjDetector() {return objdet;}

    public int pixel_seek() {return pixel_seek;}

    public void set_pixel_seek(int val) {pixel_seek = (val==0)? 0 : 1;}

    /* public Eyes getEyes() {
        return eyes;
    } */
}
