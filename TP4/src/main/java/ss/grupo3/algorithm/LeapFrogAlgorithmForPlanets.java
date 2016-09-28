package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;

public class LeapFrogAlgorithmForPlanets implements Algorithm {
	
	public double velocityX(Particle p, double sumForce, double t, double dt) {
		return p.getPrev_vx() + (dt/p.getMass())*sumForce;
	}

	public double positionX(Particle p, double t, double dt) {
		return p.getX() + dt*p.getNext_vx();
	}

	public double velocityY(Particle p, double sumForce, double t, double dt) {
		return p.getPrev_vy() + (dt/p.getMass())*sumForce;
	}

	public double positionY(Particle p, double t, double dt) {
		return p.getY() + dt*p.getNext_vy();
	}
	
	@Override
	public double positionX(Particle p, double dt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double positionY(Particle p, double dt) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double velocityX(Particle p, double dt) {
		return (p.getPrev_vx() + p.getNext_vx())/2.0;
	}

	@Override
	public double velocityY(Particle p, double dt) {
		// TODO Auto-generated method stub
		return 0;
	}

}
