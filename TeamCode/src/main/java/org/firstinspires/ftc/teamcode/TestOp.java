package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by andrew on Oct 31, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 * Stop yelling at me thanks
 */

@TeleOp(name = "TestOp")
@Disabled
public class TestOp extends BaseRobot {
    @Override
    public void init() {
        super.init();
        set_jewel_servo(Constants.K_JEWEL_SERVO_UP);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        arcade_drive(-gamepad1.left_stick_y, gamepad1.left_stick_x);
        telemetry.addData("00 Left Drive Enc: ", get_left_drive_enc());
        telemetry.addData("01 Right Drive Enc: ", get_right_drive_enc());
        if (gamepad1.a) {
            auto_turn(0.5, 90);
        } else if (gamepad1.b) {
            reset_drive_encoders();
        }
    }
}
