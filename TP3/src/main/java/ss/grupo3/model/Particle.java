package ss.grupo3.model;

public class Particle implements Cloneable {

	private double x;
	private double y;
	private double vx;
	private double vy;
	private double radius;
	private double mass;	
	private int countCollision;
	
	public Particle(double x, double y, double vx, double vy, double radius, double mass) {
		this.x = x;
		this.y = y; 
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
		this.mass = mass;
		this.countCollision = 0;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		System.out.println("valor X:" + x);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		System.out.println("valor Y:" + y);
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
		if(Math.abs(vx) > 10)
			System.out.println("muy grande");
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
		if(Math.abs(vy) > 10)
			System.out.println("muy grande");
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
		return getRadius() + " " + getX() + " " + getY();
	}

	public String printAuxPosition(double t) {
		return getRadius() + " " + (this.x + this.vx*t) + " " + (this.y + this.vy*t);
	}
	
	@Override
	public String toString() {
		return "Particle[x:"+ getX() + " y:"+ getY()+"]";
	}
	
    protected Particle clone() throws CloneNotSupportedException
    {
        return (Particle) super.clone();
    }

	public void updatePosition(double t) {
		this.x += this.vx*t;
		this.y += this.vy*t;
	}

	public void addCountCollision() {
		this.countCollision++;
	}
	
	public int getCountCollision() {
		return this.countCollision;
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
}
