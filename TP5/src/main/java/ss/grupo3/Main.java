package ss.grupo3;

import ss.grupo3.simulation.GlobalTimeObserver;
import ss.grupo3.simulation.OvitoWriterObserver;
import ss.grupo3.simulation.Simulation;
import ss.grupo3.simulation.SimulationObserver;

public class Main {

    public static void main(String[] args) {
        Simulation simulation = new MediosGranulares();
        SimulationObserver ovitoObserver = new OvitoWriterObserver();
        SimulationObserver globalTimeObserver = new GlobalTimeObserver();

        simulation.addObserver(ovitoObserver);
        simulation.addObserver(globalTimeObserver);

        simulation.simulate();
    }

}
