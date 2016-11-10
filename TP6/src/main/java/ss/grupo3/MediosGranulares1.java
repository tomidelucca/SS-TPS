package ss.grupo3;

import ss.grupo3.generator.ParticleGenerator;
import ss.grupo3.model.*;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.ExistingAnnotationsType;

public class MediosGranulares1 extends MediosGranulares {

    @Override
    public void initialize() {

//        setM( 19);

        double L = getL();
        double W = getW();
        double D = getD();
        double T = getT();
        double MASS = getMASS();
        int PARTICLE = getPARTICLE();
        double utb = getUnderTheBox();
        
        //Genero particulas
        List<Particle> particles = ParticleGenerator.generate(L, W, D, T, PARTICLE, utb);
        //Particulas que estan arriba
        setTopParticle(new ArrayList<Particle>());
        //Particulas ocultas que se agregan si no hay overlap con ninguna de topParticle
        setInvisibleParticle(new ArrayList<Particle>());

        List<Wall> walls = new ArrayList<Wall>();
        walls.add(new Wall(new Vector(0, L + utb), new Vector(W, L + utb), Position.UP));
        walls.add(new Wall(new Vector(0, 0), new Vector(0, L + utb), Position.LEFT));
        walls.add(new Wall(new Vector(W, 0), new Vector(W, L + utb), Position.RIGHT));
        walls.add(new Wall(new Vector(0, utb), new Vector((W - D) / 2, utb), Position.DOWN));
        walls.add(new Wall(new Vector(W - (W - D) / 2, utb), new Vector(W, utb), Position.DOWN));
        setWalls(walls);

        //El target para todas las particulas
        setExitTarget(new Vector( W/2, utb - 0.2));
        
        //Particulas fijas en los extremos de la ranura. Necesarias para evitar que rompa el proceso.
        particles.add(new Particle(particles.size(), new Vector((W - D) / 2, utb), new Velocity(0, 0), 0, MASS, null, 0, 0, true));
        particles.add(new Particle(particles.size(), new Vector(W - (W - D) / 2, utb), new Velocity(0, 0), 0, MASS, null, 0, 0, true));

        setParticles(particles);

    }
}
