package org.firstinspires.ftc.teamcode.Bot13981.hardware_tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Bot13981.MainRobot;

/**
 * Created by andrew on Oct 31, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 * Stop yelling at me thanks
 * ^ I feel the same way
 */

@TeleOp(name = "DriveTestOp")
@Disabled
public class DriveTestOp extends MainRobot {
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
        holonomic_drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        telemetry.addData("00 Left Drive Front Enc: ", get_left_drive_front_enc());
        telemetry.addData("01 Right Drive Front Enc: ", get_right_drive_front_enc());
        telemetry.addData("02 Left Drive Back Enc: ", get_left_drive_back_enc());
        telemetry.addData("03 Right Drive Back Enc: ", get_right_drive_back_enc());

        if (gamepad1.a) {
            auto_turn(0.5, 90);
        } else if (gamepad1.b) {
            reset_drive_encoders();
        } else if (gamepad1.x) {
            auto_drive(0.5,10);
        }
    }
}
