package ss.grupo3.simulation;

import ss.grupo3.MediosGranulares;
import ss.grupo3.model.Particle;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class KineticEnergyObserver extends Observer {

    private PrintWriter writer = null;
    private static double SAMPLE_DT = 1E-2;
    private double timeEnergy = SAMPLE_DT;
    private String path = "output/kinetic/";

    public KineticEnergyObserver(String name) {
        new File(path).mkdirs();
        try {
            this.path = this.path + name;
            this.writer = new PrintWriter(path, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void simulationDidStart(Simulation simulation) {
        System.out.println("[KineticEnergyObserver] Started...");
        writer.println("TIME,ENERGY");
    }

    @Override
    public void notify(Simulation simulation) {
        MediosGranulares s = (MediosGranulares)simulation;

        timeEnergy -= MediosGranulares.getSimulationDt();

        if (timeEnergy <= 0) {

            List<Particle> particles = s.getParticles();
            Double totalEnergy = 0.0;

            for(Particle p : particles) {
                if(!p.isFixed() && p.isVisible()) {
                    totalEnergy += p.kineticEnergy();
                }
            }

            writer.println(s.getTimeSimulation() + "," + totalEnergy);
            timeEnergy += SAMPLE_DT;
        }
    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        writer.close();
        System.out.println("[KineticEnergyObserver] Finished.");
    }
}
