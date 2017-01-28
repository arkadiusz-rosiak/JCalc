package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Line implements Drawable, Strokeable {

    private Coordinates from;
    private Coordinates to;
    private Paint color = Color.BLACK;
    private int thickness = 1;

    public Line(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }

    public Line(Line other) {
        this.from = other.from;
        this.to = other.to;
        color = other.color;
        thickness = other.thickness;
    }

    public void setTo(Coordinates to) {
        this.to = to;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(thickness);
        gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
    }

    @Override
    public void setStroke(Paint p) {
        this.color = p;
    }

    @Override
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
}
