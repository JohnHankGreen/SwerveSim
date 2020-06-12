package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;
import java.lang.Math;

import java.util.List;

public class TranslationalSwerve implements RobotController {
    @Override // this tells Java that the `loop` method implements the `loop` method specified in `RobotController`
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {

        double yTriangleLeg = joysticks.getRightStick().y; // gets the length of the y side on the "triangle"
        double xTriangleLeg = joysticks.getRightStick().x; // gets the length of the x side on the "triangle"
        double velocity = Math.sqrt((xTriangleLeg * xTriangleLeg) + (yTriangleLeg * yTriangleLeg)); // hypotenuse length


        // this is a list of the swerve modules that are on the robot
        List<SwerveWheelInterface> drivetrain = robot.getDrivetrain();


        for(int i = 0; i < drivetrain.size(); i++) { // loop through each swerve module
            SwerveWheelInterface wheel = drivetrain.get(i); // the swerve module we are looking at
            double angle;
            if(yTriangleLeg < 0 && xTriangleLeg == 0) {
                angle = 0;
                velocity = -(Math.sqrt((xTriangleLeg * xTriangleLeg) + (yTriangleLeg * yTriangleLeg)));
            } else if (xTriangleLeg < 0) {
                angle = Math.acos(yTriangleLeg / velocity);
            } else if(xTriangleLeg > 0) {
                angle = -(Math.acos(yTriangleLeg / velocity)); // what angle the wheel needs set to. doodle it.
            } else {
                angle = 0;
            }
            wheel.setWheelAngle(angle); // angle (robot doesn't move, just wheels)

            wheel.setWheelVelocity(velocity * 5); // robot motion (never turns robot body)

        }
    }
}










/*robot go vroom weeeee
At this point, it's 10 pm without any sign of progress*/