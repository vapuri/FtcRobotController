package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private Servo clawServo;

    public Claw(HardwareMap hardwareMap) {
        clawServo = hardwareMap.get(Servo.class, "claw-servo");
        clawServo.scaleRange(0.5,0.8);
    }

    public void release() {
        clawServo.setPosition(0.6);
    }

    public void grab() {
        clawServo.setPosition(0.9);
    }
}
