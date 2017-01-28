package pl.rosiakit.ocr.scaler;

import pl.rosiakit.ocr.image.Pixel;
import pl.rosiakit.ocr.image.RawPixel;
import pl.rosiakit.ocr.image.decorator.BinaryDecorator;

// http://tech-algorithm.com/articles/bilinear-image-scaling/
class BilinearAlgorithm {

    private BilinearAlgorithm() {
    }


    static Pixel[] scale(Pixel[] pixels, int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {
        Pixel[] scaled = new Pixel[targetHeight * targetWidth];

        float xRatio = ((float) (sourceWidth)) / targetWidth;
        float yRatio = ((float) (sourceHeight)) / targetHeight;

        int alpha = 0xFF000000;

        int offset = 0;

        for (int i = 0; i < targetHeight; i++) {
            for (int j = 0; j < targetWidth; j++) {
                int x = (int) (xRatio * j);
                int y = (int) (yRatio * i);

                float xDiff = (xRatio * j) - x;
                float yDiff = (yRatio * i) - y;
                int index = y * sourceWidth + x;

                int a = pixels[index].getColor();

                int b, c, d;

                if (index + 1 < pixels.length) {
                    b = pixels[index + 1].getColor();
                } else {
                    b = pixels[index].getColor();
                }

                if (index + sourceWidth < pixels.length) {
                    c = pixels[index + sourceWidth].getColor();
                } else {
                    c = pixels[index].getColor();
                }

                if (index + sourceWidth + 1 < pixels.length) {
                    d = pixels[index + sourceWidth + 1].getColor();
                } else {
                    d = pixels[index].getColor();
                }


                int green = (int) (((a >> 8) & 0xff) * (1 - xDiff) * (1 - yDiff) +
                        ((b >> 8) & 0xff) * (xDiff) * (1 - yDiff) +
                        ((c >> 8) & 0xff) * (yDiff) * (1 - xDiff) +
                        ((d >> 8) & 0xff) * (xDiff * yDiff));

                int red = (int) (((a >> 16) & 0xff) * (1 - xDiff) * (1 - yDiff) +
                        ((b >> 16) & 0xff) * (xDiff) * (1 - yDiff) +
                        ((c >> 16) & 0xff) * (yDiff) * (1 - xDiff) +
                        ((d >> 16) & 0xff) * (xDiff * yDiff));

                int blue = (int) ((a & 0xff) * (1 - xDiff) * (1 - yDiff) +
                        (b & 0xff) * (xDiff) * (1 - yDiff) +
                        (c & 0xff) * (yDiff) * (1 - xDiff) +
                        (d & 0xff) * (xDiff * yDiff));


                int color = alpha | ((red << 16) & 0xff0000) | ((green << 8) & 0xff00) | blue;
                Pixel calculatedPixel = new BinaryDecorator(new RawPixel(color));
                scaled[offset++] = calculatedPixel;

            }
        }


       /* for(int y = 0; y < targetHeight; ++y){
            int x = 0;
            int index = y * targetWidth + x;

            scaled[index] =scaled[index+1];
        }
*/
        return scaled;
    }

}
