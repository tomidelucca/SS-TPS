package ss.grupo3.TP3.models;

public class Movement {

    //x(t+1) = x(t) + sin(tita)*v*(t)
    //y(t+1) = y(t) + cos(tita)*v*(t)
    public static Agent doMove(Agent agent) {
        double newX = agent.getPosition().getX() + Math.cos(agent.getAngle())*agent.getSpeed();
        double newY = agent.getPosition().getY() + Math.sin(agent.getAngle())*agent.getSpeed();

        Agent newAgent = new Agent(newX, newY);
        newAgent.setAngle(agent.getAngle());
        
        return newAgent;
    }
}