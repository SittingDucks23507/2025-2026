package org.firstinspires.ftc.teamcode.Auto;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Config;

@Autonomous(name="triple", group="Autos")
public class TripleAuto extends LinearOpMode {
    DcMotor midIntake, frontIntake;
    DcMotor shooter;
    MechanumAuto Auto;
    @Override
    public void runOpMode() {
        setup();
        waitForStart();

        shooter.setPower(Config.smax * 0.8125f);
        Auto.movetime(3f, Auto.FORWARD);

        for (int i = 0; i < 3; i++) {
            midIntake.setPower(Config.mimax);
            frontIntake.setPower(Config.fimax);
            safeWait(1.5f);
            midIntake.setPower(0.0f);
            frontIntake.setPower(0.0f);
        }
        shooter.setPower(0.0f);
        safeWait(1.5f);
    }
    private void safeWait(float time) {
        ElapsedTime t = new ElapsedTime();
        t.reset();
        while (opModeIsActive() && t.seconds() < time) {
            telemetry.addData("waiting", t.seconds());
            telemetry.update();
        }
    }
    private void setup() {
        Auto = new MechanumAuto();
        Auto.lop = this;
        Auto.frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        Auto.frontRight = hardwareMap.get(DcMotor.class, "front_right");
        Auto.backLeft = hardwareMap.get(DcMotor.class, "back_left");
        Auto.backRight = hardwareMap.get(DcMotor.class, "back_right");

        Auto.frontRight.setDirection(REVERSE);
        Auto.backRight.setDirection(REVERSE);

        Auto.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Auto.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Auto.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Auto.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontIntake = hardwareMap.get(DcMotor.class, "front_intake");
        midIntake = hardwareMap.get(DcMotor.class, "mid_intake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        frontIntake.setDirection(DcMotor.Direction.REVERSE);
        shooter.setDirection(DcMotor.Direction.REVERSE);
    }
}
