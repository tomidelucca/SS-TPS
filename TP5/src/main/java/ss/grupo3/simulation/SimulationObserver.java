package ss.grupo3.simulation;

public interface SimulationObserver {
    void simulationDidStart(Simulation simulation);
    void notify(Simulation simulation);
    void simulationDidFinish(Simulation simulation);
}
