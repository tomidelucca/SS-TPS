package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;
import ss.grupo3.oscillation.Force;
import ss.grupo3.oscillation.Oscillation;

public class LeapFrogAlgorithm implements Algorithm {


	public double velocityXInit(Particle p, double t, double dt) {
		return p.getPrev_vx() + (dt/p.getMass())*Force.get3((Oscillation)p, t);
	}
	
	public double velocityX(Particle p, double t, double dt) {
		return p.getPrev_vx() + (dt/p.getMass())*Force.get4((Oscillation)p, t);
	}

	public double positionX(Particle p, double t, double dt) {
		return p.getX() + dt*p.getNext_vx();
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
