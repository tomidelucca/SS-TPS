package ss.grupo3.methods;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class InputGenerator {

    public static void generateRandomInput(int N, double L, double maxRadius, String path) {
        PrintWriter writer = null;
        Random rand = new Random();

        try {
            writer = new PrintWriter(path, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        writer.println(N);
        writer.println(L);

        for(int i = 0; i<N; i++) {
            double x = ((double)rand.nextInt((int) (L*100)))/100;
            double y = ((double)rand.nextInt((int) (L*100)))/100;;
            double radius = ((double)rand.nextInt((int) (maxRadius*100)))/100;
            writer.println(radius + " " + x + " " + y);
        }

        writer.close();
    }

}