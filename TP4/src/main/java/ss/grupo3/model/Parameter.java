package ss.grupo3.model;

public class Parameter {

	private double m;
	private double k;
	private double l;
	private double tf;
	private double rt0;
	private double vt0;

	public Parameter(double m, double k, double l, double tf, double rt0) {
		this.m = m;
		this.k = k;
		this.l = l;
		this.tf = tf;
		this.rt0 = rt0;
		this.vt0 = -(l / (2 * m));
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
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

	public double getTf() {
		return tf;
	}

	public void setTf(double tf) {
		this.tf = tf;
	}

	public double getRt0() {
		return rt0;
	}

	public void setRt0(double rt0) {
		this.rt0 = rt0;
	}

	public double getVt0() {
		return vt0;
	}

	public void setVt0(double vt0) {
		this.vt0 = vt0;
	}

}
