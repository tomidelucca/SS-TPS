package ss.grupo3.Force;

import java.util.List;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Wall;

public class ForceWall {
	
	public static double[] total(Particle p, List<Wall> others, double kn, double kt) {
		double forceX = 0;
		double forceY = 0;
		Vector tVector = null;
		Vector nVector = null;
		
		for(Wall w: others) {
			switch (w.getPosition()) {
			case RIGHT:
//				tVector = new Vector(0, 1);
//				nVector = new Vector(-1, 0);
				nVector = new Vector(1, 0);
				tVector = new Vector(0, 1);
				break;
			case LEFT:
//				tVector = new Vector(0, 1);
//				nVector = new Vector(1, 0);
				nVector = new Vector(-1, 0);
				tVector = new Vector(0, -1);
				break;
			case UP:
//				tVector = new Vector(1, 0);
//				nVector = new Vector(0, -1);
				nVector = new Vector(0, 1);
				tVector = new Vector(-1, 0);
				break;
			case DOWN:
//				tVector = new Vector(1, 0);
//				nVector = new Vector(0, 1);
				nVector = new Vector(0, -1);
				tVector = new Vector(1, 0);
				break;
			default:
				//do nothing
				break;
			}
			
			if(isOverlap(p, w)){
				forceX += normalForce(p, w, kn)*nVector.getX() + tangencialForce(p, w, kt, tVector) * tVector.getX();
				forceY += normalForce(p, w, kn)*nVector.getY() + tangencialForce(p, w, kt, tVector) * tVector.getY();
			} 
		}
		
		return new double[]{forceX, forceY};
	}
	
	public static double normalForce(Particle p, Wall w, double kn) {
		return - kn * overlap(p, w);
	}
	
	public static double tangencialForce(Particle p, Wall w, double kt, Vector tVector) {
		return - kt * overlap(p, w) * relativeVelocity(p, w, tVector);
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
			if((w.getVec1().getX() < p.getPosition().getX() && p.getPosition().getX() < w.getVec2().getX()) &&
					(p.getPosition().getY() - p.getRadius()) < w.getVec1().getY() && w.getVec2().getY() < (p.getPosition().getY() + p.getRadius()))
				ol = p.getRadius() - Math.abs(p.getPosition().getY() - w.getVec1().getY());
			break;
		default:
			//do nothing
			break;
		}
		
		return ol;
	}
	
	private static double relativeVelocity(Particle p, Wall w, Vector tVector) {
		return p.getVelocity().getVx() * tVector.getX() + p.getVelocity().getVy() * tVector.getY();
	}
	
	private static boolean isOverlap(Particle p, Wall w) {
		if(overlap(p, w) > 0)
			return true;
		return false;
	}
}
