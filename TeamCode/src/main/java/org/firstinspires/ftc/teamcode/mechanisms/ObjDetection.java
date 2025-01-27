package org.firstinspires.ftc.teamcode.mechanisms;


import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;

public class ObjDetection {
    private int init_done=0;

    private double prev_confidence = 0.0;

    private TfodProcessor tfod;
    // Create the vision portal the easy way.
    private VisionPortal.Builder myVisionPortalBuilder;
    private VisionPortal myVisionPortal;
    HardwareMap hardwareMap_copy;

    public ObjDetection (HardwareMap hardwareMap) {
        hardwareMap_copy = hardwareMap;
        initobj();
    }

    private void initobj () {
        HardwareMap hardwareMap = hardwareMap_copy;

        // Create the TensorFlow processor the easy way.
        tfod = TfodProcessor.easyCreateWithDefaults();

//        // Create a new VisionPortal Builder object.
//        myVisionPortalBuilder = new VisionPortal.Builder();
//
//        // Specify the camera to be used for this VisionPortal.
//        myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));      // Other choices are: RC phone camera and "switchable camera name".
//
//        // Add the tfod Processor to the VisionPortal Builder.
//        myVisionPortalBuilder.addProcessor(tfod);       // An added Processor is enabled by default.
//
//        // Optional: set other custom features of the VisionPortal (4 are shown here).
//        myVisionPortalBuilder.setCameraResolution(new Size(960, 720));  // Each resolution, for each camera model, needs calibration values for good pose estimation.
//        myVisionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.MJPEG);  // MJPEG format uses less bandwidth than the default YUY2.
//        myVisionPortalBuilder.enableLiveView(true);                             // Enable LiveView (RC preview).
//        myVisionPortalBuilder.setAutoStopLiveView(true);                        // Automatically stop LiveView (RC preview) when all vision processors are disabled.
//
//        // Create a VisionPortal by calling build()
//        myVisionPortal = myVisionPortalBuilder.build();

        // bind the object detection processor (simple version)
        myVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap_copy.get(WebcamName.class, "Webcam 1"), tfod);

        tfod.setZoom(4.0);
        tfod.setMinResultConfidence((float) 0.50);

        // we're done with init
        init_done=1;
    }

    public double telemetryTfod(Telemetry telemetry) {
        if (init_done==0)
            initobj();

        double confidence=-1;

        //tfod.setClippingMargins(0,0,0,0);

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        //List<Recognition> currentRecognitions = tfod.getFreshRecognitions();
        //if (currentRecognitions == null) return -1;

        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
            confidence = recognition.getConfidence() * 100;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(),confidence);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f (%.0f)", recognition.getWidth(), recognition.getHeight(),( recognition.getWidth()/recognition.getHeight()));

            if (confidence  > prev_confidence)
                prev_confidence = confidence;
        }   // end for() loop
        //public abstract void setClippingMargins(int left, int top, int right, int bottom);
        //tfod.setClippingMargins(640/2,480/2,640/2, 480/2);
        //tfod.setClippingMargins(0,0,0,0);

        return (confidence);
    }   // end method telemetryTfod()

    public void delobj () {
        myVisionPortal.close();
        init_done=0;
    }
}