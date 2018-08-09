package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by andrew on Nov 14, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 */

@TeleOp
@Disabled
public class LiftTest extends BaseRobot {


    @Override
    public void init() {
        super.init();
        reset_lift_encoders();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        double left_speed = 0;
        double right_speed = 0;
        double error = get_left_lift_enc() - get_right_lift_enc();
        error /= Constants.K_LIFT_ERROR_P;

        if (gamepad1.dpad_up) {
            left_speed = 1;
            right_speed = 1;
        } else if (gamepad1.dpad_down) {
            left_speed = -1;
            right_speed = -1;
        }

        left_speed -= error;
        right_speed += error;


        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        left_lift.setPower(left_speed);
        right_lift.setPower(right_speed);

        telemetry.addData("00 Left Lift Enc: ", get_left_lift_enc());
        telemetry.addData("01 Right Lift Enc: ", get_right_lift_enc());

    }
}
