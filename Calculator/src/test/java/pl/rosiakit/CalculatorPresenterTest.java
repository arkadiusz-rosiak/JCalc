package pl.rosiakit;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorPresenterTest {

    @Test
    public void shouldHandleMoreThanOneDigitOnScreen() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();

        presenter.numClicked(1);
        presenter.numClicked(2);
        Assert.assertEquals("12", presenter.getScreenString());
    }

    @Test
    public void shouldPrintIntegerIfResultIsInteger() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();

        presenter.numClicked(1);
        presenter.additionClicked();
        presenter.numClicked(2);
        presenter.resultClicked();
        Assert.assertEquals("3", presenter.getScreenString());
    }

    @Test
    public void shouldPrintFloatIfResultIsNotInteger() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();

        presenter.numClicked(1);
        presenter.divisionClicked();
        presenter.numClicked(2);
        presenter.resultClicked();
        Assert.assertEquals("0.5", presenter.getScreenString());
    }

    @Test
    public void screenShouldBeEmptyOnStart() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();
        Assert.assertEquals("", presenter.getScreenString());
    }

    @Test
    public void shouldSkipLeadingZeros() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();

        presenter.numClicked(0);
        presenter.numClicked(0);
        presenter.numClicked(1);
        presenter.numClicked(0);
        Assert.assertEquals("10", presenter.getScreenString());
    }

    @Test
    public void shouldClearScreenAfterOperationClicked() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();

        presenter.numClicked(2);
        presenter.additionClicked();
        Assert.assertEquals("", presenter.getScreenString());

        presenter.numClicked(2);
        presenter.multiplicationClicked();
        Assert.assertEquals("", presenter.getScreenString());

        presenter.numClicked(2);
        presenter.divisionClicked();
        Assert.assertEquals("", presenter.getScreenString());

        presenter.numClicked(2);
        presenter.subtractionClicked();
        Assert.assertEquals("", presenter.getScreenString());
    }


    @Test
    public void shouldTreatAnswerAsFirstParam() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();

        presenter.numClicked(2);
        presenter.additionClicked();
        presenter.numClicked(3);
        presenter.resultClicked();
        Assert.assertEquals("5", presenter.getScreenString());

        presenter.subtractionClicked();
        presenter.numClicked(1);
        presenter.resultClicked();
        Assert.assertEquals("4", presenter.getScreenString());

        presenter.multiplicationClicked();
        presenter.numClicked(3);
        presenter.resultClicked();
        Assert.assertEquals("12", presenter.getScreenString());

        presenter.divisionClicked();
        presenter.numClicked(3);
        presenter.resultClicked();
        Assert.assertEquals("4", presenter.getScreenString());
    }

    @Test
    public void shouldReplaceAnswerWithProvidedNumber() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();

        presenter.numClicked(2);
        presenter.additionClicked();
        presenter.numClicked(3);
        presenter.resultClicked();
        Assert.assertEquals("5", presenter.getScreenString());

        presenter.numClicked(2);
        presenter.numClicked(1);
        Assert.assertEquals("21", presenter.getScreenString());
    }

    @Test
    public void shouldCalculateChainedComputations() throws Exception {
        CalculatorPresenter presenter = new CalculatorPresenter();
        presenter.numClicked(2);
        presenter.additionClicked();
        presenter.numClicked(4);
        presenter.multiplicationClicked();
        presenter.numClicked(2);
        presenter.divisionClicked();
        presenter.numClicked(6);
        presenter.subtractionClicked();
        presenter.numClicked(5);
        presenter.resultClicked();

        Assert.assertEquals("-3", presenter.getScreenString());
    }

    @Test
    public void shouldChangeOperationWhenNeeded() throws Exception {

        CalculatorPresenter presenter = new CalculatorPresenter();

        presenter.numClicked(5);
        presenter.divisionClicked();
        presenter.multiplicationClicked();
        presenter.numClicked(6);
        presenter.resultClicked();

        Assert.assertEquals("30", presenter.getScreenString());
    }
}