package pl.rosiakit.ocr.image.decorator;

import pl.rosiakit.ocr.image.Pixel;

public class GrayscaleDecorator extends PixelDecorator{

    public GrayscaleDecorator(Pixel component) {
        super(component);
    }

    @Override
    public int getColor() {
        int gray = (getRed() + getGreen() + getBlue()) / 3;
        return getAlpha() | (gray << 16) | (gray << 8) | gray;
    }
}
