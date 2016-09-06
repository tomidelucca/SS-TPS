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
		int iteration;
		String mode;
		
		FileProperties fp = new FileProperties("config.properties");
		N = fp.getN();
		L = fp.getL();
		iteration = fp.getIteration();
		mode = fp.getMode();
		
		Particle[] particles = ParticleGenerator.randomParticleGenerator(N, L);
		FileGenerator.generate(N, L, particles, "input/input2.xyz");
		
		BrownianMotion bm = new BrownianMotion(particles, L);
		bm.run(iteration);
		
	}
}
