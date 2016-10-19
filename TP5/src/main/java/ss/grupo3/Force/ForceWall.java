package ss.grupo3.Force;

import java.util.List;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Wall;

public class ForceWall {
	
	public static double[] total(Particle p, List<Wall> others, double kn, double kt) {
		double forceX = 0;
		double forceY = 0;
		double normalForceValue;
		double tangencialForceValue;
		double overlapValue;
		double relativeVelocityValue;
		
		for(Wall w: others) {			
			overlapValue = overlap(p, w);	
			if(overlapValue > 0) {
				relativeVelocityValue = relativeVelocity(p, w, w.getTVector());
				normalForceValue = - kn * overlapValue;
				tangencialForceValue = - kt * overlapValue * relativeVelocityValue;
				forceX += normalForceValue * w.getNVector().getX() + tangencialForceValue * w.getTVector().getX();
				forceY += normalForceValue * w.getNVector().getY() + tangencialForceValue * w.getTVector().getY();
			} 
		}
		
		return new double[]{forceX, forceY};
	}

	
	private static double overlap(Particle p, Wall w) {
		double ol = 0;
		
		//podria mejorarse, pero funciona...
		switch (w.getPosition()) {
		case RIGHT:
			if((w.getVec1().getY() < p.getPrevPosition().getY() && p.getPrevPosition().getY() < w.getVec2().getY()) &&
					(p.getPrevPosition().getX() - p.getRadius()) < w.getVec1().getX() && w.getVec1().getX() < (p.getPrevPosition().getX() + p.getRadius()))
				ol = p.getRadius() - Math.abs(Math.abs(p.getPrevPosition().getX()) - Math.abs(w.getVec1().getX()));
			break;
		case LEFT:
			if((w.getVec1().getY() < p.getPrevPosition().getY() && p.getPrevPosition().getY() < w.getVec2().getY()) &&
					(p.getPrevPosition().getX() - p.getRadius()) < w.getVec1().getX() && w.getVec1().getX() < (p.getPrevPosition().getX() + p.getRadius()))
				ol = p.getRadius() - Math.abs(Math.abs(p.getPrevPosition().getX()) - Math.abs(w.getVec1().getX()));
			break;
		case UP:
			if((w.getVec1().getX() < p.getPrevPosition().getX() && p.getPrevPosition().getX() < w.getVec2().getX()) &&
					(p.getPrevPosition().getY() - p.getRadius()) < w.getVec1().getY() && w.getVec1().getY() < (p.getPrevPosition().getY() + p.getRadius()))
				ol = p.getRadius() - Math.abs(Math.abs(p.getPrevPosition().getY()) - Math.abs(w.getVec1().getY()));
			break;
		case DOWN:
			if((w.getVec1().getX() < p.getPrevPosition().getX() && p.getPrevPosition().getX() < w.getVec2().getX()) &&
					(p.getPrevPosition().getY() - p.getRadius()) < w.getVec1().getY() && w.getVec1().getY() < (p.getPrevPosition().getY() + p.getRadius()))
				ol = p.getRadius() - Math.abs(Math.abs(p.getPrevPosition().getY()) - Math.abs(w.getVec1().getY()));
			break;
		default:
			//do nothing
			break;
		}
		return ol;
	}
	
	private static double relativeVelocity(Particle p, Wall w, Vector tVector) {
		return p.getPrevVelocity().getVx() * tVector.getX() + p.getPrevVelocity().getVy() * tVector.getY();
	}
	
	private static boolean isOverlap(Particle p, Wall w) {
		if(overlap(p, w) > 0)
			return true;
		return false;
	}
}
