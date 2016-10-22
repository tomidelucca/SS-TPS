package ss.grupo3.simulation;

import ss.grupo3.MediosGranulares;

import java.util.Date;

public class GlobalTimeObserver implements SimulationObserver {

    long lStartTime = new Date().getTime();
    long lEndTime;
    long difference;

    @Override
    public void simulationDidStart(Simulation simulation) {
        System.out.println("INICIO simulacion");
    }

    @Override
    public void notify(Simulation simulation) {

    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        lEndTime = new Date().getTime();
        difference = lEndTime - lStartTime;
        System.out.println("tiempo en segundos: " + difference/1000);
        System.out.println("FIN simulacion");
    }

}
