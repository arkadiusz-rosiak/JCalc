package it.rosiak.jpaint;

import it.rosiak.jpaint.command.CommandStack;
import it.rosiak.jpaint.document.Document;
import it.rosiak.jpaint.shape.Drawable;
import it.rosiak.jpaint.state.State;
import it.rosiak.jpaint.state.drawing.DrawingBrushState;
import it.rosiak.jpaint.state.drawing.EraserState;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.apache.logging.log4j.LogManager;
import pl.rosiakit.InlineInputCalculator;
import pl.rosiakit.ocr.ImageRecognizer;
import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.utils.MyImageUtils;


public class CalcPresenter {

    private final CalculatorView view;
    private ImageRecognizer recognizer = new ImageRecognizer();
    private Document currentDocument = new Document();
    private State state;
    private Drawable tempDrawable;
    private CommandStack commandStack = new CommandStack();
    private InlineInputCalculator calc = new InlineInputCalculator();

    CalcPresenter(CalculatorView view) {
        this.view = view;
        this.state = new DrawingBrushState(this);
    }

    public Canvas getCanvas(){
        return view.getCanvas();
    }

    public void addDrawableToCurrentDocument(Drawable drawable) {
        currentDocument.addDrawable(drawable);
    }

    public void removeDrawableFromCurrentDocument(Drawable drawable) {
        currentDocument.removeDrawable(drawable);
    }

    public Color getForegroundColor() {
        return view.getForegroundColor();
    }

    public Color getBackgroundColor() {
        return view.getBackgroundColor();
    }

    public int getLineThickness() {
        return view.getLineThickness();
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    void onMousePressed(MouseEvent e) {
        state.handleMousePressed(e);
        renderCurrentDocument();
    }

    void onMouseReleased(MouseEvent e) {
        state.handleMouseReleased(e);
        renderCurrentDocument();
        String textOutput = recognizeCurrentDocument();
        view.setTextOutput(textOutput);
    }

    private String recognizeCurrentDocument() {
        MyImage snapshot = MyImageUtils.fromFXImageToMyImage(getCanvasSnapshot());

        StringBuilder sb = new StringBuilder();
        for (String line : recognizer.recognizeImage(snapshot).getLines()) {
            sb.append(parseLine(line));
        }

        LogManager.getLogger().info(sb.toString());
        return sb.toString();
    }

    private String parseLine(String line) {
        StringBuilder sb = new StringBuilder(line);

        if (line.endsWith("=")) {
            calc.calculateFromString(line);
            sb.append(calc.getResult());
        }
        sb.append("\n");

        return sb.toString();
    }

    private Image getCanvasSnapshot() {
        WritableImage wi = new WritableImage((int) view.getCanvas().getWidth(), (int) view.getCanvas().getHeight());
        return view.getCanvas().snapshot(new SnapshotParameters(), wi);
    }

    void onMouseDragged(MouseEvent e) {
        state.handleMouseDragged(e);
        renderCurrentDocument();
    }

    void onMouseMoved(MouseEvent e) {
        state.handleMouseMoved(e);
    }

    void onScroll(ScrollEvent e) {
        state.handleScroll(e);
        renderCurrentDocument();
    }

    void onKeyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getCode() == KeyCode.Z) {
            undoClicked();
        } else if (e.isControlDown() && e.getCode() == KeyCode.Y) {
            redoClicked();
        }

        state.handleKeyPressed(e);
        renderCurrentDocument();
    }

    void onKeyReleased(KeyEvent e) {
        state.handleKeyReleased(e);
        renderCurrentDocument();
    }

    void onForegroundChange(Paint paint) {
        state.handleForegroundChanged(paint);
    }

    void onBackgroundChange(Paint paint) {
        state.handleBackgroundChanged(paint);
    }

    void onThicknessChange(int thickness) {
        state.handleThicknessChanged(thickness);
    }

    public void setTempDrawable(Drawable drawable) {
        tempDrawable = drawable;
        renderCurrentDocument();
    }

    public void renderCurrentDocument() {
        GraphicsContext gc = view.getCanvas().getGraphicsContext2D();
        gc.clearRect(0, 0, view.getCanvas().getWidth(), view.getCanvas().getHeight());
        currentDocument.render(gc);

        if (tempDrawable != null) {
            tempDrawable.draw(gc);
        }

    }

    void eraserClicked() {
        state = new EraserState(this);
    }

    void drawCurveClicked() {
        state = new DrawingBrushState(this);

    }

    private void undoClicked() {
        commandStack.undo();
    }

    private void redoClicked() {
        commandStack.redo();
    }

}
