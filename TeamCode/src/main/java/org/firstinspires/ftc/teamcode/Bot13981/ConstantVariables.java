package org.firstinspires.ftc.teamcode.Bot13981;

/**
 * Created by andrew on Nov 14, 2017 as part of ftc_app in org.firstinspires.ftc.teamcode.
 * and edited by matthew for 13981
 */

public class ConstantVariables {
    //need to change constants for drive/wheels/turning

    public static final double K_LIFT_SERVO_RIGHT_OUT= 0.6;
    public static final double K_LIFT_SERVO_RIGHT_IN = 0.1;
    public static final double K_LIFT_SERVO_LEFT_OUT = 0.45;
    public static final double K_LIFT_SERVO_LEFT_IN = 0.95;

    public static final int K_GRABBER_MIN = 0;
    public static final int K_GRABBER_MAX = 8400;

    public static final int K_LIFT_MIN = 0;
    public static final int K_LIFT_MAX = 3200;

    public static final double K_JEWEL_SERVO_LEFT_DOWN = 0.8;
    public static final double K_JEWEL_SERVO_LEFT_UP = 0.35;
    public static final double K_JEWEL_SERVO_RIGHT_DOWN = 0.1;
    public static final double K_JEWEL_SERVO_RIGHT_UP = 0.9;

    public static final int K_PPR_DRIVE = 1120;
    public static final double K_DRIVE_WHEEL_DIA = 4;
    public static final double K_DRIVE_DIA = 20;

    public static final double K_DRIVE_WHEEL_CIRCUMFERENCE = K_DRIVE_WHEEL_DIA * Math.PI;
    public static final double K_PPIN_DRIVE = K_PPR_DRIVE / K_DRIVE_WHEEL_CIRCUMFERENCE;

    public static final double K_TURN_CIRCUMFERENCE = K_DRIVE_DIA * Math.PI;
    public static final double K_PPTURN_DRIVE = K_PPIN_DRIVE * K_TURN_CIRCUMFERENCE;
    public static final double K_PPDEG_DRIVE = K_PPTURN_DRIVE / 360;

    public static final double K_DRIVE_ERROR_P = 500; // higher = less sensitive
}
