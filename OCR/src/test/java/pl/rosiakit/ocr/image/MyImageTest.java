package pl.rosiakit.ocr.image;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;
import pl.rosiakit.ocr.image.iterators.HorizontalLineIterator;
import pl.rosiakit.ocr.image.iterators.VerticalLineIterator;
import pl.rosiakit.ocr.utils.ColorUtils;
import pl.rosiakit.ocr.utils.MyImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MyImageTest {

    private static final Image image = new Image(MyImage.class.getResourceAsStream("/rainbowTest.png"));
    private static final int[] imagePixels = new int[]{
            -8960, -1945808, -613198, -5287855,
            -4943164, -9219977, -7953214, -12822667,
            -16730928, -16745573, -3872793, -16744076,
            -7956364, -3621603, -1517397, -3043499};


    @Test
    public void shouldReturnTrueImageSize() throws Exception {
        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);

        Assert.assertEquals(4, ocrImage.getWidth());
        Assert.assertEquals(4, ocrImage.getHeight());
    }

    @Test
    public void shouldReturnTruePixelsWhenNoDecorators() throws Exception {
        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);

        int[] pixels = ocrImage.getPixelsRaw();

        for(int i = 0; i < pixels.length; ++i){
            Assert.assertEquals(imagePixels[i], pixels[i]);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenPixelOutOfImageWanted() throws Exception {
        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);
        ocrImage.getPixel(16);
    }

    @Test
    public void shouldProvideSingleColorOfPixelValues() throws Exception {

        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);

        for(int i = 0; i < imagePixels.length; ++i){
            int c = imagePixels[i];

            int a = c & 0xFF000000;
            int r = (c >> 16) & 0xFF;
            int g = (c >> 8) & 0xFF;
            int b = c & 0xFF;

            Assert.assertEquals(a, ColorUtils.extractAlpha(ocrImage.getPixel(i).getColor()));
            Assert.assertEquals(r, ColorUtils.extractRed(ocrImage.getPixel(i).getColor()));
            Assert.assertEquals(g, ColorUtils.extractGreen(ocrImage.getPixel(i).getColor()));
            Assert.assertEquals(b, ColorUtils.extractBlue(ocrImage.getPixel(i).getColor()));
        }

    }

    @Test
    public void OCRImageShouldProvideIteratorOverAllPixels() throws Exception {

        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);

        int pixelsCount = 0;
        for(Pixel pixel : ocrImage){
            ++pixelsCount;
        }

        Assert.assertEquals(imagePixels.length, pixelsCount);
    }

    @Test
    public void shouldProvideHorizontalLineIterator() throws Exception {
        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);
        Iterator<Pixel> it = ocrImage.getHorizontalLineIterator(1);
        List<Integer> colors = new ArrayList<>();

        while(it.hasNext()){
            Pixel pixel = it.next();
            colors.add(pixel.getColor());
        }

        List<Integer> wanted = Arrays.asList(-4943164, -9219977, -7953214, -12822667);
        Assert.assertEquals(wanted, colors);
    }

    @Test
    public void shouldProvideHorizontalLineIteratorWithBounds() throws Exception {

        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);
        Iterator<Pixel> it = new HorizontalLineIterator(ocrImage, 1, 1, 2);
        List<Integer> colors = new ArrayList<>();

        while(it.hasNext()){
            Pixel pixel = it.next();
            colors.add(pixel.getColor());
        }

        List<Integer> wanted = Arrays.asList(-9219977, -7953214);
        Assert.assertEquals(wanted, colors);

    }

    @Test
    public void shouldProvideVerticalLineIterator() throws Exception {

        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);
        Iterator<Pixel> it = ocrImage.getVerticalLineIterator(0);
        List<Integer> colors = new ArrayList<>();

        while(it.hasNext()){
            Pixel pixel = it.next();
            colors.add(pixel.getColor());
        }

        List<Integer> wanted = Arrays.asList(-8960, -4943164, -16730928, -7956364);
        Assert.assertEquals(wanted, colors);
    }

    @Test
    public void shouldProvideVerticalLineIteratorWithBounds() throws Exception {

        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);
        Iterator<Pixel> it = new VerticalLineIterator(ocrImage, 0, 1, 2);
        List<Integer> colors = new ArrayList<>();

        while(it.hasNext()){
            Pixel pixel = it.next();
            colors.add(pixel.getColor());
        }

        List<Integer> wanted = Arrays.asList(-4943164, -16730928);
        Assert.assertEquals(wanted, colors);

    }

    @Test
    public void shouldReturnImageSlice() throws Exception {

        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);
        List<Pixel> slice = Arrays.asList(ocrImage.getPixelsSlice(1, 1, 2, 2));

        List<Integer> wanted = Arrays.asList(-9219977, -7953214, -16745573, -3872793);

        for(int i = 0; i < wanted.size(); ++i){
            Assert.assertEquals(wanted.get(i).intValue(), slice.get(i).getColor());
        }
    }

    @Test
    public void shouldCreateNewImageFormSlice() throws Exception {

        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);
        Pixel[] slice = ocrImage.getPixelsSlice(1, 1, 2, 2);

        MyImage img = new MyImage(slice, 2, 2);

        List<Pixel> foo = Arrays.asList(img.getPixels());
        List<Integer> wanted = Arrays.asList(-9219977, -7953214, -16745573, -3872793);

        for(int i = 0; i < wanted.size(); ++i){
            Assert.assertEquals(wanted.get(i).intValue(), img.getPixel(i).getColor());
        }
    }
}