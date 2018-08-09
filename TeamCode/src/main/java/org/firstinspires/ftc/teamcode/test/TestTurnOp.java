package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseRobot;

/**
 * Created by andrew on Dec 01, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.test.
 */

@Autonomous
public class TestTurnOp extends BaseRobot {

    private int stage = 0;

    @Override
    public void loop() {
        super.loop();
        switch (stage) {
            case 0:
                if (auto_turn(0.5, 90)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            case 1:
                if (auto_turn(-0.5, 90)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            default:
                break;
        }
    }
}
