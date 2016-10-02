package ss.grupo3.parameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ss.grupo3.oscillation.Oscillation;

public class FileProperties {

    private double m;
    private double k;
    private double l;
    private double tf;
    private double rt0;
	
    public FileProperties(String path) {
        Properties prop = new Properties();
        InputStream inputFile = null;

        try {
            inputFile = new FileInputStream(path);
            prop.load(inputFile);

            this.m = Double.valueOf(prop.getProperty("m"));
            this.k = Double.valueOf(prop.getProperty("k"));
            this.l = Double.valueOf(prop.getProperty("l"));
            this.tf = Double.valueOf(prop.getProperty("tf"));
            this.rt0 = Double.valueOf(prop.getProperty("rt0"));
                        
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
    
    public Oscillation getOscillation() {
    	return new Oscillation(m,k,l,rt0);
    }
    
    public double tf() {
    	return tf;
    }
}
