package ss.grupo3.TP3.ovito;

import ss.grupo3.TP3.models.Agent;

import java.io.PrintWriter;

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

	public void write(int iteration, double L, Agent[] agents) {
        this.writer.println(agents.length + 4);
        this.writer.println(iteration);
        this.writer.println("0.1 0.0 0.0 0 1");
        this.writer.println("0.1 0.0 " + L + " 0 1");
        this.writer.println("0.1 " +L +" 0.0 0 1");
        this.writer.println("0.1 "+ L +" "+L + " 0 1");

        for (int i = 0; i < agents.length; i++)
            this.writer.println(agents[i]); //escribo la nueva posicion de un agente en archivo
    }

    public void closeFile() {
        this.writer.close();
    }

}