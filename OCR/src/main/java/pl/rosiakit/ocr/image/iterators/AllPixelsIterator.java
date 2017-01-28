package pl.rosiakit.ocr.image.iterators;

import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.Pixel;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AllPixelsIterator implements Iterator<Pixel> {
    private final MyImage image;
    private int currentIndex = 0;

    public AllPixelsIterator(MyImage image) {
        this.image = image;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < image.getWidth() * image.getHeight();
    }

    @Override
    public Pixel next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return image.getPixel(currentIndex++);
    }
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
