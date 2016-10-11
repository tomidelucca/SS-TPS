package ss.grupo3.ovito;

import java.io.PrintWriter;
import java.util.List;

import ss.grupo3.model.Particle;
import ss.grupo3.model.Wall;

public class OvitoFile {

    private PrintWriter writer = null;
    private String path;
    private int frame = 0;
    
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

	public void write(List<Particle> listParticle, Wall[] walls) {
        this.writer.println(listParticle.size() + walls.length);
        this.writer.println(frame++);

        for(int i = 0; i < walls.length; i++) {
        	this.writer.println(walls[i].print());
        }
        
        for(Particle p: listParticle) {
        	this.writer.println(p.print() + " 1 0 0");
        }
    }

    public void closeFile() {
        this.writer.close();
    }

}