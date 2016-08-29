package ss.grupo3.file;

import ss.grupo3.models.Particle;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FileGenerator {
    public static void write(int n, int l, List<Particle> listParticle, String path) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(path, "UTF-8");
            writer.println(n);
            writer.println(l);

            for(Particle p: listParticle) {
                writer.println(p.getPosition());
            }
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