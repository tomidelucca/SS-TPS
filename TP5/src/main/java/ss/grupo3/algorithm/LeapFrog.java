package ss.grupo3.algorithm;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Velocity;

public class LeapFrog {
	
	private static double velocityX(Particle p, double sumForce, double dt) {
		double velocity = p.getPrevVelocity().getVx() + (dt/p.getMass())*sumForce;
		return (Math.abs(velocity) > 10)?p.getPrevVelocity().getVx():velocity;
	}

	private static double velocityY(Particle p, double sumForce, double dt) {
		double velocity = p.getPrevVelocity().getVy() + (dt/p.getMass())*sumForce;
		return (Math.abs(velocity) > 10)?p.getPrevVelocity().getVy():velocity;
	}

	public static Velocity velocity(Particle p, double[] force, double dt) {
		return new Velocity(velocityX(p, force[0], dt), velocityY(p, force[1], dt));
	}
	
//	private static double positionX(Particle p, double dt) {
//		return p.getPosition().getX() + dt*p.getNextVelocity().getVx();
//	}
//
//	private static double positionY(Particle p, double dt) {
//		return p.getPosition().getY() + dt*p.getNextVelocity().getVy();
//	}

	private static double positionX(Particle p, double dt) {
		return p.getPrevPosition().getX() + dt*p.getNextVelocity().getVx();
	}

	private static double positionY(Particle p, double dt) {
		return p.getPrevPosition().getY() + dt*p.getNextVelocity().getVy();
	}
	
	public static Vector position(Particle p, double dt) {
		return new Vector(positionX(p, dt), positionY(p, dt));
	}
}
