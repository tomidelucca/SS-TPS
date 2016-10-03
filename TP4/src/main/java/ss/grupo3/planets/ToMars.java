package ss.grupo3.planets;

import ss.grupo3.file.ReadParticle;
import ss.grupo3.model.Color;
import ss.grupo3.model.Particle;
import ss.grupo3.ovito.OvitoFile;

import java.util.HashMap;
import java.util.Map;

public class ToMars {

    private static double V0 = 8 * 1000;
    private static double ORBITAL_SPEED = 7.12 * 1000;
    private static double DISTANCIE_EARTH2SPACESHIP = 1500 * 1000;//4E7 * 1000;

    private static double HOUR = 3600;
    private static double DAY = 24 * HOUR;
    private static double YEAR = 365 * DAY;

    protected enum Bodies {
      SUN, EARTH, MARS
    };

    public static void simulate() {

        OvitoFile ovito = new OvitoFile("output/output.xyz");

        double tf = 2*YEAR;
        double dt = 10;
        double t = 0.0;
        int day = 0;

        ReadParticle rp = new ReadParticle();
        rp.read("input/input.xyz");

        Map<Bodies, Particle> bodies = new HashMap<Bodies, Particle>(3);

        Particle sun = rp.getSun();
        Particle earth = rp.getEarth();
        Particle mars = rp.getMars();

        sun.setPrev_vx(sun.getVx());
        sun.setPrev_vy(sun.getVy());

        earth.setPrev_vx(earth.getVx());
        earth.setPrev_vy(earth.getVy());

        mars.setPrev_vx(mars.getVx());
        mars.setPrev_vy(mars.getVy());

        bodies.put(Bodies.SUN, sun);
        bodies.put(Bodies.EARTH, earth);
        bodies.put(Bodies.MARS, mars);

        while (day < 1.6*365) {

            PlanetAlgorithm.updateVelocities(bodies.get(Bodies.EARTH), bodies.values(), dt);
            PlanetAlgorithm.updateVelocities(bodies.get(Bodies.MARS), bodies.values(), dt);

            PlanetAlgorithm.updatePosition(bodies.get(Bodies.EARTH), dt);
            PlanetAlgorithm.updatePosition(bodies.get(Bodies.MARS), dt);

            if(Math.abs(t - 86400.0) < 0.1) {
                day++;
                t = 0.0;
            }

            t += dt;
        }

        Particle spaceship = new Particle(0,0,0,0, 100, 2E5, new Color(1,1,1));

        double angleToSun = angleSun(bodies.get(Bodies.EARTH));

        spaceship.setX(bodies.get(Bodies.EARTH).getX() + bodies.get(Bodies.EARTH).getRadius()*Math.cos(angleToSun) + DISTANCIE_EARTH2SPACESHIP*Math.cos(angleToSun));
        spaceship.setY(bodies.get(Bodies.EARTH).getY() + bodies.get(Bodies.EARTH).getRadius()*Math.sin(angleToSun) + DISTANCIE_EARTH2SPACESHIP*Math.sin(angleToSun));

        double angleToMars = angleToSun;

        System.out.println(V0*Math.sin(Math.PI/2+angleToMars));
        System.out.println(ORBITAL_SPEED*Math.sin(Math.PI/2+angleToSun));
        System.out.println(bodies.get(Bodies.EARTH).getPrev_vx());

        spaceship.setPrev_vx(V0*Math.cos(Math.PI/2+angleToMars) + ORBITAL_SPEED*Math.cos(Math.PI/2+angleToSun) + bodies.get(Bodies.EARTH).getPrev_vx());
        spaceship.setPrev_vy(V0*Math.sin(Math.PI/2+angleToMars) + ORBITAL_SPEED*Math.sin(Math.PI/2+angleToSun) + bodies.get(Bodies.EARTH).getPrev_vy());
        
//        spaceship.setPrev_vx((V0 + ORBITAL_SPEED)*Math.cos(Math.PI/2 - angleToSun) + bodies.get(Bodies.EARTH).getPrev_vx());
//        spaceship.setPrev_vy((V0 + ORBITAL_SPEED)*Math.sin(Math.PI/2 - angleToSun) + bodies.get(Bodies.EARTH).getPrev_vy());

        //spaceship.setPrev_vx(bodies.get(Bodies.EARTH).getPrev_vx());
        //spaceship.setPrev_vy(bodies.get(Bodies.EARTH).getPrev_vy());

        Particle[] printParticle = new Particle[4];

        printParticle[0] = bodies.get(Bodies.SUN);
        printParticle[1] = bodies.get(Bodies.EARTH);
        printParticle[2] = bodies.get(Bodies.MARS);
        printParticle[3] = spaceship;

        ovito.write(printParticle);

        while (tf > 0.0) {

            PlanetAlgorithm.updateVelocities(spaceship, bodies.values(), dt);
            PlanetAlgorithm.updateVelocities(bodies.get(Bodies.EARTH), bodies.values(), dt);
            PlanetAlgorithm.updateVelocities(bodies.get(Bodies.MARS), bodies.values(), dt);

            PlanetAlgorithm.updatePosition(spaceship, dt);
            PlanetAlgorithm.updatePosition(bodies.get(Bodies.EARTH), dt);
            PlanetAlgorithm.updatePosition(bodies.get(Bodies.MARS), dt);

            if(Math.abs(t - 86400.0) < 0.1){
                ovito.write(printParticle);
                day++;
                System.out.println("day: " + day);
                t = 0.0;
            }

            tf -= dt;
            t += dt;
        }

        ovito.closeFile();
    }

