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

@TeleOp(name = "LiftResetOp")
@Disabled
public class LiftResetOp extends MainRobot {
    @Override
    public void init() {
        super.init();

        lift_servo_left.setPosition(ConstantVariables.K_LIFT_SERVO_LEFT_IN);
        lift_servo_right.setPosition(ConstantVariables.K_LIFT_SERVO_RIGHT_IN);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();

        if (gamepad1.dpad_up) {
            lift.setPower(1);
        } else if (gamepad1.dpad_down) {
            lift.setPower(-1);
        } else {
            lift.setPower(0);
        }

        telemetry.addData("00 Lift Enc: ", get_lift_enc());
        telemetry.addData("01 Left Servo Position: ", lift_servo_left.getPosition());
        telemetry.addData("02 Right Servo Position: ", lift_servo_right.getPosition());
        if (gamepad1.a) {
            intake(0);
        } else if (gamepad1.b) {
            intake(1);
        } else if (gamepad1.x) {

        } else if (gamepad1.y) {

        }
    }
}
