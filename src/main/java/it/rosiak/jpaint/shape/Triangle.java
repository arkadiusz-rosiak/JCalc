package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Triangle implements Drawable, Strokeable, Fillable {

    private Coordinates first;
    private Coordinates second;
    private Coordinates third;

    private Paint fillColor = Color.TRANSPARENT;
    private Paint strokeColor = Color.BLACK;
    private int strokeThickness = 1;

    public Triangle(Coordinates first, Coordinates second, Coordinates third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public void draw(GraphicsContext gc) {
        drawFillTriangle(gc);
        drawStrokeTriangle(gc);
    }

    private void drawFillTriangle(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.fillPolygon(new double[] { first.getX(), second.getX(), third.getX()},
                new double[] { first.getY(), second.getY(), third.getY()}, 3);
    }

    private void drawStrokeTriangle(GraphicsContext gc) {
        gc.setStroke(strokeColor);
        gc.setLineWidth(strokeThickness);
        gc.strokePolygon(new double[] { first.getX(), second.getX(), third.getX()},
                new double[] { first.getY(), second.getY(), third.getY()}, 3);
    }

    @Override
    public void setFill(Paint fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public void setStroke(Paint strokeColor) {
        this.strokeColor = strokeColor;
    }

    @Override
    public void setThickness(int thickness) {
        this.strokeThickness = thickness;
    }
}
