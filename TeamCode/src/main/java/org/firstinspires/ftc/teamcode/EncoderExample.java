package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by andrew on Nov 10, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 */
@Disabled
public class EncoderExample extends BaseRobot {
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
        // you should reset encoders here
        // I recommend writing a public `reset_encoders()` method in `BaseRobot`
    }

    @Override
    public void loop() {
        super.loop();
        // Ex: print the current encoder value to driver station (DS)
        telemetry.addData("Left Lift Encoder Value: ", left_lift.getCurrentPosition());
        if (gamepad1.a) {
            reset_encoders(); // example implementation, see below
        }
    }

    private void reset_encoders() {
        left_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // tell motor to stop and begin resetting
        while (Math.abs(left_lift.getCurrentPosition()) < 5) {
            // make sure we've reset
        }
        left_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // back to normal operation
    }
}
