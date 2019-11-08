package am.calculator;

class CalculatorName {

    private String name;

    private CalculatorName(String name) {
        this.name = name;
    }

    static CalculatorName from(String nameValue) {
        return new CalculatorName(nameValue);
    }

    String name() {
        return name;
    }
}
