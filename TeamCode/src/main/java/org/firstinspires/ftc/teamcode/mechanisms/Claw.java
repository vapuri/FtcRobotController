package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private Servo clawServo;

    public enum ClawStateType {
        CLAW_INIT,
        CLAW_OPEN,
        CLAW_CLOSED
    };
    private ClawStateType clawState;

    public Claw(HardwareMap hardwareMap) {
        clawServo = hardwareMap.get(Servo.class, "claw-servo");
        // don't modify these ranges!
        clawServo.scaleRange(0.5,0.8);
        ClawStateType clawState = ClawStateType.CLAW_INIT;
    }

    public void release() {
        clawServo.setPosition(0.4);
        clawState = ClawStateType.CLAW_OPEN;
    }

    public void grab() {
        clawServo.setPosition(0.9);
        clawState = ClawStateType.CLAW_CLOSED;
    }

    public boolean is_claw_closed() {return clawState == ClawStateType.CLAW_CLOSED;}
    public boolean is_claw_open()   {return clawState == ClawStateType.CLAW_OPEN;}
}
