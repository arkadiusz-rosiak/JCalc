package pl.rosiakit.ocr.image.decorator;

import pl.rosiakit.ocr.image.Pixel;

public class BinaryDecorator extends PixelDecorator{

    private static final int THRESHOLD = 220;

    public BinaryDecorator(Pixel component) {
        super(component);
    }

    @Override
    public int getColor() {

        if(getRed() > THRESHOLD || getGreen() > THRESHOLD || getBlue() > THRESHOLD){
            return 0xFFFFFFFF;
        }
        else{
            return 0xFF000000;
        }
    }
}