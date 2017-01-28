package it.rosiak.jpaint.shape;

import javafx.scene.paint.Paint;

@FunctionalInterface
interface Fillable {
    void setFill(Paint fillColor);
}
