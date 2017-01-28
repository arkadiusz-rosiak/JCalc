package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Curve implements Drawable, Strokeable {

    private final List<Coordinates> vertices = new ArrayList<>();
    private Paint strokeColor = Color.BLACK;
    private int thickness = 1;

    public Curve() {
        // default contructor
    }

    public Curve(Curve other) {
        this.vertices.addAll(other.vertices);
        this.strokeColor = other.strokeColor;
        this.thickness = other.thickness;
    }

    public void addVertex(Coordinates vertex) {
        vertices.add(vertex);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(strokeColor);
        gc.setLineWidth(thickness);

        ListIterator<Coordinates> it = vertices.listIterator();
        if (it.hasNext()) {
            Coordinates from = it.next();

            while (it.hasNext()) {
                Coordinates to = it.next();
                drawSingleLine(gc, from, to);
                from = to;
            }
        }
    }

    private void drawSingleLine(GraphicsContext gc, Coordinates from, Coordinates to) {
        gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
    }

    @Override
    public void setStroke(Paint strokeColor) {
        this.strokeColor = strokeColor;
    }

    @Override
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    @Override
    public String toString() {
        return "curve";
    }
}
