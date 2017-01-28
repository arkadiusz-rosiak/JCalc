package pl.rosiakit.ocr.image.iterators;

import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.Pixel;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class VerticalLineIterator implements Iterator<Pixel> {

    private final MyImage image;
    private final int x;
    private final int end;
    private int y = 0;

    public VerticalLineIterator(MyImage image, int x, int start, int end) {
        this.image = image;
        this.x = x;
        this.end = end;
        this.y = start;
    }

    public VerticalLineIterator(MyImage image, int x) {
        this(image, x, 0, image.getHeight()-1);
    }

    @Override
    public boolean hasNext() {
        return y <= end && x < image.getWidth();
    }

    @Override
    public Pixel next() {

        if(!hasNext()){
            throw new NoSuchElementException();
        }

        return image.getPixel(x, y++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
