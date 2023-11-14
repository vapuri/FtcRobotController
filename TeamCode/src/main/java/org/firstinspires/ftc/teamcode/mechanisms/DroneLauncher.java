package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

// Center Stage Challenge: Paper drone launcher using simple servo motor mechanism.
public class DroneLauncher {
    private Servo DroneServo;

    public DroneLauncher(HardwareMap hardwareMap) {
        DroneServo = hardwareMap.get(Servo.class, "DroneServo");
        DroneServo.scaleRange(0.0,1.0);
    }

    // set to drone preparation position (down)
    public void prepare() {
        DroneServo.setPosition(0.9);
    }

    // set to drone launch position (Up)
    public void launch() {
        DroneServo.setPosition(0.60);
    }
}
