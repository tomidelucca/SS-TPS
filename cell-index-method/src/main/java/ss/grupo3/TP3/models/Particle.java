package ss.grupo3.TP3.models;

public class Particle {

    private double radius;
    private Point position;

    public Particle(double x, double y, double radius) {
        this.position = new Point(x, y);
        this.radius = radius;
    }

    public Particle(Particle p, double moveFactorX, double moveFactorY) {
        Point pos = p.getPosition();
        this.position = new Point(pos.getX()+moveFactorX, pos.getY()+moveFactorY);
        this.radius = p.getRadius();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double distanceToParticle(Particle otherParticle) {
        return Particle.distanceBetweenParticles(this, otherParticle);
    }

    public static double distanceBetweenParticles(Particle p1, Particle p2) {
        double distance = Point.distanceBetweenPoints(p1.getPosition(), p2.getPosition()) - p1.radius - p2.radius;
        return distance>0?distance:0;
    }

    @Override
    public String toString() {
        return "P {" +
                "r=" + radius +
                ", p=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        if (Double.compare(particle.radius, radius) != 0) return false;
        return position.equals(particle.position);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(radius);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + position.hashCode();
        return result;
    }
}