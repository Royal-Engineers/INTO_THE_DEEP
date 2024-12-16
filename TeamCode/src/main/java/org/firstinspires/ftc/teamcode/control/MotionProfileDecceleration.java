package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.util.ElapsedTime;

public class MotionProfileDecceleration {
    private ElapsedTime timer = new ElapsedTime();

    private double distance;
    private double max_velocity, max_acceleration, elapsed_time;
    private double decceleraion_time, cruise_time, entire_time;
    private double decceleration_distance, cruise_distance;
    private double velocity, acceleration, position;

    public void start(double max_velocity, double max_acceleration, double distance) {
        this.max_velocity = max_velocity;
        this.max_acceleration = max_acceleration;
        this.distance = distance;

        decceleraion_time = this.max_velocity / max_acceleration;

        if (distance < this.max_velocity * decceleraion_time - max_acceleration * Math.pow(decceleraion_time, 2) / 2) {
            this.max_velocity = Math.sqrt(2 * max_acceleration * distance);
            decceleraion_time = this.max_velocity / max_acceleration;
            decceleration_distance = distance;
        }

        cruise_distance = distance - decceleration_distance;
        cruise_time = cruise_distance / this.max_velocity;

        entire_time = cruise_time + decceleraion_time;
        timer.reset();
    }
    public void update() {
        elapsed_time = timer.seconds();

        if (elapsed_time < cruise_time) {
            position = max_velocity * elapsed_time;
            velocity = max_velocity;
            acceleration = 0;
        }

        else if (elapsed_time < entire_time) {
            position = cruise_distance + max_velocity * (elapsed_time - cruise_time) - 0.5 * max_acceleration * Math.pow (elapsed_time - cruise_time, 2);
            velocity = max_velocity - max_acceleration * (elapsed_time - cruise_time);
            acceleration = - max_acceleration;
        }

        else {
            position = distance;
            velocity = 0;
            acceleration = 0;
        }
    }

    public double getPosition() {return position;}
    public double getVelocity() {return velocity;}
    public double getAcceleration() {return acceleration;}
    public double getMaxVelocity() {return max_velocity;}
}
