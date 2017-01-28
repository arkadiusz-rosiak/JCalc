package pl.rosiakit.ocr.image;

import pl.rosiakit.ocr.image.iterators.AllPixelsIterator;
import pl.rosiakit.ocr.image.iterators.HorizontalLineIterator;
import pl.rosiakit.ocr.image.iterators.VerticalLineIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyImage implements Iterable<Pixel> {

    private final int width;
    private final int height;
    Pixel[] pixels;

    public MyImage(Pixel[] pixels, int width, int height) {

        if(width*height != pixels.length){
            throw new IllegalArgumentException("Pixels length should be width*height!");
        }

        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public MyImage(int width, int height){
        this.width = width;
        this.height = height;
        this.pixels = new Pixel[width * height];

        for(int i = 0; i < pixels.length; ++i){
            pixels[i] = new RawPixel(0x00FFFFFF);
        }
    }

    Pixel[] getDecoratedPixels() {
        Pixel[] decorated = new Pixel[pixels.length];

        for (int i = 0; i < pixels.length; ++i) {
            decorated[i] = this.decoratePixel(pixels[i]);
        }

        return decorated;
    }

    Pixel decoratePixel(Pixel pixel) {
        return pixel;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pixel[] getPixels() {
        return pixels;
    }

    public Pixel[] getPixelsSlice(int x1, int y1, int width, int height){

        if(x1 < 0 || y1 < 0 || x1 >= this.width || y1 >= this.height){
            throw new IllegalArgumentException("Start point should be placed on image area");
        }

        List<Pixel> slice = new ArrayList<>();

        for(int y = y1; y < y1 + height; ++y){
            for(int x = x1; x < x1 + width; ++x){
                slice.add(pixels[getIndexFromXY(x, y)]);
            }
        }

        return slice.toArray(new Pixel[0]);
    }

    public int[] getPixelsRaw() {
        int[] imagePixelsRaw = new int[width * height];

        for (int i = 0; i < imagePixelsRaw.length; ++i) {
            imagePixelsRaw[i] = getPixel(i).getColor();
        }

        return imagePixelsRaw;
    }

    public void setPixel(int x, int y, Pixel pixel){
        setPixel(getIndexFromXY(x, y), pixel);
    }

    public void setPixel(int i, Pixel pixel){
        if (i >= 0 && i < pixels.length) {
            pixels[i] = pixel;
        }
        else{
            throw new IllegalArgumentException("Image has only " + pixels.length + " pixels. Cannot set: " + i);
        }
    }

    public Pixel getPixel(int x, int y) {
        return getPixel(getIndexFromXY(x, y));
    }

    private int getIndexFromXY(int x, int y) {
        return width * y + x;
    }

    public Pixel getPixel(int i) {
        if (i >= 0 && i < pixels.length) {
            return pixels[i];
        }

        throw new IllegalArgumentException("Image has only " + pixels.length + " pixels. Wanted: " + i);
    }

    @Override
    public Iterator<Pixel> iterator() {
        return new AllPixelsIterator(this);
    }

    public Iterator<Pixel> getHorizontalLineIterator(int y) {
        return new HorizontalLineIterator(this, y);
    }

    public Iterator<Pixel> getVerticalLineIterator(int x) {
        return new VerticalLineIterator(this, x);
    }
}
