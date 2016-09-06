package ss.grupo3.model;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import ss.grupo3.utils.Colission;

public class BrownianMotion {
	private Set<Event> eventSet;
	private Particle[] particles;
	private double L;

	public BrownianMotion(Particle[] particles, double L) {
		this.eventSet = new TreeSet<Event>();
		this.particles = particles;
		this.L = L;
	}
	
	public void run(int iteration) {
		int i = 0;
		
		while(i < iteration) {
			i++;
			calculateEvents(particles);
		}
	}
	
	private void calculateEvents(Particle[] particle) {
		Event e;
		
		for(int i = 0; i < particle.length; i++) {
			e = calculateColissionWallEvent(particle[i]);
			if(e != null)
				eventSet.add(e);
		}
				
		for(int i = 0; i < particle.length; i++)
			for(int j = i + 1; j < particle.length; j++) {
				e = calculateColissionParticleEvent(particle[i], particle[j]);
				if(e != null)
					eventSet.add(e);
			}
		
		Iterator<Event>  it = eventSet.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}

	//TODO chequear que los calculos sean correctos
	private Event calculateColissionParticleEvent(Particle a, Particle b) {
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
			return new ColissionParticle(tc, a, b, dx, dy, dvdr, sigma);
		}
		

	}
	
	//TODO chequear que los calculos sean correctos
	private Event calculateColissionWallEvent(Particle p) {		
		double tcx = 0;
		double tcy = 0;

		//PAREDES VERTICALES
		//tc = (xp2 - R - x(0)) / vx ; vx > 0
		//tc = (xp1 + R - x(0)) / vx ; vx < 0
		if(p.getVx() > 0)
			tcx = ( L - p.getRadius() - p.getX()) / p.getVx();
		else if (p.getVx() < 0)
			tcx = ( 0 + p.getRadius() - p.getX()) / p.getVx();
		
		//PAREDES HORIZONTALES
		//tc = (yp2 - R - y(0)) / vy ; vy > 0
		//tc = (yp1 + R - y(0)) / vy ; vy < 0
		if(p.getVy() > 0)
			tcy = ( L - p.getRadius() - p.getY()) / p.getVy();
		else if (p.getVy() < 0)
			tcy = ( 0 + p.getRadius() - p.getY()) / p.getVy();

		if(tcx < 0 || tcy < 0)
			System.out.println("error");
		
		if(tcx < tcy) {
			return new ColissionWall(tcx, p, Colission.HORIZONTAL);			
		} else if(tcx > tcy) {
			return new ColissionWall(tcy, p, Colission.VERTICAL);
		} else
			return null;
	}
		
}
