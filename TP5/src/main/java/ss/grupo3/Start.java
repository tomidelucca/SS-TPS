package ss.grupo3;

import java.util.ArrayList;
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
import ss.grupo3.model.Wall;
import ss.grupo3.ovito.OvitoFile;

public class Start {

	private static final double MASS = 0.01;
	private static final double KN = 100000;
	private static final double KT = 2*KN;
	private static final double G = 9.8; //m/s^2
	
	//L>W>D
	private static double L = 3;
	private static double W = 2;
	private static double D = 1;

	private static int M = 5;
	private static double Rc = 1;
	private static boolean PERIODIC_BORDER = false;
	
	private static double TIME = 0.01/6;
//	private static double DT = 0.1 * Math.sqrt(MASS/KN);
	private static double DT = 0.0001;

	
	public static void main(String[] args) {
		simulation();
	}
	
	public static void simulation() {
		OvitoFile ovito = new OvitoFile("output/test.xyz");
		List<Particle> particles = ParticleGenerator.generate(L, W, D, MASS);
		Map<Particle, Set<Particle>> map;
		double timeSimulation = TIME;
		
		List<Wall> walls = new ArrayList<Wall>();
		walls.add(new Wall(new Vector(0, 0), new Vector(0, L + 1), Position.LEFT));
		walls.add(new Wall(new Vector(W, 0), new Vector(W, L + 1), Position.RIGHT));
		walls.add(new Wall(new Vector(0, L + 1), new Vector(W, L + 1), Position.UP));
		walls.add(new Wall(new Vector(0, 1), new Vector((W - D)/2, 1), Position.DOWN));
		walls.add(new Wall(new Vector(W - (W - D)/2, 1), new Vector(W, 1), Position.DOWN));
		
		Particle p;
		Set<Particle> setp;
		double[] force;
		double[] sumForce = {0, 0};
		double[] f;		
		ovito.write(particles, walls);

		while(timeSimulation > 0) {
			System.out.println("timesimulation: " + timeSimulation);
			map = CellIndexMethod.neighbours(particles, L + 1, M, Rc, PERIODIC_BORDER);
			
			for(Map.Entry<Particle,Set<Particle>> entry : map.entrySet()) {
				p = entry.getKey();
				setp = entry.getValue();
				
				force = ForceParticles.total(p, setp, KN, KT, G);
				sumForce[0] += force[0];
				sumForce[1] += force[1];
//				System.out.println("ForceX: " + sumForce[0] + " ForceY: " + sumForce[1]);
				
				force = ForceWall.total(p, walls, KN, KT, G);
				sumForce[0] += force[0];
				sumForce[1] += force[1];
//				System.out.println("ForceX: " + sumForce[0] + " ForceY: " + sumForce[1]);
				
				f = new double[]{sumForce[0], sumForce[1]};
				p.setForce(f);
				
				sumForce[0] = 0;
				sumForce[1] = 0;
			}
			
			for(Map.Entry<Particle,Set<Particle>> entry : map.entrySet()) {
				p = entry.getKey();
			
				p.setNextVelocity(LeapFrog.velocity(p, p.getForce(), DT));
				p.setNextPosition(LeapFrog.position(p, DT));
				
				p.setPosition(p.getNextPosition());
				p.setPrevVelocity(p.getNextVelocity());
				p.setVelocity(p.getNextVelocity());
			}
			ovito.write(particles, walls);

			timeSimulation -= DT;
		}
		
		ovito.closeFile();
	}
}