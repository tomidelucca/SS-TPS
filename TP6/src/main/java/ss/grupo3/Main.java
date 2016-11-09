package ss.grupo3;

import ss.grupo3.simulation.*;

public class Main {

    public static void main(String[] args) {
        ejercicio1();
    }

    public static void ejercicio2() {
        String name = "2L3";

        // Create simulation
        Simulation simulation = new MediosGranulares2();

        // Create observers
        SimulationObserver globalTimeObserver = new GlobalTimeObserver();
        SimulationObserver kineticStopper = new KineticEnergyStopper(0.005);
        SimulationObserver ovitoObserver = new OvitoWriterObserver(name + ".xyz");

        // Register observers
        simulation.addObserver(globalTimeObserver);
        simulation.addObserver(ovitoObserver);
        simulation.addObserver(kineticStopper);

        // Start simulation
        simulation.simulate();
    }

    public static void ejercicio1() {

        String name = "L5";

        // Create simulation
        Simulation simulation = new MediosGranulares1();

        // Create observers
        SimulationObserver globalTimeObserver = new GlobalTimeObserver();
        SimulationObserver kineticEnergyObserver = new KineticEnergyObserver(name + ".csv");
        SimulationObserver flowObserver = new FlowObserver(name + ".csv");
        SimulationObserver ovitoObserver = new OvitoWriterObserver(name + ".xyz");

        // Register observers
        simulation.addObserver(globalTimeObserver);
        simulation.addObserver(ovitoObserver);
        simulation.addObserver(flowObserver);
        simulation.addObserver(kineticEnergyObserver);

        // Start simulation
        simulation.simulate();
    }

}
