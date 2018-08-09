package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by andrew on Oct 31, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 */

//@Disabled
public class BaseRobot extends OpMode {

    public DcMotor left_drive, right_drive, left_lift, right_lift, left_intake, right_intake;
    public ElapsedTime timer = new ElapsedTime();
    public Servo jewel_servo;
    public ColorSensor jewel_color;

    @Override
    public void init() {
        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        right_drive = hardwareMap.get(DcMotor.class, "right_drive");
        left_lift = hardwareMap.get(DcMotor.class, "left_lift");
        right_lift = hardwareMap.get(DcMotor.class, "right_lift");
        left_intake = hardwareMap.get(DcMotor.class, "left_intake");
        right_intake = hardwareMap.get(DcMotor.class, "right_intake");

        left_drive.setDirection(DcMotor.Direction.REVERSE);
        left_lift.setDirection(DcMotorSimple.Direction.REVERSE);
        right_intake.setDirection(DcMotorSimple.Direction.REVERSE);

        jewel_servo = hardwareMap.get(Servo.class, "jewel_servo");
        jewel_color = hardwareMap.get(ColorSensor.class, "jewel_color");

    }

    @Override
    public void start() {
        timer.reset();
        reset_drive_encoders();
        reset_lift_encoders();
        set_jewel_servo(Constants.K_JEWEL_SERVO_UP);
        jewel_color.enableLed(true);
    }

    @Override
    public void loop() {
        telemetry.addData("D00 Left Drive Enc: ", get_left_drive_enc());
        telemetry.addData("D01 Right Drive Enc: ", get_right_drive_enc());
        telemetry.addData("D02 Left Lift Enc: ", get_left_lift_enc());
        telemetry.addData("D03 Right Lift Enc: ", get_right_lift_enc());
        telemetry.addData("D04 Color Red: ", jewel_color.red());
        telemetry.addData("D05 Color Blue: ", jewel_color.blue());
    }

    /**
     * @param power: The speed to drive at. Positive for forward.
     * @param turn:  The speed to turn at. Positive for right.
     */
    public void arcade_drive(double power, double turn) {
        double leftPower = Range.clip(power + turn, -1.0, 1.0);
        double rightPower = Range.clip(power - turn, -1.0, 1.0);

        left_drive.setPower(leftPower);
        right_drive.setPower(rightPower);
    }

    /**
     * @param leftPwr:  The speed for the left motor to turn at. Positive for forward.
     * @param rightPwr: The speed for the right motor to turn at. Positive for forward.
     */
    public void tank_drive(double leftPwr, double rightPwr) {
        double leftPower = Range.clip(leftPwr, -1.0, 1.0);
        double rightPower = Range.clip(rightPwr, -1.0, 1.0);

        left_drive.setPower(leftPower);
        right_drive.setPower(rightPower);
    }

    /**
     * @param power: The speed to drive the lift at. Positive for up.
     *               TODO: check encoder max/min values for overshoot handling
     */
    public void drive_lift(double power) {
        double left_speed = power;
        double right_speed = power;
        double error = get_left_lift_enc() - get_right_lift_enc();

        error /= Constants.K_LIFT_ERROR_P;
        left_speed -= error;
        right_speed += error;

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        if (get_left_lift_enc() >= Constants.K_LIFT_MAX) {
            left_speed = Range.clip(left_speed, -1, 0);
        } else if (get_left_lift_enc() <= Constants.K_LIFT_MIN) {
            left_speed = Range.clip(left_speed, 0, 1);
        }

        if (get_right_lift_enc() >= Constants.K_LIFT_MAX) {
            right_speed = Range.clip(right_speed, -1, 0);
        } else if (get_right_lift_enc() <= Constants.K_LIFT_MIN) {
            right_speed = Range.clip(right_speed, 0, 1);
        }
        left_lift.setPower(left_speed);
        right_lift.setPower(right_speed);
    }

    /**
     * @param power: The speed to drive the intake at. Positive for output.
     */
    public void drive_intake(double power) {
        power = Range.clip(power, -1, 1);

        left_intake.setPower(power);
        right_intake.setPower(power);
    }

    public void set_jewel_servo(double pos) {
        jewel_servo.setPosition(pos);
    }


    public boolean auto_drive(double power, double inches) {
        double TARGET_ENC = Constants.K_PPIN_DRIVE * inches;

        double left_speed = power;
        double right_speed = power;
        double error = get_left_drive_enc() - get_right_drive_enc();

        error /= Constants.K_DRIVE_ERROR_P;
        left_speed -= error;
        right_speed += error;

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        left_drive.setPower(left_speed);
        right_drive.setPower(right_speed);

        if (Math.abs(get_left_drive_enc()) >= TARGET_ENC &&
                Math.abs(get_right_drive_enc()) >= TARGET_ENC) {
            left_drive.setPower(0);
            right_drive.setPower(0);
            return true;
        }
        return false;
    }

    /**
     * @param power:   the speed to turn at. Negative for left.
     * @param degrees: the number of degrees to turn.
     * @return Whether the target angle has been reached.
     */
    public boolean auto_turn(double power, double degrees) {
        double TARGET_ENC = Math.abs(Constants.K_PPDEG_DRIVE * degrees);
        telemetry.addData("D99 TURNING TO ENC: ", TARGET_ENC);

        if (Math.abs(get_left_drive_enc()) >= TARGET_ENC &&
                Math.abs(get_right_drive_enc()) >= TARGET_ENC) {
            left_drive.setPower(0);
            right_drive.setPower(0);
            return true;
        } else {
//            double left_speed = power;
//            double right_speed = power;
//            double error = get_left_drive_enc() + get_right_drive_enc();
//
//            error /= Constants.K_DRIVE_ERROR_P;
//            left_speed += error;
//            right_speed -= error;
//
//            left_speed = Range.clip(left_speed, -1, 1);
//            right_speed = Range.clip(right_speed, -1, 1);

            left_drive.setPower(power);
            right_drive.setPower(-power);
        }
        return false;
    }

    public void reset_lift_encoders() {
        left_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public int get_left_lift_enc() {
        if (left_lift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            left_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return left_lift.getCurrentPosition();
    }

    public int get_right_lift_enc() {
        if (right_lift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            right_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return right_lift.getCurrentPosition();
    }

    public void reset_drive_encoders() {
        left_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int get_left_drive_enc() {
        if (left_drive.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            left_drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return left_drive.getCurrentPosition();
    }

    public int get_right_drive_enc() {
        if (right_drive.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            right_drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return right_drive.getCurrentPosition();
    }

    public int get_turn_mult(boolean isRed) {
        return (jewel_color.red() > jewel_color.blue() ? 1 : -1) * (isRed ? 1 : -1);
    }

}
