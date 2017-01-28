package pl.rosiakit.ocr.image;

import pl.rosiakit.ocr.image.decorator.BinaryDecorator;
import pl.rosiakit.ocr.image.decorator.GrayscaleDecorator;

public class OCRImage extends MyImage{

    public OCRImage(Pixel[] pixels, int width, int height) {
        super(pixels, width, height);
    }

    public OCRImage(MyImage image){
        super(image.getPixels(), image.getWidth(), image.getHeight());
        pixels = getDecoratedPixels();
    }

    @Override
    Pixel decoratePixel(Pixel pixel) {
        return new BinaryDecorator(new GrayscaleDecorator(pixel));
    }
}
