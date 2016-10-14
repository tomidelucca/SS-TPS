package ss.grupo3.Force;

import java.util.List;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Wall;

public class ForceWall {
	
	public static double[] total(Particle p, List<Wall> others, double kn, double kt, double g) {
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
				tVector = new Vector(-1, 0);
				nVector = new Vector(0, 1);
				break;
			case DOWN:
				tVector = new Vector(1, 0);
				nVector = new Vector(0, -1);
				break;
			default:
				//do nothing
				break;
			}
			
			if(!isOverlap(p, w)){
				tVector = new Vector(0, 0);
				nVector = new Vector(0, 0);
			}
			
			forceX += normalForce(p, w, kn)*nVector.getX() + tangencialForce(p, w, kt) * tVector.getX();
			forceY += p.getMass() * (-g) + normalForce(p, w, kn)*nVector.getY() + tangencialForce(p, w, kt) * tVector.getY();
		}
		
		return new double[]{forceX, forceY};
	}
	
	public static double normalForce(Particle p, Wall w, double kn) {
		return - kn * overlap(p, w);
	}
	
	public static double tangencialForce(Particle p, Wall w, double kt) {
		return - kt * overlap(p, w) * relativeVelocity(p, w);
	}
	
	private static double overlap(Particle p, Wall w) {
		double ol = 0;
		
		switch (w.getPosition()) {
		case RIGHT:
			ol = p.getRadius() - Math.abs(p.getPosition().getX() - w.getVec1().getX());
			break;
		case LEFT:
			ol = p.getRadius() - Math.abs(p.getPosition().getX() - w.getVec1().getX());
			break;
		case UP:
			ol = p.getRadius() - Math.abs(p.getPosition().getY() - w.getVec1().getY());
			break;
		case DOWN:
			ol = p.getRadius() - Math.abs(p.getPosition().getY() - w.getVec1().getY());
			break;
		default:
			//do nothing
			break;
		}
		
		return (ol > 0)? ol : 0.0;
	}
	
	private static double relativeVelocity(Particle p, Wall w) {
		return p.getVelocity().mod();
	}
	
	private static boolean isOverlap(Particle p, Wall w) {
		if(overlap(p, w) > 0)
			return true;
		return false;
	}
}
