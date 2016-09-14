package ss.grupo3.tp3.generator;

import java.io.PrintWriter;
import java.util.List;

public class CollisionPerDtFileGenerator {
    
    public static void write(String path, List<Integer> list, double dt) {
        
    	try {
    		PrintWriter writer = new PrintWriter(path, "UTF-8");
    		double sum = dt;

            writer.println("dt = " + dt + ", value");
            for (int i = 0; i < list.size(); i++){
                writer.printf("%.2f", sum);
                writer.println(", " + list.get(i));
                sum += dt;
            }
            
            writer.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
