package ss.grupo3.TP3.file;

import ss.grupo3.TP3.models.Agent;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileAgentsReader {
    private int N;
    private double L;
    private String path;

    public FileAgentsReader(String path) {
        this.path = path;
    }

    public Agent[] read() {
        BufferedReader br = null;
        Agent[] agents = null;
        try {
            br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            if (line != null) {
                this.N = Integer.valueOf(line);
                this.L = Double.valueOf(br.readLine());
            }

            agents = new Agent[this.N];
            int nbis = this.N;

            while (nbis > 0) {
                String[] input = br.readLine().split(" ");
                agents[this.N - nbis] = new Agent(Double.valueOf(input[1]),
                        Double.valueOf(input[2]));
                nbis--;
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return agents;
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
}