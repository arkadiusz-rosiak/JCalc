package pl.rosiakit.ocr;

import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rosiakit.ocr.character.CharacterPosition;
import pl.rosiakit.ocr.character.CharactersLocator;
import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.OCRImage;
import pl.rosiakit.ocr.image.Pixel;
import pl.rosiakit.ocr.line.TextLine;
import pl.rosiakit.ocr.line.TextLinesLocator;
import pl.rosiakit.ocr.scaler.ImageScaler;
import pl.rosiakit.ocr.utils.MyImageUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("SameParameterValue")
class SamplesSheetEncoder {

    private static final Logger Log = LogManager.getLogger();

    private final OCRImage image;
    private final CharactersLocator charactersLocator;
    private final StringBuilder outputBuilder = new StringBuilder();

    private SamplesSheetEncoder(OCRImage image) {
        this.image = image;
        this.charactersLocator = new CharactersLocator(image);
    }

    private static List<String> getSheets() throws IOException {
        List<String> files = new ArrayList<>();

        try(Stream<Path> paths = Files.walk(Paths.get(".\\X.OCR\\src\\main\\resources\\sample\\sheets"))) {
            paths.forEach(filePath -> {
                if (filePath.toFile().isFile() && !"template.png".equals(filePath.getFileName().toString())) {
                    files.add(filePath.getFileName().toString());
                }
            });
        }

        return files;
    }

    public static void main(String[] args) throws Exception{

        List<String> files = getSheets();

        Files.write(Paths.get("train.txt"), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);

        for(String name : files){
            Log.info("Parsing " + name);

            Image image = new Image(SamplesSheetEncoder.class.getResourceAsStream("/sample/sheets/"+name));
            OCRImage ocrImage = new OCRImage(MyImageUtils.fromFXImageToMyImage(image));

            StringBuilder line = new StringBuilder();

            SamplesSheetEncoder app = new SamplesSheetEncoder(ocrImage);
            app.parseSheet();
            String out = app.getEncodedSheet();
            line.append(out);

            Files.write(Paths.get("train.txt"), line.toString().getBytes(), StandardOpenOption.APPEND);
        }
    }

    private void parseSheet() {
        List<TextLine> textLines = new TextLinesLocator(this.image).locate();

        for(int i =0; i < textLines.size(); ++i){
            TextLine line = textLines.get(i);

            List<CharacterPosition> positions = locateAllCharactersInLine(line, 2);
            List<MyImage> extracted = extractSubImages(positions);
            List<MyImage> scaled = scaleCharactersTo(28, 28, extracted);

            for(MyImage img : scaled){
                ImageEncoder encoder = new ImageEncoder(img);
                String input = parseInput(encoder.getEncodedImage());
                String output = parseOutput(i);
                outputBuilder.append(input).append(output).append("\n");
            }
        }
    }

    private List<CharacterPosition> locateAllCharactersInLine(TextLine line, int skipFirstCount) {
        List<CharacterPosition> charactersInLine = charactersLocator.locateAllInTextLine(line);

        while(skipFirstCount-- > 0 && !charactersInLine.isEmpty()){
            charactersInLine.remove(0);
        }

        return charactersInLine;
    }

    private List<MyImage> extractSubImages(List<CharacterPosition> positions) {
        List<MyImage> charactersImages = new ArrayList<>(10);

        for(CharacterPosition position : positions){
            int x1 = (int) position.getTopLeft().getX();
            int y1 = (int) position.getTopLeft().getY();

            Pixel[] slice = image.getPixelsSlice(x1, y1, position.getWidth(), position.getHeight());

            MyImage croppedImage = new OCRImage(slice, position.getWidth(), position.getHeight());
            charactersImages.add(croppedImage);
        }

        return charactersImages;
    }

    private List<MyImage> scaleCharactersTo(int width, int height, List<MyImage> characters) {

        List<MyImage> scaled = new ArrayList<>(characters.size());
        ImageScaler scaler = new ImageScaler(width, height);

        for(MyImage character : characters){
            character = scaler.scaleImage(character);
            scaled.add(character);
        }

        return scaled;
    }

    private String parseInput(double[] value){
        StringBuilder lineBuilder = new StringBuilder();
        for(double x : value){
            if(x > 0){
                lineBuilder.append("1.0,");
            }
            else{
                lineBuilder.append("0.0,");
            }

        }

        return lineBuilder.toString();
    }

    private String parseOutput(int value) {
        String[] desiredOutput = new String[] {"0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0",
                "0.0","0.0","0.0","0.0","0.0","0.0", "0.0"};
        desiredOutput[value] = "1.0";
        return String.join(",", desiredOutput);
    }

    private String getEncodedSheet() {
        return outputBuilder.toString();
    }
}
