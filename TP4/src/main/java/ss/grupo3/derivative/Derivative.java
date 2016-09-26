/**
 * r0: exp(a*t)*cos(b*t)
 * r1: exp(a*t)*(a*cos(b*t) - b*sin(b*t)) 
 * r2: exp(a*t)*(b^2*(-cos(b*t)) - a*b*sin(b*t)) + a*exp(a*t)*(a*cos(b*t) - b*sin(b*t))
 * r3: 
 * r4:
 * r5;
 */
package ss.grupo3.derivative;

import ss.grupo3.oscillation.Oscillation;

public class Derivative {

	//posicion
	public static double r0(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		
		return Math.exp(a*t)*(a*Math.cos(b*t));
	}
	
	//velocidad
	public static double r1(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		
		return Math.exp(a*t)*(a*Math.cos(b*t) - b*Math.sin(b*t));
	}
	
	//aceleracion
	public static double r2(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		
		return Math.exp(a*t)*(Math.pow(b, 2)*(-Math.cos(b*t)) - a*b*Math.sin(b*t)) + 
		a*Math.exp(a*t)*(a*Math.cos(b*t) - b*Math.sin(b*t));
	}
	
	//3-th derivative
	public static double r3(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		
		return Math.pow(a,3)*Math.exp(a*t)*Math.cos(b*t) -
				3*Math.pow(a,2)*b*Math.exp(a*t)*Math.sin(b*t) +
				Math.pow(b,3)*Math.exp(a*t)*Math.sin(b*t) -
				3*a*Math.pow(b,2)*Math.exp(a*t)*Math.cos(b*t);
	}
	
	//4-th derivative
	public static double r4(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		
		return Math.pow(a,4)*Math.exp(a*t)*Math.cos(b*t) -
				4*Math.pow(a,3)*b*Math.exp(a*t)*Math.sin(b*t) -
				6*Math.pow(a,2)*Math.pow(b,2)*Math.exp(a*t)*Math.cos(b*t) +
				Math.pow(b,4)*Math.exp(a*t)*Math.cos(b*t) +
				4*a*Math.pow(b,3)*Math.exp(a*t)*Math.sin(b*t);
	}

	//5-th derivative
	public static double r5(Oscillation p, double t) {
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		
		return Math.pow(a,5)*Math.exp(a*t)*Math.cos(b*t) -
				5*Math.pow(a,4)*b*Math.exp(a*t)*Math.sin(b*t) -
				10*Math.pow(a,3)*Math.pow(b,2)*Math.exp(a*t)*Math.cos(b*t) +
				10*Math.pow(a,2)*Math.pow(b,3)*Math.exp(a*t)*Math.sin(b*t) -
				Math.pow(b,5)*Math.exp(a*t)*Math.sin(b*t) +
				5*a*Math.pow(b,4)*Math.exp(a*t)*Math.cos(b*t);
	}
}
