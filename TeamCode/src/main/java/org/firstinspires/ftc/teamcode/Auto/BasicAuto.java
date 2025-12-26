package org.firstinspires.ftc.teamcode.Auto;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Config;

@Autonomous(name="Auto", group="Autos")
public class BasicAuto extends LinearOpMode {
    DcMotor spinny;
    CRServo left_assist, right_assist;
    LED red, green;
    ElapsedTime finger = new ElapsedTime();
    @Override
    public void runOpMode() {
        float target = 0.0f;
        MechanumAuto Auto = new MechanumAuto();

        Auto.frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        Auto.frontRight = hardwareMap.get(DcMotor.class, "front_right");
        Auto.backLeft = hardwareMap.get(DcMotor.class, "back_left");
        Auto.backRight = hardwareMap.get(DcMotor.class, "back_right");

        Auto.frontLeft.setDirection(REVERSE);
        Auto.backLeft.setDirection(REVERSE);

        Auto.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Auto.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Auto.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Auto.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spinny = hardwareMap.get(DcMotor.class, "spinny");

        left_assist = hardwareMap.get(CRServo.class, "left_assist");
        right_assist = hardwareMap.get(CRServo.class, "right_assist");
        red = hardwareMap.get(LED.class, "red");
        green = hardwareMap.get(LED.class, "green");

        spinny.setDirection(REVERSE);
        right_assist.setDirection(REVERSE);
        Auto.lop = this;
        waitForStart();
        finger.reset();
        spinny.setPower(Config.spinny_speed *.9);
        Auto.movetime(1, Auto.BACKWARD);
        target += 3.0f;
        while (finger.seconds() < target) {
            telemetry.addData("spinny is spinning", finger.seconds());
            telemetry.update();
        }
        // three balls
        // math
        for (int i = 0; i < 4; i++) {
            // TODO: move 0.3f to Config.java
            left_assist.setPower(0.3f);
            right_assist.setPower(0.3f);
            green.off();
            red.on();
            target += Config.secs * 1.3f;
            while (finger.seconds() < target) {
                red.off();
                green.on();
                telemetry.addData("assist time", finger.seconds());
                telemetry.update();
            }
            left_assist.setPower(0.0f);
            right_assist.setPower(0.0f);
            target += 5f; // no idea
            while (finger.seconds() < target) {
                telemetry.addData("patience", finger.seconds());
                telemetry.update();
            }
        }
    }
}
