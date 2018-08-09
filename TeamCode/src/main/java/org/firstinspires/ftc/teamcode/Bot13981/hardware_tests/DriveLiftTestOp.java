package org.firstinspires.ftc.teamcode.Bot13981.hardware_tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Bot13981.ConstantVariables;
import org.firstinspires.ftc.teamcode.Bot13981.MainRobot;

/**
 * Created by andrew on Oct 31, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 * Stop yelling at me thanks
 * ^ I feel the same way
 */

@TeleOp(name = "DriveLiftTestOp")
// @Disabled
public class DriveLiftTestOp extends MainRobot {
    double relic_servo_position = 0.1;
    double grabber_servo_position = 0.15;

    @Override
    public void init() {
        super.init();
        set_jewel_servo_left(ConstantVariables.K_JEWEL_SERVO_LEFT_UP);
        set_jewel_servo_right((ConstantVariables.K_JEWEL_SERVO_RIGHT_UP));
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        telemetry.addData("00 Left Drive Front Enc: ", get_left_drive_front_enc());
        telemetry.addData("01 Right Drive Front Enc: ", get_right_drive_front_enc());
        telemetry.addData("02 Left Drive Back Enc: ", get_left_drive_back_enc());
        telemetry.addData("03 Right Drive Back Enc: ", get_right_drive_back_enc());

        holonomic_drive(gamepad1.left_stick_x/3, -gamepad1.left_stick_y/3, gamepad1.right_stick_x/3); // omnidirectional movement

        if (gamepad1.left_bumper) {
            drive_lift(1); //move lift up
        } else if (gamepad1.left_trigger > 0.5) {
            drive_lift(-1); //move lift
            // down
        } else {
            drive_lift(0);
        }

        if (gamepad1.a) {
            intake(0); //close glyph servos
        } else if (gamepad1.b) {
            intake(1); //open glyph servos
        }

        if (gamepad1.right_bumper && get_grabber_enc() < ConstantVariables.K_GRABBER_MAX) {
            grab_relic(1);
        } else if (gamepad1.right_trigger > 0.5 && get_grabber_enc() > ConstantVariables.K_GRABBER_MIN) {
            grab_relic(-1);
        } else {
            grab_relic(0);
        }

        if (gamepad1.dpad_down) {
            relic_servo_position = 0.55;
        } else if (gamepad1.dpad_up) {
            relic_servo_position = 1;
        }
        if (gamepad1.dpad_left) {
            grabber_servo_position -= 0.05;
        } else if (gamepad1.dpad_right) {
            grabber_servo_position += 0.05;
        } else if (gamepad1.x) {
            grabber_servo_position = 0.2;                                                                                                                                                 ;
        } else
                    Range.clip(relic_servo_position, 0,1);
        Range.clip(grabber_servo_position, 0,1);
        relic_servo.setPosition(relic_servo_position);
        grabber_servo.setPosition(grabber_servo_position);
    }
}
