package ss.grupo3.model;

import ss.grupo3.model.Vector;

public class Wall {

	private Vector vec1;
	private Vector vec2;
	private Position position;
	private Vector tVector;
	private Vector nVector;
	
	public Wall(Vector vec1, Vector vec2, Position position) {
		this.vec1 = vec1;
		this.vec2 = vec2;
		this.position = position;
		
		switch (this.position) {
		case RIGHT:
			this.nVector = new Vector(1, 0);
			this.tVector = new Vector(0, 1);
			break;
		case LEFT:
			this.nVector = new Vector(-1, 0);
			this.tVector = new Vector(0, -1);
			break;
		case UP:
			this.nVector = new Vector(0, 1);
			this.tVector = new Vector(-1, 0);
			break;
		case DOWN:
			this.nVector = new Vector(0, -1);
			this.tVector = new Vector(1, 0);
			break;
		default:
			//do nothing
			break;
		}
	}

	public Vector getVec1() {
		return vec1;
	}

	public void setVec1(Vector vec1) {
		this.vec1 = vec1;
	}

	public Vector getVec2() {
		return vec2;
	}

	public void setVec2(Vector vec2) {
		this.vec2 = vec2;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Vector getTVector() {
		return tVector;
	}

	public Vector getNVector() {
		return nVector;
	}
	
	public String print1() {
		return vec1.getX() + " " + vec1.getY() + " 0.0 0.0 0.05 1 0 1 0";
	}
	public String print2() {
		return vec2.getX() + " " + vec2.getY() + " 0.0 0.0 0.05 1 0 1 0";
	}
}
