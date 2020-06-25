package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;
import java.lang.Math;

import java.util.List;

public class SpinningInPlace implements RobotController {
    @Override // this tells Java that the `loop` method implements the `loop` method specified in `RobotController`
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {

        double spinnyPower = joysticks.getLeftStick().x * 5.0; // get the velocity of the wheels that turn the robot


        // this is a list of the swerve modules that are on the robot
        List<SwerveWheelInterface> drivetrain = robot.getDrivetrain();


        for(int i = 0; i < drivetrain.size(); i++) { // loop through each swerve module
            SwerveWheelInterface wheel = drivetrain.get(i); // the swerve module we are looking at
            double xTriangleLeg = wheel.getPosition().x;
            double yTriangleLeg = wheel.getPosition().y;
            double spinnier = Math.sqrt((xTriangleLeg * xTriangleLeg) + (yTriangleLeg * yTriangleLeg)); //maths and such

            double wheelAngle = Math.atan2(xTriangleLeg, -yTriangleLeg);

            wheel.setWheelAngle(-wheelAngle);

            // use the x position of the wheel (with the center of the robot being (0, 0)) to see if the wheel is on the right half of the robot or the left half
            if(wheel.getPosition().y * wheel.getPosition().x < 0 && wheel.getPosition().y != 0) {
                wheel.setWheelVelocity(-(spinnyPower * spinnier));
            } else if(wheel.getPosition().y * wheel.getPosition().x > 0 && wheel.getPosition().y != 0) {
                wheel.setWheelVelocity(spinnyPower * spinnier);
            } else if (wheel.getPosition().y == 0) {
                wheel.setWheelAngle(0);
                wheel.setWheelVelocity(spinnyPower * spinnier * (-wheel.getPosition().x / Math.abs(wheel.getPosition().x))); //I was too lazy to make another if statement just to change a sign, so you get this.
            }
        }
    }
}
