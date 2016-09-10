package ss.group3.generator;

import java.io.PrintWriter;
import java.util.List;

import ss.grupo3.model.Particle;

public class CollisionPerDtFileGenerator {
    
    public static void write(String path, List<Integer> list, double dt) {
        
    	try {
    		PrintWriter writer = new PrintWriter(path, "UTF-8");
    		double sum = dt;

            writer.println("#Colisiones en intervalos dt = " + dt);
            for (int i = 0; i < list.size(); i++){
                writer.println(list.get(i) + " TIME:" + sum);
                sum += dt;
            }
            
            writer.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
