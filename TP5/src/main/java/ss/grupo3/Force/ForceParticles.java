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
		
		for(Particle particle: others) {
			enx = (particle.getPosition().getX() - p.getPosition().getX()) / distance(p, particle);
			eny = (particle.getPosition().getY() - p.getPosition().getY()) / distance(p, particle);
			
			tVector = new Vector(- eny, enx);
			normalForceValue = normalForce(p, particle, kn);
			tangencialForceValue = tangencialForce(p, particle, kt, tVector);
			
			forceX += normalForceValue * enx + tangencialForceValue * (-eny);
			forceY += normalForceValue * eny + tangencialForceValue * enx;
		}
		
		return new double[]{forceX, forceY};
	}
	
	public static double normalForce(Particle p, Particle other, double kn) {
		return - kn * overlap(p, other);
	}
	
	public static double tangencialForce(Particle p, Particle other, double kt, Vector tVector) {
		return - kt * overlap(p, other) * relativeVelocity(p, other, tVector);
	}

	private static double overlap(Particle p, Particle other) {
		double ol = p.getRadius() + other.getRadius() - distance(p, other); 
		
		if(ol < 0)
			return 0;
		return ol;
	}

	private static double relativeVelocity(Particle p, Particle other, Vector tangencialVector) {
		Velocity v = other.getVelocity().rest(p.getVelocity());
		return v.getVx() * tangencialVector.getX() + v.getVy() * tangencialVector.getY();
	}
	
    private static double distance(Particle p1, Particle p2) {
    	return p2.getPosition().rest(p1.getPosition()).mod();
	}
}
