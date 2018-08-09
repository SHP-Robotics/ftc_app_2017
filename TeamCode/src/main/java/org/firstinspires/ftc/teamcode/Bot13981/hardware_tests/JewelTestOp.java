package org.firstinspires.ftc.teamcode.Bot13981.hardware_tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Bot13981.ConstantVariables;
import org.firstinspires.ftc.teamcode.Bot13981.MainRobot;

/**
 * Created by andrew on Oct 31, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 * Stop yelling at me thanks
 * ^ I feel the same way
 */

@TeleOp(name = "JewelTestOp")
@Disabled
public class JewelTestOp extends MainRobot {
    double jewel_left_position = ConstantVariables.K_JEWEL_SERVO_LEFT_UP;
    double jewel_right_position = ConstantVariables.K_JEWEL_SERVO_RIGHT_UP;

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

        telemetry.addData("00 Left Jewel Servo Position: ", jewel_servo_left.getPosition());
        telemetry.addData("01 Right Jewel Servo Position: ", jewel_servo_right.getPosition());
        if (gamepad1.a) {
            jewel_left_position += 0.05;
        } else if (gamepad1.b) {
            jewel_left_position -= 0.05;
        } else if (gamepad1.x) {
            jewel_right_position += 0.05;
        } else if (gamepad1.y) {
            jewel_right_position -= 0.05;
        }
        jewel_servo_left.setPosition(jewel_left_position);
        jewel_servo_right.setPosition(jewel_right_position);
    }
}
