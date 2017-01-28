package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface Drawable {
    void draw(GraphicsContext gc);
}
