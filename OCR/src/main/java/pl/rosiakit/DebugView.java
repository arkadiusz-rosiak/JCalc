package pl.rosiakit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.rosiakit.ocr.ImageRecognizer;
import pl.rosiakit.ocr.RecognitionResult;
import pl.rosiakit.ocr.image.MyImage;
import pl.rosiakit.ocr.utils.MyImageUtils;

public class DebugView extends Application {

    private static final Logger Log = LogManager.getLogger();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        Image image = new Image(getClass().getResourceAsStream("/sample/sheets/new.png"));
        MyImage ocrImage = MyImageUtils.fromFXImageToMyImage(image);

        ImageRecognizer recognizer = new ImageRecognizer();
        RecognitionResult result = recognizer.recognizeImage(ocrImage);

        ImageView imageView = new ImageView(MyImageUtils.fromMyImageToFXImage(recognizer.getImage()));

        HBox extractedImages = new HBox();

        Label output = new Label();
        output.setTextFill(Color.RED);
        output.setText("Recognized text: \n" + result.getText());

        Log.info(output.getText());

        VBox top = new VBox();
        top.getChildren().addAll(extractedImages, output, imageView);

        StackPane pane = new StackPane(top);

        Scene scene = new Scene(pane, 1100, 600);
        stage.setScene(scene);
        stage.setTitle("OCR Debug View");
        stage.show();
    }
}
