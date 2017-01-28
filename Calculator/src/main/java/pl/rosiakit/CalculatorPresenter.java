package pl.rosiakit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class CalculatorPresenter {
    private static final Logger LOG = LogManager.getLogger();


    private StringProperty screen = new SimpleStringProperty("");

    private Num first;
    private Operation operation;
    private boolean isResultPrinted = false;

    CalculatorPresenter(){

    }

    String getScreenString() {
        return screen.getValueSafe();
    }

    void reset(){
        first = null;
        operation = null;
        screen.setValue("");
    }

    void numClicked(int finalI) {
        if (shouldReplaceScreenValue()) {
            screen.setValue(String.valueOf(finalI));
        } else {
            screen.setValue(screen.getValue() + String.valueOf(finalI));
        }

        isResultPrinted = false;
    }

    private boolean shouldReplaceScreenValue() {
        return isResultPrinted || screen.getValue().isEmpty() || "0".equals(screen.getValue());
    }

    void additionClicked() {
        calculateIfChainedOperation();
        setFirstOperand();
        operation = new AdditionOperation(this);
    }

    void multiplicationClicked() {
        calculateIfChainedOperation();
        setFirstOperand();
        operation = new MultiplicationOperation(this);
    }

    void subtractionClicked() {
        calculateIfChainedOperation();
        setFirstOperand();
        operation = new SubtractionOperation(this);
    }

    void divisionClicked() {
        calculateIfChainedOperation();
        setFirstOperand();
        operation = new DivisionOperation(this);
    }

    private void calculateIfChainedOperation() {
        if (operation != null) {
            resultClicked();
        }
    }

    private void setFirstOperand() {
        try {
            first = new Num(Double.parseDouble(screen.getValueSafe()));
            screen.setValue("");
        } catch (NumberFormatException e) {
            LOG.info("Cannot parse first operand.");
        }
    }

    void resultClicked() {
        try {
            Num second = new Num(Double.parseDouble(screen.getValueSafe()));
            operation.execute(first, second);
            operation = null;
        } catch (NumberFormatException e) {
            LOG.warn("Cannot parse second operand. Probably user selected one operation after the other. " + e);
        } catch (NullPointerException e) {
            LOG.warn("Operation has not been set yet. " + e);
        } catch (IllegalArgumentException e){
            screen.setValue(e.getMessage());
            LOG.warn(e);
        }
    }

    void printResult(Num answer) {
        screen.setValue(answer.toString());
        isResultPrinted = true;
    }

}
