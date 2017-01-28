package pl.rosiakit.ocr.scaler;

import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.OCRImage;
import pl.rosiakit.ocr.image.Pixel;

public class ImageScaler {

    private final int targetWidth;
    private final int targetHeight;

    public ImageScaler(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    public MyImage scaleImage(MyImage image){

        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();

        double factor = calculateScaleFactor(sourceWidth, sourceHeight);

        MyImage output;

        if(factor < 1){
            output = downscaleImage(image, sourceWidth, sourceHeight, factor);
        }
        else{
            output = image;
        }

        output = fitImageToOutputSize(output);

        return output;
    }


    private double calculateScaleFactor(double sourceWidth, double sourceHeight){
        double widthFactor = targetWidth / sourceWidth;
        double heightFactor = targetHeight / sourceHeight;
        return Math.min(widthFactor, heightFactor);
    }

    private MyImage downscaleImage(MyImage sourceImage, int sourceWidth, int sourceHeight, double factor){

        Pixel[] pixels = sourceImage.getPixels();
        int newWidth = (int) Math.round(sourceWidth * factor);
        int newHeight = (int) Math.round(sourceHeight * factor);

        Pixel[] newPixels = BilinearAlgorithm.scale(pixels, sourceWidth, sourceHeight, newWidth, newHeight);

        MyImage downscaled = new MyImage(newWidth, newHeight);

        for(int i = 0; i < newPixels.length; ++i){
            downscaled.setPixel(i, newPixels[i]);
        }

        return new OCRImage(downscaled);
    }

    private MyImage fitImageToOutputSize(MyImage image) {
        MyImage fittedHorizontally = fitHorizontal(image);
        return fitVertical(fittedHorizontally);
    }

    private MyImage fitHorizontal(MyImage image) {

        int delta = (targetWidth - image.getWidth()) / 2;

        MyImage fittedImage = new MyImage(targetWidth, image.getHeight());

        for(int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < targetWidth; ++x) {

                if(x-delta >= 0 && x-delta < image.getWidth()){
                    Pixel pixel = image.getPixel(x-delta, y);
                    fittedImage.setPixel(x, y, pixel);
                }

            }
        }

        return fittedImage;
    }

    private MyImage fitVertical(MyImage image) {
        int delta = (targetHeight - image.getHeight()) / 2;

        MyImage fittedImage = new MyImage(image.getWidth(), targetHeight);

        for(int y = 0; y < targetHeight; ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {

                if(y-delta >= 0 && y-delta < image.getHeight() && x < image.getWidth()){
                    Pixel pixel = image.getPixel(x, y-delta);
                    fittedImage.setPixel(x, y, pixel);
                }

            }
        }

        return fittedImage;
    }

}
