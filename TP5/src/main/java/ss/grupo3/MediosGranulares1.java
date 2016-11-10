package ss.grupo3;

import ss.grupo3.generator.ParticleGenerator;
import ss.grupo3.model.*;

import java.util.ArrayList;
import java.util.List;

public class MediosGranulares1 extends MediosGranulares {

    @Override
    public void initialize() {

//        setL(3.0);
        setM(19);

        double L = getL();
        double W = getW();
        double D = getD();
        double MASS = getMASS();

        //Genero particulas
        List<Particle> particles = ParticleGenerator.generate(L, W, D, MASS);
        //Particulas que estan arriba
        setTopParticle(new ArrayList<Particle>());
        //Particulas ocultas que se agregan si no hay overlap con ninguna de topParticle
        setInvisibleParticle(new ArrayList<Particle>());

        List<Wall> walls = new ArrayList<Wall>();
        walls.add(new Wall(new Vector(0, L + 1), new Vector(W, L + 1), Position.UP));
        walls.add(new Wall(new Vector(0, 0), new Vector(0, L + 1), Position.LEFT));
        walls.add(new Wall(new Vector(W, 0), new Vector(W, L + 1), Position.RIGHT));
        walls.add(new Wall(new Vector(0, 1), new Vector((W - D) / 2, 1), Position.DOWN));
        walls.add(new Wall(new Vector(W - (W - D) / 2, 1), new Vector(W, 1), Position.DOWN));
        setWalls(walls);

        //Particulas fijas en los extremos de la ranura. Necesarias para evitar que rompa el proceso.
        particles.add(new Particle(particles.size(), new Vector((W - D) / 2, 1), new Velocity(0, 0), 0, MASS, true));
        particles.add(new Particle(particles.size(), new Vector(W - (W - D) / 2, 1), new Velocity(0, 0), 0, MASS, true));

        setParticles(particles);

    }
}
