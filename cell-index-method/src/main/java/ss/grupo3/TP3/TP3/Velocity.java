package ss.grupo3.TP3.TP3;

public class Velocity {

	private double vx;
	private double vy;
	
	public Velocity(double vx, double vy) {
		this.setVx(vx);
		this.setVy(vy);
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
}
