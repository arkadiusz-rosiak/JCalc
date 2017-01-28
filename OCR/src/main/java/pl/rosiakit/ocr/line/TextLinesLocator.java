package pl.rosiakit.ocr.line;

import javafx.geometry.Point2D;
import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.Pixel;
import pl.rosiakit.ocr.image.iterators.HorizontalLineIterator;
import pl.rosiakit.ocr.utils.LineUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TextLinesLocator {

    private final MyImage image;
    private final Point2D topLeft;
    private final Point2D bottomRight;

    public TextLinesLocator(MyImage image, Point2D topLeft, Point2D bottomRight) {
        this.image = image;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public TextLinesLocator(MyImage image) {
        this(image, new Point2D(0, 0), new Point2D(image.getWidth() - 1, image.getHeight() - 1));
    }

    public List<TextLine> locate() {
        List<Integer> boundaryLines = findHorizontalBoundaryLines();
        return createTextLinesFromBoundaryLines(boundaryLines);
    }

    private List<Integer> findHorizontalBoundaryLines() {
        List<Integer> boundaryLines = new ArrayList<>();

        boolean isCharacterStarted = false;

        for (int y = (int) topLeft.getY(); y <= bottomRight.getY(); ++y) {

            if (!isCharacterStarted && hasHorizontalLineFilledPixels(y)) {
                isCharacterStarted = true;
                boundaryLines.add(y);
            }

            if (isCharacterStarted && !hasHorizontalLineFilledPixels(y)) {
                isCharacterStarted = false;
                boundaryLines.add(y - 1);
            }
        }

        if (isCharacterStarted) {
            boundaryLines.add((int) bottomRight.getY());
        }

        return boundaryLines;
    }


    private List<TextLine> createTextLinesFromBoundaryLines(List<Integer> borderLines) {
        List<TextLine> lines = new ArrayList<>(borderLines.size() / 2);
        ListIterator<Integer> it = borderLines.listIterator();

        while (it.hasNext()) {
            int top = it.next();
            int bottom = it.hasNext() ? it.next() : top;
            lines.add(new TextLine(top, bottom));
        }

        return lines;
    }

    private boolean hasHorizontalLineFilledPixels(int y) {
        Iterator<Pixel> it = new HorizontalLineIterator(image, y, (int) topLeft.getX(), (int) bottomRight.getX());
        return LineUtils.hasFilledPixels(it);
    }

}