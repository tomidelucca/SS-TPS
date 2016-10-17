package ss.grupo3;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ss.grupo3.Force.ForceParticles;
import ss.grupo3.Force.ForceWall;
import ss.grupo3.algorithm.LeapFrog;
import ss.grupo3.generator.ParticleGenerator;
import ss.grupo3.method.CellIndexMethod;
import ss.grupo3.model.Particle;
import ss.grupo3.model.Position;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Velocity;
import ss.grupo3.model.Wall;
import ss.grupo3.ovito.OvitoFile;

public class Start {

	private static final double MASS = 0.01;
	private static final double KN = 1E5;
	private static final double KT = 2*KN;
	private static final double GRAVITY = 9.8;
	
	//L > W > D
	private static double L = 3;
	private static double W = 2;
	private static double D = 1;

	private static int M = 5;
	private static double Rc = 1;
	private static boolean PERIODIC_BORDER = false;
	
	private static double TIME = 2;
	private static double DT = 0.1 * Math.sqrt(MASS/KN);
//	private static double DT = 6E-5;
//	private static double DT = 1E-4;
	
	private static int FPS = 120;

	public static void main(String[] args) {
		long lStartTime = new Date().getTime();
		long lEndTime;
		long difference;
		
		System.out.println("INICIO simulacion");
		simulation();

		lEndTime = new Date().getTime();
		difference = lEndTime - lStartTime;
		System.out.println("tiempo en segundos: " + difference/1000);

		System.out.println("FIN simulacion");
	}
	
	public static void simulation() {
		int frames = 0;
		OvitoFile ovito = new OvitoFile("output/test.xyz");
		List<Particle> particles = ParticleGenerator.generate(L, W, D, MASS);
		List<Particle> waitToAppear = new ArrayList<Particle>();
		Map<Particle, Set<Particle>> map;
		double timeSimulation = TIME;

//	UBICACION DE PAREDES
//		|		    |
//		|__.	.___|
//		 __.|	|.__
//		|		    |
//		|		    |
		List<Wall> walls = new ArrayList<Wall>();
		walls.add(new Wall(new Vector(0, L + 1), new Vector(W, L + 1), Position.UP));
		walls.add(new Wall(new Vector(0, 0), new Vector(0, L + 1), Position.LEFT));
		walls.add(new Wall(new Vector(W, 0), new Vector(W, L + 1), Position.RIGHT));
		
		walls.add(new Wall(new Vector(0, 1), new Vector((W - D)/2, 1), Position.DOWN));
		walls.add(new Wall(new Vector((W - D)/2, 1), new Vector((W - D)/2, 1 - 0.05), Position.LEFT));
		walls.add(new Wall(new Vector(0, 1 - 0.05), new Vector((W - D)/2, 1 - 0.05), Position.UP));
	
		walls.add(new Wall(new Vector(W - (W - D)/2, 1), new Vector(W, 1), Position.DOWN));
		walls.add(new Wall(new Vector(W - (W - D)/2, 1), new Vector(W - (W - D)/2, 1 - 0.05), Position.RIGHT));
		walls.add(new Wall(new Vector(W - (W - D)/2, 1 - 0.05), new Vector(W, 1 - 0.05), Position.UP));
	
		//Particulas fijas en los extremos de la ranura. Necesarias para evitar que rompa el proceso.
		particles.add(new Particle(particles.size() + 1, new Vector((W - D)/2, 1), new Velocity(0, 0), 0, MASS, true));
		particles.add(new Particle(particles.size() + 1, new Vector((W - D)/2, 1), new Velocity(0, 0), 0, MASS, true));
		particles.add(new Particle(particles.size() + 1, new Vector(W - (W - D)/2, 1 - 0.05), new Velocity(0, 0), 0, MASS, true));
		particles.add(new Particle(particles.size() + 1, new Vector(W - (W - D)/2, 1 - 0.05), new Velocity(0, 0), 0, MASS, true));
		
		Particle p;
		Set<Particle> setp;
		double[] force;
		double[] sumForce = {0, 0};
		double[] f;		
		ovito.write(particles, walls);

		while(timeSimulation > 0) {
			addParticles(particles, waitToAppear);
			map = CellIndexMethod.neighbours(particles, L + 1, M, Rc, PERIODIC_BORDER);
			
			for(Map.Entry<Particle,Set<Particle>> entry : map.entrySet()) {
				p = entry.getKey();
				setp = entry.getValue();
				 if(!p.isFixed()) {
						force = ForceParticles.total(p, setp, KN, KT);
						sumForce[0] += force[0];
						sumForce[1] += force[1];
						
						force = ForceWall.total(p, walls, KN, KT);
						sumForce[0] += force[0];
						sumForce[1] += force[1];
						
						f = new double[]{sumForce[0], sumForce[1] + p.getMass() * (- GRAVITY)};
						p.setForce(f);
				 }						
						sumForce[0] = 0;
						sumForce[1] = 0;					 

			}
			
			//Leap Frog Algorithm
			for(Map.Entry<Particle,Set<Particle>> entry : map.entrySet()) {
				p = entry.getKey();
			
				if(!p.isFixed()) {
					p.setNextVelocity(LeapFrog.velocity(p, p.getForce(), DT));
					p.setNextPosition(LeapFrog.position(p, DT));
					
					p.setPosition(p.getNextPosition());
					p.setPrevVelocity(p.getNextVelocity());
					p.setVelocity(p.getNextVelocity());
					p.setForce(new double[]{0,0});					
				}
			}
			
			removeParticles(particles, waitToAppear);
			
			frames++;
			if(frames == FPS){
				ovito.write(particles, walls);
				frames = 0;
			}
			
			timeSimulation -= DT;
		}
		
		ovito.closeFile();
	}

	/**
	 * Remueve las particulas que puseen un ubicacion en el eje Y menor a cero
	 * 
	 * @param particles
	 * @param waitToAppear
	 */
	private static void removeParticles(List<Particle> particles, List<Particle> waitToAppear) {
		Iterator<Particle> ite = particles.iterator();
		Particle par;
		while(ite.hasNext()){
			par = ite.next();
			if(par.getPosition().getY() < 0) {
				par.reset(L + 1);
				waitToAppear.add(par);
				ite.remove();
			}
		}
	}

	/**
	 * Agrega las particulas que se quitaron de manera tal que de que no se superpongan
	 * con las que ya se encuantran en la caja.
	 * 
	 * @param particles
	 * @param waitToAppear
	 */
	private static void addParticles(List<Particle> particles, List<Particle> waitToAppear) {
		Iterator<Particle> ite = waitToAppear.iterator();
		Particle par;
		boolean valid = true;
		
			while(ite.hasNext()){
				par = ite.next();
				valid = true;

				for(int i = 0; i < particles.size() && valid; i++)
//					if(particles.get(i).getPosition().getY() > L + 1 - D)
						valid = validParticle(par, particles.get(i));
			
				if(valid) {
					particles.add(par);
					ite.remove();
				}					
			}
	}
	
	private static boolean validParticle(Particle p, Particle other) {
		if(overlap(other, p) < 0)
			return true;
		return false;
	}
	
	private static double overlap(Particle p, Particle other) {
		return p.getRadius() + other.getRadius() - p.getPosition().rest(other.getPosition()).mod();
	}
}
