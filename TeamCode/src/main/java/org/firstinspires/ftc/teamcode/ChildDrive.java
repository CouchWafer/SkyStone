package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Child drive", group="Iterative Opmode")

public class ChildDrive extends OpMode{

    float drivePower = 1;
    float K = 1;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor arm = null;
    private DcMotor intake = null;

    @Override
    public void init()
    {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        arm = hardwareMap.get(DcMotor.class, "arm");
        intake = hardwareMap.get(DcMotor.class, "intake");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        //Display Initialized
        telemetry.addData("Status", "Initialized");
    }
    @Override
    public void loop()
    {
        if(gamepad1.left_trigger > .1)
            arm.setPower(1);
        else if(gamepad1.right_trigger > .1)
            arm.setPower(-1);
        else
            arm.setPower(0);

        if(gamepad1.left_bumper)
            intake.setPower(.2);
        else if(gamepad1.right_bumper)
            intake.setPower(-.2);
        else
            intake.setPower(0);


        Drive();
    }
    private void Drive()
    {
        double forward = -gamepad1.left_stick_y; // push joystick1 forward to go forward
        double right = gamepad1.left_stick_x; // push joystick1 to the right to strafe right
        double clockwise = gamepad1.right_stick_x; // push joystick2 to the right to rotate clockwise


        double front_left = forward  + right + K*clockwise;
        double front_right = forward - right - K*clockwise;
        double rear_left = forward - right + K*clockwise;
        double rear_right = forward + right - K*clockwise;

        double max = Math.abs(front_left);
        if (Math.abs(front_right)>max) max = Math.abs(front_right);
        if (Math.abs(rear_left)>max) max = Math.abs(rear_left);
        if (Math.abs(rear_right)>max) max = Math.abs(rear_right);
        if (max>1)
        {front_left/=max; front_right/=max; rear_left/=max; rear_right/=max;}
        frontLeft.setPower(front_left * drivePower);
        frontRight.setPower(front_right * drivePower);
        backRight.setPower( rear_right* drivePower);
        backLeft.setPower(rear_left * drivePower);

    }
}
