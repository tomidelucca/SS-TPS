package ss.grupo3.simulation;

import ss.grupo3.ModeloPeatonal;
import ss.grupo3.model.Particle;

import java.util.List;

public class EmptyRoomStopper implements SimulationObserver {

    private int FIXED_PARTICLES = 2;

    @Override
    public void simulationDidStart(Simulation simulation) {
        System.out.println("[EmptyRoomStopper] Started...");
    }

    @Override
    public void notify(Simulation simulation) {

    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        ModeloPeatonal s = (ModeloPeatonal)simulation;
        System.out.println("[EmptyRoomStopper] Finished in " + s.getTimeSimulation() + " s.");
    }

    @Override
    public Boolean shouldStopSimulation(Simulation simulation) {
        ModeloPeatonal s = (ModeloPeatonal)simulation;

        List<Particle> particles = s.getParticles();

        return particles.size() <= FIXED_PARTICLES;
    }
}
