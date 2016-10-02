package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;

public class VervetAlgorithm {

	public static double positionX(Particle particle, double dt) {
		return 2*particle.getX() - particle.getPrev_x() + (Math.pow(dt,2)/particle.getMass())*particle.kineticEnergy();
	}
	
	public static double positionY(Particle particle, double dt) {
		return 2*particle.getY() - particle.getPrev_y() + (Math.pow(dt,2)/particle.getMass())*particle.kineticEnergy();
	}
}
