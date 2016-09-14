package ss.grupo3.tp3.generator;

import ss.grupo3.tp3.model.Particle;

//L=0.5m
//N particulas
//particulas
//	Ri = 0.005m
//	Mi = 0.1g
//particula grande
//	R = 0.05m
//	M = 100g
public class ParticleGenerator {
	
	private static final double MAX_VELOCITY = 0.1;
	private static final double MIN_VELOCITY = -0.1;
	private static final double LITTLE_PARTICLE_RADIUS = 0.005;
	private static final double LITTLE_PARTICLE_MASS = 0.0001;
	private static final double BIG_PARTICLE_RADIUS = 0.05;
	private static final double BIG_PARTICLE_MASS = 0.1;
	
	//-0.1m/s < vx < 0.1m/s
	//-0.1m/s < vy < 0.1m/s
	//particula grande v = 0
	/**
	 * Genera particulas
	 * 
	 * @param N cantidad de particulas a generar
	 * @param L area
	 * @return array de particulas
	 */
	public static Particle[] randomParticleGenerator(int N, double L) {
		Particle[] particles = new Particle[N];
		Particle aParticle = null;
		double x;
		double y;
		double vx;
		double vy;

		for(int i = 0; i < N;) {
			x = Math.random() * L;
			y = Math.random() * L;
			vx = Math.random() * (MAX_VELOCITY - MIN_VELOCITY) + MIN_VELOCITY;
			vy = Math.random() * (MAX_VELOCITY - MIN_VELOCITY) + MIN_VELOCITY;
			
			if(particles[0] == null) {
				aParticle = new Particle(x, y, 0.0, 0.0, BIG_PARTICLE_RADIUS, BIG_PARTICLE_MASS);
				if(!isOutofBound(aParticle, L)) {
					particles[i] = aParticle;
					i++;					
				}
			} else {
				aParticle = new Particle(x, y, vx, vy, LITTLE_PARTICLE_RADIUS, LITTLE_PARTICLE_MASS);
				if(validParticle(particles, aParticle, L, i)){
					particles[i] = aParticle;
					i++;
				}
			}
		}
		return particles;
	}
	
	/**
	 * Chequea que una particula no se solapen y no esten fuera del area
	 * 
	 * @param particle
	 * @param b
	 * @param L
	 * @param limit
	 * @return
	 */
	private static boolean validParticle(Particle[] particle, Particle b, double L, int limit) {
		for(int j = 0; j < limit; j++) {
			if(isOutofBound(b, L))
				return false;
			if(!NoOverlapParticle(particle[j], b))
				return false;
		}
		return true;
	}
	
	/**
	 * Compara si dos particulas no ocupan el mismo espacio
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static boolean NoOverlapParticle(Particle a, Particle b) {
		// (xi - xj)^2 - (yi - yj)^2 > (ri - rj)^2
		return (Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2)) > Math.pow(a.getRadius() + b.getRadius(), 2);
	}
	
	/**
	 * Chequeo que una particula no este sobre el limite del area
	 * 
	 * @param a particula
	 * @param L area
	 * @return true si esta sobre el limite. Sino, false.
	 */
	private static boolean isOutofBound(Particle a, double L) {
		if((a.getX() + a.getRadius()) > L || (a.getX() - a.getRadius())  < 0 || 
				(a.getY() + a.getRadius()) > L || (a.getY() - a.getRadius())  < 0)
			return true;
		
		return false;
	}
}
