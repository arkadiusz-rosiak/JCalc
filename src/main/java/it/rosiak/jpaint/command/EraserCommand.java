package it.rosiak.jpaint.command;

import it.rosiak.jpaint.CalcPresenter;
import it.rosiak.jpaint.shape.Drawable;

public class EraserCommand extends DrawCommand {
    public EraserCommand(Drawable drawable, CalcPresenter presenter) {
        super(drawable, presenter);
    }

    @Override
    public String toString() {
        return "Erased something";
    }
}
