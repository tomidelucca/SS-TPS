package ss.grupo3.Force;

import java.util.Set;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Velocity;

public class ForceParticles {
	
	public static double[] total(Particle p, Set<Particle> others, double kn, double kt) {
		double forceX = 0;
		double forceY = 0;
		double enx;
		double eny;
		Vector tVector;
		double normalForceValue;
		double tangencialForceValue;
		double overlapValue;
		double relativeVelocityValue;
		
		for(Particle particle: others) {
			if(particle.isVisible()) {
				enx = (particle.getPrevPosition().getX() - p.getPrevPosition().getX()) / distance(p, particle);
				eny = (particle.getPrevPosition().getY() - p.getPrevPosition().getY()) / distance(p, particle);
				
				tVector = new Vector(- eny, enx);
				overlapValue = overlap(p, particle);
				relativeVelocityValue = relativeVelocity(p, particle, tVector);
				
				normalForceValue = - kn * overlapValue;
				tangencialForceValue = - kt * overlapValue * relativeVelocityValue;
				
				forceX += normalForceValue * enx + tangencialForceValue * (-eny);
				forceY += normalForceValue * eny + tangencialForceValue * enx;				
			}
		}
		
		return new double[]{forceX, forceY};
	}
	
	private static double overlap(Particle p, Particle other) {
		double ol = p.getRadius() + other.getRadius() - distance(p, other); 
		return (ol < 0)? 0 : ol;
	}

	private static double relativeVelocity(Particle p, Particle other, Vector tangencialVector) {
		Velocity v = other.getPrevVelocity().rest(p.getPrevVelocity());
		return v.getVx() * tangencialVector.getX() + v.getVy() * tangencialVector.getY();
	}
	
    private static double distance(Particle p1, Particle p2) {
    	return p2.getPrevPosition().rest(p1.getPrevPosition()).mod();
	}
}
