package ss.grupo3;

import ss.group3.generator.FileGenerator;
import ss.group3.generator.ParticleGenerator;
import ss.grupo3.model.BrownianMotion;
import ss.grupo3.model.Particle;
import ss.grupo3.reader.FileProperties;

public class Start {

	public static void main(String[] args) {
		int N;
		double L;
		double dt;
		double total_simulation_time;
		String mode; // no utilizado aun...
		
		FileProperties fp = new FileProperties("config.properties");
		N = fp.getN();
		L = fp.getL();
		dt = fp.getDt();
		total_simulation_time = fp.getTotalSimulationTime();
		mode = fp.getMode();
		
		Particle[] particles = ParticleGenerator.randomParticleGenerator(N, L);
		FileGenerator.generate(N, L, particles, "input/input2.xyz");
		
		BrownianMotion bm = new BrownianMotion(particles, L, dt, total_simulation_time);
		bm.simulate();
		
	}
}
