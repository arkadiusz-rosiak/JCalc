package pl.rosiakit;

public class InlineInputCalculator {

    private final CalculatorPresenter presenter = new CalculatorPresenter();

    public void calculateFromString(String input){

        presenter.reset();

        for (char ch: input.toCharArray()) {

            if(Character.isDigit(ch)){
                int number = Character.getNumericValue(ch);
                presenter.numClicked(number);
            }
            else if(ch == '+'){
                presenter.additionClicked();
            }
            else if(ch == '-'){
                presenter.subtractionClicked();
            }
            else if(ch == '*'){
                presenter.multiplicationClicked();
            }
            else if(ch == '/'){
                presenter.divisionClicked();
            }
            else{
                presenter.resultClicked();
            }

        }

    }

    public String getResult(){
        return presenter.getScreenString();
    }
}
