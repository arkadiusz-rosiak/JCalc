package pl.rosiakit.ocr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NetworkLearning {

    private static final Logger Log = LogManager.getLogger();


    private NeuralNetwork<BackPropagation> network;
    private DataSet dataSet;

    private int inputUnits;
    private int hiddenUnits;
    private int outputUnits;

    private NetworkLearning(int inputUnits, int hiddenUnits, int outputUnits) {
        this.inputUnits = inputUnits;
        this.hiddenUnits = hiddenUnits;
        this.outputUnits = outputUnits;

        createNeuralNetwork();
        prepareDataSet();
        learn();
        testNeuralNetwork();
    }

    public static void main(String[] args) {
        new NetworkLearning(784, 100, 17);
    }

    private void createNeuralNetwork() {
        network = new MultiLayerPerceptron(inputUnits, hiddenUnits, outputUnits);
    }

    private void prepareDataSet() {
        dataSet = new DataSet(inputUnits, outputUnits);

        try {
            fetchDataFromResource("train.txt");
        } catch (Exception e) {
            Log.error(e);
        }

    }

    private void fetchDataFromResource(String resourceName) throws IOException {
        Log.info("Przetwarzanie zbioru...");

        try (InputStream is = new FileInputStream(resourceName)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                dataSet.addRow(prepareDataSetRow(line));
            }
        }

    }

    private DataSetRow prepareDataSetRow(String line) {
        double[] input = getInputFromLine(line);
        double[] output = getOutputFromLine(line);
        return new DataSetRow(input, output);
    }

    private double[] getInputFromLine(String line) {

        String[] split = line.split(",");
        List<Double> values = new ArrayList<>();

        for(int i = 0; i < inputUnits; ++i){
            values.add(Double.parseDouble(split[i]));
        }

        return values.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private double[] getOutputFromLine(String line) {
        String[] split = line.split(",");
        List<Double> values = new ArrayList<>();

        for(int i = inputUnits; i < inputUnits+outputUnits; ++i){
            values.add(Double.parseDouble(split[i]));
        }

        return values.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private void learn() {

        Log.info("Uczenie...");

        BackPropagation rule = new BackPropagation();
        rule.setMaxError(0.01);

        network.setLearningRule(rule);

        int iteration = 0;
        do {

            rule.doOneLearningIteration(dataSet);
            ++iteration;

            Log.info(" Iteration: " + iteration + " Total Network Error: " + rule.getTotalNetworkError());
            network.save("network.nnet");

        }
        while (rule.getTotalNetworkError() > rule.getMaxError());

    }

    private void testNeuralNetwork() {

        try(InputStream is = new FileInputStream("network.nnet")) {
            network = NeuralNetwork.load(is);

            int line = 1;
            double errors = 0;
            for (DataSetRow dataRow : dataSet.getRows()) {

                network.setInput(dataRow.getInput());
                network.calculate();
                double[] networkOutput = network.getOutput();

                int desired = getHighestResultIndex(dataRow.getDesiredOutput());
                int real = getHighestResultIndex(networkOutput);


                if (desired != real) {
                    Log.info(line + " : " + desired + " -> " + real);
                    ++errors;
                }


                ++line;
            }


            Log.info("Correct " + (line - errors) + " of " + line + " which is " + ((line - errors) / line) * 100 + "%");
        }
        catch (Exception e){
            Log.error(e);
        }

    }

    private int getHighestResultIndex(double[] results){

        int maxIndex = 0;

        for(int i = 1; i < results.length; ++i){

            if(results[i] > results[maxIndex]){
                maxIndex = i;
            }
        }

        return maxIndex;
    }
}
