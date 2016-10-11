package ss.grupo3.Force;

import java.util.List;

import ss.grupo3.model.Particle;

public class ForceParticles {

	private static final double KN = 100000;
	private static final double KT = 2*KN;
	private static final double G = 9.8; //m/s^2
	
	public static double[] total(Particle p, List<Particle> others) {
		double forceX = 0;
		double forceY = 0;
		double enx;
		double eny;
		
		for(Particle particle: others) {
			enx = (particle.getPosition().getX() - p.getPosition().getX()) / Math.abs(particle.getRadius() - p.getRadius());
			eny = (particle.getPosition().getY() - p.getPosition().getY()) / Math.abs(particle.getRadius() - p.getRadius());
			
			forceX += normalForce(p, particle)*enx + tangencialForce(p, particle) * (-eny);
			forceY += p.getMass() * (-G) + normalForce(p, particle)*eny + tangencialForce(p, particle) * enx;
		}
		
		return new double[]{forceX, forceY};
	}
	
	public static double normalForce(Particle p, Particle other) {
		return - KN * overlap(p, other);
	}
	
	public static double tangencialForce(Particle p, Particle other) {
		return - KT * overlap(p, other) * relativeVelocity(p, other);
	}
	
	private static double overlap(Particle p, Particle other) {
		return p.getRadius() + other.getRadius() - other.getPosition().rest(p.getPosition()).mod();
	}
	
	private static double relativeVelocity(Particle p, Particle other) {
		return other.getVelocity().rest(p.getVelocity()).mod();
	}
}
