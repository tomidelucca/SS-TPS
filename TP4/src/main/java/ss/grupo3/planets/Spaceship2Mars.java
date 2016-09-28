package ss.grupo3.planets;

import ss.grupo3.algorithm.LeapFrogAlgorithmForPlanets;
import ss.grupo3.file.ReadParticle;
import ss.grupo3.model.Particle;
import ss.grupo3.oscillation.Force;
import ss.grupo3.ovito.OvitoFile;

public class Spaceship2Mars {
	private static double G = 6.693e-11;
	private static double v0 = 3.00;
	private static double orbitalSpeed = 7.12;
	
	public static void run() {
		OvitoFile ovito = new OvitoFile("output/output.xyz");
//		Particle sun;
//		Particle earth;
//		Particle mars;
		Particle[] bodies = new Particle[3];
		Particle spaceship;
		double radians = 0.0;
		
		double tf = 4*360.0*86400.0;
		double dt = 100.0;
		double t = 0.0;
		
		ReadParticle rp = new ReadParticle();
		rp.read("input/input.xyz");
		
		bodies[0] = rp.getSun();
		bodies[1] = rp.getEarth();
		bodies[2] = rp.getMars();
		
		radians = Math.toRadians(180.0);
		//la nave esta a una distantcia de 1500km respecto de la tierra y 
		//tiene una velocidad v0 + velocidad orbital.
		spaceship = new Particle((bodies[1].getX() + bodies[1].getRadius()*Math.cos(radians) + 1500*1000), 
				(bodies[1].getY() + bodies[1].getRadius()*Math.sin(radians) + 1500*1000), 
				(v0 + orbitalSpeed)*1000*Math.sin(radians), 
				(v0 + orbitalSpeed)*1000*Math.cos(radians), 
				100.0, 200000.0);
		
		spaceship.setPrev_vx(spaceship.getVx());
		spaceship.setPrev_vy(spaceship.getVy());
		for(int i = 0; i < bodies.length; i++){
			bodies[i].setPrev_vx(bodies[i].getVx());
			bodies[i].setPrev_vy(bodies[i].getVy());
		}
		
//		bodies[3] = spaceship;
				
		LeapFrogAlgorithmForPlanets leapFrog = new LeapFrogAlgorithmForPlanets();
		double[] sumForce;
		int day = 0;
		Particle[] printParticle = new Particle[4];

		printParticle[0] = bodies[0];
		printParticle[1] = bodies[1];
		printParticle[2] = bodies[2];
		printParticle[3] = spaceship;
		
		ovito.write(printParticle);
		
		while(tf > 0.0) {
			sumForce = Force.sumForce(spaceship, bodies, G);
			spaceship.setNext_vx(leapFrog.velocityX(spaceship,sumForce[0], t, dt));
			spaceship.setNext_vy(leapFrog.velocityY(spaceship,sumForce[1], t, dt));
			spaceship.setNext_x(leapFrog.positionX(spaceship, t, dt));
			spaceship.setNext_y(leapFrog.positionY(spaceship, t, dt));
			
			for(int i = 0; i < bodies.length; i++) {
				sumForce = Force.sumForce(bodies[i], bodies, G);
				bodies[i].setNext_vx(leapFrog.velocityX(bodies[i],sumForce[0], t, dt));
				bodies[i].setNext_vy(leapFrog.velocityY(bodies[i],sumForce[1], t, dt));
				bodies[i].setNext_x(leapFrog.positionX(bodies[i], t, dt));
				bodies[i].setNext_y(leapFrog.positionY(bodies[i], t, dt));				
			}
			
			spaceship.setX(spaceship.getNext_x());
			spaceship.setY(spaceship.getNext_y());
			spaceship.setPrev_vx(spaceship.getNext_vx());
			spaceship.setVx(spaceship.getNext_vx());
			spaceship.setPrev_vy(spaceship.getNext_vy());
			spaceship.setVy(spaceship.getNext_vy());
			
			for(int i = 1; i < bodies.length; i++){
				bodies[i].setX(bodies[i].getNext_x());
				bodies[i].setY(bodies[i].getNext_y());
				bodies[i].setPrev_vx(bodies[i].getNext_vx());
				bodies[i].setPrev_vy(bodies[i].getNext_vy());
				bodies[i].setVx(bodies[i].getNext_vx());				
				bodies[i].setVy(bodies[i].getNext_vy());
			}
			
			printParticle[0] = bodies[0];
			printParticle[1] = bodies[1];
			printParticle[2] = bodies[2];
			printParticle[3] = spaceship;
			
			if(Math.abs(t - 86400.0) < 0.1){
				ovito.write(printParticle);
				day++;
				System.out.println("day: " + day);
				t = 0.0;
			}

			
//			System.out.println("time: " + t);				
//			System.out.println("spaceship: " + spaceship);
//			System.out.println("sun: " + bodies[0]);
//			System.out.println("earth: " + bodies[1]);
//			System.out.println("mars: " + bodies[2]);
			
			tf -= dt;
			t += dt;
		}
		
		ovito.closeFile();
		
//		System.out.println("listo");
	}
	
	public static void move(Particle p, double t, double dt) {
		
	}
	
	
}
