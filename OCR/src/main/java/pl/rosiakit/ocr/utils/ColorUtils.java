package pl.rosiakit.ocr.utils;

public class ColorUtils {

    public static int extractAlpha(int color) {
        return color & 0xFF000000;
    }

    public static int extractRed(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int extractGreen(int color) {
        return (color >> 8) & 0xFF;
    }

    public static int extractBlue(int color) {
        return color & 0xFF;
    }

    public static boolean isBlack(int color) {
        return (color & 0x00FFFFFF) == 0x00000000;
    }
}
