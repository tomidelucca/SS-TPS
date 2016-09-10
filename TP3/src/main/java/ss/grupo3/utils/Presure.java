package ss.grupo3.utils;

import ss.grupo3.model.Particle;

public class Presure {


	/*
	 * Retorna la presion total en cierto momento del sistema
	 */
	public static double get(Particle[] particles) {
		double sum = 0;
		
		for(Particle p: particles) {
			sum += p.getMass()*Math.pow(p.getSpeed(), 2);
		}
		
		return 1.38*sum;
	}
}
