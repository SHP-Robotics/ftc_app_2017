package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by andrew on Nov 30, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 */

@Autonomous
@Disabled
public class BlueJewelAuto extends BaseRobot {
    private int stage = 0;
    private int turnMult = 0;

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
        switch (stage) {
            case 0:
                if (auto_drive(-0.3, 2.7)) {
                    reset_drive_encoders();
                    stage++;
                }
                set_jewel_servo(Constants.K_JEWEL_SERVO_DOWN);
                break;
            case 1:
                turnMult = get_turn_mult(false);
                stage++;
                break;
            case 2:
                if (auto_turn(turnMult * 0.6, 15)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
//            case 3:
//                if (auto_drive(0.5, 15)) {
//                    reset_drive_encoders();
//                    stage++;
//                }
//                break;
//            case 4:
//                arcade_drive(0, 1);
//                break;
//            case 3:
//                if (auto_turn(-turnMult * 0.2, 15)) {
//                    reset_drive_encoders();
//                    stage++;
//                }
//                set_jewel_servo(Constants.K_JEWEL_SERVO_UP);
//                break;
//            case 4:
//                if (auto_drive(0.6, 3)) {
//                    reset_drive_encoders();
//                    stage++;
//                }
//                break;
            default:
                break;
        }
        telemetry.addData("TurnMult: ", turnMult);
    }
}
