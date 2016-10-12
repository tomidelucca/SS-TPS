package ss.grupo3.method;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ss.grupo3.model.Particle;

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
                if(distance(p, otherParticle) <= Rc && !p.equals(otherParticle)) {
                    result.get(p).add(otherParticle);
                    result.get(otherParticle).add(p);
                }
            }

            // x+1 y
            particlesAtPoint = matrix.getElement(x+1,y);
            if(particlesAtPoint != null) {
                for (Particle otherParticle : particlesAtPoint) {
                    tmpParticle = new Particle(otherParticle, L * matrix.getScaleFactorX(x + 1), L * matrix.getScaleFactorY(y));
                    if (distance(p, tmpParticle) <= Rc) {
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
                    if (distance(p, tmpParticle) <= Rc) {
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
                    if (distance(p, tmpParticle) <= Rc) {
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
                    if (distance(p, tmpParticle)  <= Rc) {
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

    private static double distance(Particle p1, Particle p2) {
		return Math.hypot(p1.getPosition().getX() - p2.getPosition().getX(), p1.getPosition().getY() - p2.getPosition().getY()) 
				- p1.getRadius() - p2.getRadius();
	}
}