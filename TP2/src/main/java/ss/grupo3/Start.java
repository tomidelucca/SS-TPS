package ss.grupo3;

import ss.grupo3.file.FileAgentsReader;
import ss.grupo3.file.FileProperties;
import ss.grupo3.methods.InputGenerator;
import ss.grupo3.methods.SelfDrivenParticles;
import ss.grupo3.models.Agent;
import ss.grupo3.models.Particle;
import ss.grupo3.ovito.OvitoFile;

import java.util.Map;
import java.util.Set;

public class Start {

    private final static double DELTA_TIME = 1.0;

    public static void main(String[] args) {

        /*InputGenerator.generateRandomInput(40, 7, 1, "resources/input/40_7.xyz");
        InputGenerator.generateRandomInput(100, 7, 1, "resources/input/100_7.xyz");
        InputGenerator.generateRandomInput(400, 7, 1, "resources/input/400_7.xyz");
        InputGenerator.generateRandomInput(4000, 7, 1, "resources/input/4000_7.xyz");
        InputGenerator.generateRandomInput(10000, 7, 1, "resources/input/10000_7.xyz");*/

        OvitoFile ovitoFile = new OvitoFile("out/result.xyz");
        FileAgentsReader far = new FileAgentsReader("resources/input/40_7.xyz");
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
        Va = new double[iterations];

        System.out.printf("VALORES N:%d L:%.2f M:%d Rc:%.2f\n", N,L,M,Rc);
        System.out.printf("VALOR DE DENSIDAD: %.3f\n", ((double)N/Math.pow(L,2)));
        System.out.printf("DELTA TIEMPO: %.2f\n",DELTA_TIME);
        System.out.printf("ITERACIONES: %d\n", iterations);

        for(double e = 0.0; e <= 5.0; e += 0.25) {
            long start = System.currentTimeMillis();
            int times = 0;
            ETA = e;
            agents = far.read();
            ovitoFile = new OvitoFile("out/eta_" + ETA + ".xyz");
            System.out.println("PROCESO CON ETA: " + ETA);
            while(times < iterations) {
                SelfDrivenParticles.move(agents, DELTA_TIME);
                map = SelfDrivenParticles.neighbours(agents, L, M, Rc, true);
                SelfDrivenParticles.updateAngle(map, ETA);
                SelfDrivenParticles.updatePosition(map, L); //esto lo hago en caso de que las particulas esten fuera del cuadado L.
                Va[times] = SelfDrivenParticles.simulationNormalizedVelocity(agents);

                ovitoFile.write(times, L, agents);
                times++;
            }
            ovitoFile.createVaFile(Va, N, "out/Va/" + N + "_eta" + ETA + ".txt");
            ovitoFile.closeFile();

            System.out.println();
            System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - start) / 1000) + " segundos");
            System.out.println("PROCESO FINALIZADO.");
        }

    }
}