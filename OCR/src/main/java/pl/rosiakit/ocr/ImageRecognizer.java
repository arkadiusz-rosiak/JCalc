package pl.rosiakit.ocr;

import org.apache.logging.log4j.Logger;
import org.neuroph.core.NeuralNetwork;
import pl.rosiakit.ocr.character.CharacterPosition;
import pl.rosiakit.ocr.character.CharactersLocator;
import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.image.OCRImage;
import pl.rosiakit.ocr.image.Pixel;
import pl.rosiakit.ocr.line.TextLine;
import pl.rosiakit.ocr.line.TextLinesLocator;
import pl.rosiakit.ocr.scaler.ImageScaler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImageRecognizer {

    private static final Logger Log = org.apache.logging.log4j.LogManager.getLogger();

    private static final int NETWORK_INPUT_WIDTH = 28;
    private static final int NETWORK_INPUT_HEIGHT = 28;

    private final SingleCharacterRecognizer characterRecognizer;
    private MyImage image;

    public ImageRecognizer() {
        NeuralNetwork network = NeuralNetwork.load(getClass().getResourceAsStream("/digits.nnet"));
        characterRecognizer = new SingleCharacterRecognizer(network);
    }

    public RecognitionResult recognizeImage(MyImage image) {
        long startTime = System.nanoTime();
        this.image = new OCRImage(image);

        CharactersLocator locator = new CharactersLocator(this.image);
        RecognitionResult result = new RecognitionResult();

        List<TextLine> textLines = locateTextLines();

        for(TextLine textLine : textLines) {
            List<CharacterPosition> characterPositions = locator.locateAllInTextLine(textLine);
            List<MyImage> images = prepareImages(characterPositions);
            List<Character> characters = recognizeCharacters(images);
            result.addLine(characters);
        }

        Log.info("Recognized in 0." + (System.nanoTime() - startTime));
        return result;
    }

    private List<TextLine> locateTextLines() {
        return new TextLinesLocator(this.image).locate();
    }

    private List<MyImage> prepareImages(List<CharacterPosition> positions) {

        List<MyImage> images = new ArrayList<>();
        ImageScaler scaler = new ImageScaler(NETWORK_INPUT_WIDTH, NETWORK_INPUT_HEIGHT);

        //noinspection Convert2streamapi
        for (CharacterPosition position : positions) {
            images.add(scaler.scaleImage(cropImage(position)));
        }

        return images;
    }

    private MyImage cropImage(CharacterPosition position) {
        int x1 = (int) position.getTopLeft().getX();
        int y1 = (int) position.getTopLeft().getY();

        Pixel[] slice = image.getPixelsSlice(x1, y1, position.getWidth(), position.getHeight());

        return new MyImage(slice, position.getWidth(), position.getHeight());
    }

    private List<Character> recognizeCharacters(List<MyImage> images) {
        return images.stream().map(characterRecognizer::recognizeCharacter).collect(Collectors.toList());
    }

    public MyImage getImage(){
        return this.image;
    }

}
