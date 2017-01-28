package pl.rosiakit;

interface Operation {
    void execute(Num a, Num b);
}

class Num {

    private double value;

    Num(double value) {
        this.value = value;
    }

    double getValue() {
        return value;
    }

    @Override
    public String toString() {
        if ((value == Math.floor(value)) && !Double.isInfinite(value)) {
            return String.valueOf((int) value);
        } else {
            return String.valueOf(value);
        }
    }

}

class AdditionOperation implements Operation {

    private final CalculatorPresenter presenter;

    AdditionOperation(CalculatorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(Num a, Num b) {
        Num result = new Num(a.getValue() + b.getValue());
        presenter.printResult(result);
    }
}

class SubtractionOperation implements Operation {

    private final CalculatorPresenter presenter;

    SubtractionOperation(CalculatorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(Num a, Num b) {
        Num result = new Num(a.getValue() - b.getValue());
        presenter.printResult(result);
    }
}

class MultiplicationOperation implements Operation {

    private final CalculatorPresenter presenter;

    MultiplicationOperation(CalculatorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(Num a, Num b) {
        Num result = new Num(a.getValue() * b.getValue());
        presenter.printResult(result);
    }
}

class DivisionOperation implements Operation {

    private final CalculatorPresenter presenter;

    DivisionOperation(CalculatorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(Num a, Num b) {
        if (b.getValue() == 0) {
            throw new IllegalArgumentException("Nie możesz dzielić przez 0!");
        } else {
            Num result = new Num(a.getValue() / b.getValue());
            presenter.printResult(result);
        }
    }
}
