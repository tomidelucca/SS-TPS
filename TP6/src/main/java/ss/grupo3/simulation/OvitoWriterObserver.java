package ss.grupo3.simulation;

import ss.grupo3.ModeloPeatonal;
import ss.grupo3.ovito.OvitoFile;

import java.io.File;

public class OvitoWriterObserver extends Observer {

    private static double ANIMATION_DT = 1E-1;
    private double timeAnimation = ANIMATION_DT;
    private OvitoFile ovitoFile = null;
    private String path = "output/animation/";

    public OvitoWriterObserver(String name) {
        new File(path).mkdirs();
        this.ovitoFile = new OvitoFile(path + name);
    }

    @Override
    public void simulationDidStart(Simulation simulation) {
        ModeloPeatonal s = (ModeloPeatonal)simulation;
        ovitoFile.write(s.getParticles(), s.getWalls(), 0);
        System.out.println("[OvitoWriterObserver] Started...");
    }

    @Override
    public void notify(Simulation simulation) {
        ModeloPeatonal s = (ModeloPeatonal)simulation;

        timeAnimation -= ModeloPeatonal.getSimulationDt();

        if (timeAnimation <= 0) {
            System.out.println("[OvitoWriterObserver] Animation saved at: " + s.getTimeSimulation());
            ovitoFile.write(s.getParticles(), s.getWalls(), s.getInvisibleParticle().size());
            timeAnimation += ANIMATION_DT;
        }
    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        ovitoFile.closeFile();
        System.out.println("[OvitoWriterObserver] Finished...");
    }
}
