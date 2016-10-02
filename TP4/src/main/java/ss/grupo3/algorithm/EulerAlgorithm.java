package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;
import ss.grupo3.oscillation.Force;
import ss.grupo3.oscillation.Oscillation;

public class EulerAlgorithm implements Algorithm{

	public double positionX(Particle particle, double dt) {
		double prev_x = particle.getX() + dt*particle.getVx() + (Math.pow(dt, 2)/(2.0*particle.getMass()))*Force.get((Oscillation)particle, dt);
		
		particle.setPrev_x(prev_x);
		return prev_x;
	}
	
	public double positionY(Particle particle, double dt) {
		double prev_y = particle.getX() + dt*particle.getVx() + (Math.pow(dt, 2)/(2.0*particle.getMass()))*Force.get((Oscillation)particle, dt);		
		
		particle.setPrev_y(prev_y);
		return prev_y;
	}
	
	public double velocityX(Particle particle, double dt) {
		double prev_vx = particle.getVx() + (dt/particle.getMass())*Force.get((Oscillation)particle, dt);		
		
		particle.setPrev_vx(prev_vx);
		return prev_vx;
	}
	
	public double velocityX(Particle particle, double t, double dt) {
		double prev_vx = particle.getVx() + (dt/particle.getMass())*Force.get((Oscillation)particle, t);		
		
		particle.setPrev_vx(prev_vx);
		return prev_vx;
	}
	
	public double velocityY(Particle particle, double dt) {
		double prev_vy = particle.getVy() + (dt/particle.getMass())*particle.kineticEnergy();		
		
		particle.setPrev_vy(prev_vy);
		return prev_vy;
	}
}
