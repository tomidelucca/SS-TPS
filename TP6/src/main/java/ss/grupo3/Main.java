package ss.grupo3;

import ss.grupo3.simulation.*;

public class Main {

    public static void main(String[] args) {
        ejercicio1();
    }

    public static void ejercicio1() {

        String name = "5_10";

        // Create simulation
        Simulation simulation = new ModeloPeatonal();

        // Create observers
        SimulationObserver ovitoObserver = new OvitoWriterObserver(name + ".xyz");
        SimulationObserver flowObserver = new FlowObserver(name + ".csv");
        SimulationObserver stopper = new EmptyRoomStopper();

        // Register observers
        simulation.addObserver(ovitoObserver);
        simulation.addObserver(flowObserver);
        simulation.addObserver(stopper);

        // Start simulation
        simulation.simulate();

    }

}
