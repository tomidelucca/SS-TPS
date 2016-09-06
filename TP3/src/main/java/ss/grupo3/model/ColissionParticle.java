package ss.grupo3.model;

public class ColissionParticle extends Event {

	private double dx;
	private double dy;
	private double dvdr;
	private double sigma;
	
	public ColissionParticle(double time, Particle a, Particle b, double dx, double dy, double dvdr, double sigma) {
		super(time, a, b);
		this.dx = dx;
		this.dy = dy;
		this.dvdr = dvdr;
		this.sigma = sigma;
	}

	@Override
	public void update() {
		double j = (2*getA().getMass()*getB().getMass()*dvdr) / (sigma*(getA().getMass() + getB().getMass()));
		double jx = (j*dx) / sigma;
		double jy = (j*dy) / sigma;
		
		getA().setVx(getA().getVx() + jx/getA().getMass());
		getA().setVy(getA().getVy() + jy/getA().getMass());
		
		getB().setVx(getB().getVx() + jx/getB().getMass());
		getB().setVy(getB().getVy() + jy/getB().getMass());
	}


	@Override
	public int compareTo(Event o) {
		return (this.getTime() <= o.getTime())? -1 : 1;
	}

}
