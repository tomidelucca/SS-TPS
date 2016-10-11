package ss.grupo3;

import java.util.List;

import ss.grupo3.generator.ParticleGenerator;
import ss.grupo3.model.Position;
import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Wall;
import ss.grupo3.ovito.OvitoFile;

public class Start {

	//L>W>D
	private static double L = 10;
	private static double W = 5;
	private static double D = 2;

	
	public static void main(String[] args) {
		simulation();
	}
	
	public static void simulation() {
		OvitoFile ovito = new OvitoFile("output/test.xyz");
		List<Particle> particles = ParticleGenerator.generate(L, W, D);
		
		Wall[] walls = new Wall[5];
		walls[0] = new Wall(new Vector(0, 0), new Vector(0, L + 1), Position.LEFT);
		walls[1] = new Wall(new Vector(W, 0), new Vector(W, L + 1), Position.RIGHT);
		walls[2] = new Wall(new Vector(0, L + 1), new Vector(W, L + 1), Position.UP);
		walls[3] = new Wall(new Vector(0, 1), new Vector((W - D)/2, 1), Position.DOWN);
		walls[4] = new Wall(new Vector(W - (W - D)/2, 1), new Vector(W, 1), Position.DOWN);
		
		ovito.write(particles, walls);
		ovito.closeFile();
	}
}
