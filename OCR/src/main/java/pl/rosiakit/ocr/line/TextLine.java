package pl.rosiakit.ocr.line;

public class TextLine {

    private final double top;
    private final double bottom;

    TextLine(double top, double bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    @Override
    public String toString() {
        return "Text line from " + top + " to " + bottom;
    }
}
