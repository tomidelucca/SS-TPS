package ss.grupo3.model;

public class Vector {

	private double x;
	private double y;
	
	public Vector(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Vector rest(Vector other) {
		return new Vector(other.x - x, other.y - y);
	}
	
	public Vector sum(Vector other) {
		return new Vector(other.x + x, other.y + y);
	}
	
	public double mod() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public Vector mult(Double v) {
		return new Vector(x * v, y * v);
	}

	public Vector mult(Vector vector) {
		return new Vector(vector.x*x, vector.y*y);
	}
}
