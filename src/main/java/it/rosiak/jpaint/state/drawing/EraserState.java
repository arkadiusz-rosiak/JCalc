package it.rosiak.jpaint.state.drawing;

import it.rosiak.jpaint.CalcPresenter;
import it.rosiak.jpaint.command.Command;
import it.rosiak.jpaint.command.EraserCommand;
import it.rosiak.jpaint.shape.Coordinates;
import it.rosiak.jpaint.shape.Eraser;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class EraserState extends DrawingState {

    private Eraser eraser;
    private List<Coordinates> points;

    public EraserState(CalcPresenter presenter) {
        super(presenter);
    }

    @Override
    public void handleMousePressed(MouseEvent e) {
        super.handleMousePressed(e);

        points = new ArrayList<>();
        addCurrentPointToList(e);

        eraser = new Eraser(points);
        eraser.setThickness(getThickness());
    }

    @Override
    public void handleMouseDragged(MouseEvent e) {
        super.handleMouseDragged(e);

        addCurrentPointToList(e);
        presenter.setTempDrawable(eraser);
    }

    @Override
    public void handleMouseReleased(MouseEvent e) {
        super.handleMouseReleased(e);

        if(eraser != null){
            Command cmd = new EraserCommand(eraser, presenter);
            presenter.getCommandStack().addCommandToExecute(cmd);
            eraser = null;
            points = null;
        }
    }

    private void addCurrentPointToList(MouseEvent e) {
        points.add(new Coordinates(e.getX(), e.getY()));
    }
}
