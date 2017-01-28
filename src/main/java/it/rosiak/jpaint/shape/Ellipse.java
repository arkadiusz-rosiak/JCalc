package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Ellipse implements Drawable, Fillable, Strokeable {

    private Coordinates center;
    private Coordinates radius;
    private Paint fillColor = Color.TRANSPARENT;
    private Paint strokeColor = Color.BLACK;
    private int strokeThickness = 1;

    public Ellipse(Coordinates center, Coordinates radius) {
        if (radius.getX() <= 0 || radius.getY() <= 0) {
            throw new IllegalArgumentException("Ellipse radius should be positive!");
        }
        this.center = center;
        this.radius = radius;
    }

    public Ellipse(Coordinates center, int radius) {
        this(center, new Coordinates(radius, radius));
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.setLineWidth(strokeThickness);
        gc.fillOval(center.getX(), center.getY(), radius.getX(), radius.getY());
        gc.strokeOval(center.getX(), center.getY(), radius.getX(), radius.getY());
    }

    @Override
    public void setFill(Paint p) {
        this.fillColor = p;
    }

    @Override
    public void setStroke(Paint p) {
        this.strokeColor = p;
    }

    @Override
    public void setThickness(int thickness) {
        this.strokeThickness = thickness;
    }
}
