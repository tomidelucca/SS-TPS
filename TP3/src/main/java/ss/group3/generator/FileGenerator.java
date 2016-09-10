package ss.group3.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import ss.grupo3.model.Particle;

/**
 * Crea el archivo de entrada con las particulas
 * 
 * @author Matias
 *
 */
public class FileGenerator {
	
	/**
	 * Genera el el archivo con las particulas
	 * 
	 * @param N cantidad de particulas
	 * @param L	area
	 * @param path ruta del archivo a generar
	 */
	public static void generate(int N, double L, Particle[] particles, String path) {
		PrintWriter writer;
	    try {
	        writer = new PrintWriter(path, "UTF-8");
		    writer.println(N + 4);
		    writer.println(L);
		    writer.println("0.005 0 0");
		    writer.println("0.005 "+L+" 0");
		    writer.println("0.005 0 " + L);
		    writer.println("0.005 " + L + " " + L);
		    for(Particle p: particles)
		    	writer.println(p.print());
		    writer.close();		
		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
	}
}
