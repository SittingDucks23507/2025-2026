/* Copyright (c) 2025 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Config;

/*
 * This OpMode illustrates how to program your robot to drive field relative.  This means
 * that the robot drives the direction you push the joystick regardless of the current orientation
 * of the robot.
 *
 * This OpMode assumes that you have four mecanum wheels each on its own motor named:
 *   front_left_motor, front_right_motor, back_left_motor, back_right_motor
 *
 *   and that the left motors are flipped such that when they turn clockwise the wheel moves backwards
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 *
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "first", group = "Robot")
public class TeleOp extends OpMode {
    // This declares the four motors needed
    DcMotor frontLeft, frontRight, backLeft, backRight;
    DcMotor shooter;
    DcMotor midIntake, frontIntake;
    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");

        frontIntake = hardwareMap.get(DcMotor.class, "front_intake");
        midIntake = hardwareMap.get(DcMotor.class, "mid_intake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");

        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        frontIntake.setDirection(DcMotor.Direction.REVERSE);
        shooter.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    @Override
    public void loop() {
        double lsY, lsX, rsX;
        float speed = 1f;
        float frontIntakePower = 0.0f;
        float midIntakePower = 0.0f;
        float shooterPower = 0.0f;

        if (gamepad1.right_trigger > 0.1) {
            speed = 0.25f;
        }
        if (gamepad1.left_trigger > 0.1) {
            shooterPower = Config.smax;
        }

        if (gamepad1.cross) {
            frontIntakePower = Config.fimax;
        }
        if (gamepad1.square) {
            midIntakePower = Config.mimax;
        }
        if (gamepad1.circle) {
            midIntakePower = -Config.mimax;
        }

        lsY = -gamepad1.left_stick_y; // W_UP is now positive
        lsX = -gamepad1.left_stick_x;
        rsX = -gamepad1.right_stick_x; // un-backwards it
        frontLeft.setPower((lsY + lsX + rsX)  * speed);
        frontRight.setPower((lsY - lsX - rsX) * speed);
        backLeft.setPower((lsY - lsX + rsX)   * speed);
        backRight.setPower((lsY + lsX - rsX)  * speed);

        shooter.setPower(shooterPower);
        frontIntake.setPower(frontIntakePower);
        midIntake.setPower(midIntakePower);

        /* telemetry.addData("shooter", shooterPower);
        telemetry.addData("frontIntakePower", frontIntakePower);
        telemetry.addData("midIntakePower", midIntakePower);
        telemetry.update(); */
    }
}
