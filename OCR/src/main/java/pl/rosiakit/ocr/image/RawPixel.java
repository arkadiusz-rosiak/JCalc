package pl.rosiakit.ocr.image;

public class RawPixel implements Pixel {

    private final int color;

    public RawPixel(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "#" + color;
    }
}
