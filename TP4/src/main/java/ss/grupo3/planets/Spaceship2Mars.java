package ss.grupo3.planets;

import ss.grupo3.algorithm.LeapFrogAlgorithmForPlanets;
import ss.grupo3.file.ReadParticle;
import ss.grupo3.model.Color;
import ss.grupo3.model.Particle;
import ss.grupo3.oscillation.Force;
import ss.grupo3.ovito.OvitoFile;

public class Spaceship2Mars {
	private static double G = 6.693E-11;
	private static double V0 = 3.0 * 1000;
	private static double ORBITAL_SPEED = 7.12 * 1000;
	private static double ANGLE = 0;
	private static double DISTANCIE_EARTH2SPACESHIP = 1500 * 1000;//4E7 * 1000;
	
	private static double HOUR = 3600;
	private static double DAY = 24 * HOUR;
	private static double YEAR = 365 * DAY;
	
	public static void run() {
		OvitoFile ovito = new OvitoFile("output/output.xyz");

		Particle[] bodies = new Particle[3];
		Particle spaceship;
		
		double tf = 4*YEAR;
		double dt = 100;
		double t = 0.0;
		
		ReadParticle rp = new ReadParticle();
		rp.read("input/input.xyz");
		
		bodies[0] = rp.getSun();
		bodies[1] = rp.getEarth();
		bodies[2] = rp.getMars();
				
		//la nave esta a una distantcia de 1500km respecto de la tierra y
		//tiene una velocidad v0 + velocidad orbital.
		spaceship = new Particle(0, 0, 0, 0, 100, 200000, new Color(1,1,1));

        double radians = angleSun(bodies[1]);

		spaceship.setX((bodies[1].getX() + bodies[1].getRadius()*Math.cos(radians) + DISTANCIE_EARTH2SPACESHIP*Math.cos(radians)));
		spaceship.setY((bodies[1].getY() + bodies[1].getRadius()*Math.sin(radians) + DISTANCIE_EARTH2SPACESHIP*Math.sin(radians)));

		spaceship.setVx(V0*Math.cos(radians) + ORBITAL_SPEED*Math.sin(radians) + bodies[1].getVx());
		spaceship.setVy(V0*Math.sin(radians) + ORBITAL_SPEED*Math.cos(radians) + bodies[1].getVy());

		spaceship.setPrev_vx(spaceship.getVx());
		spaceship.setPrev_vy(spaceship.getVy());
		
		for(int i = 0; i < bodies.length; i++) {
			bodies[i].setPrev_vx(bodies[i].getVx());
			bodies[i].setPrev_vy(bodies[i].getVy());
		}
						
		LeapFrogAlgorithmForPlanets leapFrog = new LeapFrogAlgorithmForPlanets();
		double[] sumForce;
		int day = 0;
		Particle[] printParticle = new Particle[4];

		printParticle[0] = bodies[0];
		printParticle[1] = bodies[1];
		printParticle[2] = bodies[2];
		printParticle[3] = spaceship;
		
		ovito.write(printParticle);
		
		double orbitalSpeedX = 0;
		double orbitalSpeedY = 0;
		
		double d2s = distance2Sun(spaceship);
		
		while(tf > 0.0) {
			sumForce = Force.sumForce(spaceship, bodies, G);
			//spaceship.setNext_vx(leapFrog.velocityX(spaceship,sumForce[0] + Force.ForceCentripeta(spaceship)*Math.cos(angleSun(spaceship)), t, dt));
			//spaceship.setNext_vy(leapFrog.velocityY(spaceship,sumForce[1] + Force.ForceCentripeta(spaceship)*Math.sin(angleSun(spaceship)), t, dt));
            spaceship.setNext_vx(leapFrog.velocityX(spaceship,sumForce[0], t, dt));
			spaceship.setNext_vy(leapFrog.velocityY(spaceship,sumForce[1], t, dt));
			
            //orbitalSpeedX = ORBITAL_SPEED*Math.sin(angleSun(spaceship))*(d2s/distance2Sun(spaceship));
			//orbitalSpeedY = ORBITAL_SPEED*Math.cos(angleSun(spaceship))*(d2s/distance2Sun(spaceship));

            spaceship.setNext_x(leapFrog.positionX(spaceship, t, dt, orbitalSpeedX));
			spaceship.setNext_y(leapFrog.positionY(spaceship, t, dt, orbitalSpeedY));
			
			for(int i = 0; i < bodies.length; i++) {
				sumForce = Force.sumForce(bodies[i], bodies, G);
				bodies[i].setNext_vx(leapFrog.velocityX(bodies[i],sumForce[0], t, dt));
				bodies[i].setNext_vy(leapFrog.velocityY(bodies[i],sumForce[1], t, dt));
				bodies[i].setNext_x(leapFrog.positionX(bodies[i], t, dt, 0));
				bodies[i].setNext_y(leapFrog.positionY(bodies[i], t, dt, 0));	
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
									

			
			if(Math.abs(t - 86400.0) < 0.1){
                System.out.println(radians);
                ovito.write(printParticle);
				day++;
				System.out.println("day: " + day);
				System.out.println("osx:" + orbitalSpeedX + " osy:" + orbitalSpeedY + " os:" + Math.sqrt(Math.pow(orbitalSpeedX, 2) + Math.pow(orbitalSpeedY, 2)));

				t = 0.0;
			}

			tf -= dt;
			t += dt;
		}		
		ovito.closeFile();		
	}
	
	private static double angleSun(Particle spaceship) {
	    return Math.atan2(spaceship.getY(), spaceship.getX());
	}	
	
	private static double distance2Sun(Particle spaceship) {
	    return Math.sqrt(Math.pow(spaceship.getX(), 2) + Math.pow(spaceship.getY(), 2));
	}	
}
