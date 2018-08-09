package org.firstinspires.ftc.teamcode.Bot13981;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Bot13981.ConstantVariables;


/**
 * Created by andrew on Dec 05, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 * and edited by matthew for 13981
 */


public class MainRobot extends OpMode {

    public DcMotor left_drive_front, right_drive_front, left_drive_back, right_drive_back, lift, grabber;
    public ElapsedTime timer = new ElapsedTime();
    public Servo jewel_servo_left, lift_servo_right, lift_servo_left, relic_servo, grabber_servo, jewel_servo_right;
    public ColorSensor jewel_color_left, jewel_color_right;


    @Override
    public void init() {
        left_drive_front = hardwareMap.get(DcMotor.class, "left_drive_front");
        right_drive_front = hardwareMap.get(DcMotor.class, "right_drive_front");
        left_drive_back = hardwareMap.get(DcMotor.class, "left_drive_back");
        right_drive_back = hardwareMap.get(DcMotor.class, "right_drive_back");
        lift = hardwareMap.get(DcMotor.class, "lift");
        grabber = hardwareMap.get(DcMotor.class, "grabber");

        right_drive_front.setDirection(DcMotor.Direction.REVERSE);
        right_drive_back.setDirection(DcMotor.Direction.REVERSE);

        jewel_servo_left = hardwareMap.get(Servo.class, "jewel_servo_left");
        jewel_color_left = hardwareMap.get(ColorSensor.class, "jewel_color_left");
        jewel_servo_right = hardwareMap.get(Servo.class, "jewel_servo_right");
        jewel_color_right = hardwareMap.get(ColorSensor.class, "jewel_color_right");

        lift_servo_right = hardwareMap.get(Servo.class, "lift_servo_right");
        lift_servo_left = hardwareMap.get(Servo.class, "lift_servo_left");
        relic_servo = hardwareMap.get(Servo.class, "relic_servo");
        grabber_servo = hardwareMap.get(Servo.class, "grabber_servo");
    }

    @Override
    public void start() {
        timer.reset();
        reset_drive_encoders();
        reset_lift_encoder();
        reset_grabber_encoder();
        jewel_color_left.enableLed(true);
        jewel_color_right.enableLed(true);
    }

    @Override
    public void loop() {
        telemetry.addData("D00 Left Drive Front Enc: ", get_left_drive_front_enc());
        telemetry.addData("D01 Right Drive Front Enc: ", get_right_drive_front_enc());
        telemetry.addData("D02 Left Drive Back Enc: ", get_left_drive_back_enc());
        telemetry.addData("D03 Right Drive Back Enc: ", get_right_drive_back_enc());
        telemetry.addData("D04 Lift Enc: ", get_lift_enc());
        telemetry.addData("D05 Left Color Red: ", jewel_color_left.red());
        telemetry.addData("D06 Left Color Blue: ", jewel_color_left.blue());
        telemetry.addData("D07 Right Color Red: ", jewel_color_right.red());
        telemetry.addData("D08 Right Color Blue: ", jewel_color_right.blue());
    }

    /**
     * @param xPower:  The speed to drive forward at. Positive for forward.
     * @param yPower: The speed to drive laterally at. Positive for right.
     * @param turn: The speed to turn at. Positive for right.
     */
    public void holonomic_drive(double xPower, double yPower, double turn) {
        double leftFrontPower = Range.clip(yPower + xPower + turn, -1.0, 1.0);
        double rightFrontPower = Range.clip(yPower - xPower - turn, -1.0, 1.0);
        double leftBackPower = Range.clip(yPower - xPower + turn, -1.0, 1.0);
        double rightBackPower = Range.clip(yPower + xPower - turn, -1.0, 1.0);


        left_drive_front.setPower(leftFrontPower);
        right_drive_front.setPower(rightFrontPower);
        left_drive_back.setPower(leftBackPower);
        right_drive_back.setPower(rightBackPower);
    }

    /**
     * @param power: The speed to drive the lift at. Positive for up.
     */
    public void drive_lift(double power) {
        double lift_speed = power;

        lift_speed = Range.clip(lift_speed, -1, 1);

        if (get_lift_enc() >= ConstantVariables.K_LIFT_MAX) {
            lift_speed = Range.clip(lift_speed, -1, 0);
        } else if (get_lift_enc() <= ConstantVariables.K_LIFT_MIN) {
            lift_speed = Range.clip(lift_speed, 0, 1);
        }

        lift.setPower(lift_speed);
    }

