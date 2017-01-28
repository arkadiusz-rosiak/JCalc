package pl.rosiakit.ocr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neuroph.core.NeuralNetwork;
import pl.rosiakit.ocr.image.MyImage;

class SingleCharacterRecognizer {

    private static final Logger Log = LogManager.getLogger();
    private static final char[] charactersMap
            = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '*', '/', '/', '='};
    private final NeuralNetwork network;

    SingleCharacterRecognizer(NeuralNetwork network) {
        this.network = network;
    }

    char recognizeCharacter(MyImage characterImage) {
        double[] character = new ImageEncoder(characterImage).getEncodedImage();
        return parseValue(character);
    }

    private char parseValue(double[] character) {
        network.setInput(character);
        network.calculate();
        Log.debug(buildDebugInfo(network.getOutput()));
        return fetchNetworkOutput(network.getOutput());
    }

    private char fetchNetworkOutput(double[] output) {
        return charactersMap[getHighestResultIndex(output)];
    }

    private String buildDebugInfo(double[] output) {
        StringBuilder sb = new StringBuilder();

        sb.append("Recognized value: ").append(charactersMap[getHighestResultIndex(output)]);
        sb.append(" [Network output: ");
        for (int i = 0; i < output.length; ++i) {
            sb.append(charactersMap[i]).append("  ");
            sb.append(Math.round(output[i] * 100)).append("% | ");
        }
        sb.append("]");

        return sb.toString();
    }

    private int getHighestResultIndex(double[] output) {
        int maxIndex = 0;

        for (int i = 1; i < output.length; ++i) {
            if (output[i] > output[maxIndex]) {
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}
