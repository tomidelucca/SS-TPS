package ss.grupo3.planets;

import ss.grupo3.model.Particle;

public class GravityForce {

	public static double[] get(Particle p1, Particle p2, double gravity) {
		double rij = Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
		double ei = (p2.getX() - p1.getX())/rij;
		double ej = (p2.getY() - p1.getY())/rij;
		
		double Fi = gravity*((p1.getMass()*p2.getMass())/Math.pow(rij, 2))*ei;
		double Fj = gravity*((p1.getMass()*p2.getMass())/Math.pow(rij, 2))*ej;
		
		double[] resultForce = {Fi, Fj};
		
		return resultForce;
	}
}
