package it.rosiak.jpaint.shape;

import org.junit.Test;

public class ShapesTests {

    @Test(expected = IllegalArgumentException.class)
    public void meshConstructorShouldFailWhenArgumentsAreLowerOrEqualZero() throws Exception {
        new Mesh(new Coordinates(-20, -20), -20, -20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ellipseConstructorShouldFailWhenRadiusIsNotPositive() throws Exception {
        new Ellipse(new Coordinates(0, 0), -20);
    }

}