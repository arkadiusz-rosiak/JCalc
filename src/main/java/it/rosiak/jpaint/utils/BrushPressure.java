package it.rosiak.jpaint.utils;

public class BrushPressure{

    private double currentPressure = 0.0;
    private double pressureStep;
    private double maxPressure;

    public BrushPressure(double pressureStep, double maxPressure) {
        this.pressureStep = pressureStep;
        this.maxPressure = maxPressure;
    }

    public double getCurrentPressure(){
        return currentPressure;
    }

    public void pushHarder(){
        currentPressure = Math.min( currentPressure + pressureStep, maxPressure);
    }

    public void reset() { currentPressure = 0.0; }

    public double getMaxPressure(){
        return maxPressure;
    }
}