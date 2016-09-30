package ss.grupo3.planets;

import ss.grupo3.model.Particle;

public class PotentialEnergy {

	public static double get(Particle p1, Particle p2, double gravity) {
		double rij = Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
		
		return - gravity*(p1.getMass()*p2.getMass())/rij;
	}
	
	public static double system(Particle[] bodies, Particle spaceship, double gravity) {
		double result = 0.0;
		
		for(int i = 0; i < bodies.length; i++) {
			result += PotentialEnergy.get(spaceship, bodies[i], gravity);
		}
		
		return result;
	}
}
