package ss.grupo3.TP3;

import ss.grupo3.models.Point;

public class MovingParticle {

	private Point point;
	private Velocity velocity;
	private double radius;
	
	public MovingParticle(Point p, Velocity v, double r) {
		this.point = p;
		this.velocity = v;
		this.radius = r;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
}
