package ss.grupo3.model;

public class Particle{

	private double prev_x;
	private double prev_y;
	private double prev_vx;
	private double prev_vy;
	private double prev_ax;
	private double prev_ay;
	
	private double x;
	private double y;
	private double vx;
	private double vy;
	private double ax;
	private double ay;
	
	private double next_x;
	private double next_y;
	private double next_vx;
	private double next_vy;
	private double next_ax;
	private double next_ay;
	
	private double radius;
	private double mass;	
	
	public Particle(double x, double y, double vx, double vy, double radius, double mass) {
		this.x = x;
		this.y = y; 
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
		this.mass = mass;
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

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public String print() {
		return getX() + " " + getY() + " " + getVx() + " " + getVy() + " " + 1E10;
	}

	public String printAuxPosition(double t) {
		return getRadius() + " " + (this.x + this.vx*t) + " " + (this.y + this.vy*t) + " " + getVx() + " " + getVy();
	}

	public double kineticEnergy() {
		return 0.5 * mass * (vx*vx + vy*vy);
	}

	@Override
	public String toString() {
		return "Particle[x:"+ getX() + " y:"+ getY()+"]";
	}

	public void updatePosition(double t) {
		this.x += this.vx*t;
		this.y += this.vy*t;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(vx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vy);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Particle other = (Particle) obj;
		if (Double.doubleToLongBits(vx) != Double.doubleToLongBits(other.vx))
			return false;
		if (Double.doubleToLongBits(vy) != Double.doubleToLongBits(other.vy))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	public double getSpeed() {
		return Math.sqrt(Math.pow(getVx(),2) + Math.pow(getVy(),2));
	}
	
	// ver si realmente necesito estos metodos.

	public double getPrev_x() {
		return prev_x;
	}

	public void setPrev_x(double prev_x) {
		this.prev_x = prev_x;
	}

	public double getPrev_y() {
		return prev_y;
	}

	public void setPrev_y(double prev_y) {
		this.prev_y = prev_y;
	}

	public double getPrev_vx() {
		return prev_vx;
	}

	public void setPrev_vx(double prev_vx) {
		this.prev_vx = prev_vx;
	}

	public double getPrev_vy() {
		return prev_vy;
	}

	public void setPrev_vy(double prev_vy) {
		this.prev_vy = prev_vy;
	}

	public double getNext_x() {
		return next_x;
	}

	public void setNext_x(double next_x) {
		this.next_x = next_x;
	}

	public double getNext_y() {
		return next_y;
	}

	public void setNext_y(double next_y) {
		this.next_y = next_y;
	}

	public double getNext_vx() {
		return next_vx;
	}

	public void setNext_vx(double next_vx) {
		this.next_vx = next_vx;
	}

	public double getNext_vy() {
		return next_vy;
	}

	public void setNext_vy(double next_vy) {
		this.next_vy = next_vy;
	}

	public double getPrev_ax() {
		return prev_ax;
	}

	public void setPrev_ax(double prev_ax) {
		this.prev_ax = prev_ax;
	}

	public double getPrev_ay() {
		return prev_ay;
	}

	public void setPrev_ay(double prev_ay) {
		this.prev_ay = prev_ay;
	}

	public double getNext_ax() {
		return next_ax;
	}

	public void setNext_ax(double next_ax) {
		this.next_ax = next_ax;
	}

	public double getNext_ay() {
		return next_ay;
	}

	public void setNext_ay(double next_ay) {
		this.next_ay = next_ay;
	}

	public double getAx() {
		return ax;
	}

	public void setAx(double ax) {
		this.ax = ax;
	}

	public double getAy() {
		return ay;
	}

	public void setAy(double ay) {
		this.ay = ay;
	}
}
