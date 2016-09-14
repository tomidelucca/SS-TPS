package ss.grupo3.tp3;

import ss.grupo3.tp3.generator.FileGenerator;
import ss.grupo3.tp3.generator.ParticleGenerator;
import ss.grupo3.tp3.model.BrownianMotion;
import ss.grupo3.tp3.model.Particle;
import ss.grupo3.tp3.reader.FileProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Start {

	public static void main(String[] args) {
		int N;
		double L;
		double dt;
		double total_simulation_time;
		String mode; // no utilizado aun...

		new File("files/input/").mkdirs();
		new File("files/output/").mkdirs();
		new File("files/CPDT/").mkdirs();
		new File("files/CT/").mkdirs();
		new File("files/output/distribution/").mkdirs();
		new File("files/velocity/").mkdirs();
		
		FileProperties fp = new FileProperties("config.properties");
		N = fp.getN();
		L = fp.getL();
		dt = fp.getDt();
		total_simulation_time = fp.getTotalSimulationTime();
		mode = fp.getMode();
		
		Particle[] particles = ParticleGenerator.randomParticleGenerator(N, L);
		FileGenerator.generate(N, L, particles, "files/input/input.xyz");

		BrownianMotion bm = new BrownianMotion(particles, L, dt, total_simulation_time);

		switch (mode) {
			case "simulate":
				System.out.println("[LOG] Simulating...");
				bm.simulate();
				System.out.println("[LOG] Done");
				break;
			case "trajectory":
				System.out.println("[LOG] Simulating trajectory...");
				bm.simulateTrajectory();
				System.out.println("[LOG] Done");
				break;
			case "collision-time":
				System.out.println("[LOG] Simulating collision vs. time...");
				bm.plotCollisionTime(250000, 2);
				System.out.println("[LOG] Done");
				break;
			case "plot-velocity":
				System.out.println("[LOG] Simulating velocity...");
				bm.plotVelocity();
				System.out.println("[LOG] Done");
			default:
				System.out.println("[ERROR] Invalid mode.");
				break;
		}

	}

	public static void generateDistributionGraph(String path, Double min, Double max, Integer steps, String name) {
		LineNumberReader  lnr = null;
		try {
			lnr = new LineNumberReader(new FileReader(new File(path)));
			lnr.skip(Long.MAX_VALUE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Double> values = new ArrayList<>(lnr.getLineNumber() + 1);

		try(BufferedReader br = new BufferedReader(new FileReader(path))) {

			Double readNumber = null;

			String line = br.readLine();

			while (line != null) {

				readNumber = Double.valueOf(line);
				values.add(readNumber);

				if(readNumber > max) {
					max = readNumber;
				}
				if(readNumber < min) {
					min = readNumber;
				}

				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Double stepSize = (max - min)/(steps);
		System.out.println("STEP SIZE: " + stepSize);

		Map<Double, Integer> distribution = new HashMap<>(steps);

		for(int i = 0; i < steps; i++) {
			distribution.put(i*stepSize + min, 0);
		}

		for(int i = 0; i < values.size(); i++) {
			Double indicator = Math.floor(values.get(i)/stepSize)*stepSize + min;
			if(distribution.containsKey(indicator)) {
				Integer count = distribution.get(indicator);
				count = count + 1;
				distribution.put(indicator, count);
			} else {
				distribution.put(indicator, 1);
			}
		}

		try (PrintWriter writer = new PrintWriter("files/output/distribution/" + name + ".csv", "UTF-8")) {

			for (Map.Entry<Double, Integer> entry : distribution.entrySet()) {
				Double key = entry.getKey();
				Integer value = entry.getValue();
				writer.printf("%.4f", key);
				writer.println(", " + value);
			}

			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	public static void printMaxMin(String path) {

		Double max = Double.MIN_VALUE;
		Double min = Double.MAX_VALUE;

		try(BufferedReader br = new BufferedReader(new FileReader(path))) {

			Double readNumber = null;

			String line = br.readLine();

			while (line != null) {

				readNumber = Double.valueOf(line);

				if(readNumber > max) {
					max = readNumber;
				}
				if(readNumber < min) {
					min = readNumber;
				}

				line = br.readLine();
			}

			System.out.println("MAX: " + max + ", MIN: " + min);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public static void generateDistributionGraph(String path, Integer steps, String name) {
		LineNumberReader  lnr = null;
		try {
			lnr = new LineNumberReader(new FileReader(new File(path)));
			lnr.skip(Long.MAX_VALUE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Double max = Double.MIN_VALUE;
		Double min = Double.MAX_VALUE;
		List<Double> values = new ArrayList<>(lnr.getLineNumber() + 1);

		try(BufferedReader br = new BufferedReader(new FileReader(path))) {

			Double readNumber = null;

			String line = br.readLine();

			while (line != null) {

				readNumber = Double.valueOf(line);
				values.add(readNumber);

				if(readNumber > max) {
					max = readNumber;
				}
				if(readNumber < min) {
					min = readNumber;
				}

				line = br.readLine();
			}

			System.out.println("MAX: " + max + ", MIN: " + min);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Double stepSize = (max - min)/(steps) + 0.0000000000001;
		System.out.println("STEP SIZE: " + stepSize);

		Map<Double, Integer> distribution = new HashMap<>(steps);

		for(int i = 0; i < values.size(); i++) {
			Double indicator = Math.floor(values.get(i)/stepSize)*stepSize + min;
			if(distribution.containsKey(indicator)) {
				Integer count = distribution.get(indicator);
				count = count + 1;
				distribution.put(indicator, count);
			} else {
				distribution.put(indicator, 1);
			}
		}

		try (PrintWriter writer = new PrintWriter("files/output/distribution/" + name + ".csv", "UTF-8")) {

			for (Map.Entry<Double, Integer> entry : distribution.entrySet()) {
				Double key = entry.getKey();
				Integer value = entry.getValue();
				writer.printf("%.4f", key);
				writer.println(", " + value);
			}

			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}*/

}
