package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;

import java.util.List;

public class BothSwervesTogether implements RobotController {
    @Override // this tells Java that the `loop` method implements the `loop` method specified in `RobotController`
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {

        double spinnyPower = joysticks.getLeftStick().x * 5.0; // get the velocity of the wheels that turn the robot


        // this is a list of the swerve modules that are on the robot
        List<SwerveWheelInterface> drivetrain = robot.getDrivetrain();


        for(int i = 0; i < drivetrain.size(); i++) { // loop through each swerve module
            SwerveWheelInterface wheel = drivetrain.get(i); // the swerve module we are looking at
            double xSpinningLeg = wheel.getPosition().x;
            double ySpinningLeg = wheel.getPosition().y;

            double xTriangleLeg = joysticks.getRightStick().x; // gets the length of the x side on the "triangle"
            double yTriangleLeg = joysticks.getRightStick().y; // gets the length of the y side on the "triangle"

            double velocity = Math.sqrt((xTriangleLeg * xTriangleLeg) + (yTriangleLeg * yTriangleLeg)) * 5.0; // hypotenuse length
            double spinnier = Math.sqrt((xSpinningLeg * xSpinningLeg) + (ySpinningLeg * ySpinningLeg)); //maths and such

            double spinnyAngle = Math.atan2(xSpinningLeg, -ySpinningLeg);

            // use the x position of the wheel (with the center of the robot being (0, 0)) to see if the wheel is on the right half of the robot or the left half
            if(ySpinningLeg * xSpinningLeg < 0 && ySpinningLeg != 0) {
                wheel.setWheelVelocity(-(spinnyPower * spinnier + velocity));
            } else if(ySpinningLeg * xSpinningLeg > 0 && ySpinningLeg != 0) {
                wheel.setWheelVelocity(spinnyPower * spinnier + velocity);
            } else if (ySpinningLeg == 0) {
                wheel.setWheelAngle(0);
                wheel.setWheelVelocity(spinnyPower * spinnier * (-wheel.getPosition().x / Math.abs(wheel.getPosition().x) + velocity)); //I was too lazy to make another if statement just to change a sign, so you get this.
            }

            double angle = -Math.atan2(xTriangleLeg, yTriangleLeg);

            wheel.setWheelAngle(angle - spinnyAngle);
        }
    }
}
