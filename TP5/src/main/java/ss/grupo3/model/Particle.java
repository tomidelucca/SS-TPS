package ss.grupo3.model;

public class Particle {

	private int id;
	private Vector position;
	private Velocity velocity;
	private Vector prevPosition;
	private Velocity prevVelocity;
	private Vector nextPosition;
	private Velocity nextVelocity;
	private double radius;
	private double mass;
	private double[] forceOnMe;
	private boolean fixed;
	
	public Particle(int id, Vector position, Velocity velocity, double radius, double mass) {
		super();
		this.id = id;
		this.position = position;
		this.velocity = velocity;
		this.radius = radius;
		this.mass = mass;
		this.fixed = false;
		this.prevPosition = position;
		this.prevVelocity = velocity;
		
	}

    public Particle(Particle p, double moveFactorX, double moveFactorY) {
        Vector vector = p.getPosition();
        this.position = new Vector(vector.getX()+moveFactorX, vector.getY()+moveFactorY);
        this.radius = p.getRadius();
        this.id = p.getId();
        this.velocity = p.getVelocity();
        this.mass = p.getMass();
        this.fixed = isFixed();
        this.prevPosition = p.getPrevPosition();
        this.prevVelocity = p.getPrevVelocity();
        this.nextPosition = p.getNextPosition();
        this.nextVelocity = p.getNextVelocity();
    }
	
	
	public Particle(int id, Vector position, Velocity velocity, double radius,
			double mass, boolean b) {
		this(id, position, velocity, radius, mass);
		this.fixed = b;
	}

	public void reset(double y) {
		getPosition().setY(y - getRadius());
		setPrevPosition(new Vector(0, 0));
		setNextPosition(new Vector(0, 0));
		setVelocity(new Velocity(0, 0));
		setPrevVelocity(new Velocity(0, 0));
		setNextVelocity(new Velocity(0, 0));
		setForce(new double[]{0,0});
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFixed() {
		return fixed;
	}
	
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
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

	public Vector getPrevPosition() {
		return prevPosition;
	}

	public void setPrevPosition(Vector prevPosition) {
		this.prevPosition = prevPosition;
	}

	public Velocity getPrevVelocity() {
		return prevVelocity;
	}

	public void setPrevVelocity(Velocity prevVelocity) {
		this.prevVelocity = prevVelocity;
	}

	public Vector getNextPosition() {
		return nextPosition;
	}

	public void setNextPosition(Vector nextPosition) {
		this.nextPosition = nextPosition;
	}

	public Velocity getNextVelocity() {
		return nextVelocity;
	}

	public void setNextVelocity(Velocity nextVelocity) {
		this.nextVelocity = nextVelocity;
	}

	public double[] getForce() {
		return forceOnMe;
	}

	public void setForce(double[] forceOnMe) {
		this.forceOnMe = forceOnMe;
	}
}
