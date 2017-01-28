package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Mesh implements Drawable, Strokeable {

    private final Coordinates cellSize;
    private final double width;
    private final double height;
    private Paint strokeColor = Color.BLACK;
    private int strokeThickness = 1;

    public Mesh(Coordinates cellSize, double screenWidth, double screenHeight) {
        if (cellSize.getX() <= 0 || cellSize.getY() <= 0 || screenWidth <= 0 || screenHeight <= 0) {
            throw new IllegalArgumentException("All mesh arguments should be positive!");
        }

        this.cellSize = cellSize;
        this.width = screenWidth;
        this.height = screenHeight;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(strokeColor);
        gc.setLineWidth(strokeThickness);
        drawVerticalLines(gc);
        drawHorizontalLines(gc);
    }

    private void drawVerticalLines(GraphicsContext gc) {
        for (double x = 0; x <= width; x += cellSize.getX()) {
            gc.strokeLine(x, 0, x, height);
        }
    }

    private void drawHorizontalLines(GraphicsContext gc) {
        for (double y = 0; y <= height; y += cellSize.getY()) {
            gc.strokeLine(0, y, width, y);
        }
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
