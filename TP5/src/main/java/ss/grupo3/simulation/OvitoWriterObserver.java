package ss.grupo3.simulation;

import ss.grupo3.MediosGranulares;
import ss.grupo3.ovito.OvitoFile;

public class OvitoWriterObserver implements SimulationObserver {

    private static double ANIMATION_DT = 1E-2;
    private double timeAnimation = ANIMATION_DT;
    private OvitoFile ovitoFile = new OvitoFile("output/test.xyz");

    @Override
    public void simulationDidStart(Simulation simulation) {
        MediosGranulares s = (MediosGranulares)simulation;
        ovitoFile.write(s.getParticles(), s.getWalls(), 0);
    }

    @Override
    public void notify(Simulation simulation) {
        MediosGranulares s = (MediosGranulares)simulation;

        timeAnimation -= MediosGranulares.getSimulationDt();

        if (timeAnimation <= 0) {
            System.out.println("SAVE: " + s.getTimeSimulation());
            ovitoFile.write(s.getParticles(), s.getWalls(), s.getInvisibleParticle().size());
            timeAnimation = ANIMATION_DT;
        }
    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        ovitoFile.closeFile();
    }
}
