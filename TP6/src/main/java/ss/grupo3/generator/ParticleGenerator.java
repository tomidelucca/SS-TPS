package ss.grupo3.generator;

import java.util.ArrayList;
import java.util.List;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Velocity;

public class ParticleGenerator {
	private static double MAXRADIUS = 0.58/2;
	private static double MINRADIUS = 0.50/2;
	private static double MAXMASS = 80;
	private static double MINMASS = 50;
	private static double MAXDESIREDVELOCITY = - 3.0;
	private static double MINDESIREDVELOCITY = - 0.8;
	private static double TRIES = 1E6;
	
	public static List<Particle> generate(double L, double W, double D, double T, int numberOfParticle, double utb) {
		List<Particle> listParticle = new ArrayList<Particle>();
		double counterTries = TRIES;
		int id = 0;
		Particle particle;
		Vector position;
		Velocity velocity;
		double desiredVelocity;
		double radius;
		Vector desiredTarget;
		double mass;
		
		while(counterTries > 0 && listParticle.size() < numberOfParticle) { //cantidad fija de particulas			
			position = new Vector(Math.random()*W, Math.random()*(L - 5) + utb + 5);
			velocity = new Velocity(0, 0);
			radius = Math.random()*(MAXRADIUS - MINRADIUS) + MINRADIUS;
//			mass = Math.random()*(MAXMASS - MINMASS) + MINMASS;
			mass = MAXMASS;
//			desiredVelocity = Math.random() * (MAXDESIREDVELOCITY - MINDESIREDVELOCITY) + MINDESIREDVELOCITY;
			desiredVelocity = MAXDESIREDVELOCITY;
			desiredTarget = new Vector( radius + Math.random() * (W - radius), 0);

			particle = new Particle(id, position, velocity, radius, mass, desiredTarget, desiredVelocity, T);
			
			if(!outOfBound(particle, L, W, utb) && validParticle(particle, listParticle)) {
				listParticle.add(particle);
				counterTries = TRIES;
				id++;
			}
			else
				counterTries--;
		}

		System.out.println("[LOG] Number of particles: " + listParticle.size());
		return listParticle;
	}
	
	private static boolean outOfBound(Particle particle, double L, double W, double utb) {
		if(particle.getPosition().getX() - particle.getRadius() > 0 && particle.getPosition().getX() + particle.getRadius() < W &&
				particle.getPosition().getY() - particle.getRadius() > utb && particle.getPosition().getY() + particle.getRadius() < L + utb)
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
