package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;

import java.util.List;
import java.lang.Math;

public class BetterArcadeDrive implements RobotController {
    @Override // this tells Java that the `loop` method implements the `loop` method specified in `RobotController`
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {

        double forwardAndBackwardPower = joysticks.getLeftStick().y * 5.0; // get the velocity of the robot going forward/backward
        double spinnyPower = joysticks.getRightStick().x * 5.0; // get the velocity of the wheels that turn the robot

        // this is a list of the swerve modules that are on the robot
        List<SwerveWheelInterface> drivetrain = robot.getDrivetrain();


        for(int i = 0; i < drivetrain.size(); i++) { // loop through each swerve module
            SwerveWheelInterface wheel = drivetrain.get(i); // the swerve module we are looking at
            double spinnierX = wheel.getPosition().x;
            double spinnierY = wheel.getPosition().y;
            double spinnier = Math.sqrt((spinnierX * spinnierX) + (spinnierY * spinnierY)); //maths and such
            wheel.setWheelAngle(0); // make sure the wheels are always facing forward (none of this funny swerve business)

            // use the x position of the wheel (with the center of the robot being (0, 0)) to see if the wheel is on the right half of the robot or the left half

            if(wheel.getPosition().x > 0) {
                wheel.setWheelVelocity(-(spinnyPower * spinnier - forwardAndBackwardPower));
            } else if(wheel.getPosition().x < 0) {
                wheel.setWheelVelocity(spinnyPower * spinnier + forwardAndBackwardPower);
            }
        }
    }
}
