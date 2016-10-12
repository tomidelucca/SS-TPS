package ss.grupo3.model;

public class Particle {

	private int id;
	private Vector position;
	private Velocity velocity;
	private double radius;
	private double mass;
	
	public Particle(int id, Vector position, Velocity velocity, double radius) {
		super();
		this.id = id;
		this.position = position;
		this.velocity = velocity;
		this.radius = radius;
		this.mass = 0.01;
	}

    public Particle(Particle p, double moveFactorX, double moveFactorY) {
        Vector vector = p.getPosition();
        this.position = new Vector(vector.getX()+moveFactorX, vector.getY()+moveFactorY);
        this.radius = p.getRadius();
        this.id = p.getId();
        this.velocity = p.getVelocity();
        this.mass = p.getMass();
    }
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
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

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public String print() {
		return position.getX() + " " + position.getY() + " " + velocity.getVx() + " " + velocity.getVy() + " " + radius;
	}
	
}
