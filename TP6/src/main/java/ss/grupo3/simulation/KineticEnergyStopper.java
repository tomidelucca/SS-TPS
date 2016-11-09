package ss.grupo3.simulation;

import ss.grupo3.MediosGranulares;
import ss.grupo3.model.Particle;

import java.util.List;

public class KineticEnergyStopper implements SimulationObserver {

    private double ENERGY_DELTA = 0.01;
    private double maxEnergy = -1;

    public KineticEnergyStopper() {

    }

    public KineticEnergyStopper(Double delta) {
        this.ENERGY_DELTA = delta;
    }

    @Override
    public void simulationDidStart(Simulation simulation) {
        System.out.println("[KineticEnergyStopper] Started...");
    }

    @Override
    public void notify(Simulation simulation) {

    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        System.out.println("[KineticEnergyStopper] Finished.");
    }

    @Override
    public Boolean shouldStopSimulation(Simulation simulation) {
        MediosGranulares s = (MediosGranulares)simulation;

        List<Particle> particles = s.getParticles();
        Double totalEnergy = 0.0;

        for(Particle p : particles) {
            if(!p.isFixed() && p.isVisible()) {
                totalEnergy += p.kineticEnergy();
            }
        }

        if(maxEnergy <= totalEnergy) {
            maxEnergy = totalEnergy;
            return Boolean.FALSE;
        } else {
            return (totalEnergy <= ENERGY_DELTA*maxEnergy);
        }

    }
}
