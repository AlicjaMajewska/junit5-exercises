package am.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssertionDemo {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void standardAssertions() {
        assertEquals(2, calculator.add(1, 1));
        assertEquals(4, calculator.add(2, 2),
                "The optional failure message is now the last parameter");
        assertTrue('a' < 'b', () -> "Assertion messages can be lazily evaluated -- "
                + "to avoid constructing complex messages unnecessarily.");
    }

    @Test
    void groupAssertions() {
        // In a grouped assertion all assertions are executed, and all failures will be reported together.
        assertAll("result",
                () -> assertEquals(3, calculator.add(2, 1)),
                () -> assertTrue(calculator.isNegative(-3))
                );
    }

    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("properties",
                () -> {
                    int sum = calculator.add(1,2);
                    assertNotNull(sum);

                    // Executed only if the previous assertion is valid.
                    assertAll("first name",
                            () -> assertEquals(3, sum),
                            () -> assertTrue(calculator.isPositive(sum))
                    );
                },
                () -> {
                    // Grouped assertion, so processed independently
                    // of results of first name assertions.

                    // Executed only if the previous assertion is valid.
                    assertAll("last name",
                            () -> assertTrue(true),
                            () -> assertTrue(true)
                    );
                }
        );
    }
}
