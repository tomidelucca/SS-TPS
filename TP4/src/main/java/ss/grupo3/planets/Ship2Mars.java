package ss.grupo3.planets;

import ss.grupo3.file.ReadParticle;
import ss.grupo3.model.Particle;

public class Ship2Mars {
	private static double G = 6.693e-11;
	
	public void run() {
		Particle sun;
		Particle earth;
		Particle mars;
		
		ReadParticle rp = new ReadParticle();
		rp.read("input/input.xtyz");
		
		sun = rp.getSun();
		earth = rp.getEarth();
		mars = rp.getMars();
	}
}
