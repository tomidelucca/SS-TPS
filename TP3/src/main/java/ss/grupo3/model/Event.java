package ss.grupo3.model;


public abstract class Event implements Comparable<Event> {

	private double time;
	private Particle a;
	private Particle b;
	private int countA;
	private int countB;
	
	public Event(double time, Particle a, Particle b) {
		this.time = time;
		this.a = a;
		this.b = b;
		this.countA = a.getCountCollision();
		if(b != null)
			this.countB = b.getCountCollision();
		
//		System.out.println("event time: " + this.time);
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public Particle getA() {
		return a;
	}

	public void setA(Particle a) {
		this.a = a;
	}

	public Particle getB() {
		return b;
	}

	public void setB(Particle b) {
		this.b = b;
	}
	
	@Override
	public String toString() {
		return "Event[ time:" + getTime() + " A:"+ getA() + " B:"+ getB() + "]";
	}
	
	public abstract void execute();

	public abstract boolean contains(Particle a2, Particle b2);
	
    public boolean isValid() {
        if (a != null && a.getCountCollision() != countA) 
        	return false;
        if (b != null && b.getCountCollision() != countB) 
        	return false;
        return true;
    }
}
