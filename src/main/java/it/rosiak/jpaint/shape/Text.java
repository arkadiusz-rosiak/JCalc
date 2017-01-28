package it.rosiak.jpaint.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Text implements Drawable, Colorable {

    private String content;
    private Coordinates bottomLeft;
    private Color color = Color.BLACK;

    public Text(String content, Coordinates bottomLeft) {
        this.content = content;
        this.bottomLeft = bottomLeft;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeText(content, bottomLeft.getX(), bottomLeft.getY());
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
