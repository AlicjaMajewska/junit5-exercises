package am.calculator;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagDemo {

    @Test
    @Tag("calculator")
    void testsAdding() {
        Calculator calculator = new Calculator();
        assertEquals(4, calculator.add(2, 2));
    }


    @Test
    @Tag("calculator")
    @Tag("division")
    void testsDivision() {
        Calculator calculator = new Calculator();
        assertEquals(1, calculator.divide(2, 2));
    }

}

