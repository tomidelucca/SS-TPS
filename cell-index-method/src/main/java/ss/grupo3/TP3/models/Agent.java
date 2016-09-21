package ss.grupo3.TP3.models;

public class Agent extends Particle {

    private double angle;
    private double speed;

    public Agent(double x, double y) {
        super(x, y, 0);
        this.speed = 0.03;
        this.angle = randomAngle();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // angulo random para una particula
    private static double randomAngle() {
        return Math.toRadians(Math.random() * 360);
    }

    public String toString() {
        return "0.1" + " " + getPosition().getX() + " " + getPosition().getY() + " " + getAngle() + " 0";
    }
}