package am.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
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

    @Test
    void exceptionTesting() {
        Exception exception = assertThrows(ArithmeticException.class, () ->
                calculator.divide(1, 0));
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void timeoutNotExceeded() {
        // The following assertion succeeds.
        assertTimeout(ofMinutes(2), () -> {
            // Perform task that takes less than 2 minutes.
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutExceeded() {
        // The following assertion fails with an error message similar to:
        // execution exceeded timeout of 10 ms by 91 ms
        assertTimeout(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    /**
     *
     * Preemptive Timeouts with assertTimeoutPreemptively()
     * Contrary to declarative timeouts, the various assertTimeoutPreemptively() methods in the Assertions class execute
     * the provided executable or supplier in a different thread than that of the calling code. This behavior can lead
     * to undesirable side effects if the code that is executed within the executable or supplier relies
     * on java.lang.ThreadLocal storage.
     *
     * One common example of this is the transactional testing support in the Spring Framework.
     * Specifically, Spring’s testing support binds transaction state to the current thread (via a ThreadLocal)
     * before a test method is invoked. Consequently, if an executable or supplier provided to assertTimeoutPreemptively()
     * invokes Spring-managed components that participate in transactions, any actions taken by those components
     * will not be rolled back with the test-managed transaction. On the contrary, such actions will be committed
     * to the persistent store (e.g., relational database) even though the test-managed transaction is rolled back.
     */
    @Test
    void timeoutExceededWithPreemptiveTermination() {
        // The following assertion fails with an error message similar to:
        // execution timed out after 10 ms
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }




}
