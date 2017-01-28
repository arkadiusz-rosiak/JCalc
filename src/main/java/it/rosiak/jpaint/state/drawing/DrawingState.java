package it.rosiak.jpaint.state.drawing;

import it.rosiak.jpaint.CalcPresenter;
import it.rosiak.jpaint.state.State;
import javafx.scene.paint.Paint;

public abstract class DrawingState extends State {

    private Paint foreground;
    private Paint background;
    private int thickness;

    public DrawingState(CalcPresenter presenter) {
        super(presenter);
        handleThicknessChanged(presenter.getLineThickness());
        handleForegroundChanged(presenter.getForegroundColor());
        handleBackgroundChanged(presenter.getBackgroundColor());
    }

    @Override
    public void handleForegroundChanged(Paint foreground) {
        super.handleForegroundChanged(foreground);
        setForeground(foreground);
    }

    @Override
    public void handleBackgroundChanged(Paint background) {
        super.handleBackgroundChanged(background);
        setBackground(background);
    }

    @Override
    public void handleThicknessChanged(int thickness) {
        super.handleThicknessChanged(thickness);
        setThickness(thickness);
    }

    Paint getForeground() {
        return foreground;
    }

    private void setForeground(Paint foreground) {
        this.foreground = foreground;
    }

    protected Paint getBackground() {
        return background;
    }

    private void setBackground(Paint background) {
        this.background = background;
    }

    protected int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

}
