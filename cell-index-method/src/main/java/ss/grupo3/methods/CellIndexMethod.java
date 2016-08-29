package ss.grupo3.methods;

import ss.grupo3.models.Particle;
import ss.grupo3.models.PeriodicSquareMatrix;
import ss.grupo3.models.SquareMatrix;

import java.util.*;

public class CellIndexMethod {

    public static Map<Particle, Set<Particle>> neighbours(Particle[] particles, double L, int M, double Rc, boolean periodicBorder) {

        SquareMatrix matrix = CellIndexMethod.createAndPopulateMatrix(particles, M, L, periodicBorder);

        Map<Particle, Set<Particle>> result = CellIndexMethod.createAndPopulateEmptyMap(particles);

        double cellSize = L/M;

        for(int i = 0; i<particles.length; i++) {
            Particle p = particles[i];
            int x = (int)(p.getPosition().getX()/cellSize);
            int y = (int)(p.getPosition().getY()/cellSize);

            List<Particle> particlesAtPoint = null;
            Particle tmpParticle = null;

            // x y
            particlesAtPoint = matrix.getElement(x,y);
            for(Particle otherParticle : particlesAtPoint) {
                if(p.distanceToParticle(otherParticle) <= Rc && !p.equals(otherParticle)) {
                    result.get(p).add(otherParticle);
                    result.get(otherParticle).add(p);
                }
            }

            // x+1 y
            particlesAtPoint = matrix.getElement(x+1,y);
            if(particlesAtPoint != null) {
                for (Particle otherParticle : particlesAtPoint) {
                    tmpParticle = new Particle(otherParticle, L * matrix.getScaleFactorX(x + 1), L * matrix.getScaleFactorY(y));
                    if (p.distanceToParticle(tmpParticle) <= Rc) {
                        result.get(p).add(otherParticle);
                        result.get(otherParticle).add(p);
                    }
                }
            }

            // y+1 x
            particlesAtPoint = matrix.getElement(x,y+1);
            if(particlesAtPoint != null) {
                for (Particle otherParticle : particlesAtPoint) {
                    tmpParticle = new Particle(otherParticle, L * matrix.getScaleFactorX(x), L * matrix.getScaleFactorY(y + 1));
                    if (p.distanceToParticle(tmpParticle) <= Rc) {
                        result.get(p).add(otherParticle);
                        result.get(otherParticle).add(p);
                    }
                }
            }

            // x+1 y+1
            particlesAtPoint = matrix.getElement(x+1,y+1);
            if(particlesAtPoint != null) {
                for (Particle otherParticle : particlesAtPoint) {
                    tmpParticle = new Particle(otherParticle, L * matrix.getScaleFactorX(x + 1), L * matrix.getScaleFactorY(y + 1));
                    if (p.distanceToParticle(tmpParticle) <= Rc) {
                        result.get(p).add(otherParticle);
                        result.get(otherParticle).add(p);
                    }
                }
            }

            // x+1 y-1
            particlesAtPoint = matrix.getElement(x+1,y-1);
            if(particlesAtPoint != null) {
                for (Particle otherParticle : particlesAtPoint) {
                    tmpParticle = new Particle(otherParticle, L * matrix.getScaleFactorX(x + 1), L * matrix.getScaleFactorY(y - 1));
                    if (p.distanceToParticle(tmpParticle) <= Rc) {
                        result.get(p).add(otherParticle);
                        result.get(otherParticle).add(p);
                    }
                }
            }
        }

        return result;
    }

    private static Map<Particle, Set<Particle>> createAndPopulateEmptyMap(Particle[] particles) {

        Map<Particle, Set<Particle>> map = new HashMap<>();

        for(int i = 0; i<particles.length; i++) {
            map.put(particles[i], new HashSet<>());
        }

        return map;
    }

    private static SquareMatrix createAndPopulateMatrix(Particle[] particles, int M, double L, boolean periodicBorder) {

        SquareMatrix matrix;

        if(periodicBorder) {
            matrix = new PeriodicSquareMatrix(M);
        } else {
            matrix = new SquareMatrix(M);
        }

        double cellSize = L/M;

        for(int i = 0; i<particles.length; i++) {
            Particle p = particles[i];
            int x = (int)(p.getPosition().getX()/cellSize);
            int y = (int)(p.getPosition().getY()/cellSize);
            matrix.getElement(x,y).add(p);
        }

        return matrix;
    }

}