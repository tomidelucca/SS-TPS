package ss.grupo3.tp3.model;

import ss.grupo3.tp3.utils.Collision;

public class CollisionWall extends Event {
	private Collision colission;
	
	public CollisionWall(double time, Particle a, Collision c) {
		super(time, a, null);
		this.colission = c;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		
		//Si la particula no se actualizo,
		//entonces ejecuto el evento.
		
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
		
		getA().addCountCollision();
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
