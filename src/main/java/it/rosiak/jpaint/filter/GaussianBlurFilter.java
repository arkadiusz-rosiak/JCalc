package it.rosiak.jpaint.filter;

import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;

public class GaussianBlurFilter extends Filter {

    private final GaussianBlur blur;

    public GaussianBlurFilter(double radius) {

        if (radius < 0 || radius > 63) {
            throw new IllegalArgumentException("Blur radius must be between 0 and 63");
        }

        this.blur = new GaussianBlur(radius);
    }

    public GaussianBlurFilter() {
        this.blur = new GaussianBlur(10.0);
    }

    @Override
    public Effect getEffect() {
        return blur;
    }

    @Override
    public void mixWith(Filter filter) {
        blur.setInput(filter.getEffect());
    }
}
