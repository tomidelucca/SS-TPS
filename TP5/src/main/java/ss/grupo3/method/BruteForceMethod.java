package ss.grupo3.method;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ss.grupo3.model.Particle;

public class BruteForceMethod {

    public static Map<Particle, Set<Particle>> neighbours(Particle[] particles, double Rc) {

        Map<Particle, Set<Particle>> result = BruteForceMethod.createAndPopulateEmptyMap(particles);

        for(int i = 0; i<particles.length; i++) {
            Particle p = particles[i];
            for(int j = 0; j<particles.length; j++) {
                Particle otherParticle = particles[j];
                if (distance(p, otherParticle) <= Rc && !p.equals(otherParticle)) {
                    result.get(p).add(otherParticle);
                    result.get(otherParticle).add(p);
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
    
    private static double distance(Particle p1, Particle p2) {
		return Math.hypot(p1.getPosition().getX() - p2.getPosition().getX(), p1.getPosition().getY() - p2.getPosition().getY()) 
				- p1.getRadius() - p2.getRadius();
	}
}