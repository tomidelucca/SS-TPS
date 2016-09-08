package ss.grupo3.model;

import ss.grupo3.utils.Collision;

public class CollisionWall extends Event {
	private Collision colission;

	public CollisionWall(double time, Particle a, Collision c) {
		super(time, a, null);
		this.colission = c;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		
		//Si la particula no se actualizo,
		//entonces ejecuto el evento.
		if(!getA().isUpdated()) {
			switch (colission) {
			case VERTICAL:
				getA().setVy(- getA().getVy());
				break;
			case HORIZONTAL:
				getA().setVx(- getA().getVx());
				break;
			default:
				//do nothing
				break;
			}			
			
			getA().setUpdated(true);
		}

	}

	@Override
	public int compareTo(Event o) {
		return (this.getTime() <= o.getTime())? 1 : -1;
	}

	@Override
	public boolean contains(Particle a2, Particle b2) {
		return this.getA().equals(a2);
	}

}
