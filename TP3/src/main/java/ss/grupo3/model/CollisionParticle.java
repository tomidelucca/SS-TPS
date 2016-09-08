package ss.grupo3.model;

public class CollisionParticle extends Event {

	private double dx;
	private double dy;
	private double dvdr;
	private double sigma;
	
	public CollisionParticle(double time, Particle a, Particle b, double dx, double dy, double dvdr, double sigma) {
		super(time, a, b);
		this.dx = dx;
		this.dy = dy;
		this.dvdr = dvdr;
		this.sigma = sigma;
	}

	@Override
	public void update() {
		//si ninguno de los das particulas fue actualizada,
		//entonces ejecuto el evento.
				
		if(!getA().isUpdated() && !getB().isUpdated()){
			double j = (2*getA().getMass()*getB().getMass()*dvdr) / (sigma*(getA().getMass() + getB().getMass()));
			double jx = (j*dx) / sigma;
			double jy = (j*dy) / sigma;
			
			getA().setVx(getA().getVx() + jx/getA().getMass());
			getA().setVy(getA().getVy() + jy/getA().getMass());
			
			getB().setVx(getB().getVx() - jx/getB().getMass());
			getB().setVy(getB().getVy() - jy/getB().getMass());		
			
			getA().setUpdated(true);
			getB().setUpdated(true);
		}
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
