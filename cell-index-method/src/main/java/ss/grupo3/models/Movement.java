package ss.grupo3.models;

public class Movement {

    //x(t+1) = x(t) + sin(tita)*v*(t)
    //y(t+1) = y(t) + cos(tita)*v*(t)
    public static void doMove(Agent agent) {
        double newX = agent.getPosition().getX() + Math.cos(agent.getAngle())*agent.getSpeed();
        double newY = agent.getPosition().getY() + Math.sin(agent.getAngle())*agent.getSpeed();

        agent.setPosition(new Point(newX, newY));
    }
}