    private static double angleSun(Particle spaceship) {
        return Math.atan2(spaceship.getY(), spaceship.getX());
    }

       /*public static void simulate() {

        OvitoFile ovito = new OvitoFile("output/output.xyz");

        double tf = 2*YEAR;
        double dt = 100;
        double t = 0.0;
        int day = 0;

        ReadParticle rp = new ReadParticle();
        rp.read("input/input.xyz");

        Map<Bodies, Particle> bodies = new HashMap<Bodies, Particle>(3);

        Particle sun = rp.getSun();
        Particle earth = rp.getEarth();
        Particle mars = rp.getMars();

        sun.setPrev_vx(sun.getVx());
        sun.setPrev_vy(sun.getVy());
           sun.name = "Sun";

        earth.setPrev_vx(earth.getVx());
        earth.setPrev_vy(earth.getVy());
           earth.name = "Earth";

        mars.setPrev_vx(mars.getVx());
        mars.setPrev_vy(mars.getVy());
           mars.name = "Mars";

        bodies.put(Bodies.SUN, sun);
        bodies.put(Bodies.EARTH, earth);
        bodies.put(Bodies.MARS, mars);

        Particle spaceship = new Particle(0,0,0,0, 100, 2E5, new Color(1,1,1));

        double[] forces;

        double angleToSun = angleSun(bodies.get(Bodies.EARTH));

        spaceship.setX(bodies.get(Bodies.EARTH).getX() + bodies.get(Bodies.EARTH).getRadius()*Math.cos(angleToSun) + DISTANCIE_EARTH2SPACESHIP*Math.cos(angleToSun));
        spaceship.setY(bodies.get(Bodies.EARTH).getY() + bodies.get(Bodies.EARTH).getRadius()*Math.sin(angleToSun) + DISTANCIE_EARTH2SPACESHIP*Math.sin(angleToSun));

        spaceship.setPrev_vx(V0*Math.sin(angleToSun) + ORBITAL_SPEED*Math.sin(angleToSun) + bodies.get(Bodies.EARTH).getPrev_vx());
        spaceship.setPrev_vy(V0*Math.cos(angleToSun) + ORBITAL_SPEED*Math.sin(angleToSun) + bodies.get(Bodies.EARTH).getPrev_vy());

        Particle[] printParticle = new Particle[4];

        printParticle[0] = bodies.get(Bodies.SUN);
        printParticle[1] = bodies.get(Bodies.EARTH);
        printParticle[2] = bodies.get(Bodies.MARS);
        printParticle[3] = spaceship;

        ovito.write(printParticle);

        while (tf > 0.0) {

            PlanetAlgorithm.updateVelocities(spaceship, bodies.values(), dt);
            PlanetAlgorithm.updateVelocities(bodies.get(Bodies.EARTH), bodies.values(), dt);
            PlanetAlgorithm.updateVelocities(bodies.get(Bodies.MARS), bodies.values(), dt);

            PlanetAlgorithm.updatePosition(spaceship, dt);
            PlanetAlgorithm.updatePosition(bodies.get(Bodies.EARTH), dt);
            PlanetAlgorithm.updatePosition(bodies.get(Bodies.MARS), dt);

            if(Math.abs(t - 86400.0) < 0.1){
                ovito.write(printParticle);
                day++;
                System.out.println("day: " + day);
                t = 0.0;
            }

            tf -= dt;
            t += dt;
        }

        ovito.closeFile();
    }*/
}
