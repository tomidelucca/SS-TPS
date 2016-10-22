package ss.grupo3.simulation;

import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
    private List<SimulationObserver> observers = new ArrayList<>();

    public void addObserver(SimulationObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(SimulationObserver observer) {
        this.observers.remove(observer);
    }

    protected void updateObservers() {
        for(SimulationObserver obs: observers) {
            obs.notify(this);
        }
    }

    protected void finishSimulation() {
        for(SimulationObserver obs: observers) {
            obs.simulationDidFinish(this);
        }
    }

    protected void startSimulation() {
        for(SimulationObserver obs: observers) {
            obs.simulationDidStart(this);
        }
    }

    public abstract Boolean simulate();
    public abstract void reset();
    public abstract void initialize();

}
