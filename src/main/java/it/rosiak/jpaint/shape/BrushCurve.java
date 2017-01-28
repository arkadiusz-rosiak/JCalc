package it.rosiak.jpaint.shape;

import it.rosiak.jpaint.utils.BresenhamLineAlgorithm;
import it.rosiak.jpaint.utils.BrushPressure;
import it.rosiak.jpaint.utils.LineAlgorithm;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class BrushCurve implements Drawable, Strokeable {

    private final List<Coordinates> vertices = new LinkedList<>();
    private final BrushPressure pressure;
    private final Image originalBrush;
    private WritableImage brush;
    private int thickness = 5;
    private LineAlgorithm lineDrawAlgorithm;

    public BrushCurve(Image brush, double pressureStep, double maxPressure) {
        this.pressure = new BrushPressure(pressureStep, maxPressure);
        this.originalBrush = brush;
        this.brush = createColoredBrushFromOriginalImage(Color.BLACK);
    }

    public BrushCurve(Image brush) {
        this(brush, 0.01, 0.25);
    }


    @Override
    public void setStroke(Paint strokeColor) {

        if (strokeColor instanceof Color) {
            Color color = (Color) strokeColor;
            this.brush = createColoredBrushFromOriginalImage(color);
        }
    }

    @Override
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    private WritableImage createColoredBrushFromOriginalImage(Color newColor) {
        WritableImage wi = new WritableImage((int) originalBrush.getWidth(), (int) originalBrush.getHeight());

        PixelReader pixelReader = originalBrush.getPixelReader();
        PixelWriter pixelWriter = wi.getPixelWriter();

        for (int y = 0; y < originalBrush.getHeight(); y++) {
            for (int x = 0; x < originalBrush.getWidth(); x++) {
                int argb = pixelReader.getArgb(x, y);
                int alpha = (argb >> 24) & 0xFF;
                Color color = new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), alpha / 255);
                pixelWriter.setColor(x, y, color);
            }
        }

        return wi;
    }

    public void addVertex(Coordinates vertex) {
        vertices.add(vertex);
    }

    @Override
    public void draw(GraphicsContext gc) {
        pressure.reset();
        lineDrawAlgorithm = new BresenhamLineAlgorithm(gc, brush);

        ListIterator<Coordinates> it = vertices.listIterator();
        if (it.hasNext()) {
            Coordinates from = it.next();

            while (it.hasNext()) {
                Coordinates to = it.next();
                gc.setGlobalAlpha(pressure.getCurrentPressure());
                drawLineBetween(from, to);
                pressure.pushHarder();
                from = to;
            }
        }

        gc.setGlobalAlpha(1);
    }

    private void drawLineBetween(Coordinates from, Coordinates to) {
        double brushSize = calculateBrushSize();
        lineDrawAlgorithm.drawLine(from, to, brushSize);
    }

    private double calculateBrushSize() {
        return thickness * pressure.getCurrentPressure() * (1 / pressure.getMaxPressure());
    }

    @Override
    public String toString() {
        return "BrushCurve";
    }

}
