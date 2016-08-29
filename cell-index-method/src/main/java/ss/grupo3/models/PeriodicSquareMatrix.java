package ss.grupo3.models;

import java.util.List;

public class PeriodicSquareMatrix extends SquareMatrix {

    public PeriodicSquareMatrix(int size) {
        super(size);
    }

    @Override
    public List<Particle> getElement(int x, int y) {
        return matrix.get(Math.floorMod(x, size)).get(Math.floorMod(y, size));
    }

    @Override
    public int getScaleFactorX(int x) {
        return Math.floorDiv(x, size);
    }

    @Override
    public int getScaleFactorY(int y) {
        return Math.floorDiv(y, size);
    }
}
