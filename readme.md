# JCalc

This project is based on **Java 8** + **JavaFX** + **Gradle**. It also uses [neuroph](https://github.com/neuroph/neuroph) library.

JCalc is my final project for the Advanced Java classes. It recognizes handwritten numbers and then calculates it. 
Initially it was supposed to be a distributed paint application (so src/main/design contains JPaint design), but with time requirements has changed and application evaluated into handwritten equations recognizer & calculator.

## Some in action screenshots
* http://prntscr.com/edac8j
* http://prntscr.com/edadnu
* http://prntscr.com/edaelu

## How it's made

There are three modules:

###Calculator module
Calculates value from string input. Made as regular homework during classes.

###OCR module
Recognizes numbers written in the picture (so the input is an image) and returns String with all recognized values. First the whole image is changed to Black&White version. Then it scan all horizontal lines to detect text lines. Then in every text line vertical lines scanning is performing to detect single characters. In next step all characters are scaling to 28x28px because it's the size of neural network input. Eventually pixels of every characters are sent to neural network and the output is saved to RecognitionResult object.

###Main module
It's the part that provides user interface and sends canvas snapshot to OCR module

## Other
* Scaling algorithm could be better, but bilinear algorithm is enough for downscaling and extreme fast
* Neural network should be trained more (Neural network is in digits.nnet file)