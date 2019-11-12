package am.calculator;

import java.util.stream.IntStream;

class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    boolean isPositive(int a) {
        return a > 0;
    }

    boolean isNegative(int a) {
        return a < 0;
    }

    static IntStream getAllDigits() {
        return IntStream.rangeClosed(0, 9);
    }

    int divide(int dividend, int divider) {
        return dividend / divider;
    }
}
