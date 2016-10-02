package ss.grupo3.oscillation;

import ss.grupo3.model.Particle;

public class Oscillation extends Particle {

    private double k;
    private double l;
    
    public Oscillation(double m, double k, double l, double rt0) {
    	super(rt0, 0, -(l / (2 * m)), 0, 0, m);
    	this.k = k;
    	this.l = l;
    }

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getL() {
		return l;
	}

	public void setL(double l) {
		this.l = l;
	}

	public void update() {
		this.setPrev_x(this.getX());
		this.setPrev_vx(this.getVx());
		this.setPrev_ax(this.getAx());
		
		this.setX(this.getNext_x());
		this.setVx(this.getNext_vx());
		this.setAx(this.getNext_ax());
	}

	public String toString() {
		return String.valueOf(getX());
	}
}
