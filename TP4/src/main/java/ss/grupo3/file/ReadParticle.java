package ss.grupo3.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ss.grupo3.model.Color;
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
			sun = new Particle(Double.valueOf(input[0]) * 1000.0,
					Double.valueOf(input[1]) * 1000.0,
					Double.valueOf(input[2]) * 1000.0,
					Double.valueOf(input[3]) * 1000.0,
					Double.valueOf(input[4]) * 1000.0,
					Double.valueOf(input[5]),
					new Color(251.0/255.0,236.0/255.0,87.0/255.0));
			input = br.readLine().split(" ");
			earth = new Particle(Double.valueOf(input[0]) * 1000.0,
					Double.valueOf(input[1]) * 1000.0,
					Double.valueOf(input[2]) * 1000.0,
					Double.valueOf(input[3]) * 1000.0,
					Double.valueOf(input[4]) * 1000.0,
					Double.valueOf(input[5]),
					new Color(80.0/255.0,139.0/255.0,181.0/255.0));
			input = br.readLine().split(" ");
			mars = new Particle(Double.valueOf(input[0]) * 1000.0,
					Double.valueOf(input[1]) * 1000.0,
					Double.valueOf(input[2]) * 1000.0,
					Double.valueOf(input[3]) * 1000.0,
					Double.valueOf(input[4]) * 1000.0,
					Double.valueOf(input[5]),
					new Color(183.0/255.0,62.0/255.0,21.0/255.0));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
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
