package ss.grupo3;

import ss.grupo3.Force.ForceParticles;
import ss.grupo3.Force.ForceWall;
import ss.grupo3.algorithm.LeapFrog;
import ss.grupo3.generator.ParticleGenerator;
import ss.grupo3.method.CellIndexMethod;
import ss.grupo3.model.Particle;
import ss.grupo3.model.Position;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MediosGranulares2 extends MediosGranulares {

    @Override
    public void initialize() {

        setL(3.0);
        setM(19);

        double L = getL();
        double W = getW();
        double D = getD();
        double MASS = getMASS();

        //Genero particulas
        setParticles(ParticleGenerator.generate(L, W, D, MASS));
        //Particulas que estan arriba
        setTopParticle(new ArrayList<Particle>());
        //Particulas ocultas que se agregan si no hay overlap con ninguna de topParticle
        setInvisibleParticle(new ArrayList<Particle>());
        //Particula y sus vecinas
        Map<Particle, Set<Particle>> map;

        List<Wall> walls = new ArrayList<Wall>();
        walls.add(new Wall(new Vector(0, L + 1), new Vector(W, L + 1), Position.UP));
        walls.add(new Wall(new Vector(0, 0), new Vector(0, L + 1), Position.LEFT));
        walls.add(new Wall(new Vector(W, 0), new Vector(W, L + 1), Position.RIGHT));
        walls.add(new Wall(new Vector(0, 1), new Vector(W, 1), Position.DOWN));

        setWalls(walls);

    }

}

