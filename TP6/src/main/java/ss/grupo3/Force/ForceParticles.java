package ss.grupo3.Force;

import java.util.Set;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Position;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Velocity;

public class ForceParticles {
	
	public static double[] total(Particle p, Set<Particle> others, double kn, double kt, double A, double B, double dt, Vector exitTarget, double utb) {
		double forceX = 0;
		double forceY = 0;
		double enx;
		double eny;
		Vector tVector;
		double socialForceValue;
		Vector drivingForceValue;
		double overlapValue;
		double relativeVelocityValue;
		int g;
		
		for(Particle particle: others) {
			if(particle.isVisible() && !particle.isFixed()) {
				enx = (p.getPrevPosition().getX() - particle.getPrevPosition().getX()) / distance(p, particle);
				eny = (p.getPrevPosition().getY() - particle.getPrevPosition().getY()) / distance(p, particle);
				
				tVector = new Vector(- eny, enx);
				overlapValue = overlap(p, particle);
				g = g(p, particle);
				relativeVelocityValue = relativeVelocity(p, particle, tVector);

				//FUERZA SOCIA
				socialForceValue = A * Math.exp(- overlapValue / B);
				forceX += socialForceValue * enx;
				forceY += socialForceValue * eny;
				
				//FUERZA GRANULAR
				forceX += (- overlapValue * kn * g) * enx + (relativeVelocityValue * overlapValue *kt * g) * (-eny);
				forceY += (- overlapValue * kn * g) * eny + (relativeVelocityValue * overlapValue *kt * g) * enx;				
			}
		}
		
		//FUERZA DE DESEO
		Vector aux;
		if(p.getPosition().getY() < utb)
			aux = p.getDesiredTarget().rest(p.getPosition());
		else
			aux = exitTarget.rest(p.getPosition());
		aux = aux.mult(1/aux.mod());
		drivingForceValue = p.getVelocity().getVector().rest(aux.mult(p.getDesiredVelocity()));
		drivingForceValue = drivingForceValue.mult(p.getMass()/p.getT());
		
		return new double[]{forceX + drivingForceValue.getX(), forceY + drivingForceValue.getY()};
	}
	
	private static double overlap(Particle p, Particle other) {
		return distance(p, other) - (p.getRadius() + other.getRadius()); 
	}
	
	private static int g(Particle p, Particle other) {
		double ol = overlap(p, other); 
		return (ol < 0)? 1 : 0;
	}
	
	private static double relativeVelocity(Particle p, Particle other, Vector tangencialVector) {
		Velocity v = p.getPrevVelocity().rest(other.getPrevVelocity());
//		Velocity v = other.getPrevVelocity().rest(p.getPrevVelocity());

		return v.getVx() * tangencialVector.getX() + v.getVy() * tangencialVector.getY();
	}
	
    private static double distance(Particle p1, Particle p2) {
    	return p2.getPrevPosition().rest(p1.getPrevPosition()).mod();
	}
}
