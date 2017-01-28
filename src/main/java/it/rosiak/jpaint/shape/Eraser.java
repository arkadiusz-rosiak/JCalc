package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.List;

public class Eraser implements Drawable, Strokeable {

    private double size = 5;

    private final List<Coordinates> points;

    public Eraser(List<Coordinates> points) {
        this.points = points;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double halfSize = size / 2;
        for(Coordinates point : points){
            gc.clearRect(point.getX() - halfSize, point.getY() - halfSize , size, size);
        }
    }

    @Override
    public void setStroke(Paint strokeColor) {
        throw new UnsupportedOperationException("Cannot set color to eraser");
    }

    @Override
    public void setThickness(int thickness) {
        this.size = thickness;
    }
}
