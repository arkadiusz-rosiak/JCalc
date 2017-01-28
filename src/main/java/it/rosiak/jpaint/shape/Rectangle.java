package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class Rectangle implements Drawable, Fillable, Strokeable {

    private final Coordinates topLeft;
    private Coordinates bottomRight;
    private Paint fillColor = Color.TRANSPARENT;
    private Paint strokeColor = Color.BLACK;
    private int strokeThickness = 1;

    public Rectangle(Coordinates topLeft, Coordinates bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Rectangle(Rectangle other){
        this.topLeft = other.topLeft;
        this.bottomRight = other.bottomRight;
        fillColor = other.fillColor;
        strokeColor = other.strokeColor;
        strokeThickness = other.strokeThickness;
    }

    public Coordinates getTopLeft(){
        return topLeft;
    }

    public void setBottomRight(Coordinates bottomRight){
        this.bottomRight = bottomRight;
    }

    @Override
    public void draw(GraphicsContext gc) {
        drawFillRectangle(gc);
        drawStrokeRectangle(gc);
    }

    private void drawFillRectangle(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.fillPolygon(new double[] { topLeft.getX(), topLeft.getX(), bottomRight.getX(), bottomRight.getX()},
                new double[] { topLeft.getY(), bottomRight.getY(), bottomRight.getY(), topLeft.getY()}, 4);
    }

    private void drawStrokeRectangle(GraphicsContext gc){
        gc.setStroke(strokeColor);
        gc.setLineWidth(strokeThickness);
        gc.strokePolygon(new double[] { topLeft.getX(), topLeft.getX(), bottomRight.getX(), bottomRight.getX()},
                new double[] { topLeft.getY(), bottomRight.getY(), bottomRight.getY(), topLeft.getY()}, 4);
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
