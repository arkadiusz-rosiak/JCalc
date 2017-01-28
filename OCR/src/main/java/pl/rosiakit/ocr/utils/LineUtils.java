package pl.rosiakit.ocr.utils;

import pl.rosiakit.ocr.image.Pixel;

import java.util.Iterator;

public class LineUtils {

    public static boolean hasFilledPixels(Iterator<Pixel> iterator){

        while(iterator.hasNext()){
            Pixel pixel = iterator.next();

            if (ColorUtils.isBlack(pixel.getColor())) {
                return true;
            }

        }

        return false;
    }

}
