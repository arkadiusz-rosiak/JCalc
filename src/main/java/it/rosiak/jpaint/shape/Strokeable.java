package it.rosiak.jpaint.shape;

import javafx.scene.paint.Paint;

interface Strokeable {
    void setStroke(Paint strokeColor);
    void setThickness(int thickness);
}
