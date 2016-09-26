package ss.grupo3.oscillation;

import ss.grupo3.derivative.Derivative;
import ss.grupo3.model.Parameter;
import ss.grupo3.model.Particle;

public class Force {

//	public static double get(Oscillation p, double t) {
//		System.out.println("valor de la fuerza: " + p.getMass()*Derivada.r2(p,t));		
//		return p.getMass()*Derivative.r2(p,t);
//	}
	
	public static double get2(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		
		double r = Math.exp(a*t)*Math.cos(b*t);		
		return -p.getK()*r - p.getL()*Derivative.r1(p,t);
	}

	public static double get3(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		
		double r = Math.exp(a*t)*Math.cos(b*t);		
		return -p.getK()*r - p.getL()*p.getVx();
	}
	
	//este deberia usar.
	public static double get4(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
				
		return -p.getK()*p.getX() - p.getL()*p.getVx();
	}
}
