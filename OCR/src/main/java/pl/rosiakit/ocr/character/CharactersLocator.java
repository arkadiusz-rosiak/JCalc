package pl.rosiakit.ocr.character;

import javafx.geometry.Point2D;
import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.Pixel;
import pl.rosiakit.ocr.image.iterators.VerticalLineIterator;
import pl.rosiakit.ocr.line.TextLine;
import pl.rosiakit.ocr.line.TextLinesLocator;
import pl.rosiakit.ocr.utils.LineUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CharactersLocator {

    private final MyImage image;
    private Point2D topLeft = new Point2D(0, 0);
    private Point2D bottomRight;

    public CharactersLocator(MyImage image) {
        this.image = image;
        bottomRight = new Point2D(image.getWidth() - 1, image.getHeight() - 1);
    }

    private void setWorkingArea(TextLine textLine) {
        topLeft = new Point2D(topLeft.getX(), textLine.getTop());
        bottomRight = new Point2D(bottomRight.getX(), textLine.getBottom());
    }

    public List<CharacterPosition> locateAllInTextLine(TextLine textLine) {

        setWorkingArea(textLine);
        List<CharacterPosition> characterPositions = new ArrayList<>();

        List<Integer> boundaryLines = findVerticalBoundaryLines();
        ListIterator<Integer> it = boundaryLines.listIterator();

        while (it.hasNext()) {
            int left = it.next();
            int right = it.hasNext() ? it.next() : left;
            characterPositions.add(getActualCharacterPosition(left, right));
        }

        return characterPositions;
    }

    private CharacterPosition getActualCharacterPosition(int left, int right) {
        Point2D start = new Point2D(left, Math.max(0, this.topLeft.getY() - 1));
        Point2D end = new Point2D(right, Math.min(this.bottomRight.getY() + 1, image.getHeight() - 1));

        List<TextLine> linesInCharacterArea = new TextLinesLocator(image, start, end).locate();

        if (!linesInCharacterArea.isEmpty()) {
            TextLine firstLine = linesInCharacterArea.get(0);
            TextLine lastLine = linesInCharacterArea.get(linesInCharacterArea.size() - 1);
            start = new Point2D(left, firstLine.getTop());
            end = new Point2D(right, lastLine.getBottom());
        }

        return new CharacterPosition(start, end);
    }

    private List<Integer> findVerticalBoundaryLines() {
        List<Integer> boundaryLines = new ArrayList<>();

        boolean isCharacterStarted = false;

        for (int x = (int) topLeft.getX(); x <= bottomRight.getX(); ++x) {

            if (!isCharacterStarted && hasVerticalLineFilledPixels(x)) {
                isCharacterStarted = true;
                boundaryLines.add(x);
            }

            if (isCharacterStarted && !hasVerticalLineFilledPixels(x)) {
                isCharacterStarted = false;
                boundaryLines.add(x - 1);
            }
        }

        if (isCharacterStarted) {
            boundaryLines.add((int) bottomRight.getX());
        }

        return boundaryLines;
    }

    private boolean hasVerticalLineFilledPixels(int x) {
        Iterator<Pixel> it = new VerticalLineIterator(image, x, (int) topLeft.getY(), (int) bottomRight.getY());
        return LineUtils.hasFilledPixels(it);
    }

}
