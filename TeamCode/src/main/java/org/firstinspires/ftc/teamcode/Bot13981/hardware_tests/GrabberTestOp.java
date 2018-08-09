package org.firstinspires.ftc.teamcode.Bot13981.hardware_tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Bot13981.MainRobot;

/**
 * Created by andrew on Oct 31, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 * Stop yelling at me thanks
 * ^ I feel the same way
 */

@TeleOp(name = "GrabberTestOp")
@Disabled
public class GrabberTestOp extends MainRobot {
    double relic_servo_position = 0.1;
    double grabber_servo_position = 0.15;

    @Override
    public void init() {
        super.init();
        relic_servo.setPosition(0.5);
        grabber_servo.setPosition(0.5);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        if (gamepad1.left_stick_x > 0) {
            grab_relic(1);
        } else if (gamepad1.left_stick_x < 0) {
            grab_relic(-1);
        } else {
            grab_relic(0);
        }

        telemetry.addData("00 Grabber Enc: ", get_grabber_enc());
        telemetry.addData("01 Relic Servo: ", relic_servo.getPosition());
        telemetry.addData("02 Grabber Servo: ", grabber_servo.getPosition());

        if (gamepad1.a) {
            relic_servo_position += 0.05;
            relic_servo.setPosition(relic_servo_position);
        } else if (gamepad1.b) {
            relic_servo_position -= 0.05;
            relic_servo.setPosition(relic_servo_position);
        } else if (gamepad1.x) {
            grabber_servo_position += 0.05;
            grabber_servo.setPosition(grabber_servo_position);
        } else if (gamepad1.y) {
            grabber_servo_position -= 0.05;
            grabber_servo.setPosition(grabber_servo_position);
        }
        Range.clip(relic_servo_position, 0,1);
        Range.clip(grabber_servo_position, 0,1);
    }
}