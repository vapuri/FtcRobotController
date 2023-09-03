package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorDistanceSensor {
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    public ColorDistanceSensor(HardwareMap hardwareMap) {
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor-color-distance");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "sensor-color-distance");
    }

    public int getAmountRed(){
        return colorSensor.red();
    }

    public int getAmountBlue(){
        return colorSensor.blue();
    }

    public double getDistance(DistanceUnit du) {
        return distanceSensor.getDistance(du);
    }
}
