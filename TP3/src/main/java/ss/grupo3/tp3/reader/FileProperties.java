package ss.grupo3.tp3.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileProperties {
    private int N;
    private double L;
    private double dt;
    private double totalSimulationTime;
    private String mode;

    public FileProperties(String path) {
        Properties prop = new Properties();
        InputStream inputFile = null;

        // Lectura de propiedades
        try {
            inputFile = new FileInputStream(path);
            prop.load(inputFile);

            this.N = Integer.valueOf(prop.getProperty("N"));
            this.L = Double.valueOf(prop.getProperty("L"));
            this.mode = prop.getProperty("mode");
            this.dt = Double.valueOf(prop.getProperty("dt"));
            this.totalSimulationTime = Double.valueOf(prop.getProperty("total_simulation_time"));
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public double getDt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	public double getTotalSimulationTime() {
		return totalSimulationTime;
	}

	public void setTotalSimulationTime(double totalSimulationTime) {
		this.totalSimulationTime = totalSimulationTime;
	}
}
