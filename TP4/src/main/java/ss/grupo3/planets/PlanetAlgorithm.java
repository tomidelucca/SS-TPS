package ss.grupo3.planets;

import ss.grupo3.model.Particle;

import java.util.Collection;

public class PlanetAlgorithm {

    public static double[] sumForces(Particle p, Collection<Particle> bodies) {

        double forcesX = 0.0;
        double forcesY = 0.0;

        for(Particle otherParticle: bodies) {
            if(!otherParticle.equals(p)) {
                double[] force = gravityForce(p, otherParticle);
                forcesX += force[0];
                forcesY += force[1];
            }
        }

        double[] rF = {forcesX, forcesY};

        return rF;
    }

    private static double[] gravityForce(Particle p1, Particle p2) {

        double G = 6.693E-11;

        double rij = Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
        double ei = (p2.getX() - p1.getX())/rij;
        double ej = (p2.getY() - p1.getY())/rij;

        double Fi = G*((p1.getMass()*p2.getMass())/Math.pow(rij, 2))*ei;
        double Fj = G*((p1.getMass()*p2.getMass())/Math.pow(rij, 2))*ej;

        double[] resultForce = {Fi, Fj};

        return resultForce;
    }

    public static void updateVelocities(Particle p, Collection<Particle> bodies, double dt) {

        double[] forces = sumForces(p, bodies);

        // t - dy/2

        p.setPrev_vx(p.getPrev_vx() + ((dt/p.getMass()) * forces[0]));
        p.setPrev_vy(p.getPrev_vy() + ((dt/p.getMass()) * forces[1]));

        // t + dt/2
    }

    public static void updatePosition(Particle p, double dt) {
        p.setX(p.getX() + dt*p.getPrev_vx());
        p.setY(p.getY() + dt*p.getPrev_vy());
    }

}
