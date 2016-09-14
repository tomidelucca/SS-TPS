package ss.grupo3.tp3.generator;

import java.io.PrintWriter;
import java.util.List;

public class CollisionTimeFileGenerator {

    public static void write(String path, List<Double> list) {

        try {

            PrintWriter writer = new PrintWriter(path, "UTF-8");
            double aux = 0.0;

            for (int i = 0; i < list.size(); i++){
                writer.println(list.get(i));
                aux += list.get(i);
            }

            System.out.println(aux/list.size());

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
