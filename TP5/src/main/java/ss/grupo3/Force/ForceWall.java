package ss.grupo3.Force;

import java.util.List;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Wall;

public class ForceWall {

	private static final double KN = 100000;
	private static final double KT = 2*KN;
	private static final double G = 9.8; //m/s^2
	
	public static double[] total(Particle p, List<Wall> others) {
		double forceX = 0;
		double forceY = 0;
		Vector tVector = null;
		Vector nVector = null;
		
		for(Wall w: others) {
			switch (w.getPosition()) {
			case RIGHT:
				tVector = new Vector(0, 1);
				nVector = new Vector(1, 0);
				break;
			case LEFT:
				tVector = new Vector(0, 1);
				nVector = new Vector(-1, 0);
				break;
			case UP:
				tVector = new Vector(0, -1);
				nVector = new Vector(1, 0);
				break;
			case DOWN:
				tVector = new Vector(1, 0);
				nVector = new Vector(0, -1);
				break;
			default:
				//do nothing
				break;
			}
			
			forceX += normalForce(p, w)*nVector.getX() + tangencialForce(p, w) * tVector.getX();
			forceY += p.getMass() * (-G) + normalForce(p, w)*nVector.getX() + tangencialForce(p, w) * tVector.getY();
		}
		
		return new double[]{forceX, forceY};
	}
	
	public static double normalForce(Particle p, Wall w) {
		return - KN * overlap(p, w);
	}
	
	public static double tangencialForce(Particle p, Wall w) {
		return - KT * overlap(p, w) * relativeVelocity(p, w);
	}
	
	private static double overlap(Particle p, Wall w) {
		double ol = 0;
		
		switch (w.getPosition()) {
		case RIGHT:
			ol = p.getRadius() - Math.abs(w.getVec1().getX() - p.getPosition().getX());
			break;
		case LEFT:
			ol = p.getRadius() - Math.abs(p.getPosition().getX() - w.getVec1().getX());
			break;
		case UP:
			ol = p.getRadius() - Math.abs(p.getPosition().getY() - w.getVec1().getY());
			break;
		case DOWN:
			ol = p.getRadius() - Math.abs(w.getVec1().getY() - p.getPosition().getY());
			break;
		default:
			//do nothing
			break;
		}
		
		return ol;
	}
	
	private static double relativeVelocity(Particle p, Wall w) {
		return p.getVelocity().mod();
	}
}
