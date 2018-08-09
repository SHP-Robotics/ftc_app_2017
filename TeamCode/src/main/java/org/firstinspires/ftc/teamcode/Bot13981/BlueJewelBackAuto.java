package org.firstinspires.ftc.teamcode.Bot13981;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by andrew on Nov 30, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 */

@Autonomous
//@Disabled
public class BlueJewelBackAuto extends MainRobot {
    private int stage = 0;
    private int turnMult = 0;

    @Override
    public void init() {
        super.init();
        set_jewel_servo_left(ConstantVariables.K_JEWEL_SERVO_LEFT_UP);
        set_jewel_servo_right(ConstantVariables.K_JEWEL_SERVO_RIGHT_UP);
        lift_servo_left.setPosition(ConstantVariables.K_LIFT_SERVO_LEFT_OUT);
        lift_servo_right.setPosition(ConstantVariables.K_LIFT_SERVO_RIGHT_OUT);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
        public void loop() {
            super.loop();
            switch (stage) {
                case 0:
                    lift_servo_left.setPosition(ConstantVariables.K_LIFT_SERVO_LEFT_IN);
                    lift_servo_right.setPosition(ConstantVariables.K_LIFT_SERVO_RIGHT_IN);
                    if (timer.time() > 0.5) {
                        stage++;
                    }
                    break;
                case 1:
                    if (Math.abs(get_lift_enc()) < 500) {
                        lift.setPower(1);
                    } else {
                        lift.setPower(0);
                        stage++;
                    }
                    break;
                case 2:
                    set_jewel_servo_right(ConstantVariables.K_JEWEL_SERVO_RIGHT_DOWN + 0.2);
                    stage++;
                    break;
                case 3:
                    if (timer.time() > 2 && timer.time() < 3) {
                        set_jewel_servo_right(ConstantVariables.K_JEWEL_SERVO_RIGHT_DOWN);
                    } else if (timer.time() > 3) {
                        stage++;
                    }
                    break;
                case 4:
                    turnMult = get_turn_mult(true);
                    stage++;
                    break;
                case 5:
                    if (auto_turn(-turnMult * 0.3, 10)) {
                        reset_drive_encoders();
                        stage++;
                    }
                    break;
                case 6:
                    if (auto_turn(turnMult * 0.3, 10)) {
                        reset_drive_encoders();
                        stage++;
                    }
                    set_jewel_servo_right(ConstantVariables.K_JEWEL_SERVO_RIGHT_UP);
                    break;
                case 7:
                    if (auto_drive(-0.1, 13)) {
                        reset_drive_encoders();
                        stage++;
                    }
                    break;
                case 8:
                    if (auto_turn(0.1, 110)) {
                        reset_drive_encoders();
                        stage++;
                    }
                    break;
                case 9:
                    if (Math.abs(get_lift_enc()) > 200) {
                        lift.setPower(-0.5);
                    } else {
                        lift.setPower(0);
                        stage++;
                    }
                    break;
                case 10:
                    lift_servo_left.setPosition(ConstantVariables.K_LIFT_SERVO_LEFT_OUT);
                    lift_servo_right.setPosition(ConstantVariables.K_LIFT_SERVO_RIGHT_OUT);
                    stage++;
                    break;
                case 11:
                    if (auto_drive(0.1, 8)) {
                        reset_drive_encoders();
                        stage++;
                    }
                    break;

                default:
                    break;
            }
        telemetry.addData("TurnMult: ", turnMult);
    }
}
