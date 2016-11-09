package ss.grupo3;

import ss.grupo3.Force.ForceParticles;
import ss.grupo3.Force.ForceWall;
import ss.grupo3.algorithm.LeapFrog;
import ss.grupo3.method.CellIndexMethod;
import ss.grupo3.model.Particle;
import ss.grupo3.model.Vector;
import ss.grupo3.model.Wall;
import ss.grupo3.simulation.Simulation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class MediosGranulares extends Simulation {

	private static final int PARTICLES = 10;
	
    // Datos
    private static final double MASS = 1E-2;
    private static final double KN = 1.2E5;
    private static final double KT = 2 * KN;

    private static double L = 20;
    private static double W = 20;
    private static double D = 1.2;
    private static double underTheBox = 5;
    //CellIndexMethod
    private static int M = 29;
    private static double RC = 0.2;
    private static boolean PERIODIC_BORDER = false;

    //Tiempos
    private static double SIMULATION_TIME = 40;
    private static double SIMULATION_DT = 5E-5;
    private static double ANIMATION_DT = 1E-2;

    private List<Particle> particles = null;
    private List<Wall> walls = null;
    private List<Particle> invisibleParticle = null;
    private List<Particle> topParticle = null;

    private double timeSimulation = SIMULATION_TIME;
    private double timeAnimation = ANIMATION_DT;

    private static double A = 2E4; //N
    private static double B = 8E-2; //m
    private static double T = 0.5; //Sseconds
    private Vector exitTarget = null;
   
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
                if (validParticle(part, topParticle)) {
                    part.setVisible(true);
                    topParticle.add(part);
                }

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
                        p.reset(L + underTheBox, W);
                        p.setVisible(false);
                    }

                    //particulas que estan arriba
                    if (p.getPosition().getY() > (L + underTheBox - D / 2))
                        topParticle.add(p);

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

    public double getTimeAnimation() {
        return timeAnimation;
    }

    public void setTimeAnimation(double timeAnimation) {
        this.timeAnimation = timeAnimation;
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
