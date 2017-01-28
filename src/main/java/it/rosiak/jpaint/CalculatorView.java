package it.rosiak.jpaint;

import it.rosiak.jpaint.layout.ResizableCanvas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CalculatorView extends Application{

    private CalcPresenter presenter;

    private Pane root;
    private ResizableCanvas canvas;
    private TextArea textOutput;

    private Slider lineThicknessSlider;
    private ColorPicker foregroundColorPicker;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            presenter = new CalcPresenter(this);

            root = FXMLLoader.load(getClass().getResource("/calculator.fxml"));

            textOutput = (TextArea) root.lookup("#textOutput");

            Scene scene = new Scene(root);

            String css = getClass().getResource("/css/gray.css").toExternalForm();
            scene.getStylesheets().add(css);


            prepareResizableCanvas();

            primaryStage.setScene(scene);
            primaryStage.setTitle("Handwritten numbers recognizer & calculator");
            primaryStage.show();

            foregroundColorPicker = (ColorPicker) root.lookup("#foregroundColor");
            foregroundColorPicker.setValue(Color.BLACK);
            foregroundColorPicker.setOnAction(e -> presenter.onForegroundChange(getForegroundColor()));

            Label thicknessDisplay = (Label) root.lookup("#thicknessDisplay");
            lineThicknessSlider = (Slider) root.lookup("#thickness");
            lineThicknessSlider.valueProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        thicknessDisplay.textProperty().setValue(String.valueOf(getLineThickness()));
                        presenter.onThicknessChange(getLineThickness());
                    });

            thicknessDisplay.textProperty().setValue(String.valueOf(getLineThickness()));
            presenter.onThicknessChange(getLineThickness());

            Circle circle = new Circle(12);
            circle.setCenterX(12);
            circle.setCenterY(12);
            thicknessDisplay.setPrefSize(24, 24);
            thicknessDisplay.setClip(circle);

            canvas.setOnMousePressed(presenter::onMousePressed);
            canvas.setOnMouseReleased(presenter::onMouseReleased);
            canvas.setOnMouseDragged(presenter::onMouseDragged);
            canvas.setOnMouseMoved(presenter::onMouseMoved);
            root.setOnKeyPressed(presenter::onKeyPressed);
            root.setOnKeyReleased(presenter::onKeyReleased);

            root.lookup("#drawTool").setOnMouseClicked(e -> presenter.drawCurveClicked());
            root.lookup("#eraseTool").setOnMouseClicked(e -> presenter.eraserClicked());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void prepareResizableCanvas() {
        Pane wrapper = (Pane) root.lookup("#canvasWrapper");
        canvas = (ResizableCanvas) wrapper.lookup("#canvas");

        canvas.heightProperty().bind(wrapper.heightProperty());
        canvas.widthProperty().bind(wrapper.widthProperty());
    }

    public Canvas getCanvas() {
        return canvas;
    }

    Color getForegroundColor() {
        if(this.foregroundColorPicker != null){
            return foregroundColorPicker.getValue();
        }

        return Color.BLACK;
    }

    Color getBackgroundColor() {
        return Color.TRANSPARENT;
    }

    int getLineThickness() {
        if(lineThicknessSlider != null) {
            return (int) lineThicknessSlider.getValue();
        }

        return 1;
    }

    void setTextOutput(String textOutput) {
        this.textOutput.setText(textOutput);
    }

    void setCanvasCursor(Image cursorImage){
        Cursor cursor = new ImageCursor(cursorImage, cursorImage.getWidth() / 2, cursorImage.getHeight() / 2);
        canvas.setCursor(cursor);
    }
}
