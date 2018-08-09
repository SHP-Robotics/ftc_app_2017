package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by andrew on Nov 28, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 */

@TeleOp
@Disabled
public class DannyColorTestOp extends BaseRobot {
    private static final double SERVO_DOWN = 0.7;
    private static final double SERVO_UP = 0.1;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        if (gamepad1.a) {
            jewel_servo.setPosition(SERVO_DOWN);
        } else if (gamepad1.b) {
            jewel_servo.setPosition(SERVO_UP);
        }
        telemetry.addData("00 R: ", jewel_color.red());
        telemetry.addData("01 G: ", jewel_color.green());
        telemetry.addData("02 B: ", jewel_color.blue());
    }
}
