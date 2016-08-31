package ss.grupo3;

import ss.grupo3.file.FileAgentsReader;
import ss.grupo3.file.FileGenerator;
import ss.grupo3.file.FileProperties;
import ss.grupo3.methods.InputGenerator;
import ss.grupo3.methods.SelfDrivenParticles;
import ss.grupo3.models.Agent;
import ss.grupo3.models.Particle;
import ss.grupo3.ovito.OvitoFile;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.lang.Math.floor;
import static java.lang.Math.floorDiv;
import static java.lang.Math.pow;

public class Start {

    private final static double DELTA_TIME = 1.0;

    public static void main(String[] args) {

        new File("files/input/").mkdirs();
        new File("files/output/Va").mkdirs();

        String option = "";
        Integer times = 0;

        if(args.length == 2) {
            option = args[0];
            times = Integer.valueOf(args[1]);
        }
        else {
            System.out.println("Invalid input.\n");
            return;
        }

        int iterations;
        int M;
        double Rc;
        double ETA;
        double L;
        int N;
        double delta;

        try {
            FileProperties fp = new FileProperties("config.properties");
            iterations = fp.getIterations();
            M = fp.getM();
            Rc = fp.getRc();
            ETA = fp.getETA();
            L = fp.getL();
            N = fp.getN();
            delta = fp.getDelta();
        } catch (Exception e) {
            System.out.println("Invalid config file.\n");
            return;
        }

        if(option.equals("va_density")) {
            Start.plotVaVsDensity(L, iterations, Rc, times, ETA, delta);
        } else if(option.equals("va_eta")) {
            Start.plotVaVsETA(N, L, M, iterations, Rc, times, delta);
        } else if (option.equals("simulate")) {
            Start.simulate(M, Rc, N, L, iterations, ETA);
        } else {
            System.out.println("Invalid input.\n");
            return;
        }
    }

    public static void simulate(int M, double Rc, int N, double L, int iterations, double ETA) {

        long start = System.currentTimeMillis();

        String path = "files/input/" + N + "_" + L + "_.xyz";

        InputGenerator.generateRandomInput(N, L, 1, path);

        OvitoFile ovitoFile;
        FileAgentsReader far = new FileAgentsReader(path);
        Map<Particle, Set<Particle>> map = null;

        Agent[] agents = far.read();

        System.out.printf("VALORES N:%d L:%.2f M:%d Rc:%.2f\n", N, L, M, Rc);
        System.out.printf("VALOR DE DENSIDAD: %.3f\n", ((double) N / Math.pow(L, 2)));
        System.out.printf("DELTA TIEMPO: %.2f\n", DELTA_TIME);
        System.out.printf("ITERACIONES: %d\n", iterations);

        ovitoFile = new OvitoFile("files/output/"+ N+"_"+L+"_"+ETA+".xyz");
        System.out.println("PROCESO CON ETA: " + ETA);

        int k = 0;

        while (k < iterations) {
            SelfDrivenParticles.move(agents, DELTA_TIME);
            map = SelfDrivenParticles.neighbours(agents, L, M, Rc, true);
            SelfDrivenParticles.updateAngle(map, ETA);
            SelfDrivenParticles.updatePosition(map, L);

            ovitoFile.write(k, L, agents);
            k++;
        }
        System.out.println(k);
        ovitoFile.closeFile();

        System.out.println();
        System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - start) / 1000) + " segundos");
        System.out.println("PROCESO FINALIZADO.");
    }


    public static void plotVaVsDensity(double L, int iterations, double Rc, int times, double eta, double density_delta) {

        int N = 0;
        int M = 0;

        for(int j = 0; j < times; j++) {
            Map<Particle, Set<Particle>> map = null;

            Agent[] agents;
            double[] Va = new double[(int) floor(10.0/density_delta + 1)];

            System.out.printf("DELTA TIEMPO: %.2f\n", DELTA_TIME);
            System.out.printf("ITERACIONES: %d\n", iterations);
            System.out.println("PROCESO CON ETA: " + eta);

            int i = 0;

            for (double d = density_delta; d <= 10.0; d += density_delta) {

                long start = System.currentTimeMillis();
                int k = 0;

                N = (int) Math.round(d*L*L);
                M = (int) Math.floor(L/Rc - 0.1);

                String inputPath = "files/input/" + N + "_" + (int)floor(L) + ".xyz";
                InputGenerator.generateRandomInput(N, L, 1, inputPath);
                FileAgentsReader far = new FileAgentsReader(inputPath);

                agents = far.read();

                System.out.printf("VALORES N:%d L:%.2f M:%d Rc:%.2f\n", N, L, M, Rc);
                System.out.printf("VALOR DE DENSIDAD: %.3f\n", ((double) N / Math.pow(L, 2)));

                while (k < iterations) {
                    k++;
                    SelfDrivenParticles.move(agents, DELTA_TIME);
                    map = SelfDrivenParticles.neighbours(agents, L, M, Rc, true);
                    SelfDrivenParticles.updateAngle(map, eta);
                    SelfDrivenParticles.updatePosition(map, L);
                }

                Va[i++] = SelfDrivenParticles.simulationNormalizedVelocity(agents);

                System.out.println();
                System.out.println("TIEMPO TOTAL: " + ((double) (System.currentTimeMillis() - start) / 1000) + " segundos");
            }

            System.out.println("PROCESO FINALIZADO.");

            FileGenerator.createVaFile(Va, "files/output/Va/Va_Density_" + N + "_" + j + ".txt");
        }

    }

    public static void plotVaVsETA(int N, double L, int M, int iterations, double Rc, int times, double eta_delta) {

        for(int j = 0; j < times; j++) {

            String inputPath = "files/input/" + N + "_" + (int)floor(L) + ".xyz";

            InputGenerator.generateRandomInput(N, L, 1, inputPath);

            FileAgentsReader far = new FileAgentsReader(inputPath);
            Map<Particle, Set<Particle>> map = null;

            double ETA; //random perturbation
            Agent[] agents;
            double[] Va = new double[(int) floor(5.0/eta_delta + 1)];;

            System.out.printf("VALORES N:%d L:%.2f M:%d Rc:%.2f\n", N, L, M, Rc);
            System.out.printf("VALOR DE DENSIDAD: %.3f\n", ((double) N / Math.pow(L, 2)));
            System.out.printf("DELTA TIEMPO: %.2f\n", DELTA_TIME);
            System.out.printf("ITERACIONES: %d\n", iterations);

            int i = 0;

            for (double e = 0.0; e <= 5.0; e += eta_delta) {
                long start = System.currentTimeMillis();
                int k = 0;
                ETA = e;
                agents = far.read();
                System.out.println("PROCESO CON ETA: " + ETA);
                while (k < iterations) {
                    SelfDrivenParticles.move(agents, DELTA_TIME);
                    map = SelfDrivenParticles.neighbours(agents, L, M, Rc, true);
                    SelfDrivenParticles.updateAngle(map, ETA);
                    SelfDrivenParticles.updatePosition(map, L); //esto lo hago en caso de que las particulas esten fuera del cuadado L.
                    k++;
                }
                Va[i++] = SelfDrivenParticles.simulationNormalizedVelocity(agents);

                System.out.println();
                System.out.println("TIEMPO TOTAL: " + ((double) (System.currentTimeMillis() - start) / 1000) + " segundos");
                System.out.println("PROCESO FINALIZADO.");
            }

            FileGenerator.createVaFile(Va, "files/output/Va/Va_ETA_" + N + "_" + j + ".txt");
        }

    }
}