package it.rosiak.jpaint.document;

import it.rosiak.jpaint.filter.Filter;
import it.rosiak.jpaint.shape.Drawable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Document {

    private final List<Filter> filters = new ArrayList<>();
    private final List<Layer> layers = new ArrayList<>();
    private Layer activeLayer;

    public Document() {
        this.activeLayer = new Layer("Layer 1");
        layers.add(activeLayer);
    }

    public int getLayersCount() {
        return layers.size();
    }

    public void addDrawable(Drawable drawable) {
        activeLayer.addDrawable(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        activeLayer.removeDrawable(drawable);
    }

    public List<Layer> getLayers(){
        return layers;
    }

    public void render(GraphicsContext gc) {
        for (Layer layer : layers) {
            layer.applyDocumentFilters(filters);
            layer.render(gc);
        }
    }

}
