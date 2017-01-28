package pl.rosiakit.ocr.image.iterators;

import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.Pixel;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HorizontalLineIterator implements Iterator<Pixel> {

    private final MyImage image;
    private final int y;
    private final int end;
    private int x = 0;

    public HorizontalLineIterator(MyImage image, int y, int start, int end){
        this.image = image;
        this.y = y;
        this.end = end;
        this.x = start;
    }

    public HorizontalLineIterator(MyImage image, int y) {
        this(image, y, 0, image.getWidth()-1);
    }

    @Override
    public boolean hasNext() {
        return y < image.getHeight() && x <= end;
    }

    @Override
    public Pixel next() {

        if(!hasNext()){
            throw new NoSuchElementException();
        }

        return image.getPixel(x++, y);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
