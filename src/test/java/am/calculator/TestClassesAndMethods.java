package am.calculator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestClassesAndMethods {

    // Test Class: any top-level class, static member class, or @Nested class that contains at least one test method.

    // Test classes must not be abstract and must have a single constructor.

    // Test Method: any instance method that is directly annotated or meta-annotated with @Test, @RepeatedTest, @ParameterizedTest, @TestFactory, or @TestTemplate.

    // Lifecycle Method: any method that is directly annotated or meta-annotated with @BeforeAll, @AfterAll, @BeforeEach, or @AfterEach.

    // Test methods and lifecycle methods may be declared locally within the current test class, inherited from superclasses, or inherited from interfaces.
    // In addition, test methods and lifecycle methods must not be abstract and must not return a value.

    @BeforeAll
    static void setUpAll() {
        System.out.println("set up all");
    }

    @BeforeEach
    void setUp() {
        System.out.println("set up");
    }

    @Test
    void testNr1() {
        System.out.println("test nr 1");
    }

    @Test
    void testNr2() {
        System.out.println("test nr 2");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tear down");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("tear down all");
    }
}
