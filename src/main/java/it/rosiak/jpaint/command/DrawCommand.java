package it.rosiak.jpaint.command;

import it.rosiak.jpaint.CalcPresenter;
import it.rosiak.jpaint.shape.Drawable;

public class DrawCommand implements Command {

    private final CalcPresenter presenter;
    private final Drawable drawable;

    public DrawCommand(Drawable drawable, CalcPresenter presenter) {
        this.drawable = drawable;
        this.presenter = presenter;
    }

    @Override
    public void undo() {
        presenter.removeDrawableFromCurrentDocument(drawable);
    }

    @Override
    public void redo() {
        presenter.addDrawableToCurrentDocument(drawable);
    }

    @Override
    public void execute() {
        presenter.addDrawableToCurrentDocument(drawable);
        presenter.setTempDrawable(null);
    }

    @Override
    public String toString() {
        return "Drawn " + drawable;
    }
}