    public void grab_relic (double power) {
        grabber.setPower(Range.clip(power, -1, 1));
    }

    /**
     * @param power: The speed to drive the intake at. Positive for output.
     */
    public void intake(double intaking) {
        if (intaking == 1) {
            lift_servo_right.setPosition(ConstantVariables.K_LIFT_SERVO_RIGHT_OUT);
            lift_servo_left.setPosition(ConstantVariables.K_LIFT_SERVO_LEFT_OUT);
        } else if (intaking == 0) {
            lift_servo_right.setPosition(ConstantVariables.K_LIFT_SERVO_RIGHT_IN);
            lift_servo_left.setPosition(ConstantVariables.K_LIFT_SERVO_LEFT_IN);
        }
    }

    public void set_jewel_servo_left(double pos) {
        jewel_servo_left.setPosition(pos);
    }
    public void set_jewel_servo_right(double pos) {
        jewel_servo_right.setPosition(pos);
    }


    public boolean auto_drive(double power, double inches) {
        double TARGET_ENC = ConstantVariables.K_PPIN_DRIVE * inches * Math.sqrt(2);

        double left_speed = power;
        double right_speed = power;
        double error = get_left_drive_back_enc() - get_right_drive_back_enc();

        error /= ConstantVariables.K_DRIVE_ERROR_P;
        left_speed -= error;
        right_speed += error;

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        left_drive_front.setPower(left_speed);
        right_drive_front.setPower(right_speed);
        left_drive_back.setPower(left_speed);
        right_drive_back.setPower(right_speed);

        if (Math.abs(get_left_drive_back_enc()) >= TARGET_ENC &&
                Math.abs(get_right_drive_back_enc()) >= TARGET_ENC) {
            left_drive_front.setPower(0);
            right_drive_front.setPower(0);
            left_drive_back.setPower(0);
            right_drive_back.setPower(0);
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
        double TARGET_ENC = Math.abs(ConstantVariables.K_PPDEG_DRIVE * degrees);
        telemetry.addData("D99 TURNING TO ENC: ", TARGET_ENC);

        if (Math.abs(get_left_drive_back_enc()) >= TARGET_ENC &&
                Math.abs(get_right_drive_back_enc()) >= TARGET_ENC) {
            left_drive_front.setPower(0);
            right_drive_front.setPower(0);
            left_drive_back.setPower(0);
            right_drive_back.setPower(0);
            return true;
        } else {
            left_drive_front.setPower(power);
            right_drive_front.setPower(-power);
            left_drive_back.setPower(power);
            right_drive_back.setPower(-power);
        }
        return false;
    }

    public void reset_lift_encoder() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int get_lift_enc() {
        if (lift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return lift.getCurrentPosition();
    }

    public void reset_grabber_encoder() {
        grabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int get_grabber_enc() {
        if (grabber.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            grabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return grabber.getCurrentPosition();
    }

    public void reset_drive_encoders() {
        left_drive_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_drive_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_drive_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_drive_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_drive_front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_drive_front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left_drive_back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_drive_back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int get_left_drive_front_enc() {
        if (left_drive_front.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            left_drive_front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return left_drive_front.getCurrentPosition();
    }

    public int get_right_drive_front_enc() {
        if (right_drive_front.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            right_drive_front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return right_drive_front.getCurrentPosition();
    }
    public int get_left_drive_back_enc() {
        if (left_drive_back.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            left_drive_back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return left_drive_back.getCurrentPosition();
    }

    public int get_right_drive_back_enc() {
        if (right_drive_back.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            right_drive_back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return right_drive_back.getCurrentPosition();
    }

    public int get_turn_mult_left(boolean isRed) {
        return (jewel_color_left.red() > jewel_color_left.blue() ? 1 : -1) * (isRed ? 1 : -1);
    }
    public int get_turn_mult(boolean isRed) {
        return (jewel_color_right.red() > jewel_color_right.blue() ? 1 : -1) * (isRed ? 1 : -1);
    }
}