package ss.grupo3;

import java.util.ArrayList;
import java.util.Date;
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

	// Datos
	private static final double MASS = 1E-2;
	private static final double KN = 1E5;
	private static final double KT = 2*KN;
	private static final double GRAVITY = 9.8;
	
	//L > W > D
	private static double L = 3;
	private static double W = 2;
	private static double D = 1;

	//CellIndexMethod
	private static int M = 5;
	private static double RC = 1;
	private static boolean PERIODIC_BORDER = false;
	
	//Tiempos
	private static double SIMULATION_TIME = 5;
//	private static double SIMULATION_DT = 3E-5;
	private static double SIMULATION_DT = 5E-5;
	private static double ANIMATION_DT = 1E-2;
	
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
		double timeSimulation = SIMULATION_TIME;
		double timeAnimation = ANIMATION_DT;
		OvitoFile ovito = new OvitoFile("output/test.xyz");
		//Genero particulas
		List<Particle> particles = ParticleGenerator.generate(L, W, D, MASS);
		//Particulas que estan arriba
		List<Particle> topParticle = new ArrayList<Particle>();
		//Particulas ocultas que se agregan si no hay overlap con ninguna de topParticle 
		List<Particle> invisibleParticle = new ArrayList<Particle>();
		//Particula y sus vecinas
		Map<Particle, Set<Particle>> map;

		List<Wall> walls = new ArrayList<Wall>();
		walls.add(new Wall(new Vector(0, L + 1), new Vector(W, L + 1), Position.UP));
		walls.add(new Wall(new Vector(0, 0), new Vector(0, L + 1), Position.LEFT));
		walls.add(new Wall(new Vector(W, 0), new Vector(W, L + 1), Position.RIGHT));
		walls.add(new Wall(new Vector(0, 1), new Vector((W - D)/2, 1), Position.DOWN));
		walls.add(new Wall(new Vector(W - (W - D)/2, 1), new Vector(W, 1), Position.DOWN));
	
		//Particulas fijas en los extremos de la ranura. Necesarias para evitar que rompa el proceso.
		particles.add(new Particle(particles.size(), new Vector((W - D)/2, 1), new Velocity(0, 0), 0, MASS, true));
		particles.add(new Particle(particles.size(), new Vector(W - (W - D)/2, 1), new Velocity(0, 0), 0, MASS, true));
		
		Particle p;
		Set<Particle> setp;
		double[] force;
		double[] sumForce = {0, 0};
		ovito.write(particles, walls, 0);
		
		while(timeSimulation > 0) {
			
			//Chequeo si una particula ahora es visible
			for(Particle part: invisibleParticle)
				if(validParticle(part, topParticle)){
					part.setVisible(true);
					topParticle.add(part);
				}
			
			topParticle.clear();
			invisibleParticle.clear();
			
			map = CellIndexMethod.neighbours(particles, L + 1, M, RC, PERIODIC_BORDER);
			
			for(Map.Entry<Particle,Set<Particle>> entry : map.entrySet()) {
				p = entry.getKey();
				setp = entry.getValue();

				//Leap Frog Algorithm part-1
				p.setPrevPosition(p.getPosition());
				p.setPrevVelocity(p.getVelocity());
				p.setPosition(p.getNextPosition());
				p.setPrevVelocity(p.getNextVelocity());
				p.setVelocity(p.getNextVelocity());
								
				if(!p.isFixed() && p.isVisible()) {
					force = ForceParticles.total(p, setp, KN, KT);
					sumForce[0] += force[0]; //x
					sumForce[1] += force[1]; //y
					
					force = ForceWall.total(p, walls, KN, KT);
					sumForce[0] += force[0]; //x
					sumForce[1] += force[1]; //y
					
					sumForce[1] += p.getMass() * (- GRAVITY); //y
					
					//Leap Frog Algorithm part-2
					p.setNextVelocity(LeapFrog.velocity(p, sumForce, SIMULATION_DT));
					p.setNextPosition(LeapFrog.position(p, SIMULATION_DT));
					
					//reseteo particula
					if(p.getPosition().getY() < 0) {
						p.reset(L, W);
						p.setVisible(false);
					}
					
					//particulas que estan arriba
					if(p.getPosition().getY() > (L + 1 - D/3))
						topParticle.add(p);

				}											
					//listo particulas que no se muestran
					if(!p.isVisible())
						invisibleParticle.add(p);
					
					sumForce[0] = 0;
					sumForce[1] = 0;		
			}
			
			timeAnimation -= SIMULATION_DT;
			timeSimulation -= SIMULATION_DT;
			
			if(timeAnimation <= 0) {
				System.out.println("SAVE: " + timeSimulation);
				ovito.write(particles, walls, invisibleParticle.size());
				timeAnimation = ANIMATION_DT;
			}
		}
		
		ovito.closeFile();
	}

	private static boolean validParticle(Particle p, List<Particle> setp) {
		for(Particle sp: setp) {
			if(!validParticle(p, sp))
				return false;
		}		
		return true;
	}
	
	private static boolean validParticle(Particle p, Particle other) {
		return overlap(other, p) < 0;
	}
	
	private static double overlap(Particle p, Particle other) {
		return p.getRadius() + other.getRadius() - p.getPosition().rest(other.getPosition()).mod();
	}
}
