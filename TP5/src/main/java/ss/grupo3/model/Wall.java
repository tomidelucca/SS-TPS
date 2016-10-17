package ss.grupo3.model;

public class Wall {

	private Vector vec1;
	private Vector vec2;
	private Position position;
	
	public Wall(Vector vec1, Vector vec2, Position position) {
		this.vec1 = vec1;
		this.vec2 = vec2;
		this.position = position;
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
	
	public String print1() {
		return vec1.getX() + " " + vec1.getY() + " 0.0 0.0 0.05 1 0 1";
	}
	public String print2() {
		return vec2.getX() + " " + vec2.getY() + " 0.0 0.0 0.05 1 0 1";
	}
}
