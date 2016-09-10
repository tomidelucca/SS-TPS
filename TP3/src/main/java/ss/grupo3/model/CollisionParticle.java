package ss.grupo3.model;

public class CollisionParticle extends Event {
	
	public CollisionParticle(double time, Particle a, Particle b) {
		super(time, a, b);
	}

	@Override
	public void execute() {
		//si ninguno de los das particulas fue actualizada,
		//entonces ejecuto el evento.
				
		double dx = getB().getX() - getA().getX();
		double dy = getB().getY() - getA().getY();
		double dvx = getB().getVx() - getA().getVx();
		double dvy = getB().getVy() - getA().getVy();
		double dvdr = dvx*dx + dvy*dy;				
		double sigma = getA().getRadius() + getB().getRadius();
				
		double j = (2*getA().getMass()*getB().getMass()*dvdr) / (sigma*(getA().getMass() + getB().getMass()));
		double jx = (j*dx) / sigma;
		double jy = (j*dy) / sigma;
		
		getA().setVx(getA().getVx() + jx/getA().getMass());
		getA().setVy(getA().getVy() + jy/getA().getMass());
		
		getB().setVx(getB().getVx() - jx/getB().getMass());
		getB().setVy(getB().getVy() - jy/getB().getMass());		
		
		getA().addCountCollision();
		getB().addCountCollision();
	}

	public Event newEvent() {
		
		return null;
	}
	
	@Override
	public int compareTo(Event o) {
		return (this.getTime() <= o.getTime())? -1 : 1;
	}

	@Override
	public boolean contains(Particle a2, Particle b2) {
		return this.getA().equals(a2) && this.getB().equals(b2);
	}
}
