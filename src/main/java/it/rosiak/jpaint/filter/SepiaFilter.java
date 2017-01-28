package it.rosiak.jpaint.filter;

import javafx.scene.effect.Effect;
import javafx.scene.effect.SepiaTone;

public class SepiaFilter extends Filter {

    private final SepiaTone sepiaTone;

    public SepiaFilter(double level) {

        if(level < 0 || level > 1){
            throw new IllegalArgumentException("Sepia level must be between 0 and 1");
        }

        this.sepiaTone = new SepiaTone(level);
    }

    public SepiaFilter() {
        this(1.0);
    }

    @Override
    public Effect getEffect() {
        return sepiaTone;
    }

    @Override
    public void mixWith(Filter filter) {
        sepiaTone.setInput(filter.getEffect());
    }
}
