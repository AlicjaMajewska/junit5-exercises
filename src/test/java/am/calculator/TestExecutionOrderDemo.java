package am.calculator;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * To control the order in which test methods are executed, annotate your test class or test interface with
 * @TestMethodOrder and specify the desired MethodOrderer implementation. You can implement your own custom
 * MethodOrderer or use one of the following built-in MethodOrderer implementations.
 *
 * Alphanumeric: sorts test methods alphanumerically based on their names and formal parameter lists.
 *
 * OrderAnnotation: sorts test methods numerically based on values specified via the @Order annotation.
 *
 * Random: orders test methods pseudo-randomly and supports configuration of a custom seed.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestExecutionOrderDemo {
    @Test
    @Order(1)
    void nullValues() {
        // perform assertions against null values
    }

    @Test
    @Order(2)
    void emptyValues() {
        // perform assertions against empty values
    }

    @Test
    @Order(3)
    void validValues() {
        // perform assertions against valid values
    }
}
