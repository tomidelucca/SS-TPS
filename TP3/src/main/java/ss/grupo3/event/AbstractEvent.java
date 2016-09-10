package ss.grupo3.event;

import ss.grupo3.model.Particle;

public class AbstractEvent {

	public static boolean overlap(Particle a, Particle b) {
		double ax1 = a.getX() - a.getRadius();
		double ax2 = a.getX() + a.getRadius();
		double ay1 = a.getY() - a.getRadius();
		double ay2 = a.getY() + a.getRadius();
		
		double bx1 = b.getX() - b.getRadius();
		double bx2 = b.getX() + b.getRadius();
		double by1 = b.getY() - b.getRadius();
		double by2 = b.getY() + b.getRadius();
		
		boolean overlapX = false;
		boolean overlapY = false;
		
		if((ax1 < bx1 && bx1 < ax2) || (ax1 < bx2 && bx2 < ax2))
			overlapX = true;
		
		if((ay1 < by1 && by1 < ay2) || (ay1 < by2 && by2 < ay2))
			overlapY = true;
		
		return overlapX && overlapY;
	}
}
