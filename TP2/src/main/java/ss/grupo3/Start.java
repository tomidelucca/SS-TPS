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
import java.util.Set;

import static java.lang.Math.floor;
import static java.lang.Math.floorDiv;
import static java.lang.Math.pow;

public class Start {

    private final static double DELTA_TIME = 1.0;

    public static void main(String[] args) {

        new File("files/input/").mkdirs();
        new File("files/output/Va").mkdirs();

        //Start.plotVaVsETA(40, 3.1, 3, 1000, 1.0, 3, 0.25);
        Start.plotVaVsDensity(20, 1000, 1.0, 5, 2.0, 1.0);

        //double L, int iterations, double Rc, int times, double eta, double density_delta

        /*for(int mac = 6; mac < 11; mac++) {
            //InputGenerator.generateRandomInput(40, 3.1, 1, "files/input/40_3-1.xyz");
            //InputGenerator.generateRandomInput(100, 5, 1, "files/input/100_5.xyz");
            //InputGenerator.generateRandomInput(400, 10, 1, "files/input/400_10.xyz");
            //InputGenerator.generateRandomInput(4000, 31.6, 1, "files/input/4000_31-6.xyz");
            //InputGenerator.generateRandomInput(10000, 50, 1, "files/input/10000_50.xyz");

            OvitoFile ovitoFile = null;
            FileAgentsReader far = new FileAgentsReader("files/input/400_10.xyz");
            FileProperties fp = new FileProperties("config.properties");
            Map<Particle, Set<Particle>> map = null;

            int M = 0;
            double Rc = 0.0;
            int N = 0;
            double L = 0.0;
            int iterations = 0;
            double ETA = 0; //random perturbation
            Agent[] agents = null;
            double[] Va = null;

            //Parametros del config.properties
            iterations = fp.getIterations();
            M = fp.getM();
            Rc = fp.getRc();
            ETA = fp.getETA();

            //Lectura de particulas de archivo
            agents = far.read();
            N = far.getN();
            L = far.getL();
            Va = new double[21];

            System.out.printf("VALORES N:%d L:%.2f M:%d Rc:%.2f\n", N, L, M, Rc);
            System.out.printf("VALOR DE DENSIDAD: %.3f\n", ((double) N / Math.pow(L, 2)));
            System.out.printf("DELTA TIEMPO: %.2f\n", DELTA_TIME);
            System.out.printf("ITERACIONES: %d\n", iterations);

            int tomi = 0;

            ovitoFile = new OvitoFile("files/output/eta_" + ETA + ".xyz");

            for (double e = 0.0; e <= 5.0; e += 0.25) {
                long start = System.currentTimeMillis();
                int times = 0;
                ETA = e;
                agents = far.read();
                System.out.println("PROCESO CON ETA: " + ETA);
                while (times < iterations) {
                    SelfDrivenParticles.move(agents, DELTA_TIME);
                    map = SelfDrivenParticles.neighbours(agents, L, M, Rc, true);
                    SelfDrivenParticles.updateAngle(map, ETA);
                    SelfDrivenParticles.updatePosition(map, L); //esto lo hago en caso de que las particulas esten fuera del cuadado L.
                    if (times == 999) {
                        Va[tomi++] = SelfDrivenParticles.simulationNormalizedVelocity(agents);
                        //Va[times] = SelfDrivenParticles.simulationNormalizedVelocity(agents);
                    }

                    //ovitoFile.write(times, L, agents);
                    times++;
                }
                //ovitoFile.createVaFile(Va, N, "files/output/Va/" + N + "_eta" + ETA + ".txt");
                ovitoFile.closeFile();

                System.out.println();
                System.out.println("TIEMPO TOTAL: " + ((double) (System.currentTimeMillis() - start) / 1000) + " segundos");
                System.out.println("PROCESO FINALIZADO.");
            }

            ovitoFile.createVaFile(Va, "files/output/Va/" + N + "_" + mac + ".txt");*/
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