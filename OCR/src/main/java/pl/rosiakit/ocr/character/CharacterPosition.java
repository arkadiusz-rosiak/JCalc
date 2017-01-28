package pl.rosiakit.ocr.character;

import javafx.geometry.Point2D;

public class CharacterPosition {

    private static final int MARGIN = 1;

    private final Point2D topLeft;
    private final Point2D bottomRight;

    CharacterPosition(Point2D topLeft, Point2D bottomRight) {
        this.topLeft = calculateTopLeft(topLeft);
        this.bottomRight = calculateBottomRight(bottomRight);
    }

    private Point2D calculateTopLeft(Point2D topLeft) {
        double x = topLeft.getX() - MARGIN;
        double y = topLeft.getY() - MARGIN;
        return new Point2D(Math.max(0, x), Math.max(0, y));
    }

    private Point2D calculateBottomRight(Point2D bottomRight) {
        double x = bottomRight.getX() + MARGIN;
        double y = bottomRight.getY() + MARGIN;
        return new Point2D(x, y);
    }

    public Point2D getTopLeft() {
        return topLeft;
    }

    public Point2D getBottomRight() {
        return bottomRight;
    }

    public int getWidth() {
        return (int) (bottomRight.getX() - topLeft.getX());
    }

    public int getHeight() {
        return (int) (bottomRight.getY() - topLeft.getY());
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof CharacterPosition)) {
            return false;
        }

        CharacterPosition position = (CharacterPosition) o;

        return getTopLeft().equals(position.getTopLeft()) && getBottomRight().equals(position.getBottomRight());

    }

    @Override
    public int hashCode() {
        int result = getTopLeft().hashCode();
        result = 31 * result + getBottomRight().hashCode();
        return result;
    }
}
