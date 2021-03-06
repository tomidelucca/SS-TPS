package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;


public class BeemanAlgorithm implements Algorithm {

	public double positionX(Particle p, double dt) {
		double next_x = p.getX() + p.getVx()*dt + (2.0/3.0)*p.getAx()*Math.pow(dt,2) - (1.0/6.0)*p.getPrev_ax()*Math.pow(dt,2);
		p.setNext_x(next_x);
		
		return next_x;
	}

	public double positionY(Particle p, double dt) {
		double next_y = p.getY() + p.getVy()*dt + (2.0/3.0)*p.getAy()*Math.pow(dt,2) - (1.0/6.0)*p.getPrev_ay()*Math.pow(dt,2);
		p.setNext_y(next_y);
		
		return next_y;
	}

	public double velocityX(Particle p, double dt) {
		double next_vx = p.getVx() + (1.0/3.0)*p.getNext_ax()*dt + (5.0/6.0)*p.getAx()*dt - (1.0/6.0)*p.getPrev_ax()*dt;
		p.setNext_vx(next_vx);
		
		return next_vx;
	}

	public double velocityY(Particle p, double dt) {
		double next_vy = p.getVy() + (1.0/3.0)*p.getNext_ay()*dt + (5.0/6.0)*p.getAy()*dt - (1.0/6.0)*p.getPrev_ay()*dt;
		p.setNext_vy(next_vy);
		
		return next_vy;
	}



}
