package ss.grupo3.model;


public abstract class Event implements Comparable<Event> {

	private double time;
	private Particle a;
	private Particle b;
	
	public Event(double time, Particle a, Particle b) {
		this.time = time;
		this.a = a;
		this.b = b;
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
	
	public abstract void update();
}
