import javafx.scene.image.Image;
import org.apache.logging.log4j.Logger;
import pl.rosiakit.InlineInputCalculator;
import pl.rosiakit.ocr.ImageRecognizer;
import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.utils.MyImageUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AcceptanceTests {

    private static final Logger Log = org.apache.logging.log4j.LogManager.getLogger();
    private static ImageRecognizer recognizer = new ImageRecognizer();
    private static InlineInputCalculator calc = new InlineInputCalculator();

    private static List<String> getTestCases() throws IOException {
        List<String> files = new ArrayList<>();

        try(Stream<Path> paths = Files.walk(Paths.get(".\\OCR\\src\\main\\resources\\acceptance"))) {
            paths.forEach(filePath -> {
                if (filePath.toFile().isFile()) {
                    files.add(filePath.getFileName().toString());
                }
            });
        }

        return files;
    }

    private static String extractResultFromFileName(String filename){
        String[] split = filename.split("\\.");
        return split[0];
    }

    private static String readFileContent(String filename){
        Image image = new Image(AcceptanceTests.class.getResourceAsStream("/acceptance/"+filename));
        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);
        return recognizer.recognizeImage(ocrImage).getLines().get(0);
    }

    public static void main(String[] args) throws IOException{
        List<String> files = getTestCases();

        int recognizedCount = 0;
        List<String> unrecognizedFiles = new ArrayList<>();

        for(String filename : files){
            Log.info("Testing " + filename);
            String wantedResult = extractResultFromFileName(filename);
            String fileContent = readFileContent(filename);
            calc.calculateFromString(fileContent);

            Log.info("Recognized as " + fileContent);

            String result = calc.getResult();
            Log.info("Result " + result);

            if(result.equals(wantedResult)){
                ++recognizedCount;
            }
            else{
                unrecognizedFiles.add(filename);
            }

        }

        double total = recognizedCount + unrecognizedFiles.size();

        System.out.println("Recognized " + recognizedCount + " of " + total);
        System.out.println("Which is " + recognizedCount/total*100 + "%");

    }

}
