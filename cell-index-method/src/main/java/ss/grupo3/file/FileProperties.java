package ss.grupo3.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileProperties {
    private int M;
    private int iterations;
    private double ETA;
    private double Rc;

    public FileProperties(String path) {
        Properties prop = new Properties();
        InputStream inputFile = null;

        // Lectura de propiedades
        try {
            inputFile = new FileInputStream(path);
            prop.load(inputFile);

            this.iterations = Integer.valueOf(prop.getProperty("iterations"));
            this.M = Integer.valueOf(prop.getProperty("M"));
            this.Rc = Double.valueOf(prop.getProperty("Rc"));
            this.ETA = Double.valueOf(prop.getProperty("eta"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getETA() {
        return ETA;
    }

    public void setETA(double eTA) {
        ETA = eTA;
    }

    public double getRc() {
        return Rc;
    }

    public void setRc(double rc) {
        Rc = rc;
    }

}
