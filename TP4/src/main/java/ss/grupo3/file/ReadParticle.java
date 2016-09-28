package ss.grupo3.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ss.grupo3.model.Particle;

public class ReadParticle {

    private int N;
    private double L;
	
	private Particle sun;
	private Particle earth;
	private Particle mars;
	
	public void read(String path) {
		
		BufferedReader br = null;

		try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            if (line != null) {
                this.N = Integer.valueOf(line);
                this.L = Double.valueOf(br.readLine());
            }

            String[] input;            
            input = br.readLine().split(" ");
            sun = new Particle(Double.valueOf(input[0])*1000, Double.valueOf(input[1])*1000, Double.valueOf(input[2])*1000, Double.valueOf(input[3])*1000, Double.valueOf(input[4])*1000, Double.valueOf(input[5]));
            input = br.readLine().split(" ");
            earth = new Particle(Double.valueOf(input[0])*1000, Double.valueOf(input[1])*1000, Double.valueOf(input[2])*1000, Double.valueOf(input[3])*1000, Double.valueOf(input[4])*1000, Double.valueOf(input[5]));
            input = br.readLine().split(" ");
            mars = new Particle(Double.valueOf(input[0])*1000, Double.valueOf(input[1])*1000, Double.valueOf(input[2])*1000, Double.valueOf(input[3])*1000, Double.valueOf(input[4])*1000, Double.valueOf(input[5]));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public Particle getSun() {
		return this.sun;
	}
	
	public Particle getEarth() {
		return this.earth;
	}
	
	public Particle getMars() {
		return this.mars;
	}
}
