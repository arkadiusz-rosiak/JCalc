package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements Drawable, Strokeable, Fillable{

    private final List<Double> xPoints = new ArrayList<>();
    private final List<Double> yPoints = new ArrayList<>();
    private Paint fillColor = Color.TRANSPARENT;
    private Paint strokeColor = Color.BLACK;
    private int strokeThickness = 1;

    public Polygon(List<Coordinates> vertices) {
        if(vertices.size() < 2){
            throw new IllegalArgumentException("Vertices list should contain at least 2 coordinates!");
        }

        vertices.forEach(this::addVertex);
    }

    public void addVertex(Coordinates vertex){
        xPoints.add(vertex.getX());
        yPoints.add(vertex.getY());
    }

    @Override
    public void draw(GraphicsContext gc) {
        drawFillPolygon(gc);
        drawStrokePolygon(gc);
    }

    private void drawStrokePolygon(GraphicsContext gc) {
        gc.setLineWidth(strokeThickness);
        gc.setStroke(strokeColor);
        gc.strokePolygon(getXPointsAsArray(), getYPointsAsArray(), getVerticesCount());
    }

    private void drawFillPolygon(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.fillPolygon(getXPointsAsArray(), getYPointsAsArray(), getVerticesCount());
    }

    private double[] getXPointsAsArray() {
        return xPoints.stream().mapToDouble(d -> d).toArray();
    }

    private double[] getYPointsAsArray() {
        return yPoints.stream().mapToDouble(d -> d).toArray();
    }

    private int getVerticesCount(){
        return Math.min(xPoints.size(), yPoints.size());
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