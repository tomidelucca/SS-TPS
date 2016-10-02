package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;
import ss.grupo3.oscillation.Force;
import ss.grupo3.oscillation.Oscillation;

public class VelocityVerlet implements Algorithm {


	public double positionX(Particle p, double t, double dt) {
		return p.getX() + dt*p.getVx() + (Math.pow(dt,2)/p.getMass())*Force.get2((Oscillation)p, t);
	}

	public double velocityX(Particle p, double t, double dt) {
		return p.getVx() + (dt/(2.0*p.getMass()))*(Force.get2((Oscillation)p, t) + Force.get2((Oscillation)p, t + dt));
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double velocityY(Particle p, double dt) {
		// TODO Auto-generated method stub
		return 0;
	}

}
