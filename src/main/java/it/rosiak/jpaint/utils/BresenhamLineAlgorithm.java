package it.rosiak.jpaint.utils;

import it.rosiak.jpaint.shape.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * ULTRA SZYBKI, ALE NIE ZAPEWNIA ANTIALIASINGU
 * https://en.wikipedia.org/wiki/Bresenham's_line_algorithm
 */
public class BresenhamLineAlgorithm implements LineAlgorithm {

    private final Image strokeImage;
    private final GraphicsContext graphicsContext;

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private double lineSize;

    public BresenhamLineAlgorithm(GraphicsContext graphicsContext, Image strokeImage) {
        this.strokeImage = strokeImage;
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void drawLine(Coordinates from, Coordinates to, double lineSize) {
        x1 = (int) from.getX();
        y1 = (int) from.getY();
        x2 = (int) to.getX();
        y2 = (int) to.getY();
        this.lineSize = lineSize;

        performBresenhamAlgorithm();
    }

    private void performBresenhamAlgorithm(){
        int dx = Math.abs(x2 - x1);
        int sx = x1 < x2 ? 1 : -1;

        int dy = -Math.abs(y2 - y1);
        int sy = y1 < y2 ? 1 : -1;

        int delta = dx + dy;
        int delta2;

        while (!isLineCompleted()) {
            fillPixels();

            delta2 = 2 * delta;
            if (delta2 > dy) {
                delta += dy;
                x1 += sx;
            }

            if (delta2 < dx) {
                delta += dx;
                y1 += sy;
            }
        }
    }

    private boolean isLineCompleted(){
        return x1 == x2 && y1 == y2;
    }

    private void fillPixels(){
        double lineSizeHalf = lineSize / 2.0;
        graphicsContext.drawImage(strokeImage, x1 - lineSizeHalf, y1 - lineSizeHalf, lineSize, lineSize);
    }

}
