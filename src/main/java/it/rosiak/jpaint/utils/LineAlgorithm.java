package it.rosiak.jpaint.utils;

import it.rosiak.jpaint.shape.Coordinates;

@FunctionalInterface
public interface LineAlgorithm {

    void drawLine(Coordinates from, Coordinates to, double lineThickness);
}
