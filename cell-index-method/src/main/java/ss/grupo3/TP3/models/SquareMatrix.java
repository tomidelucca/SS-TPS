package ss.grupo3.TP3.models;

import java.util.ArrayList;
import java.util.List;

public class SquareMatrix {

    protected List<List<List<Particle>>> matrix;
    protected int size;

    public SquareMatrix(int size) {
        this.size = size;
        matrix = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            matrix.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                matrix.get(i).add(j, new ArrayList<>());
            }
        }
    }

    public List<Particle> getElement(int x, int y) {
        if(x>=size || y>=size || y<0 || x<0) return null;
        return matrix.get(x).get(y);
    }

    public int getScaleFactorX(int x) {
        return 0;
    }

    public int getScaleFactorY(int y) {
        return 0;
    }

    @Override
    public String toString() {
        return "SquareMatrix{" +
                "matrix=" + matrix +
                ", size=" + size +
                '}';
    }
}