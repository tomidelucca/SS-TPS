package ss.grupo3.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileProperties {
    private int N;
    private double L;
    private int iteration;
    private String mode;

    public FileProperties(String path) {
        Properties prop = new Properties();
        InputStream inputFile = null;

        // Lectura de propiedades
        try {
            inputFile = new FileInputStream(path);
            prop.load(inputFile);

            this.iteration = Integer.valueOf(prop.getProperty("iteration"));
            this.N = Integer.valueOf(prop.getProperty("N"));
            this.L = Double.valueOf(prop.getProperty("L"));
            this.mode = prop.getProperty("mode");

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

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public double getL() {
		return L;
	}

	public void setL(double l) {
		L = l;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
