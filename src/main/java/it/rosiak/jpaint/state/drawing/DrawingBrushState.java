package it.rosiak.jpaint.state.drawing;

import it.rosiak.jpaint.CalcPresenter;
import it.rosiak.jpaint.command.Command;
import it.rosiak.jpaint.command.DrawCommand;
import it.rosiak.jpaint.shape.BrushCurve;
import it.rosiak.jpaint.shape.Coordinates;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class DrawingBrushState extends DrawingState {

    private BrushCurve drawingCurve;

    public DrawingBrushState(CalcPresenter presenter) {
        super(presenter);
    }

    @Override
    public void handleMousePressed(MouseEvent e) {
        super.handleMousePressed(e);
        Image brush = new Image(getClass().getResourceAsStream("/brush.png"));
        drawingCurve = new BrushCurve(brush, 1, 1);
        drawingCurve.setThickness(getThickness());
        drawingCurve.setStroke(getForeground());
        drawingCurve.addVertex(new Coordinates(e.getX(), e.getY()));
        presenter.setTempDrawable(drawingCurve);
    }

    @Override
    public void handleMouseDragged(MouseEvent e) {
        super.handleMouseDragged(e);
        drawingCurve.addVertex(new Coordinates(e.getX(), e.getY()));
        presenter.setTempDrawable(drawingCurve);
    }

    @Override
    public void handleMouseReleased(MouseEvent e) {
        super.handleMouseReleased(e);

        if(drawingCurve != null){
            drawingCurve.addVertex(new Coordinates(e.getX(), e.getY()));
            Command cmd = new DrawCommand(drawingCurve, presenter);
            presenter.getCommandStack().addCommandToExecute(cmd);
            drawingCurve = null;
        }
    }
}
