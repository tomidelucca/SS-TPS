package ss.grupo3.model;

import ss.grupo3.utils.Colission;

public class ColissionWall extends Event {
	private Colission colission;

	public ColissionWall(double time, Particle a, Colission c) {
		super(time, a, null);
		this.colission = c;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
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
	}

	@Override
	public int compareTo(Event o) {
		return (this.getTime() <= o.getTime())? -1 : 1;
	}

}
