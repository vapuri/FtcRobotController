package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.ColorDistanceSensor;
@TeleOp
public class ColorDistanceSensorOpMode extends OpMode {
    ColorDistanceSensor colorDistanceSensor;

    @Override
    public void init() {
        colorDistanceSensor = new ColorDistanceSensor(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("Amount Red", colorDistanceSensor.getAmountRed());
        telemetry.addData("Amount Blue", colorDistanceSensor.getAmountBlue());
        telemetry.addData("Distance (CM)", colorDistanceSensor.getDistance(DistanceUnit.CM));
    }
}
