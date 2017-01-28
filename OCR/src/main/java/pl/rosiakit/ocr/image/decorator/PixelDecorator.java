package pl.rosiakit.ocr.image.decorator;

import pl.rosiakit.ocr.image.Pixel;
import pl.rosiakit.ocr.utils.ColorUtils;

public abstract class PixelDecorator implements Pixel {

    private final Pixel component;

    PixelDecorator(Pixel component) {
        this.component = component;
    }

    @Override
    public int getColor() {
        return component.getColor();
    }

    int getRed(){
        return ColorUtils.extractRed(component.getColor());
    }

    int getGreen(){
        return ColorUtils.extractGreen(component.getColor());
    }

    int getBlue(){
        return ColorUtils.extractBlue(component.getColor());
    }

    int getAlpha(){
        return ColorUtils.extractAlpha(component.getColor());
    }

    @Override
    public String toString() {
        return "#" + this.getColor();
    }
}
