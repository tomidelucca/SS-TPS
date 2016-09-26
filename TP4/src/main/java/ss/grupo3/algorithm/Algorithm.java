package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;

public interface Algorithm {

	public double positionX(Particle p, double dt);
	public double positionY(Particle p, double dt);
	public double velocityX(Particle p, double dt);
	public double velocityY(Particle p, double dt);
}
