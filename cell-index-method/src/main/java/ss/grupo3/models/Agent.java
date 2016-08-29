package ss.grupo3.models;

public class Agent extends Particle {

    private double angle;
    private double speed;

    public Agent(double x, double y) {
        super(x, y, 0);
        this.speed = 0.03;
        this.angle = randomAngle();
        // TODO Auto-generated constructor stub
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
    public static double randomAngle() {
        return Math.toRadians(Math.random() * 360);
    }
}