package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;

public class LeapFrog {
	
	public double velocityX(Particle p, double sumForce, double t, double dt) {
		return p.getPrev_vx() + (dt/p.getMass())*sumForce;
	}

	public double positionX(Particle p, double t, double dt, double osx) {
		return p.getX() + dt*(p.getNext_vx() + osx);
	}

	public double velocityY(Particle p, double sumForce, double t, double dt) {
		return p.getPrev_vy() + (dt/p.getMass())*sumForce;
	}

	public double positionY(Particle p, double t, double dt, double osy) {
		return p.getY() + dt*(p.getNext_vy() + osy);
	}

}
