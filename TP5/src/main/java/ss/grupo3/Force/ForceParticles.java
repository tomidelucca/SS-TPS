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
		
		for(Particle particle: others) {
			enx = (particle.getPosition().getX() - p.getPosition().getX()) / distance(p, particle);
			eny = (particle.getPosition().getY() - p.getPosition().getY()) / distance(p, particle);
			
			tVector = new Vector(- eny, enx);
			
			forceX += normalForce(p, particle, kn)*enx + tangencialForce(p, particle, kt, tVector) * (-eny);
			forceY += normalForce(p, particle, kn)*eny + tangencialForce(p, particle, kt, tVector) * enx;
		}
		
		return new double[]{forceX, forceY};
	}
	
	public static double normalForce(Particle p, Particle other, double kn) {
		return - kn * overlap(p, other);
	}
	
	public static double tangencialForce(Particle p, Particle other, double kt, Vector tVector) {
		return - kt * overlap(p, other) * relativeVelocity(p, other, tVector);
	}

	//TODO corregir el overlap...
	private static double overlap(Particle p, Particle other) {
		double ol = p.getRadius() + other.getRadius() - other.getPosition().rest(p.getPosition()).mod(); 
		
		return (ol < 0)? 0: ol;
	}

	private static double relativeVelocity(Particle p, Particle other, Vector tangencialVector) {
		Velocity v = p.getVelocity().rest(other.getVelocity());
		return v.getVx() * tangencialVector.getX() + v.getVy() * tangencialVector.getY();
	}
	
    private static double distance(Particle p1, Particle p2) {
    	return p2.getPosition().rest(p1.getPosition()).mod();
	}
}
