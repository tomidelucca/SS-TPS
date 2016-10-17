package ss.grupo3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
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
	
	//L>W>D
	private static double L = 3;
	private static double W = 2;
	private static double D = 1;

	private static int M = 5;
	private static double Rc = 0.5;
	private static boolean PERIODIC_BORDER = false;
	
	private static double TIME = 2.5;
	private static double DT = 0.1 * Math.sqrt(MASS/KN);
	
	private static int FPS = 60;

	public static void main(String[] args) {
		simulation();
	}
	
	public static void simulation() {
		int frames = 0;
		OvitoFile ovito = new OvitoFile("output/test.xyz");
		List<Particle> particles = ParticleGenerator.generate(L, W, D, MASS);
		List<Particle> waitToAppear = new ArrayList<Particle>();
		Map<Particle, Set<Particle>> map;
		double timeSimulation = TIME;
		
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
		
		Particle p;
		Set<Particle> setp;
		double[] force;
		double[] sumForce = {0, 0};
		double[] f;		
		ovito.write(particles, walls);

		while(timeSimulation > 0) {
			addParticles(particles, waitToAppear);
			
			
//			System.out.println("timesimulation: " + timeSimulation);
			map = CellIndexMethod.neighbours(particles, L + 1, M, Rc, PERIODIC_BORDER);
			
			for(Map.Entry<Particle,Set<Particle>> entry : map.entrySet()) {
				p = entry.getKey();
				setp = entry.getValue();
				
				force = ForceParticles.total(p, setp, KN, KT);
				sumForce[0] += force[0];
				sumForce[1] += force[1];
				
				force = ForceWall.total(p, walls, KN, KT);
				sumForce[0] += force[0];
				sumForce[1] += force[1];
				
				f = new double[]{sumForce[0], sumForce[1] + p.getMass() * (- GRAVITY)};
				p.setForce(f);
				
				sumForce[0] = 0;
				sumForce[1] = 0;
			}
			
			//Leap Frog Algorithm
			for(Map.Entry<Particle,Set<Particle>> entry : map.entrySet()) {
				p = entry.getKey();
			
				p.setNextVelocity(LeapFrog.velocity(p, p.getForce(), DT));
				p.setNextPosition(LeapFrog.position(p, DT));
				
				p.setPosition(p.getNextPosition());
				p.setPrevVelocity(p.getNextVelocity());
				p.setVelocity(p.getNextVelocity());
				p.setForce(new double[]{0,0});
			}
			
			removeParticles(particles, waitToAppear);
			
			frames++;
			if(frames == FPS){
				ovito.write(particles, walls);
				frames = 0;
//				System.out.println("time:" + timeSimulation);
			}
			
			timeSimulation -= DT;
		}
		
		ovito.closeFile();
	}

	private static void removeParticles(List<Particle> particles, List<Particle> waitToAppear) {
		Iterator<Particle> ite = particles.iterator();
		Particle par;
		while(ite.hasNext()){
			par = ite.next();
			if(par.getPosition().getY() < 0) {
				par.getPosition().setY(L + 1 - par.getRadius());
				par.setPrevPosition(new Vector(0, 0));
				par.setNextPosition(new Vector(0, 0));
				par.setVelocity(new Velocity(0, 0));
				par.setPrevVelocity(new Velocity(0, 0));
				par.setNextVelocity(new Velocity(0, 0));
				par.setForce(new double[]{0,0});
				waitToAppear.add(par);	
				ite.remove();
			}
		}
	}

	private static void addParticles(List<Particle> particles, List<Particle> waitToAppear) {
		Iterator<Particle> ite = waitToAppear.iterator();
		Particle par;
		boolean valid = true;
		
		if(!particles.isEmpty()) {
			while(ite.hasNext()){
				par = ite.next();
				valid = true;
				for(int i = 0; i < particles.size() && valid; i++)
						valid = validParticle(par, particles.get(i));
				
				if(valid) {
					particles.add(par);
					ite.remove();
				}
			}			
		}
		

	}
	
	private static boolean validParticle(Particle p, Particle other) {
		if(overlap(other, p) < -0.1)
			return true;
		return false;
	}
	
	private static double overlap(Particle p, Particle other) {
		return p.getRadius() + other.getRadius() - p.getPosition().rest(other.getPosition()).mod();
	}
}
