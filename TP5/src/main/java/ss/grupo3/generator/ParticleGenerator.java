package ss.grupo3.generator;

import java.util.ArrayList;
import java.util.List;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Velocity;

public class ParticleGenerator {

	public static List<Particle> generate(double L, double W, double D, double mass) {
		List<Particle> listParticle = new ArrayList<Particle>();
		double maxRadius = (D/5)/2;
		double minRadius = (D/7)/2;
		double tries = 1E6;
		double counterTries = tries;
		int id = 0;
		Particle particle;
		Vector position;
		Velocity velocity;
		double radius;
		
//		int particulas = 300; //cantidad fija de particulas
//		while(counterTries > 0 && listParticle.size() < particulas) { //cantidad fija de particulas
			
		while(counterTries > 0) { // maxima cantidad de particulas. descomentar y comentar las otras dos.
			position = new Vector(Math.random()*W, Math.random()*L + 1);
			velocity = new Velocity(0, 0);
			radius = Math.random()*(minRadius - maxRadius) + minRadius;
//			radius = maxRadius;
//			radius = 0.1;
			particle = new Particle(id, position, velocity, radius, mass);
			
			if(!outOfBound(particle, L, W) && validParticle(particle, listParticle)) {
				listParticle.add(particle);
				counterTries = tries;
				id++;
			}
			else
				counterTries--;
		}
		
//		position = new Vector(0.4, L + 0.5);
//		velocity = new Velocity(5, 0);
//		radius = maxRadius;
//		particle = new Particle(0, position, velocity, radius, mass);
//		listParticle.add(particle);
//
//		position = new Vector(W - 0.4, L + 0.5);
//		velocity = new Velocity(-5, 0);
//		radius = maxRadius;
//		particle = new Particle(1, position, velocity, radius, mass);
//		listParticle.add(particle);
//		
//		position = new Vector(W - 1.5, L);
//		velocity = new Velocity(-5, 0);
//		radius = maxRadius;
//		particle = new Particle(2, position, velocity, radius, mass);
//		listParticle.add(particle);
		
		System.out.println("[LOG] Number of particles: " + listParticle.size());
		return listParticle;
	}
	
	private static boolean outOfBound(Particle particle, double L, double W) {
		if(particle.getPosition().getX() - particle.getRadius() > 0 && particle.getPosition().getX() + particle.getRadius() < W &&
				particle.getPosition().getY() - particle.getRadius() > 1 && particle.getPosition().getY() + particle.getRadius() < L + 1)
			return false;
		
		return true;
	}

	private static boolean validParticle(Particle particle, List<Particle> listParticle) {
		for(Particle p: listParticle) {
			if(isOverlap(particle, p))
				return false;
		}
		
		return true;
	}

	private static boolean isOverlap(Particle p, Particle other) {
		if(overlap(p, other) > 0)
			return true;
		
		return false;
	}
	
	private static double overlap(Particle p, Particle other) {
		return p.getRadius() + other.getRadius() - p.getPosition().rest(other.getPosition()).mod();
	}
}
