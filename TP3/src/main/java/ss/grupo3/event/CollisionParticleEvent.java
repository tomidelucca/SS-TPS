package ss.grupo3.event;

import ss.grupo3.model.CollisionParticle;
import ss.grupo3.model.Event;
import ss.grupo3.model.Particle;

public class CollisionParticleEvent {

	public static Event check(Particle a, Particle b) {
		double sigma = a.getRadius() + b.getRadius();
		
		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();
		
		double dvx = b.getVx() - a.getVx();
		double dvy = b.getVy() - a.getVy();
		
		double drdr = Math.pow(dx, 2) + Math.pow(dy, 2);
		double dvdv = Math.pow(dvx, 2) + Math.pow(dvy, 2);
		double dvdr = dvx*dx + dvy*dy;
		
		double d = Math.pow(dvdr, 2) - dvdv*(drdr - Math.pow(sigma, 2));
		double tc = 0;
		
		if(dvdr >= 0) {
//			tc = Double.POSITIVE_INFINITY;
			return null;
		} else if(d < 0) {
//			tc = Double.POSITIVE_INFINITY;			
			return null;
		} else {
			tc = - ((dvdr + Math.sqrt(d)) / dvdv);
			//en caso de que tc < 0, se superponen
			if(tc > 0) {
				return new CollisionParticle(tc, a, b, dx, dy, dvdr, sigma);				
			}
			return null;
		}
	}
}
