package it.rosiak.jpaint.document;

import it.rosiak.jpaint.filter.Filter;
import it.rosiak.jpaint.shape.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Layer {

    private final List<Drawable> drawables = new ArrayList<>();
    private final Set<Filter> filters = new HashSet<>();

    private boolean isEnabled = true;
    private String name;

    public Layer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFilter(Filter f){
        filters.add(f);
    }

    public void removeFilter(Filter f){
        filters.remove(f);
    }

    public void disable(){
        isEnabled = false;
    }

    public void enable(){
        isEnabled = true;
    }

    void addDrawable(Drawable drawable){
        drawables.add(drawable);
    }

    void removeDrawable(Drawable drawable) { drawables.remove(drawable); }

    void render(GraphicsContext gc){

        gc.setEffect(getMixedEffects());

        if(isEnabled) {
            for (Drawable drawable : drawables) {
                drawable.draw(gc);
            }
        }
        gc.setEffect(null);
    }

    void applyDocumentFilters(List<Filter> filters) {
        this.filters.addAll(filters);
    }

    private Effect getMixedEffects(){
        Filter all = Filter.NULL;

        for(Filter filter : filters){
            filter.mixWith(all);
            all = filter;
        }

        return all.getEffect();
    }


    @Override
    public String toString() {
        return name;
    }


}
