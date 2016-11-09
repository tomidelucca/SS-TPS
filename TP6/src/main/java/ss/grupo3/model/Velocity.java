package ss.grupo3.model;

public class Velocity {

	private double vx;
	private double vy;
	
	public Velocity(double vx, double vy) {
		super();
		this.vx = vx;
		this.vy = vy;
	}
	public double getVx() {
		return vx;
	}
	public void setVx(double vx) {
		this.vx = vx;
	}
	public double getVy() {
		return vy;
	}
	public void setVy(double vy) {
		this.vy = vy;
	}
	
	public Velocity rest(Velocity other) {
		return new Velocity(other.vx - vx, other.vy - vy);
	}
	
	public Velocity sum(Velocity other) {
		return new Velocity(other.vx + vx, other.vy + vy);
	}
	
	public Velocity mult(Velocity other) {
		return new Velocity(other.vx*vx, other.vy*vy); 
	}

	public Velocity mult(Double v) {
		return new Velocity(v*vx, v*vy); 
	}
	
	public double mod() {
		return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
	}
	public Vector getVector() {
		return new Vector(vx, vy);
	}
}
