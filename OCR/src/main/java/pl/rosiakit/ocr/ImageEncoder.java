package pl.rosiakit.ocr;

import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.Pixel;
import pl.rosiakit.ocr.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

class ImageEncoder {

    private final MyImage image;

    ImageEncoder(MyImage image) {
        this.image = image;
    }

    double[] getEncodedImage() {

        int wantedSize = image.getHeight() * image.getWidth();
        List<Double> values = new ArrayList<>(wantedSize);

        for (Pixel pixel : image) {
            if (ColorUtils.isBlack(pixel.getColor())) {
                values.add(1.0);
            } else {
                values.add(0.0);
            }
        }

        return values.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
