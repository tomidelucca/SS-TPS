package ss.grupo3.simulation;

import java.util.Date;

public class GlobalTimeObserver extends Observer {

    long lStartTime = new Date().getTime();
    long lEndTime;
    long difference;

    @Override
    public void simulationDidStart(Simulation simulation) {
        System.out.println("[GlobalTimerObserver] Simulation started.");
    }

    @Override
    public void notify(Simulation simulation) {

    }

    @Override
    public void simulationDidFinish(Simulation simulation) {
        lEndTime = new Date().getTime();
        difference = lEndTime - lStartTime;
        System.out.println("[GlobalTimerObserver] Simulation time: " + difference/1000);
        System.out.println("[GlobalTimerObserver] Simulation finished.");
    }

}
