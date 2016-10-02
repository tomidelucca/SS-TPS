package ss.grupo3.ovito;

import java.io.PrintWriter;

import ss.grupo3.model.Particle;


//N                            
//frame
//x y vx vy radius
//x y vx vy radius
//x y vx vy radius
//x y vx vy radius

public class OvitoFile {

    private PrintWriter writer = null;
    private String path;
    private int iteration;
    
    public OvitoFile(String path) {
        try {
        	this.iteration = 0;
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
	public void write(Particle[] particles) {
		double L = 3.0E8*1000;
		
        this.writer.println(particles.length + 4);
        this.writer.println(iteration);
        this.writer.println(-L +" " + L + " 0 0 1 1");
        this.writer.println(L +" " + L + " 0 0 1 1");
        this.writer.println(-L +" " + -L + " 0 0 1 1");
        this.writer.println(L +" " + -L + " 0 0 1 1");
        for (int i = 0; i < particles.length; i++) {
        	if(i == 3){
        		this.writer.println(particles[i].getX() + " " + particles[i].getY() + " " + particles[i].getVx() + " " + particles[i].getVy() + " " + 10.0*1E8);        		
        	} else
        		this.writer.println(particles[i].print());
        }


        iteration++;
    }
	
    public void closeFile() {
        this.writer.close();
    }
}