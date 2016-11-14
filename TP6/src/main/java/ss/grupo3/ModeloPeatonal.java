package ss.grupo3;

import ss.grupo3.Force.ForceParticles;
import ss.grupo3.Force.ForceWall;
import ss.grupo3.algorithm.LeapFrog;
import ss.grupo3.generator.ParticleGenerator;
import ss.grupo3.method.CellIndexMethod;
import ss.grupo3.model.*;
import ss.grupo3.simulation.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModeloPeatonal extends Simulation {

	private static final int PARTICLES = 160;
	
    // Datos
    private static final double MASS = 1E-2;
    private static final double KN = 1.2E5;
    private static final double KT = 2 * KN;

    private static double L = 20;
    private static double W = 20;
    private static double D = 1.2;
    private static double underTheBox = 5; //metros debajo de la caja
    
    //CellIndexMethod
    private static int M = 30;
    private static double RC = 0.65;
    private static boolean PERIODIC_BORDER = false;

    //Tiempos
    private static double SIMULATION_TIME = 1000;
    private static double SIMULATION_DT = 1E-4;

    private List<Particle> particles = null;
    private List<Wall> walls = null;
    private List<Particle> invisibleParticle = null;
    private List<Particle> topParticle = null;

    private double timeSimulation = SIMULATION_TIME;

    private static double A = 2000; // N
    private static double B = 0.08; // m
    private static double T = 0.5; // seconds
    private Vector exitTarget = null;

    @Override
    public void initialize() {

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

    @Override
    public Boolean simulate() {

        initialize();

        //Particula y sus vecinas
        Map<Particle, Set<Particle>> map;

        Particle p;
        Set<Particle> setp;
        double[] force;
        double[] sumForce = {0, 0};

        startSimulation();

        while (timeSimulation > 0 && !shouldStopSimulation()) {

            //Chequeo si una particula ahora es visible
            for (Particle part : invisibleParticle)
            	particles.remove(part);

            topParticle.clear();
            invisibleParticle.clear();

            map = CellIndexMethod.neighbours(particles, L + underTheBox, M, RC, PERIODIC_BORDER);

            for (Map.Entry<Particle, Set<Particle>> entry : map.entrySet()) {
                p = entry.getKey();
                setp = entry.getValue();

                //Leap Frog Algorithm part-1
                p.setPrevPosition(p.getPosition());
                p.setPrevVelocity(p.getVelocity());

                p.setPosition(p.getNextPosition());
                p.setPrevVelocity(p.getNextVelocity());

                p.setVelocity(p.getNextVelocity());

                if (!p.isFixed() && p.isVisible()) {
                    force = ForceParticles.total(p, setp, KN, KT, A, B, SIMULATION_DT, getExitTarget(), underTheBox);
                    sumForce[0] += force[0]; //x
                    sumForce[1] += force[1]; //y

                    force = ForceWall.total(p, walls, KN, KT, A, B);
                    sumForce[0] += force[0]; //x
                    sumForce[1] += force[1]; //y

                    //Leap Frog Algorithm part-2
                    p.setNextVelocity(LeapFrog.velocity(p, sumForce, SIMULATION_DT));
                    p.setNextPosition(LeapFrog.position(p, SIMULATION_DT));

                    //reseteo particula
                    if (p.getPosition().getY() < 0) {
                        p.setVisible(false);
                    }

                }
                //listo particulas que no se muestran
                if (!p.isVisible())
                    invisibleParticle.add(p);

                sumForce[0] = 0;
                sumForce[1] = 0;
            }

            timeSimulation -= SIMULATION_DT;

            updateObservers();
        }

        finishSimulation();

        return Boolean.TRUE;
    }

    private static boolean validParticle(Particle p, List<Particle> setp) {
        for (Particle sp : setp) {
            if (!validParticle(p, sp))
                return false;
        }
        return true;
    }

    private static boolean validParticle(Particle p, Particle other) {
        return overlap(other, p) < 0;
    }

    private static double overlap(Particle p, Particle other) {
        return p.getRadius() + other.getRadius() - p.getPosition().rest(other.getPosition()).mod();
    }

    @Override
    public void reset() {

    }

    public double getTimeSimulation() {
        return timeSimulation;
    }

    public void setTimeSimulation(double timeSimulation) {
        this.timeSimulation = timeSimulation;
    }

    public List<Particle> getTopParticle() {
        return topParticle;
    }

    public void setTopParticle(List<Particle> topParticle) {
        this.topParticle = topParticle;
    }

    public List<Particle> getInvisibleParticle() {
        return invisibleParticle;
    }

    public void setInvisibleParticle(List<Particle> invisibleParticle) {
        this.invisibleParticle = invisibleParticle;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void setParticles(List<Particle> particles) {
        this.particles = particles;
    }

    public static double getSimulationDt() {
        return SIMULATION_DT;
    }

    public static double getL() {
        return L;
    }

    public static double getW() {
        return W;
    }

    public static double getD() {
        return D;
    }

    public static double getMASS() {
        return MASS;
    }

    public static void setL(double l) {
        L = l;
    }

    public static void setM(int m) {
        M = m;
    }
    
    public static int getPARTICLE() {
        return PARTICLES;
    }
    
    public static double getT() {
        return T;
    }
    
	public void setExitTarget(Vector vector) {
		this.exitTarget = vector;
	}
	
	public Vector getExitTarget() {
		return this.exitTarget;
	}

	public static double getUnderTheBox() {
		return underTheBox;
	}
}
