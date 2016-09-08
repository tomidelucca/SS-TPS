package ss.grupo3.ovito;

import java.io.PrintWriter;

import ss.grupo3.model.Particle;


public class OvitoFile {

    private PrintWriter writer = null;
    private String path;
    
    public OvitoFile(String path) {
        try {
        	this.path = path;
            this.writer = new PrintWriter(path, "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void write(int iteration, double L, Particle[] particles) {
        this.writer.println(particles.length + 4);
        this.writer.println(iteration);
	    writer.println("0.005 0 0");
	    writer.println("0.005 "+L+" 0");
	    writer.println("0.005 0 " + L);
	    writer.println("0.005 " + L + " " + L);

        for (int i = 0; i < particles.length; i++)
            this.writer.println(particles[i].print()); //escribo la nueva posicion de un agente en archivo
    }

    public void closeFile() {
        this.writer.close();
    }

}