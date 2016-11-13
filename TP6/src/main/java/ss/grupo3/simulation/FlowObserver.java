package ss.grupo3.simulation;

import ss.grupo3.ModeloPeatonal;
import ss.grupo3.model.Particle;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class FlowObserver extends Observer {

    private PrintWriter writer = null;
    private static double SAMPLE_DT = 5E-1;
    private double timeFlow = SAMPLE_DT;
    private String path = "output/flow/";

    private int numberOfParticles = 0;

    public FlowObserver(String name) {
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
        System.out.println("[FlowObserver] Started...");
        writer.println("TIME,FLOW");
    }

    @Override
    public void notify(Simulation simulation) {

        ModeloPeatonal s = (ModeloPeatonal)simulation;

        List<Particle> particles = s.getParticles();

        timeFlow -= ModeloPeatonal.getSimulationDt();

        if (timeFlow <= 0) {

            writer.println(s.getTimeSimulation() + "," + numberOfParticles);

            numberOfParticles = 0;

            timeFlow += SAMPLE_DT;
        }

        for(Particle p : particles) {
            if(!p.isFixed() && p.isVisible() && (p.getPrevPosition().getY() > 1 && p.getPosition().getY() <= 1)) {
                numberOfParticles++;
            }
        }
    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        writer.close();
        System.out.println("[FlowObserver] Finished.");
    }

}
