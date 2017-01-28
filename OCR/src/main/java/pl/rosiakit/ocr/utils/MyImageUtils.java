package pl.rosiakit.ocr.utils;

import javafx.scene.image.*;
import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.Pixel;
import pl.rosiakit.ocr.image.RawPixel;

public class MyImageUtils {

    public static Image fromMyImageToFXImage(MyImage image){
        WritableImage wi = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter writer = wi.getPixelWriter();

        writer.setPixels(0, 0, image.getWidth(),image.getHeight(),
                PixelFormat.getIntArgbPreInstance(), image.getPixelsRaw(), 0, image.getWidth());

        return wi;
    }

    public static MyImage fromFXImageToMyImage(Image image){
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader reader = image.getPixelReader();
        int[] imagePixels = new int[width * height];
        reader.getPixels(0, 0, width, height, PixelFormat.getIntArgbPreInstance(), imagePixels, 0, width);

        Pixel[] pixels = new Pixel[width * height];

        for (int i = 0; i < imagePixels.length; ++i) {
            pixels[i] = new RawPixel(imagePixels[i]);
        }

        return new MyImage(pixels, width, height);
    }
}
