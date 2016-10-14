package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Velocity;

public class LeapFrog {
	
	private static double velocityX(Particle p, double sumForce, double dt) {
		return p.getPrevVelocity().getVx() + (dt/p.getMass())*sumForce;
	}

	private static double velocityY(Particle p, double sumForce, double dt) {
		return p.getPrevVelocity().getVy() + (dt/p.getMass())*sumForce;
	}

	public static Velocity velocity(Particle p, double[] force, double dt) {
		return new Velocity(velocityX(p, force[0], dt), velocityY(p, force[1], dt));
	}
	
	private static double positionX(Particle p, double dt) {
		return p.getPosition().getX() + dt*p.getNextVelocity().getVx();
	}

	private static double positionY(Particle p, double dt) {
		return p.getPosition().getY() + dt*p.getNextVelocity().getVy();
	}

	public static Vector position(Particle p, double dt) {
		return new Vector(positionX(p, dt), positionY(p, dt));
	}
}
