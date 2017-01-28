package it.rosiak.jpaint.state;

import it.rosiak.jpaint.CalcPresenter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;

public abstract class State {

    protected CalcPresenter presenter;
    private boolean isShiftPressed = false;
    private boolean isControlPressed = false;

    public State(CalcPresenter presenter) {
        this.presenter = presenter;
    }

    protected final boolean isShiftEnabled(){
        return isShiftPressed;
    }

    protected final boolean isSnapToGridEnabled(){
        return isControlPressed;
    }

    public void handleKeyPressed(KeyEvent e){
        if(e.getCode() == KeyCode.SHIFT){
            isShiftPressed = true;
        }
        else if(e.getCode() == KeyCode.CONTROL){
            isControlPressed = true;
        }
    }

    public void handleKeyReleased(KeyEvent e){
        if(e.getCode() == KeyCode.SHIFT){
            isShiftPressed = false;
        }
        else if(e.getCode() == KeyCode.CONTROL){
            isControlPressed = false;
        }
    }

    public void handleMousePressed(MouseEvent e){

    }

    public void handleMouseReleased(MouseEvent e){

    }

    public void handleMouseDragged(MouseEvent e){

    }

    public void handleMouseMoved(MouseEvent e){

    }

    public void handleScroll(ScrollEvent e){

    }

    public void handleForegroundChanged(Paint foreground){

    }

    public void handleBackgroundChanged(Paint background){

    }

    public void handleThicknessChanged(int thickness){

    }
}
