package ss.grupo3.methods;

import ss.grupo3.models.Agent;
import ss.grupo3.models.Movement;
import ss.grupo3.models.Particle;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class SelfDrivenParticles extends CellIndexMethod {

    /*
     * Mueve los agentes un tiempo delta_t
     * */
    public static void move(Particle[] particleArray, double delta_t) {
        for(int i = 0; i < particleArray.length; i++) {
            Movement.doMove((Agent) particleArray[i]);
        }
    }

    /*
     * Recalcula los angulos de cada uno de los agentes
     * */
    public static void updateAngle(Map<Particle, Set<Particle>> map, double n) {
        for(Map.Entry<Particle,Set<Particle>> entry : map.entrySet()) {
            Particle key = entry.getKey();
            Set<Particle> value = entry.getValue();

            double sum_SIN_angles = Math.sin(((Agent) key).getAngle());
            double sum_COS_angles = Math.cos(((Agent) key).getAngle());
            double AVG_SIN_angle = 0.0;
            double AVG_COS_angle = 0.0;

            double thita_r = 0.0;
            double delta_thita = 0.0;
            Random r = new Random();

            for(Particle p: value) {
                sum_SIN_angles += Math.sin(((Agent) p).getAngle());
                sum_COS_angles += Math.cos(((Agent) p).getAngle());
            }

            AVG_SIN_angle = sum_SIN_angles / (1 + value.size());
            AVG_COS_angle = sum_COS_angles / (1 + value.size());

            thita_r = Math.atan2(AVG_SIN_angle, AVG_COS_angle);
            delta_thita = (-n/2) + ((n/2) - (-n/2)) * r.nextDouble();

            ((Agent) key).setAngle(thita_r + delta_thita);
        }
    }

    /*
     * Actualiza la posicion de los agentes en caso de que se encuentren fuera del cuadrado LxL
     * */
    public static void updatePosition(Map<Particle, Set<Particle>> map, double L) {
        Set<Particle> setParticle = map.keySet();

        for(Particle p: setParticle) {
            if(p.getPosition().getX() < 0)
                p.getPosition().setX( L + p.getPosition().getX());

            if(p.getPosition().getY() < 0)
                p.getPosition().setY( L + p.getPosition().getY());

            if(p.getPosition().getX() > L)
                p.getPosition().setX( L - p.getPosition().getX());

            if(p.getPosition().getY() > L)
                p.getPosition().setY( L - p.getPosition().getY());
        }
    }

    /*
     * Calcula el valor de Va (absolute value of the normalized velocity
     * */
    public static double simulationNormalizedVelocity(Particle[] particleArray) {
        double particleVelocity = ((Agent)particleArray[0]).getSpeed();
        double xVelocityAvg = 0;
        double yVelocityAvg = 0;

        for (int i = 0; i < particleArray.length; i++) {
            xVelocityAvg += Math.cos(((Agent)particleArray[i]).getAngle())*((Agent)particleArray[i]).getSpeed();
            yVelocityAvg += Math.sin(((Agent)particleArray[i]).getAngle())*((Agent)particleArray[i]).getSpeed();
        }

        double absVelocity = Math.sqrt(Math.pow(xVelocityAvg, 2) + Math.pow(yVelocityAvg, 2));
        return absVelocity / (particleArray.length * particleVelocity);
    }
}