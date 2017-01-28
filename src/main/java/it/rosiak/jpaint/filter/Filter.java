package it.rosiak.jpaint.filter;

import javafx.scene.effect.Effect;

public abstract class Filter {

    public static final Filter NULL = new Filter() {
        @Override
        public Effect getEffect() {
            return null;
        }

        @Override
        public void mixWith(Filter filter) {
            // OBJECT NULL
        }
    };

    public abstract Effect getEffect();

    public abstract void mixWith(Filter filter);
}
