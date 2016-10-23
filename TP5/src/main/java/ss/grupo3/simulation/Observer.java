package ss.grupo3.simulation;

public abstract class Observer implements SimulationObserver {

    @Override
    public Boolean shouldStopSimulation(Simulation simulation) {
        return Boolean.FALSE;
    }

}
