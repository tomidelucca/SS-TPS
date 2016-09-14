package ss.grupo3.tp3.event;

import ss.grupo3.tp3.model.CollisionWall;
import ss.grupo3.tp3.model.Event;
import ss.grupo3.tp3.model.Particle;
import ss.grupo3.tp3.utils.Collision;

public class CollisionWallEvent {

	public static Event check(Particle p, double L) {
		double tcx = 0;
		double tcy = 0;
		
		//PAREDES VERTICALES
		//tc = (xp2 - R - x(0)) / vx ; vx > 0
		//tc = (xp1 + R - x(0)) / vx ; vx < 0
		if(p.getVx() > 0)
			tcx = ( L - p.getRadius() - p.getX()) / p.getVx();			
		else if (p.getVx() < 0)
			tcx = ( 0 + p.getRadius() - p.getX()) / p.getVx();			

		
		//PAREDES HORIZONTALES
		//tc = (yp2 - R - y(0)) / vy ; vy > 0
		//tc = (yp1 + R - y(0)) / vy ; vy < 0
		if(p.getVy() > 0)
			tcy = ( L - p.getRadius() - p.getY()) / p.getVy();			
		else if (p.getVy() < 0)
			tcy = ( 0 + p.getRadius() - p.getY()) / p.getVy();			

		if(tcx < 0 || tcy < 0)
			return null;
		
		if(tcx < tcy) {
			return new CollisionWall(tcx, p, Collision.HORIZONTAL);			
		} else if(tcx > tcy) {
			return new CollisionWall(tcy, p, Collision.VERTICAL);
		} else
			return null;
	}
}
