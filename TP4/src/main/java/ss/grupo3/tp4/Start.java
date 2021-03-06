package ss.grupo3.tp4;

import ss.grupo3.algorithm.Algorithm;
import ss.grupo3.algorithm.BeemanAlgorithm;
import ss.grupo3.algorithm.GearPCAlgorithm;
import ss.grupo3.algorithm.LeapFrogAlgorithm;
import ss.grupo3.algorithm.VelocityVerlet;
import ss.grupo3.derivative.Derivative;
import ss.grupo3.oscillation.Oscillation;
import ss.grupo3.parameter.FileProperties;
import ss.grupo3.planets.Spaceship2Mars;
import ss.grupo3.planets.ToMars;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;


public class Start {
	
	public static void main(String[] args) {
		//new File("files/output/").mkdirs();
		//oscillationTest();
		//Spaceship2Mars.run();
		ToMars.simulate();
	}
	
	//Este metodo realiza lo pedido por el punto 1 del tp4
	public static void oscillationTest() {
		double[] dt = {0.0001};//{0.1, 0.05, 0.02, 0.01, 0.005, 0.002, 0.001, 0.0005, 0.0002, 0.0001, 0.00005, 0.00001, 0.000005,0.000002, 0.000001, 0.0000005};
		double[] withBeenman = new double[dt.length];
		double[] withGearPC = new double[dt.length];
		double[] withLeapFrog = new double[dt.length];
		int index = 0;
		
		double[] analitico = null;
		double[] beeman = null;
		double[] gpc = null;
		double[] lf = null;

		for(double delta_t:  dt) {
			System.out.println("valor de DELTA_T: " + delta_t);
			analitico = oscillation(delta_t);
			beeman = oscillationWithBeeman(delta_t);
			gpc = oscillationWithGearPC(delta_t);
			lf = oscillationWithLeapFrog(delta_t);
			
			withBeenman[index] = meanSquaredError(analitico, beeman);
			withGearPC[index] = meanSquaredError(analitico, gpc);
			withLeapFrog[index] = meanSquaredError(analitico, lf);
			index++;
		}

		write("files/output/analitic.csv", analitico, dt[0]);
		write("files/output/beeman.csv", beeman, dt[0]);
		write("files/output/gpc.csv", gpc, dt[0]);
		write("files/output/lf.csv", lf, dt[0]);
		
		System.out.println("with Beeman:");
		for(double value: withBeenman) { System.out.println(value );}
		System.out.println("with GearPC:");
		for(double value: withGearPC) { System.out.println(value );}
		System.out.println("with LeapFrog:");
		for(double value: withLeapFrog) { System.out.println(value );}		
	}
	
	public static double meanSquaredError(double[] array1, double[] array2) {
		double sum = 0;
		
		for(int i = 0; i < array1.length; i++) {
			sum += Math.pow(array1[i] - array2[i], 2);
		}
		
		return sum/array1.length;
	}
	
	//solucion analitica
	public static double[] oscillation(double delta_t) {
		double tf = 0.0;
		double epsilon = 0.0;
		double dt = delta_t;
		double t = 0;
		
		FileProperties fp = new FileProperties("config.properties");
		Oscillation p = fp.getOscillation();
		tf = fp.tf();
		double[] rs = new double[(int) (tf/delta_t) + 1];
		System.out.println((tf/delta_t));
		int i = 0;
		
		double a = -(p.getL()/(2.0*p.getMass()));
		double b = Math.sqrt(p.getK()/p.getMass() - Math.pow(p.getL(),2)/(4.0*Math.pow(p.getMass(),2)));
		double r;

		double gamma = p.getL();
		double mass = p.getMass();
		double k = p.getK();

		while(tf >= epsilon) {
			double term1= Math.exp(- (gamma / (2 * mass) ) * t);
			double term2_1_1= k / mass;
			double term2_1_2=Math.pow(gamma, 2) /(4 * Math.pow(mass, 2));
			double term2_1=Math.sqrt(term2_1_1 - term2_1_2);
			double term2=Math.cos(term2_1 * t);

			r = term1*term2;
			rs[i++] = r;
			tf -= dt;
			t += dt;
		}

		return rs;
	}
	
	//solucion con beeman
	public static double[] oscillationWithBeeman(double delta_t) {
		double tf = 0.0;
		double epsilon = 0.00;
		double dt = delta_t;
		double t = 0;
		
		FileProperties fp = new FileProperties("config.properties");
		Oscillation osc = fp.getOscillation();
		tf = fp.tf();
		double[] rs = new double[(int) (tf/delta_t) + 1];
		int i = 0;
		
		Algorithm beeman = new BeemanAlgorithm();
		
		osc.setPrev_ax(Derivative.r2(osc, -dt));
		osc.setAx(Derivative.r2(osc, 0.0));
		
		while(tf >= epsilon) {
			osc.setNext_ax(Derivative.r2(osc, t + dt));
			beeman.positionX(osc, dt);
			beeman.velocityX(osc, dt);
			
			osc.update();
			rs[i++] = osc.getX();

			tf -= dt;
			t += dt;
		}
		
		return rs;
	}

	//solucion con GearPC
	public static double[] oscillationWithGearPC(double delta_t) {
		double tf = 0.0;
		double epsilon = 0.00;
		double dt = delta_t;
		double t = 0;

		FileProperties fp = new FileProperties("config.properties");
		Oscillation osc = fp.getOscillation();
		osc.setAx(Derivative.r2(osc, 0));
		tf = fp.tf();

		double[] rs = new double[(int) (tf/delta_t) + 1];
		int i = 0;
		
		GearPCAlgorithm gearPC = new GearPCAlgorithm();
		
		while(tf >= epsilon) {
			gearPC.run(osc, t, dt);
			
			rs[i++] = osc.getX();

			tf -= dt;
			t += dt;
		}
		
		return rs;
	}

	//solucion con Leap Frog
	public static double[] oscillationWithLeapFrog(double delta_t) {
		double tf = 0.0;
		double epsilon = 0.00;
		double dt = delta_t;
		double t = 0.0;
		
		FileProperties fp = new FileProperties("config.properties");
		Oscillation osc = fp.getOscillation();
		osc.setPrev_vx(osc.getVx());
		tf = fp.tf();
		
		double[] rs = new double[(int) (tf/delta_t) + 1];
		int i = 0;
		
		LeapFrogAlgorithm leapFrog = new LeapFrogAlgorithm();

		while(tf >= epsilon) {
			osc.setNext_vx(leapFrog.velocityX(osc, t, dt));
			osc.setNext_x(leapFrog.positionX(osc, t, dt));

			osc.setX(osc.getNext_x());
			osc.setPrev_vx(osc.getNext_vx());
			osc.setVx(osc.getNext_vx());
			
			rs[i++] = osc.getX();
			
			tf -= dt;
			t += dt;
		}
		
		return rs;
	}

	public static void write(String path, double[] list, double dt) {

		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			double sum = 0;

			writer.println("t = " + sum + ", value");
			for (int i = 0; i < list.length; i++){
				writer.printf("%f", sum);
				writer.println(", " + list[i]);
				sum += dt;
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
